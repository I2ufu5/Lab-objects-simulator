package com.rbelcyr.kia.sol.Activities.Simulations.Balls;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.rbelcyr.kia.sol.AbstractBallMachineScene;

public abstract class AbstractBallFragment extends AndroidFragmentApplication {

    public AbstractBallMachineScene scene;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return initializeForView(scene);
    }

    @Override
    public void onStop() {
        super.onStop();
        //scene.dispose();
    }
}
