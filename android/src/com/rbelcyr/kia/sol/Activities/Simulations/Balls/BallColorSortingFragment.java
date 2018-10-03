package com.rbelcyr.kia.sol.Activities.Simulations.Balls;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.rbelcyr.kia.sol.ballPattern;
import com.rbelcyr.kia.sol.ballSorting;

public class BallColorSortingFragment extends AbstractBallFragment {

    public BallColorSortingFragment() {
        super.scene = new ballSorting();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return initializeForView(scene);
    }

}