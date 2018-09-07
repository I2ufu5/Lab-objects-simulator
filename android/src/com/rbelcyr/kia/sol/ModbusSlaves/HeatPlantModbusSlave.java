package com.rbelcyr.kia.sol.ModbusSlaves;

import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;

public class HeatPlantModbusSlave extends AbstractModbusSlave {
    public HeatPlantModbusSlave() {
        super();
        addModbusRegister(3);
    }
}
