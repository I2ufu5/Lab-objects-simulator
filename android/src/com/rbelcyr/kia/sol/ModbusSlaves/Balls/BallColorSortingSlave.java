package com.rbelcyr.kia.sol.ModbusSlaves.Balls;

import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;

public class BallColorSortingSlave extends AbstractModbusSlave {

    public BallColorSortingSlave() {
        super();
        addModbusInputs(2);
        addModbusCoils(5);
    }
}
