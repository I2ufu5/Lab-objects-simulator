package com.rbelcyr.kia.sol.ModbusSlaves;

import android.content.Context;

import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;

public class HeatPlantSlave extends AbstractModbusSlave {
    public HeatPlantSlave(Context context) {
        super(context);
        addModbusRegister(2);
        addModbusInputRegister(1);
    }
}
