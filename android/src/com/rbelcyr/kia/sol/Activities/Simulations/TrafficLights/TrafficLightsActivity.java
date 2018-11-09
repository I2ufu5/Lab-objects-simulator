package com.rbelcyr.kia.sol.Activities.Simulations.TrafficLights;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.rbelcyr.kia.sol.Dekorator.DekoratorStreetLights;
import com.rbelcyr.kia.sol.ModbusSlaves.TrafficLightsSlave;
import com.rbelcyr.kia.sol.R;

public class TrafficLightsActivity extends AppCompatActivity {

    TrafficLightsController trafficLightsController;
    TrafficLightsSlave modbusSlave;
    Handler handler;
    boolean handlerRunning;
    DekoratorStreetLights dekorator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_lights);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        modbusSlave = new TrafficLightsSlave(getApplicationContext());
        dekorator = new DekoratorStreetLights(this,modbusSlave);
        dekorator.initUiElements();
        trafficLightsController = new TrafficLightsController(this.getWindow().getDecorView().getRootView());


        handler = new Handler();

        try {
            modbusSlave.startSlaveListener();
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
                            trafficLightsController.update(modbusSlave.getAllRegisters());
                            dekorator.update();
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
        modbusSlave.stopSlaveListener();

    }
}
