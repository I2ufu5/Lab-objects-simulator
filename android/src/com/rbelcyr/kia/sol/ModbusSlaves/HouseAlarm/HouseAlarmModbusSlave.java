package com.rbelcyr.kia.sol.ModbusSlaves.HouseAlarm;

import com.serotonin.modbus4j.exception.IllegalDataAddressException;

import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;

public class HouseAlarmModbusSlave extends AbstractModbusSlave{


    public HouseAlarmModbusSlave(){
        super();

        addModbusInputs(5);
        addModbusCoils(3);
    }

}
