package com.rbelcyr.kia.sol.ModbusSlaves.HouseAlarm;

import android.content.Context;

import com.serotonin.modbus4j.exception.IllegalDataAddressException;

import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;

public class HouseAlarmModbusSlave extends AbstractModbusSlave{


    public HouseAlarmModbusSlave(Context context){
        super(context);

        addModbusInputs(5);
        addModbusCoils(3);
    }

}
