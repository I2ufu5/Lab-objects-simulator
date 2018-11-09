package com.rbelcyr.kia.sol.Activities.Simulations;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rbelcyr.kia.sol.Dekorator.DekoratorHouseAlarm;
import com.rbelcyr.kia.sol.ModbusSlaves.HouseAlarmSlave;
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
    private ImageView window1,window2,door, movementSensor,lock;
    private Thread modbusUpdater;
    private Handler handler;
    private HouseAlarmSlave modbusSlave;
    private DekoratorHouseAlarm dekorator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_alarm);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        window1Button =  findViewById(R.id.window1Button);
        window2Button =  findViewById(R.id.window2Button);
        doorButton =  findViewById(R.id.doorButton);
        armingButton =  findViewById(R.id.armingButton);
        movementSensorButton =  findViewById(R.id.movementSensorbutton);
        alarmLightTB =  findViewById(R.id.AlarmSwiatlo);
        alarmSound =  findViewById(R.id.alarmDzwiek);
        alarmOnOff =  findViewById(R.id.onOffAlarm);

        window1 = findViewById(R.id.window1_open);
        window2 = findViewById(R.id.window2_open);
        door = findViewById(R.id.door_open);
        movementSensor = findViewById(R.id.buddy);
        lock = findViewById(R.id.lock);

        handler = new Handler();
        modbusSlave = new HouseAlarmSlave(getApplicationContext());
        dekorator = new DekoratorHouseAlarm(this, modbusSlave);
        dekorator.initUiElements();

        try {
            modbusSlave.startSlaveListener();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setOnClickListeners();
        startActivity();


    }

    private void setOnClickListeners(){
        window1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    modbusSlave.switchInput(0);
                    window1Button.setText(modbusSlave.getAllInputs().get(0).toString());
                } catch (IllegalDataAddressException e) {
                    e.printStackTrace();
                }
            }
        });

        window2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    modbusSlave.switchInput(1);
                    window2Button.setText(modbusSlave.getAllInputs().get(1).toString());
                } catch (IllegalDataAddressException e) {
                    e.printStackTrace();
                }
            }
        });

        doorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    modbusSlave.switchInput(2);
                    doorButton.setText(modbusSlave.getAllInputs().get(2).toString());
                } catch (IllegalDataAddressException e) {
                    e.printStackTrace();
                }
            }
        });

        movementSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    modbusSlave.switchInput(3);
                    movementSensorButton.setText(modbusSlave.getAllInputs().get(3).toString());
                } catch (IllegalDataAddressException e) {
                    e.printStackTrace();
                }
            }
        });

        armingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    modbusSlave.switchInput(4);
                    armingButton.setText(modbusSlave.getAllInputs().get(4).toString());
                } catch (IllegalDataAddressException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void startUiUpdater(){

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                                if(modbusSlave.getAllInputs().get(0))
                                    window1.setVisibility(View.VISIBLE);
                                else window1.setVisibility(View.INVISIBLE);

                                if(modbusSlave.getAllInputs().get(1))
                                    window2.setVisibility(View.VISIBLE);
                                else window2.setVisibility(View.INVISIBLE);

                                if(modbusSlave.getAllInputs().get(2))
                                    door.setVisibility(View.VISIBLE);
                                else door.setVisibility(View.INVISIBLE);

                                if(modbusSlave.getAllInputs().get(3))
                                    movementSensor.setVisibility(View.VISIBLE);
                                else movementSensor.setVisibility(View.INVISIBLE);

                                if(modbusSlave.getAllInputs().get(4))
                                    lock.setImageResource(R.drawable.lock_on);
                                else lock.setImageResource(R.drawable.lock_off);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        handler.postDelayed(this,50);
                    }
                });
            }
        },50);
    }

    private void startModbusUpdater(){
        modbusUpdater = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

                    try {
                        dekorator.update();

                        if (modbusSlave.getAllCoils().get(0))
                            alarmLightTB.setBackgroundColor(getResources().getColor(R.color.alarmGreen));
                        else alarmLightTB.setBackgroundColor(getResources().getColor(R.color.alarmRed));

                        if (modbusSlave.getAllCoils().get(1))
                            alarmSound.setBackgroundColor(getResources().getColor(R.color.alarmGreen));
                        else alarmSound.setBackgroundColor(getResources().getColor(R.color.alarmRed));

                        if (modbusSlave.getAllCoils().get(2))
                            alarmOnOff.setBackgroundColor(getResources().getColor(R.color.alarmGreen));
                        else alarmOnOff.setBackgroundColor(getResources().getColor(R.color.alarmRed));

                    }catch (Exception e){
                        Log.e("modbusUpdaterException",e.toString());
                    }
                }
            }
        });
        modbusUpdater.start();
    }

    private void startActivity(){
        startModbusUpdater();
        startUiUpdater();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        modbusUpdater.interrupt();
        handler.removeCallbacksAndMessages(null);
    }
}



