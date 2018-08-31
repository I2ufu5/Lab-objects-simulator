package com.rbelcyr.kia.sol.ModbusSlaves.BallPattern;

import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;

public class BallColorPatternSlave extends AbstractModbusSlave{

    public BallColorPatternSlave() {
        super();
        addModbusCoils(5);
        addModbusInputs(2);
    }
}
