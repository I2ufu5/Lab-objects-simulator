package com.rbelcyr.kia.sol.ModbusSlaves.Balls;

import android.content.Context;

import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;

public class BallColorSortingSlave extends AbstractModbusSlave {

    public BallColorSortingSlave(Context context) {
        super(context);
        addModbusInputs(2);
        addModbusCoils(5);
    }
}
