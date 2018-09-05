package com.rbelcyr.kia.sol.Activities.Simulations.StreetLights;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class TrafficLight {
    private ImageView green;
    private ImageView yellow;
    private ImageView red;

    private int number;

    public TrafficLight(int number,ImageView green,ImageView yellow,ImageView red) {
        this.number = number;
        this.green = green;
        this.yellow = yellow;
        this.red = red;
    }

    public void setGreen(boolean state) {
        if(state == true)
            green.setVisibility(View.VISIBLE);
        else
            green.setVisibility(View.INVISIBLE);

    }

    public void setYellow(boolean state)throws NullPointerException{
            if(state == true)
                yellow.setVisibility(View.VISIBLE);
            else
                yellow.setVisibility(View.INVISIBLE);
    }

    public void setRed(boolean state) {
        if(state == true)
            red.setVisibility(View.VISIBLE);
        else
            red.setVisibility(View.INVISIBLE);
    }

    public int getNumber() {
        return number;
    }

}
