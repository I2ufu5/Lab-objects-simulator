package com.rbelcyr.kia.sol.ModbusSlaves;

import android.content.Context;

import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;

public class BallMachineSlave extends AbstractModbusSlave {

    public BallMachineSlave(Context context) {
        super(context);
        addModbusInputs(2);
        addModbusCoils(5);
    }
}
