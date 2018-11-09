package com.rbelcyr.kia.sol.Activities.Simulations.Balls;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rbelcyr.kia.sol.ModbusSlaves.BallMachineSlave;

public class BallColorSortingActivity extends AbstractBallMachineActivity{

    public BallColorSortingActivity(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.libgdxFragment = new BallColorSortingFragment();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void exit() {

    }
}
