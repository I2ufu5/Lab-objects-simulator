package com.rbelcyr.kia.sol.Activities.Simulations.Balls;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.Formatter;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;
import com.rbelcyr.kia.sol.R;

public abstract class AbstractBallMachineActivity extends FragmentActivity implements AndroidFragmentApplication.Callbacks {

    protected AbstractModbusSlave modbusSlave;
    TextView ipText;
    protected AbstractBallFragment libgdxFragment;
    String ip;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_scene);

        //libgdxFragment  = new BallColorSortingFragment();
        ipText = (TextView) findViewById(R.id.ipText);

        try {
            modbusSlave.startSlaveListener();

            WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            ipText.setText(ip);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    try {
                        modbusSlave.setInput(0, libgdxFragment.scene.getColorSensorValue());
                        modbusSlave.setInput(1, libgdxFragment.scene.getBallSensorValue());
                    }catch (Exception e){

                    }

                    try {
                        if (modbusSlave.getAllCoils().get(3))
                            libgdxFragment.scene.openS4();
                        else
                            libgdxFragment.scene.closeS4();
                    } catch (Exception e) {

                    }


                    try {
                        if (modbusSlave.getAllCoils().get(2))
                            libgdxFragment.scene.openS3();
                        else
                            libgdxFragment.scene.closeS3();
                    } catch (Exception e) {

                    }

                    try {
                        if (modbusSlave.getAllCoils().get(1))
                            libgdxFragment.scene.openS2();
                        else
                            libgdxFragment.scene.closeS2();
                    } catch (Exception e) {

                    }

                    try {
                        if (modbusSlave.getAllCoils().get(0))
                            libgdxFragment.scene.openS1();
                        else
                            libgdxFragment.scene.closeS1();
                    } catch (Exception e) {

                    }
                }
            }
        }).start();

        getSupportFragmentManager().beginTransaction().
                add(R.id.game_frame, libgdxFragment).
                commit();
    }


    @Override
    public void exit() {
        modbusSlave.stopSlaveListener();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        modbusSlave.stopSlaveListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        modbusSlave.stopSlaveListener();
    }

}
