package com.rbelcyr.kia.sol.ModbusSlaves;

import android.content.Context;

public class TrafficLightsModbusSlave extends AbstractModbusSlave{

    public TrafficLightsModbusSlave(Context context){
        super(context);
        addModbusRegister(3);
    }
}
