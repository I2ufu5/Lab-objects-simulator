package com.rbelcyr.kia.sol.ModbusSlaves.Balls;

import android.content.Context;

import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;

public class BallColorPatternSlave extends AbstractModbusSlave{

    public BallColorPatternSlave(Context context) {
        super(context);
        addModbusCoils(5);
        addModbusInputs(2);
    }
}
