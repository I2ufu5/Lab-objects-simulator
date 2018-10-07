package com.rbelcyr.kia.sol.Activities.Simulations;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rbelcyr.kia.sol.ModbusSlaves.HouseAlarmModbusSlave;
import com.rbelcyr.kia.sol.R;
import com.serotonin.modbus4j.exception.IllegalDataAddressException;


public class HouseAlarmActivity extends AppCompatActivity {

    private Button window1Button ;
    private Button window2Button ;
    private Button doorButton ;
    private Button armingButton ;
    private Button movementSensorButton;
    private TextView alarmLightTB;
    private TextView alarmSound;
    private TextView alarmOnOff;
    private ImageView window1,window2,door,buddy,lock;
    private Thread modbusUpdater;
    private Handler handler;
    private HouseAlarmModbusSlave houseAlarmModbusSlave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_alarm_sim);

        window1Button = (Button) findViewById(R.id.window1Button);
        window2Button = (Button) findViewById(R.id.window2Button);
        doorButton = (Button) findViewById(R.id.doorButton);
        armingButton = (Button) findViewById(R.id.armingButton);
        movementSensorButton = (Button) findViewById(R.id.movementSensorbutton);
        alarmLightTB = (TextView) findViewById(R.id.AlarmSwiatlo);
        alarmSound = (TextView) findViewById(R.id.alarmDzwiek);
        alarmOnOff = (TextView) findViewById(R.id.onOffAlarm);

        window1 = findViewById(R.id.window1_open);
        window2 = findViewById(R.id.window2_open);
        door = findViewById(R.id.door_open);
        buddy = findViewById(R.id.buddy);
        lock = findViewById(R.id.lock);

        handler = new Handler();
        houseAlarmModbusSlave = new HouseAlarmModbusSlave(getApplicationContext());

        try {
            houseAlarmModbusSlave.startSlaveListener();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
        protected void onPostCreate(@Nullable Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);
        startAlarmUiUpdater();
        setOnClickListeners();

    }

    private void setOnClickListeners(){
        window1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    houseAlarmModbusSlave.switchInput(0);
                    window1Button.setText(houseAlarmModbusSlave.getAllInputs().get(0).toString());
                } catch (IllegalDataAddressException e) {
                    e.printStackTrace();
                }
            }
        });

        window2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    houseAlarmModbusSlave.switchInput(1);
                    window2Button.setText(houseAlarmModbusSlave.getAllInputs().get(1).toString());
                } catch (IllegalDataAddressException e) {
                    e.printStackTrace();
                }
            }
        });

        doorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    houseAlarmModbusSlave.switchInput(2);
                    doorButton.setText(houseAlarmModbusSlave.getAllInputs().get(2).toString());
                } catch (IllegalDataAddressException e) {
                    e.printStackTrace();
                }
            }
        });

        movementSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    houseAlarmModbusSlave.switchInput(3);
                    movementSensorButton.setText(houseAlarmModbusSlave.getAllInputs().get(3).toString());
                } catch (IllegalDataAddressException e) {
                    e.printStackTrace();
                }
            }
        });

        armingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    houseAlarmModbusSlave.switchInput(4);
                    armingButton.setText(houseAlarmModbusSlave.getAllInputs().get(4).toString());
                } catch (IllegalDataAddressException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void startAlarmUiUpdater(){

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            try {
                                if(houseAlarmModbusSlave.getAllInputs().get(0))
                                    window1.setVisibility(View.VISIBLE);
                                else
                                    window1.setVisibility(View.INVISIBLE);
                            } catch (IllegalDataAddressException e) {
                                e.printStackTrace();
                            }

                            try {
                                if(houseAlarmModbusSlave.getAllInputs().get(1))
                                    window2.setVisibility(View.VISIBLE);
                                else
                                    window2.setVisibility(View.INVISIBLE);
                            } catch (IllegalDataAddressException e) {
                                e.printStackTrace();
                            }

                            try {
                                if(houseAlarmModbusSlave.getAllInputs().get(2))
                                    door.setVisibility(View.VISIBLE);
                                else
                                    door.setVisibility(View.INVISIBLE);
                            } catch (IllegalDataAddressException e) {
                                e.printStackTrace();
                            }

                            try {
                                if(houseAlarmModbusSlave.getAllInputs().get(3))
                                    buddy.setVisibility(View.VISIBLE);
                                else
                                    buddy.setVisibility(View.INVISIBLE);
                            } catch (IllegalDataAddressException e) {
                                e.printStackTrace();
                            }

                            try {
                                if(houseAlarmModbusSlave.getAllInputs().get(4))
                                    lock.setImageResource(R.drawable.lock_on);
                                else
                                    lock.setImageResource(R.drawable.lock_off);
                            } catch (IllegalDataAddressException e) {
                                e.printStackTrace();
                            }

                            handler.postDelayed(this,100);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        },100);

        modbusUpdater = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

                    //alarm panel
                    try {
                        if(houseAlarmModbusSlave.getAllCoils().get(0))
                            alarmLightTB.setBackgroundColor(getResources().getColor(R.color.alarmGreen));
                        else
                            alarmLightTB.setBackgroundColor(getResources().getColor(R.color.alarmRed));
                    } catch (IllegalDataAddressException e) {
                        e.printStackTrace();
                    }


                    try {
                        if(houseAlarmModbusSlave.getAllCoils().get(1))
                            alarmSound.setBackgroundColor(getResources().getColor(R.color.alarmGreen));
                        else
                            alarmSound.setBackgroundColor(getResources().getColor(R.color.alarmRed));
                    } catch (IllegalDataAddressException e) {
                        e.printStackTrace();
                    }

                    try {
                        if(houseAlarmModbusSlave.getAllCoils().get(2))
                            alarmOnOff.setBackgroundColor(getResources().getColor(R.color.alarmGreen));
                        else
                            alarmOnOff.setBackgroundColor(getResources().getColor(R.color.alarmRed));
                    } catch (IllegalDataAddressException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        modbusUpdater.start();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
            houseAlarmModbusSlave.stopSlaveListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        houseAlarmModbusSlave.stopSlaveListener();
    }
}
