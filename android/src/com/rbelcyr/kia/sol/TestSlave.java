package com.rbelcyr.kia.sol;

import com.serotonin.modbus4j.BasicProcessImage;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.ip.tcp.TcpSlave;

import java.util.Random;

public class TestSlave {
    public static void main() throws ModbusInitException, ModbusTransportException, ErrorResponseException, InterruptedException
    {
        final IpParameters ipParameters = new IpParameters();
        ipParameters.setHost("127.0.0.1");
        ipParameters.setPort(502);

        final TcpSlave slave = new TcpSlave(1502, false);

        slave.addProcessImage( new BasicProcessImage(1) );

        new Thread(new Runnable() {
            public void run() {
                try {
                    slave.start();
                }
                catch (ModbusInitException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        while (true) {
            BasicProcessImage po = (BasicProcessImage) slave.getProcessImage(1);
            po.setNumeric(RegisterRange.HOLDING_REGISTER, 1, DataType.FOUR_BYTE_FLOAT, new Random().nextInt(200));
            po.setNumeric(RegisterRange.HOLDING_REGISTER, 2, DataType.FOUR_BYTE_FLOAT_SWAPPED, new Random().nextInt(200));
            po.setNumeric(RegisterRange.HOLDING_REGISTER, 3, DataType.TWO_BYTE_INT_SIGNED, new Random().nextInt(200));
            po.setCoil(4, true);

            synchronized (slave) {
                slave.wait(5000);
            }
        }

    }
}
