package com.rbelcyr.kia.sol.Activities.Simulations.Balls;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.Formatter;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.rbelcyr.kia.sol.ModbusSlaves.Balls.BallColorSortingSlave;
import com.rbelcyr.kia.sol.R;

public class BallColorSortingActivity extends FragmentActivity implements AndroidFragmentApplication.Callbacks{

	final BallColorSortingSlave ballColorSortingSlave = new BallColorSortingSlave();
	TextView ipText;
	BallColorSortingFragment libgdxFragment;
    String ip;

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ball_scene);

		libgdxFragment  = new BallColorSortingFragment();
		ipText = (TextView) findViewById(R.id.ipText);

        try {
            ballColorSortingSlave.startSlaveListener();

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
                            ballColorSortingSlave.setInput(0, libgdxFragment.game.getColorSensorValue());
                            ballColorSortingSlave.setInput(1, libgdxFragment.game.getBallSensorValue());
                        }catch (Exception e){

                        }

                        try {
                            if (ballColorSortingSlave.getAllCoils().get(3))
                                libgdxFragment.game.openS4();
                            else
                                libgdxFragment.game.closeS4();
                        } catch (Exception e) {

                        }


                        try {
                            if (ballColorSortingSlave.getAllCoils().get(2))
                                libgdxFragment.game.openS3();
                            else
                                libgdxFragment.game.closeS3();
                        } catch (Exception e) {

                        }

                        try {
                            if (ballColorSortingSlave.getAllCoils().get(1))
                                libgdxFragment.game.openS2();
                            else
                                libgdxFragment.game.closeS2();
                        } catch (Exception e) {

                        }

                        try {
                            if (ballColorSortingSlave.getAllCoils().get(0))
                                libgdxFragment.game.openS1();
                            else
                                libgdxFragment.game.closeS1();
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
        ballColorSortingSlave.stopSlaveListener();
	}

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        ballColorSortingSlave.stopSlaveListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ballColorSortingSlave.stopSlaveListener();
    }

}
