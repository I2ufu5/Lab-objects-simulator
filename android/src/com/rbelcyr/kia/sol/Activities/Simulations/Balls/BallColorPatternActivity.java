package com.rbelcyr.kia.sol.Activities.Simulations.Balls;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rbelcyr.kia.sol.ModbusSlaves.Balls.BallColorPatternSlave;


public class BallColorPatternActivity extends AbstractBallMachineActivity{

    public BallColorPatternActivity(){
        //super.modbusSlave = new BallColorPatternSlave(getApplicationContext());
        //super.libgdxFragment = new BallColorPatternFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.modbusSlave = new BallColorPatternSlave(getApplicationContext());
        super.libgdxFragment = new BallColorPatternFragment();
        super.onCreate(savedInstanceState);
    }

}
