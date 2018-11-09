package com.rbelcyr.kia.sol.Activities.Simulations;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rbelcyr.kia.sol.Dekorator.AbstractDekorator;
import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;

abstract public class AbstractActivity extends AppCompatActivity {

    private Thread modbusUpdater;
    private Handler uiUpdater;
    private AbstractModbusSlave modbusSlave;
    private AbstractDekorator dekorator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUiElements();
        setOnClickListeners();
        startActivity();

    }

    abstract protected void setOnClickListeners();

    abstract void initUiElements();

    abstract  void startUiUpdater();

    abstract void startModbusUpdater();

    private void startActivity(){
        startModbusUpdater();
        startUiUpdater();
    }

    private void stopUpdates(){
        modbusUpdater.interrupt();
        uiUpdater.removeCallbacksAndMessages(null);
    }

    @Override
    public void onPause(){
        super.onPause();
        stopUpdates();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopUpdates();
    }

}
