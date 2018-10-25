package com.rbelcyr.kia.sol.Activities.Simulations.Balls;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.rbelcyr.kia.sol.Activities.MainActivity;
import com.rbelcyr.kia.sol.Dekorator.DekoratorBallScene;
import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;
import com.rbelcyr.kia.sol.ModbusSlaves.BallMachineSlave;
import com.rbelcyr.kia.sol.R;

public abstract class AbstractBallMachineActivity extends AppCompatActivity implements AndroidFragmentApplication.Callbacks {

    protected AbstractModbusSlave modbusSlave;
    protected AbstractBallFragment libgdxFragment;
    Thread thread;
    Handler handler;
    DekoratorBallScene dekorator;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_scene);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        modbusSlave = new BallMachineSlave(getApplicationContext());

        libgdxFragment.scene.setTimeStep(Float.parseFloat(PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext())
                .getString("simSpeed","0.01666666")));

        handler = new Handler();

        try {
            modbusSlave.startSlaveListener();

            getSupportFragmentManager().beginTransaction().
                    add(R.id.game_frame, libgdxFragment).
                    commit();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dekorator = new DekoratorBallScene(this,modbusSlave);
        dekorator.initUiElements();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    try {
                            modbusSlave.setInput(0, libgdxFragment.scene.getColorSensorValue());
                            modbusSlave.setInput(1, libgdxFragment.scene.getBallSensorValue());

                        if (modbusSlave.getAllCoils().get(3))
                            libgdxFragment.scene.openS4();
                        else libgdxFragment.scene.closeS4();


                        if (modbusSlave.getAllCoils().get(2))
                            libgdxFragment.scene.openS3();
                        else libgdxFragment.scene.closeS3();


                        if (modbusSlave.getAllCoils().get(1))
                            libgdxFragment.scene.openS2();
                        else libgdxFragment.scene.closeS2();

                        if (modbusSlave.getAllCoils().get(0))
                            libgdxFragment.scene.openS1();
                        else libgdxFragment.scene.closeS1();

                    }catch (Exception e){
                        Log.d("modbus+fragment Update: ", e.toString());
                    }

                }
            }
        });
        thread.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            dekorator.update();
                        } catch (Exception e) {
                            Log.e("uiUpdate",e.toString());
                        }
                    }
                });
                handler.postDelayed(this,20);
            }
        },20);

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
        handler.removeCallbacksAndMessages(null);
        exit();
    }
}
