package com.galanto.GalantoHealth;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
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
        yAxis.setTextColor(Color.BLACK);
        //yAxis.setTextSize(13);
        yAxis.setGranularity(20f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setGridColor(Color.parseColor("#d4d4d4"));
        yAxis.setDrawAxisLine(true);
        yAxis.setDrawGridLines(true);
        yAxis.setAxisMaximum(maxY+10);
        yAxis.setAxisMinimum(miny-10);
        yAxis.setAxisLineWidth(1);
        yAxis.setSpaceMin(2f);
        yAxis.setDrawAxisLine(false);


        LineDataSet set =new LineDataSet(graphData,legendText);
        set.setDrawHighlightIndicators(false);
        set.setFillAlpha(30);
        set.setLineWidth(2.5f);
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
        chart.getXAxis().setEnabled(false);
        chart.getAxisLeft().setEnabled(true);


        Legend legend=chart.getLegend();
        legend.setTextSize(18);
        legend.setTextColor(graphColor);
        legend.setYOffset(0);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);


    }

    public void setBarChartData(BarChart barChart,String graphlabel,ArrayList<BarEntry> barData,int maxYAxis){
//        ArrayList<BarEntry> barData=new ArrayList<>();
//
//        for (int i=0;i<3;i++){
//            barData.add(new BarEntry(i,Float.parseFloat(String.valueOf(Math.random()*10))));
//        }
        XAxis xAxis=barChart.getXAxis();
        xAxis.setDrawGridLines(false);      // remove vertical grid lines
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);      // set bottom x-axis
        xAxis.setTextColor(Color.GRAY);
        xAxis.setAxisLineWidth(1);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setEnabled(false);
        //xAxis.setSpaceMin(5f);
        //xAxis.setAxisMinimum(0);

        //xAxis.setGranularity(1);

        barChart.getAxisRight().setEnabled(false);

        YAxis yAxis=barChart.getAxisLeft();
        yAxis.setTextColor(Color.GRAY);

        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);
        yAxis.setAxisMaximum(maxYAxis);
        yAxis.setAxisMinimum(0.5f);
        yAxis.setAxisLineWidth(5);
        yAxis.setSpaceMin(2f);
        yAxis.setEnabled(false);



        BarDataSet set=new BarDataSet(barData,graphlabel);
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
        barChart.animateY(1000);
        barChart.setExtraOffsets(3f,5f,5f,10f);
        barChart.getDescription().setTextSize(10f);


        Legend legend=barChart.getLegend();
        legend.setEnabled(false);
        legend.setTextSize(18);

    }

    public void createPieChart(PieChart pieChart, ArrayList<PieEntry> entries, String centerText,int graphColor){
        //set colours of graph
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(graphColor);
        colors.add(Color.parseColor("#d4d4d4"));


        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setDrawValues(false);


        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(10);
        pieChart.setEntryLabelColor(Color.GRAY);
        pieChart.setCenterText(centerText);
        pieChart.setCenterTextSize(20);
        pieChart.setTouchEnabled(false);

        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.getDescription().setEnabled(false);
        pieChart.getDescription().setEnabled(false);


        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);

        pieChart.animateY(1000, Easing.EaseInOutQuad);
    }



}
