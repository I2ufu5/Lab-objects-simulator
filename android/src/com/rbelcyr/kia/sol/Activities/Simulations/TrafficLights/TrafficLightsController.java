package com.rbelcyr.kia.sol.Activities.Simulations.TrafficLights;

import android.view.View;
import android.widget.ImageView;

import com.rbelcyr.kia.sol.R;

import java.util.ArrayList;

public class TrafficLightsController {

    private ArrayList<TrafficLight> trafficLights;
    private View view;

    public TrafficLightsController(View view){
        trafficLights = new ArrayList<>();
        this.view = view;
        add();
    }
    public void add(){
        addCarsHorizontal();
        addCarsVertical();
        addPedsHorizontal();
        addPedsVertical();

    }

    public void set(int number,boolean green, boolean yellow, boolean red){
        for(TrafficLight tf:trafficLights){
            if(tf.getNumber() == number) {
                tf.setGreen(green);
                tf.setYellow(yellow);
                tf.setRed(red);
            }
        }

    }

    private void addCarsVertical(){
        trafficLights.add(
                new TrafficLight(
                        1,
                        (ImageView) view.findViewById(R.id.tf_up_1_g),
                        (ImageView) view.findViewById(R.id.tf_up_1_y),
                        (ImageView) view.findViewById(R.id.tf_up_1_r)
                )
        );

        trafficLights.add(
                new TrafficLight(
                        2,
                        (ImageView) view.findViewById(R.id.tf_up_2_g),
                        (ImageView) view.findViewById(R.id.tf_up_2_y),
                        (ImageView) view.findViewById(R.id.tf_up_2_r)
                )
        );

        trafficLights.add(
                new TrafficLight(
                        3,
                        (ImageView) view.findViewById(R.id.tf_up_3_g),
                        (ImageView) view.findViewById(R.id.tf_up_3_y),
                        (ImageView) view.findViewById(R.id.tf_up_3_r)
                )
        );

        trafficLights.add(
                new TrafficLight(
                        1,
                        (ImageView) view.findViewById(R.id.tf_down_1_g),
                        (ImageView) view.findViewById(R.id.tf_down_1_y),
                        (ImageView) view.findViewById(R.id.tf_down_1_r)
                )
        );

        trafficLights.add(
                new TrafficLight(
                        2,
                        (ImageView) view.findViewById(R.id.tf_down_2_g),
                        (ImageView) view.findViewById(R.id.tf_down_2_y),
                        (ImageView) view.findViewById(R.id.tf_down_2_r)
                )
        );

        trafficLights.add(
                new TrafficLight(
                        3,
                        (ImageView) view.findViewById(R.id.tf_down_3_g),
                        (ImageView) view.findViewById(R.id.tf_down_3_y),
                        (ImageView) view.findViewById(R.id.tf_down_3_r)
                )
        );
    }
    private void addCarsHorizontal(){
        trafficLights.add(
                new TrafficLight(
                        4,
                        (ImageView) view.findViewById(R.id.tf_left_1_g),
                        (ImageView) view.findViewById(R.id.tf_left_1_y),
                        (ImageView) view.findViewById(R.id.tf_left_1_r)
                )
        );

        trafficLights.add(
                new TrafficLight(
                        5,
                        (ImageView) view.findViewById(R.id.tf_left_2_g),
                        (ImageView) view.findViewById(R.id.tf_left_2_y),
                        (ImageView) view.findViewById(R.id.tf_left_2_r)
                )
        );

        trafficLights.add(
                new TrafficLight(
                        6,
                        (ImageView) view.findViewById(R.id.tf_left_3_g),
                        (ImageView) view.findViewById(R.id.tf_left_3_y),
                        (ImageView) view.findViewById(R.id.tf_left_3_r)
                )
        );

        trafficLights.add(
                new TrafficLight(
                        4,
                        (ImageView) view.findViewById(R.id.tf_right_1_g),
                        (ImageView) view.findViewById(R.id.tf_right_1_y),
                        (ImageView) view.findViewById(R.id.tf_right_1_r)
                )
        );

        trafficLights.add(
                new TrafficLight(
                        5,
                        (ImageView) view.findViewById(R.id.tf_right_2_g),
                        (ImageView) view.findViewById(R.id.tf_right_2_y),
                        (ImageView) view.findViewById(R.id.tf_right_2_r)
                )
        );

        trafficLights.add(
                new TrafficLight(
                        6,
                        (ImageView) view.findViewById(R.id.tf_right_3_g),
                        (ImageView) view.findViewById(R.id.tf_right_3_y),
                        (ImageView) view.findViewById(R.id.tf_right_3_r)
                )
        );
    }
    private void addPedsVertical(){
        trafficLights.add(
                new TrafficLight(
                        7,
                        (ImageView) view.findViewById(R.id.peds_down_g),
                        null,
                        (ImageView) view.findViewById(R.id.peds_down_r)
                )
        );

        trafficLights.add(
                new TrafficLight(
                        7,
                        (ImageView) view.findViewById(R.id.peds_up_g),
                        null,
                        (ImageView) view.findViewById(R.id.peds_up_r)
                )
        );
    }
    private void addPedsHorizontal(){
        trafficLights.add(
                new TrafficLight(
                        8,
                        (ImageView) view.findViewById(R.id.peds_left_g),
                        null,
                        (ImageView) view.findViewById(R.id.peds_left_r)
                )
        );

        trafficLights.add(
                new TrafficLight(
                        8,
                        (ImageView) view.findViewById(R.id.peds_right_g),
                        null,
                        (ImageView) view.findViewById(R.id.peds_right_r)
                )
        );
    }

    public void update(ArrayList<Short> registers){
        set(
                1,
                maskBits(1,registers.get(0)),
                maskBits(2,registers.get(0)),
                maskBits(4,registers.get(0))
        );

        set(
                2,
                maskBits(8,registers.get(0)),
                maskBits(16,registers.get(0)),
                maskBits(32,registers.get(0))
        );

        set(
                3,
                maskBits(64,registers.get(0)),
                maskBits(128,registers.get(0)),
                maskBits(256,registers.get(0))
        );

        set(
                4,
                maskBits(1,registers.get(1)),
                maskBits(2,registers.get(1)),
                maskBits(4,registers.get(1))
        );

        set(
                5,
                maskBits(8,registers.get(1)),
                maskBits(16,registers.get(1)),
                maskBits(32,registers.get(1))
        );

        set(
                6,
                maskBits(64,registers.get(1)),
                maskBits(128,registers.get(1)),
                maskBits(256,registers.get(1))
        );


        set(
                8,
                maskBits(1,registers.get(2)),
                maskBits(0,registers.get(2)),
                maskBits(4,registers.get(2))
        );

        set(
                7,
                maskBits(8,registers.get(2)),
                maskBits(0,registers.get(2)),
                maskBits(32,registers.get(2))
        );

    }

    private static boolean maskBits(int mask, short register){
        //Log.e("MASKING BITS", String.valueOf(register)+"&"+String.valueOf( (short) mask)+"="+String.valueOf(((register & (short) mask))));
        return ((register & (short) mask) != 0);
    }
}
