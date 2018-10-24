package com.rbelcyr.kia.sol.Activities.Simulations.Balls;

import android.os.Bundle;
import android.support.annotation.Nullable;


public class BallColorPatternActivity extends AbstractBallMachineActivity{

    public BallColorPatternActivity(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.libgdxFragment = new BallColorPatternFragment();
        super.onCreate(savedInstanceState);
    }

}
