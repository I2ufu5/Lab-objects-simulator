package com.rbelcyr.kia.sol.Activities.Simulations.HeatPlant;

import android.os.Handler;
import android.util.Log;

import com.badlogic.gdx.utils.Array;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HeatPlantDAO {

    private GraphView graph;
    private ArrayList<DataPoint> dataTemperature;
    private ArrayList<DataPoint> dataVoltage;
    private ArrayList<DataPoint> dataFanRpm;
    private LineGraphSeries<DataPoint> seriesTemperature;
    private LineGraphSeries<DataPoint> seriesVoltage;
    private LineGraphSeries<DataPoint> seriesFanRpm;
    private Calendar calendar = Calendar.getInstance();
    private HeatPlant heatPlant;
    private Handler handler;

    HeatPlantDAO(GraphView graph, HeatPlant heatPlant){
        this.graph = graph;
        this.heatPlant = heatPlant;
        dataTemperature = new ArrayList<>();
        dataFanRpm = new ArrayList<>();
        dataVoltage = new ArrayList<>();
        seriesTemperature = new LineGraphSeries<>();
        seriesVoltage = new LineGraphSeries<>();
        seriesFanRpm = new LineGraphSeries<>();
        handler = new Handler();

    }

    private void updateData(){
        Date d = calendar.getTime();
        dataTemperature.add(new DataPoint(d,heatPlant.getRealTemperature()));
        dataVoltage.add(new DataPoint(d,heatPlant.getRealVoltage()));
        dataFanRpm.add(new DataPoint(d,heatPlant.getRealFanRpm()));
    }

    private void updateSeries(){
        seriesFanRpm.resetData(generateSeries(dataFanRpm));
        seriesVoltage.resetData(generateSeries(dataVoltage));
        seriesTemperature.resetData(generateSeries(dataTemperature));
    }

    private DataPoint[] generateSeries(ArrayList<DataPoint> arrayList){
        DataPoint[] dataSet = new DataPoint[arrayList.size()];

        for(int i=0;i<arrayList.size();i++){
            dataSet[i] = arrayList.get(i);
        }

        return dataSet;
    }

    public void setFormat(){
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxY(22.0f);
        graph.getViewport().setMinY(20.0f);
    }
    public void start(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateData();
                updateSeries();
                Log.e("CHART UPDATE","ONCE");
                handler.postDelayed(this,1000);
            }
        },500);
    }
}
