package com.rbelcyr.kia.sol.ModbusSlaves;

import android.content.Context;

import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;

public class HeatPlantModbusSlave extends AbstractModbusSlave {
    public HeatPlantModbusSlave(Context context) {
        super(context);
        addModbusRegister(3);
    }
}
