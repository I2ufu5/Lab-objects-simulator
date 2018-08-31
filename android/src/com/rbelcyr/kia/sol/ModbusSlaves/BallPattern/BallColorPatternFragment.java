package com.rbelcyr.kia.sol.ModbusSlaves.BallPattern;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.rbelcyr.kia.sol.ModbusSlaves.AbstractBallFragment;
import com.rbelcyr.kia.sol.ballPattern;
import com.rbelcyr.kia.sol.ballSorting;

public class BallColorPatternFragment extends AbstractBallFragment {

    public BallColorPatternFragment() {
        super.scene = new ballPattern();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return initializeForView(scene);
    }

}
