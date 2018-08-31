package com.rbelcyr.kia.sol.ModbusSlaves.HouseAlarm;

public enum HouseAlarmAccesNames {
    WINDOW1(0),
    WINDOW2(1),
    DOOR(2),
    MOVEMENT_SENSOR(3),
    ARMING_BUTTON(4);

    public int intvalue;

    HouseAlarmAccesNames(int intvalue) {
        this.intvalue = intvalue;
    }
}
