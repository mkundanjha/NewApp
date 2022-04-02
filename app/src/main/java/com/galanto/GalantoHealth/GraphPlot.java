package com.galanto.GalantoHealth;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.util.LocaleData;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.newapp.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import app.futured.donut.DonutProgressView;
import app.futured.donut.DonutSection;

public class GraphPlot {
    BarChart barChart;
    LineChart lineChart;
    Context context;

    public GraphPlot(Context context) {
        this.context=context;
    }
    public GraphPlot() {

    }


    public  void createLineChart(LineChart chart, ArrayList<LocalDate> dateAxis , ArrayList<Entry> graphData, String legendText, int graphColor, Float minX, Float miny, Float maxX, Float maxY){


        XAxis xAxis=chart.getXAxis();
        xAxis.setDrawGridLines(false);      // remove vertical grid lines
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);      // set bottom x-axis
        xAxis.setTextColor(Color.BLACK);
        //xAxis.setTextSize();
        xAxis.setAxisLineWidth(5);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);

        xAxis.setSpaceMin(50f);
        //xAxis.setSpaceMax(2f);
        xAxis.setAxisMinimum(0);

        xAxis.setAxisMaximum(maxX+1);
        if (dateAxis!=null) {
            xAxis.setEnabled(true);
            if (dateAxis.size() > 1) {
                xAxis.setAxisMaximum(dateAxis.size()-1);
            }else {
                xAxis.setEnabled(false);
                xAxis.setAxisMaximum(dateAxis.size());
            }

            xAxis.setValueFormatter(new MyXAxisValueFormatter(dateAxis));

        }else {
            xAxis.setEnabled(false);
            xAxis.setValueFormatter(null);
        }

        chart.getAxisRight().setEnabled(false);



        YAxis yAxis=chart.getAxisLeft();
//        yAxis.setTextColor(Color.BLACK);

        yAxis.setTextColor(Color.BLACK);
        //yAxis.setTextSize(13);
        yAxis.setGranularity(5f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setGridColor(Color.parseColor("#d4d4d4"));
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(true);
        yAxis.setAxisMaximum(maxY+10);
//        yAxis.setAxisMaximum(120f);
        yAxis.setAxisMinimum(miny-10);
//        yAxis.setAxisMinimum(60f);
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

        chart.clear();
        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);


        chart.animateX(200);
        chart.setExtraOffsets(3f,5f,5f,10f);
        chart.getXAxis().setEnabled(false);
        chart.getAxisLeft().setEnabled(true);

        Legend legend = chart.getLegend();

//        if(!legendText.isEmpty()) {
//
//            legend.setTextSize(18);
//            legend.setTextColor(graphColor);
//            legend.setYOffset(1);
//            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//            legend.setEnabled(true);
//        }else {
//            legend.setEnabled(false);
//        }
        legend.setEnabled(false);


    }

    public void setBarChartData(BarChart barChart,String graphlabel,ArrayList<BarEntry> barData,int maxYAxis,int color){
//        ArrayList<BarEntry> barData=new ArrayList<>();
//
//        for (int i=0;i<3;i++){
//            barData.add(new BarEntry(i,Float.parseFloat(String.valueOf(Math.random()*10))));
//        }
        ArrayList<LocalDate> dates=new ArrayList<>();
        dates.add(LocalDate.parse("2022-04-01"));
        dates.add(LocalDate.parse("2022-04-02"));
        dates.add(LocalDate.parse("2022-04-03"));

        ArrayList<String> fingers=new ArrayList<>();

        fingers.add("T");
        fingers.add("I");
        fingers.add("M");
        fingers.add("R");
        fingers.add("P");
        fingers.add("W");

        XAxis xAxis=barChart.getXAxis();
        xAxis.setDrawGridLines(false);      // remove vertical grid lines
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);      // set bottom x-axis
        xAxis.setTextColor(Color.GRAY);
        xAxis.setAxisLineWidth(1);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setEnabled(true);
        xAxis.setGranularity(1f);
        xAxis.setTextSize(15f);
        xAxis.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//        xAxis.setSpaceMax(10f);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(fingers));


        barChart.getAxisRight().setEnabled(false);

        YAxis yAxis=barChart.getAxisLeft();
        yAxis.setTextColor(Color.GRAY);

        yAxis.setDrawAxisLine(true);
        yAxis.setDrawGridLines(false);
        yAxis.setAxisMaximum(maxYAxis);
        yAxis.setAxisMinimum(0.5f);
        yAxis.setAxisLineWidth(3);
        yAxis.setSpaceMin(2f);
        yAxis.setAxisLineWidth(1f);
        yAxis.setGranularity(20f);
        yAxis.setEnabled(true);



        BarDataSet set=new BarDataSet(barData,graphlabel);
        set.setColor(color);
        set.setDrawValues(false);
//        set.setColor(Color.parseColor("#3f48a6"));

        //set.setBarBorderWidth(2);

        ArrayList<IBarDataSet> dataSets=new ArrayList<>();
        dataSets.add(set);

        BarData data=new BarData(dataSets);
        data.setBarWidth(0.5f);

        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        barChart.setTouchEnabled(false);
        barChart.animateY(500);
//        barChart.setExtraTopOffset(10f);
        barChart.setExtraOffsets(3f,5f,5f,5f);
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


        data.setValueTextColor(Color.GRAY);


        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(10);
        pieChart.setEntryLabelColor(Color.GRAY);
        pieChart.setCenterText(centerText);
        pieChart.setCenterTextSize(20);

        pieChart.setTouchEnabled(true);

        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.getDescription().setEnabled(false);



        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);

        pieChart.animateY(1000, Easing.EaseInOutQuad);
    }

    public void setupDonutChart(DonutProgressView view,Float inputValue, Float maxValue,int color) {
        List<DonutSection> inputList = new ArrayList<>();
        try {
            DonutSection section1 = new DonutSection("section1",color/*Color.parseColor("#4287f5")*/, inputValue);
            //DonutSection section2 = new DonutSection("section2", Color.parseColor("#f55442"), 2f);

            inputList.add(section1);
            //inputList.add(section2);


            view.setCap(maxValue);
            view.setGapWidthDegrees(0f);
            view.setGapAngleDegrees(90f);
            view.setStrokeWidth(30f);
            view.submitData(inputList);
            //demoDonut.removeAmount("section1",1f);
        }catch (Exception ex){
            Toast.makeText(context, "Error in setupDonutChart:"+ex.toString(), Toast.LENGTH_SHORT).show();
        }

    }



}
