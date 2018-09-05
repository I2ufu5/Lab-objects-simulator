package com.rbelcyr.kia.sol.ModbusSlaves;

public class TrafficLightsModbusSlave extends AbstractModbusSlave{

    public TrafficLightsModbusSlave(){
        super();
        addModbusRegister(3);
    }
}
