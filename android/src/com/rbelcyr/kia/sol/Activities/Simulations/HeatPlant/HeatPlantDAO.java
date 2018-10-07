package com.rbelcyr.kia.sol.Activities.Simulations.HeatPlant;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.badlogic.gdx.utils.Array;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
        Calendar calendar = Calendar.getInstance();
        Long d = calendar.getTimeInMillis();

        if(dataTemperature.size()>20)
            for(int i=1;i<dataTemperature.size();i++){
            dataTemperature.set(i-1,dataTemperature.get(i));
            }else

        dataTemperature.add(new DataPoint(d,heatPlant.getRealTemperature()));
        dataVoltage.add(new DataPoint(d,heatPlant.getRealVoltage()));
        dataFanRpm.add(new DataPoint(d,heatPlant.getRealFanRpm()/10));

            graph.getViewport().setMinX(dataTemperature.get(0).getX());
        if(dataTemperature.size()>20) graph.getViewport().setMaxX(dataTemperature.get(dataTemperature.size()-1).getX());
        else                           graph.getViewport().setMaxX(dataTemperature.get(0).getX()+20000);
        graph.getViewport().setMinY(0);

        graph.getViewport().setMaxY(getMaxValue(dataTemperature)+5);

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

    public void setFormat(Context context){
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getSecondScale().setMinY(0);
        graph.getSecondScale().setMaxY(250);
        graph.getSecondScale().addSeries(seriesFanRpm);
        graph.getSecondScale().addSeries(seriesVoltage);
        graph.addSeries(seriesTemperature);
        seriesTemperature.setColor(Color.RED);
        seriesFanRpm.setColor(Color.GREEN);

        LabelFormatter formatter = new LabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {

                if(isValueX) {
                    Date currentDate = new Date((long)value);

                    DateFormat df = new SimpleDateFormat("HH:mm:ss");

                    return df.format(currentDate);
                }
                return String.valueOf(value);
            }

            @Override
            public void setViewport(Viewport viewport) {

            }
        };

        graph.getGridLabelRenderer().setLabelFormatter(formatter);
        graph.getGridLabelRenderer().setNumHorizontalLabels(4);
        graph.getGridLabelRenderer().setPadding(50);
    }


    public void start(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateData();
                updateSeries();
                handler.postDelayed(this,1000);
            }
        },500);
    }

    public static float getMaxValue(ArrayList<DataPoint> dataPoints){
        float max=0;
        for(DataPoint dp :dataPoints){
            if(dp.getY()>max) max = (float)dp.getY();
        }
        return max;
    }
}
