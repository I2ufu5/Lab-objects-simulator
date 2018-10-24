package com.rbelcyr.kia.sol.Dekorator;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;
import com.rbelcyr.kia.sol.R;
import com.serotonin.modbus4j.exception.IllegalDataAddressException;

public class DekoratorBallScene extends AbstractDekorator {

    TextView s1Text, s2Text, s3Text, s4Text;
    TextView csText, bsText;

    public DekoratorBallScene(AppCompatActivity activity, AbstractModbusSlave modbusSlave) {
        super(activity, modbusSlave);
    }

    @Override
    public void initUiElements() {
        super.initUiElements();
        s1Text = activity.findViewById(R.id.s1Text);
        s2Text = activity.findViewById(R.id.s2Text);
        s3Text = activity.findViewById(R.id.s3Text);
        s4Text = activity.findViewById(R.id.s4Text);

        csText = activity.findViewById(R.id.csText);
        bsText = activity.findViewById(R.id.bsText);
    }

    @Override
    public void update() throws IllegalDataAddressException {
        super.update();
        csText.setText(String.valueOf(modbusSlave.getAllInputs().get(0)));
        bsText.setText(String.valueOf(modbusSlave.getAllInputs().get(1)));

        s1Text.setText(String.valueOf(modbusSlave.getAllCoils().get(0)));
        s2Text.setText(String.valueOf(modbusSlave.getAllCoils().get(1)));
        s3Text.setText(String.valueOf(modbusSlave.getAllCoils().get(2)));
        s4Text.setText(String.valueOf(modbusSlave.getAllCoils().get(3)));

    }
}
