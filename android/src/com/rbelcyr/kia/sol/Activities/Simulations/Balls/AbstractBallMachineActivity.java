package com.rbelcyr.kia.sol.Activities.Simulations.Balls;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.Formatter;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.rbelcyr.kia.sol.Activities.MainActivity;
import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;
import com.rbelcyr.kia.sol.R;

public abstract class AbstractBallMachineActivity extends FragmentActivity implements AndroidFragmentApplication.Callbacks {

    protected AbstractModbusSlave modbusSlave;
    protected AbstractBallFragment libgdxFragment;
    Thread thread;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_scene);

        try {
            modbusSlave.startSlaveListener();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread = new Thread(new Runnable() {
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
        });
        thread.start();

        getSupportFragmentManager().beginTransaction().
                add(R.id.game_frame, libgdxFragment).
                commit();
    }

    @Override
    public void exit() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stop();
    }

    private void stop(){
        thread.interrupt();
        modbusSlave.stopSlaveListener();
        exit();
    }
}
