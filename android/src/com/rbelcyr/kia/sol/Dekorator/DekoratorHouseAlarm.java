package com.rbelcyr.kia.sol.Dekorator;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;
import com.rbelcyr.kia.sol.R;
import com.serotonin.modbus4j.exception.IllegalDataAddressException;

import org.w3c.dom.Text;

public class DekoratorHouseAlarm extends AbstractDekorator {

    TextView window1,window2,door,buddy,lock;
    TextView alarmStatus, alarmSound, alarmLight;

    public DekoratorHouseAlarm(AppCompatActivity activity, AbstractModbusSlave modbusSlave) {
        super(activity, modbusSlave);
    }

    @Override
    public void initUiElements() {
        super.initUiElements();
        window1 = activity.findViewById(R.id.window1Text);
        window2 = activity.findViewById(R.id.window2Text);
        door = activity.findViewById(R.id.doorText);
        buddy = activity.findViewById(R.id.buddyText);
        lock = activity.findViewById(R.id.switchText);

        alarmLight = activity.findViewById(R.id.lightText);
        alarmSound = activity.findViewById(R.id.soundText);
        alarmStatus = activity.findViewById(R.id.alarmStatusText);
    }

    @Override
    public void update() throws IllegalDataAddressException {
        super.update();
        window1.setText(String.valueOf(modbusSlave.getAllInputs().get(0)));
        window2.setText(String.valueOf(modbusSlave.getAllInputs().get(1)));
        door.setText(String.valueOf(modbusSlave.getAllInputs().get(2)));
        buddy.setText(String.valueOf(modbusSlave.getAllInputs().get(3)));
        lock.setText(String.valueOf(modbusSlave.getAllInputs().get(4)));

        alarmLight.setText(String.valueOf(modbusSlave.getAllCoils().get(0)));
        alarmSound.setText(String.valueOf(modbusSlave.getAllCoils().get(1)));
        alarmStatus.setText(String.valueOf(modbusSlave.getAllCoils().get(2)));

    }
}
