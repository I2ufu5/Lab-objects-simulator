package com.rbelcyr.kia.sol.Activities.Simulations.Balls;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rbelcyr.kia.sol.BallPatternScene;

public class BallColorPatternFragment extends AbstractBallFragment {

    public BallColorPatternFragment() {
        super.scene = new BallPatternScene();
    }

    /*
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return initializeForView(scene);
    }
     */
}
