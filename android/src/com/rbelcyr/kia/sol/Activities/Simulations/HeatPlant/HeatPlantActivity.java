package com.rbelcyr.kia.sol.Activities.Simulations.HeatPlant;

import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.rbelcyr.kia.sol.ModbusSlaves.HeatPlantModbusSlave;
import com.rbelcyr.kia.sol.R;
import com.serotonin.modbus4j.exception.IllegalDataAddressException;

import java.util.ArrayList;

public class HeatPlantActivity extends AppCompatActivity {

    HeatPlantDAO heatPlantGraph;
    HeatPlantModbusSlave modbusSlave;
    HeatPlant heatPlant;
    GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_object);
        TextView ipText = (TextView) findViewById(R.id.ipText);

        graph = (GraphView) findViewById(R.id.chart);

        heatPlant = new HeatPlant();
        heatPlant.start();

        heatPlantGraph = new HeatPlantDAO(graph,heatPlant);
        heatPlantGraph.setFormat();
        heatPlantGraph.start();

        modbusSlave = new HeatPlantModbusSlave();

        try {
            modbusSlave.startSlaveListener();
            modbusSlave.setRegister(0,(short)512);

            WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            ipText.setText(ip);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    heatPlant.setFanRpm(modbusSlave.getAllRegisters().get(0));
                    heatPlant.setVoltage(modbusSlave.getAllRegisters().get(1));
                } catch (IllegalDataAddressException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
