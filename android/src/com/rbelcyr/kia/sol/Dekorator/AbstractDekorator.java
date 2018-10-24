package com.rbelcyr.kia.sol.Dekorator;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.widget.TextView;

import com.rbelcyr.kia.sol.ModbusSlaves.AbstractModbusSlave;
import com.rbelcyr.kia.sol.R;
import com.serotonin.modbus4j.exception.IllegalDataAddressException;

import static android.content.Context.WIFI_SERVICE;

public abstract class AbstractDekorator {

    public String ipAddres;
    public String unitId;
    public String port;
    public AbstractModbusSlave modbusSlave;
    public AppCompatActivity activity;

    private TextView ipAddressTextView;
    private TextView unitIdTextView;
    private TextView portTextView;


    public AbstractDekorator(AppCompatActivity activity, AbstractModbusSlave modbusSlave) {
        WifiManager wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(WIFI_SERVICE);
        this.ipAddres = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        this.unitId = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext()).getString("unitID","255");

        this.port = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext()).getString("Port","10502");

        this.modbusSlave = modbusSlave;

        this.activity = activity;

        initUiElements();
    }

    public void initUiElements(){
        ipAddressTextView = activity.findViewById(R.id.ipText);
        unitIdTextView = activity.findViewById(R.id.unitIdText);
        portTextView = activity.findViewById(R.id.portText);
    }


    public void update() throws IllegalDataAddressException {
        ipAddressTextView.setText(ipAddres);
        unitIdTextView.setText(unitId);
        portTextView.setText(port);
    }





}
