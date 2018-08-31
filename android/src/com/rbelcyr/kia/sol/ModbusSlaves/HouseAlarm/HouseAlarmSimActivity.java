package com.rbelcyr.kia.sol.ModbusSlaves.HouseAlarm;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rbelcyr.kia.sol.R;
import com.serotonin.modbus4j.exception.IllegalDataAddressException;


public class HouseAlarmSimActivity extends AppCompatActivity {

    private Button window1Button ;
    private Button window2Button ;
    private Button doorButton ;
    private Button armingButton ;
    private Button movementSensorButton;
    private TextView alarmLightTB;
    private TextView alarmSound;
    private TextView alarmOnOff;
    final HouseAlarmModbusSlave houseAlarmModbusSlave = new HouseAlarmModbusSlave();

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

        try {
            houseAlarmModbusSlave.startSlaveListener();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

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
        }).start();

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

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        try {
            houseAlarmModbusSlave.stopSlaveListener();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
