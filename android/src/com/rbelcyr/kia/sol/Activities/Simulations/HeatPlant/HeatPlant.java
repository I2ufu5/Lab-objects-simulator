package com.rbelcyr.kia.sol.Activities.Simulations.HeatPlant;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;


public class HeatPlant {
    private float outputTemperature;
    private float voltage;
    private float inputFlow;
    private Handler handler;
    public HeatPlantODE ode;
    private FirstOrderIntegrator dp853;
    private double odeTime = 0.0;
    private double timeStep = 0.001;
    private double y0[] = {60};

    private static float INPUT_TO_VOLTAGE = 0.224609375f;
    private static float INPUT_TO_FLOW = 0.001953125f;
    private static float INPUT_TO_TEMPERATURE = 0.09765625f;


    public HeatPlant(Activity activity){
        outputTemperature = 20.0f;
        voltage = 0;
        inputFlow = 0;
        handler = new Handler();
        dp853 = new DormandPrince853Integrator(1.0e-8,
                100.0,
                1.0e-10,
                1.0e-10);

        ode = new HeatPlantODE(activity);
    }

    public void setVoltage(short inputSignalVoltage) {
        this.voltage = inputSignalVoltage*INPUT_TO_VOLTAGE;
    }

    public short getVoltage(){
        return (short) (voltage/INPUT_TO_VOLTAGE);
    }

    public void setInputFlow(short inputFlow) {
        this.inputFlow = inputFlow * INPUT_TO_FLOW;
    }

    public short getInputFlow() {
        return (short) (inputFlow / INPUT_TO_FLOW);
    }

    public short getTemperature() {
        return (short) (outputTemperature / INPUT_TO_TEMPERATURE);
    }

    public float getRealVoltage(){
        return voltage;
    }

    public float getRealInputFlow(){return inputFlow;}

    public float getRealTemperature(){return outputTemperature;}

    private void compute(){
        //outputTemperature = outputTemperature + voltage/500 - inputFlow/5000;
        ode.U = voltage;
        ode.q = inputFlow;
        dp853.integrate(ode,odeTime,y0,odeTime+timeStep,y0);
        outputTemperature = (float)y0[0];
        //Log.e("REAL: ", String.valueOf(outputTemperature));
        //Log.e("DIGITAL: ", String.valueOf(getTemperature()));

        odeTime += timeStep;
    }

    public void start(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                compute();
                //Log.e("COMPUTING: ","F="+String.valueOf(inputFlow)+"|V:"+String.valueOf(voltage)+"|T="+String.valueOf(outputTemperature));
                handler.postDelayed(this,20);
            }
        },20);
    }

    public void stop(){
        handler.removeCallbacksAndMessages(null);
    }
}
