package com.rbelcyr.kia.sol.Activities.Simulations.Balls;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.format.Formatter;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.rbelcyr.kia.sol.ModbusSlaves.Balls.BallColorPatternSlave;
import com.rbelcyr.kia.sol.ModbusSlaves.Balls.BallColorSortingSlave;
import com.rbelcyr.kia.sol.R;

public class BallColorSortingActivity extends AbstractBallMachineActivity{

    public BallColorSortingActivity(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.modbusSlave = new BallColorSortingSlave(getApplicationContext());
        super.libgdxFragment = new BallColorSortingFragment();
        super.onCreate(savedInstanceState);
    }

}
