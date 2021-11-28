package com.galanto.GalantoHealth.ui;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newapp.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Activity extends Fragment {

    PieChart progresschart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progresschart=view.findViewById(R.id.gameScore);
        setupPieChart();
        createPieChart(progresschart);

    }
    private void setupPieChart() {



    }

    public void createPieChart(PieChart pieChart){
        ArrayList<PieEntry> entries=new ArrayList<>();

        Float score=0.4f;
        entries.add(new PieEntry(score));
        entries.add(new PieEntry(1-score));
//        entries.add(new PieEntry(0.10f, "Entertainment"));
//        entries.add(new PieEntry(0.25f, "Electricity and Gas"));
//        entries.add(new PieEntry(0.3f, "Housing"));

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#48a36c"));
        colors.add(Color.parseColor("#d4d4d4"));
//        for (int color: ColorTemplate.MATERIAL_COLORS) {
//            colors.add(color);
//        }
//
//        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
//            colors.add(color);
//        }

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
        pieChart.setEntryLabelColor(Color.RED);
        pieChart.setCenterText(String.valueOf((int)( score*100))+" %");
        pieChart.setCenterTextSize(30);

        pieChart.setCenterTextColor(Color.BLUE);
        pieChart.getDescription().setEnabled(false);
        pieChart.getDescription().setEnabled(false);


        Legend l = progresschart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }


}   
