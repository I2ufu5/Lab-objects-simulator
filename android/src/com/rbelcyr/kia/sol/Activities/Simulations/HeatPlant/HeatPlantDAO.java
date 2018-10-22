package com.rbelcyr.kia.sol.Activities.Simulations.HeatPlant;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.text.DateFormat;
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

    private void cutOldData(ArrayList<DataPoint> list){
        if(list.size()>1000){
            for(int i=1;i<=1000;i++){
                list.set(i-1,list.get(i));
            }
            list.remove(1000);
        }
    }

    private void updateData(){
        Calendar calendar = Calendar.getInstance();
        Long d = calendar.getTimeInMillis();

        cutOldData(dataTemperature);
        cutOldData(dataVoltage);
        cutOldData(dataFanRpm);

        dataTemperature.add(new DataPoint(d,heatPlant.getRealTemperature()));
        dataVoltage.add(new DataPoint(d,heatPlant.getRealVoltage()));
        dataFanRpm.add(new DataPoint(d,heatPlant.getRealFanRpm()*100));


        if(dataTemperature.size()>950){
            graph.getViewport().setMinX(dataTemperature.get(dataTemperature.size()-1).getX()-30000);
            graph.getViewport().setMaxX(dataTemperature.get(dataTemperature.size()-1).getX());
        } else{
            graph.getViewport().setMinX(dataTemperature.get(0).getX());
            graph.getViewport().setMaxX(dataTemperature.get(0).getX()+30000);
        }
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
       // Log.e("TAG", String.valueOf(dataSet.length));
        return dataSet;
    }

    public void setFormat(Context context){
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(false);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(100);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(30);
        graph.getSecondScale().setMinY(0);
        graph.getSecondScale().setMaxY(250);
        graph.getSecondScale().addSeries(seriesFanRpm);
        graph.getSecondScale().addSeries(seriesVoltage);
        graph.addSeries(seriesTemperature);
        seriesTemperature.setColor(Color.RED);
        seriesFanRpm.setColor(Color.GREEN);
        graph.setBackgroundColor(Color.LTGRAY);

        LabelFormatter formatter = new LabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {

                if(isValueX) {
                    Date currentDate = new Date((long)value);

                    DateFormat df = new SimpleDateFormat("HH:mm:ss");

                    return df.format(currentDate);
                }
                else

                return String.valueOf((int)value);
            }

            @Override
            public void setViewport(Viewport viewport) {

            }
        };

        graph.getGridLabelRenderer().setLabelFormatter(formatter);
        graph.getGridLabelRenderer().setNumHorizontalLabels(5);
    }

    public void draw(){
                updateData();
                updateSeries();
    }

}
