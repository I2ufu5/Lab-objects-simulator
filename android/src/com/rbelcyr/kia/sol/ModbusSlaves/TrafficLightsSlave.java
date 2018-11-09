package com.rbelcyr.kia.sol.ModbusSlaves;

import android.content.Context;

public class TrafficLightsSlave extends AbstractModbusSlave{

    public TrafficLightsSlave(Context context){
        super(context);
        addModbusRegister(3);
    }
}
