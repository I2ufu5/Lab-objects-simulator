package com.rbelcyr.kia.sol.Activities.Simulations.HeatObject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.rbelcyr.kia.sol.R;

public class HeatObjectActivity extends AppCompatActivity {

    LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_object);

        chart = (LineChart) findViewById(R.id.chart);
    }
}
