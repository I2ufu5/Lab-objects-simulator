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
    Handler handler;
    boolean handlerRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_lights);

        trafficLightsModbusSlave = new TrafficLightsModbusSlave(getApplicationContext());
        trafficLightsController = new TrafficLightsController(this.getWindow().getDecorView().getRootView());
        handler = new Handler();

        try {
            trafficLightsModbusSlave.startSlaveListener();

        } catch (Exception e) {
            e.printStackTrace();
        }


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handlerRunning = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            trafficLightsController.update(trafficLightsModbusSlave.getAllRegisters());

                            handler.postDelayed(this,50);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        },50);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stop();
    }

    private void stop(){
        handler.removeCallbacksAndMessages(null);
        trafficLightsModbusSlave.stopSlaveListener();

    }
}
