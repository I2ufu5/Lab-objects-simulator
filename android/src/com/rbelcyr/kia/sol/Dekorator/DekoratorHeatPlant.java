package com.rbelcyr.kia.sol.Dekorator;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;
import com.rbelcyr.kia.sol.R;
import com.serotonin.modbus4j.exception.IllegalDataAddressException;

public class DekoratorHeatPlant extends AbstractDekorator {

    private TextView register0TextView;
    private TextView register1TextView;
    private TextView register2TextView;

    public DekoratorHeatPlant(AppCompatActivity activity, AbstractModbusSlave modbusSlave) {
        super(activity, modbusSlave);

    }

    @Override
    public void initUiElements() {
        super.initUiElements();
        register0TextView = activity.findViewById(R.id.reg0Text);
        register1TextView = activity.findViewById(R.id.reg1Text);
        register2TextView = activity.findViewById(R.id.regIn0Text);
    }

    @Override
    public void update() throws IllegalDataAddressException {
        super.update();
        register0TextView.setText(
                String.valueOf(modbusSlave.getAllRegisters().get(0))
        );

        register1TextView.setText(
                String.valueOf(modbusSlave.getAllRegisters().get(1))
        );

        register2TextView.setText(
                String.valueOf(modbusSlave.getAllInputRegisters().get(0))
        );
    }
}
