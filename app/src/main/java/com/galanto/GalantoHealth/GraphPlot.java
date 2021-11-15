package com.galanto.GalantoHealth;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class GraphPlot {
    BarChart barChart;
    LineChart lineChart;

    public GraphPlot() {
    }


    public  void createLineChart(LineChart chart, ArrayList<Entry> graphData, String legendText, int graphColor,Float minX,Float miny,Float maxX,Float maxY){


        XAxis xAxis=chart.getXAxis();
        xAxis.setDrawGridLines(false);      // remove vertical grid lines
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);      // set bottom x-axis
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAxisLineWidth(5);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceMin(2f);
        xAxis.setAxisMinimum(minX-1);
        xAxis.setAxisMaximum(maxX+1);

        chart.getAxisRight().setEnabled(false);

        YAxis yAxis=chart.getAxisLeft();
        yAxis.setTextColor(Color.WHITE);

        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);
        yAxis.setAxisMaximum(maxY+1);
        yAxis.setAxisMinimum(miny-1);
        yAxis.setAxisLineWidth(5);
        yAxis.setSpaceMin(2f);


        LineDataSet set =new LineDataSet(graphData,legendText);
        set.setDrawHighlightIndicators(false);
        set.setFillAlpha(30);
        set.setLineWidth(3);
        set.setColor(graphColor);
        set.setDrawCircles(false);

        ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        dataSets.add(set);

        LineData data= new LineData(dataSets);
        data.setDrawValues(false);

        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(false);
        chart.animateX(1000);
        chart.setExtraOffsets(3f,5f,5f,10f);


        Legend legend=chart.getLegend();
        legend.setTextSize(18);
        legend.setTextColor(graphColor);
        legend.setYOffset(0);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);


    }

    public void setBarChartData(BarChart barChart){
        ArrayList<BarEntry> barData=new ArrayList<>();

        for (int i=0;i<3;i++){
            barData.add(new BarEntry(i,Float.parseFloat(String.valueOf(Math.random()*10))));
        }
        XAxis xAxis=barChart.getXAxis();
        xAxis.setDrawGridLines(false);      // remove vertical grid lines
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);      // set bottom x-axis
        xAxis.setTextColor(Color.GRAY);
        xAxis.setAxisLineWidth(1);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        //xAxis.setSpaceMin(5f);
        //xAxis.setAxisMinimum(0);

        //xAxis.setGranularity(1);

        barChart.getAxisRight().setEnabled(false);

        YAxis yAxis=barChart.getAxisLeft();
        yAxis.setTextColor(Color.GRAY);

        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);
        yAxis.setAxisMaximum(10);
        yAxis.setAxisMinimum(0.5f);
        yAxis.setAxisLineWidth(5);
        yAxis.setSpaceMin(2f);
        yAxis.setEnabled(false);



        BarDataSet set=new BarDataSet(barData,"Success rate");
        set.setColor(Color.parseColor("#DC6868"));
        set.setDrawValues(true);
        set.setColor(Color.parseColor("#3f48a6"));

        //set.setBarBorderWidth(2);

        ArrayList<IBarDataSet> dataSets=new ArrayList<>();
        dataSets.add(set);

        BarData data=new BarData(dataSets);
        data.setBarWidth(0.7f);



        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        barChart.setTouchEnabled(false);
        barChart.animateY(700);
        barChart.setExtraOffsets(3f,5f,5f,10f);


        Legend legend=barChart.getLegend();
        legend.setTextSize(18);

    }



}
