package com.rbelcyr.kia.sol.ModbusSlaves.BallSorting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.rbelcyr.kia.sol.ballSorting;

public class BallColorSortingFragment extends AndroidFragmentApplication{

    public ballSorting game;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        game = new ballSorting();
        // return the GLSurfaceView on which libgdx is drawing game stuff
        return initializeForView(game);
    }

}