package com.rbelcyr.kia.sol.ModbusSlaves;

import android.content.Context;

import com.serotonin.modbus4j.exception.IllegalDataAddressException;

import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;

public class HouseAlarmSlave extends AbstractModbusSlave{


    public HouseAlarmSlave(Context context){
        super(context);

        addModbusInputs(5);
        addModbusCoils(3);
    }

}
