package com.rbelcyr.kia.sol.Activities.Simulations.HeatPlant;

import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.rbelcyr.kia.sol.ModbusSlaves.HeatPlantModbusSlave;
import com.rbelcyr.kia.sol.R;

public class HeatPlantActivity extends AppCompatActivity {

    HeatPlantDAO heatPlantGraph;
    HeatPlantModbusSlave modbusSlave;
    HeatPlant heatPlant;
    GraphView graph;
    Handler handler;
    Thread thread;


    ImageView heater1,heater2;

    TextView voltageView;
    ProgressBar voltageProgressBar;

    TextView flowView;
    ProgressBar flowProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_object);

        heater1 = findViewById(R.id.heater1);
        heater2 = findViewById(R.id.heater2);
        voltageView = findViewById(R.id.voltageView);
        voltageProgressBar = findViewById(R.id.voltageProgresBar);
        flowView = findViewById(R.id.flowView);
        flowProgressBar = findViewById(R.id.flowProgresBar);

        graph = (GraphView) findViewById(R.id.chart);
        handler = new Handler();

        heatPlant = new HeatPlant();
        heatPlant.start();

        heatPlantGraph = new HeatPlantDAO(graph,heatPlant);
        heatPlantGraph.setFormat(getApplicationContext());

        modbusSlave = new HeatPlantModbusSlave(getApplicationContext());

        try {
            modbusSlave.startSlaveListener();
            modbusSlave.setRegister(1,(short)350);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        startModbusUpdate();
        startUiUpdate();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stop();
    }


    private void stop(){
        handler.removeCallbacksAndMessages(null);
        modbusSlave.stopSlaveListener();
        heatPlant.stop();
    }

    private void startModbusUpdate(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        heatPlant.setInputFlow(modbusSlave.getAllRegisters().get(1));
                        heatPlant.setVoltage(modbusSlave.getAllRegisters().get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        thread.start();
    }

    private void startUiUpdate(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            heatPlantGraph.draw();

                            heater1.setAlpha((int)heatPlant.getRealVoltage());
                            heater2.setAlpha((int)heatPlant.getRealVoltage());
                            voltageView.setText("[V] " + String.valueOf((int)heatPlant.getRealVoltage()));
                            voltageProgressBar.setProgress((int)heatPlant.getRealVoltage());

                            flowView.setText("[m3/s] " + String.format("%.2f",heatPlant.getRealInputFlow()));
                            flowProgressBar.setProgress((int)(heatPlant.getRealInputFlow()*50));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                handler.postDelayed(this,20);
            }
        },20);
    }
}
