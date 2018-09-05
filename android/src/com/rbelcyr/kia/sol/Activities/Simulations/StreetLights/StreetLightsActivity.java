package com.rbelcyr.kia.sol.Activities.Simulations.StreetLights;

import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;

import com.rbelcyr.kia.sol.ModbusSlaves.TrafficLightsModbusSlave;
import com.rbelcyr.kia.sol.R;

public class StreetLightsActivity extends AppCompatActivity {

    TrafficLightsController trafficLightsController;
    TrafficLightsModbusSlave trafficLightsModbusSlave;
    TextView ipText;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_lights);

        ipText = (TextView) findViewById(R.id.ipText);
        trafficLightsModbusSlave = new TrafficLightsModbusSlave();
        trafficLightsController = new TrafficLightsController(this.getWindow().getDecorView().getRootView());


        try {
            trafficLightsModbusSlave.startSlaveListener();

            WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            ipText.setText(ip);

        } catch (Exception e) {
            //e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        trafficLightsController.update(trafficLightsModbusSlave.getAllRegisters());
                    }catch (Exception e){
                        //e.printStackTrace();
                    }
                }
            }
        }).start();
    }


}
