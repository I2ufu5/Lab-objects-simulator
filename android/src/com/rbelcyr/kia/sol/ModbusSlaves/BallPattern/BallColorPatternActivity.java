package com.rbelcyr.kia.sol.ModbusSlaves.BallPattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.rbelcyr.kia.sol.ModbusSlaves.AbstractBallMachineActivity;


public class BallColorPatternActivity extends AbstractBallMachineActivity{

    public BallColorPatternActivity(){
        super.modbusSlave = new BallColorPatternSlave();
        super.libgdxFragment = new BallColorPatternFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void exit() {

    }
}
