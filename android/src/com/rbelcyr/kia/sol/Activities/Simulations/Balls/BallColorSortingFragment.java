package com.rbelcyr.kia.sol.Activities.Simulations.Balls;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rbelcyr.kia.sol.BallSortingScene;

public class BallColorSortingFragment extends AbstractBallFragment {

    public BallColorSortingFragment() {
        super.scene = new BallSortingScene();
    }

    /*
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return initializeForView(scene);
    }
    */

}