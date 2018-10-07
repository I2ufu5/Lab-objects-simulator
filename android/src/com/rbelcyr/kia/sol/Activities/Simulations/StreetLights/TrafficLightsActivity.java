package com.rbelcyr.kia.sol.Activities.Simulations.StreetLights;

import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.util.TimeFormatException;
import android.view.View;
import android.widget.TextView;

import com.rbelcyr.kia.sol.ModbusSlaves.TrafficLightsModbusSlave;
import com.rbelcyr.kia.sol.R;

import java.util.Timer;
import java.util.TimerTask;

public class TrafficLightsActivity extends AppCompatActivity {

    TrafficLightsController trafficLightsController;
    TrafficLightsModbusSlave trafficLightsModbusSlave;
    TextView ipText,register1,register2,register3;
    String ip;
    Handler handler;
    boolean handlerRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_lights);

        trafficLightsModbusSlave = new TrafficLightsModbusSlave(getApplicationContext());
        trafficLightsController = new TrafficLightsController(this.getWindow().getDecorView().getRootView());
        ipText = (TextView) findViewById(R.id.ipText);
        register1 = (TextView) findViewById(R.id.register1);
        register2 = (TextView) findViewById(R.id.register2);
        register3 = (TextView) findViewById(R.id.register3);
        handler = new Handler();

        try {
            trafficLightsModbusSlave.startSlaveListener();

            WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            ipText.setText(ip);

        } catch (Exception e) {
            //e.printStackTrace();
        }


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handlerRunning = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            register1.setText(String.valueOf(trafficLightsModbusSlave.getAllRegisters().get(0)));
                            register2.setText(String.valueOf(trafficLightsModbusSlave.getAllRegisters().get(1)));
                            register3.setText(String.valueOf(trafficLightsModbusSlave.getAllRegisters().get(2)));

                            trafficLightsController.update(trafficLightsModbusSlave.getAllRegisters());

                            if(!handlerRunning){
                                throw new Exception("OnBackPressedStopper");
                            }

                            handler.postDelayed(this,100);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        },100);

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        trafficLightsModbusSlave.stopSlaveListener();
        handlerRunning = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        trafficLightsModbusSlave.stopSlaveListener();
    }
}
