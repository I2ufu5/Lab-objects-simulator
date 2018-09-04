package com.rbelcyr.kia.sol.Activities.Simulations.HeatObject;

public class HeatObject {
    private float temperature;
    private float inputSignal;


    public void setInputSignal(float inputSignal) {
        this.inputSignal = inputSignal;
    }

    public float getInputSignal(){
        return inputSignal;
    }

    public void start(){
        Thread thread = new Thread();
    }
}
