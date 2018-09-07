package com.rbelcyr.kia.sol.Activities.Simulations.HeatPlant;

import android.os.Handler;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class HeatPlant {
    private float temperature;
    private float voltage;
    private float fanRpm;
    private Handler handler;

    private static float INPUT_TO_VOLTAGE = 0.224609375f;
    private static float INPUT_TO_FANRPM = 2.44140625f;
    private static float INPUT_TO_TEMPERATURE = 0.09765625f;


    public HeatPlant(){
        temperature = 20.0f;
        voltage = 0;
        fanRpm = 0;
        handler = new Handler();
    }



    public void setVoltage(short inputSignalVoltage) {
        this.voltage = inputSignalVoltage*INPUT_TO_VOLTAGE;
    }

    public short getVoltage(){
        return (short) (voltage/INPUT_TO_VOLTAGE);
    }

    public void setFanRpm(short fanRpm) {
        this.fanRpm = fanRpm*INPUT_TO_FANRPM;
    }

    public short getFanRpm() {
        return (short) (fanRpm/INPUT_TO_FANRPM);
    }

    public short getTemperature() {
        return (short) (temperature/INPUT_TO_TEMPERATURE);
    }

    public float getRealVoltage(){
        return voltage;
    }

    public float getRealFanRpm(){return fanRpm;}

    public float getRealTemperature(){return temperature;}

    private void compute(){
        temperature = temperature + voltage/5000 + fanRpm/50000;
    }

    public void start(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                compute();
                handler.postDelayed(this,50);
            }
        },50);
    }
}
