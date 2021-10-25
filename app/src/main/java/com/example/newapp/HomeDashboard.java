package com.example.newapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.WeakHashMap;


public class HomeDashboard extends Fragment {

    TextView currentDay;
    TextView currentDate;
    LineChart romChart;
    BarChart barChart;
    CardView gameCard,timeUsagesCard,romCard,hrCard;
    Animate animate;



    public HomeDashboard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home_dashboard,container,false);
        currentDate=view.findViewById(R.id.tvCurrentDate);
        currentDay=(TextView) view.findViewById(R.id.tvCurrentDay);
        romCard=view.findViewById(R.id.romCard);
        hrCard=view.findViewById(R.id.hrCard);
        romCard.setVisibility(View.INVISIBLE);
        hrCard.setVisibility(View.INVISIBLE);



        //setDateTime(currentDate,currentDay);


        return inflater.inflate(R.layout.fragment_home_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentDate=view.findViewById(R.id.tvCurrentDate);
        currentDay=(TextView) view.findViewById(R.id.tvCurrentDay);
        romChart=view.findViewById(R.id.romChart);
        barChart=view.findViewById(R.id.barChart);
        gameCard=view.findViewById(R.id.gameCard);
        timeUsagesCard=view.findViewById(R.id.timeUsageCard);




        animate=new Animate(getContext());

        animate.runAnimation(gameCard,500);
        animate.runAnimation(timeUsagesCard,500);
        animate.runAnimation(romCard,700);
        animate.runAnimation(hrCard,700);

        setDateTime(currentDate,currentDay);

        if (romCard.getVisibility()==View.VISIBLE){
            setRomChartData();
        }

        if (hrCard.getVisibility()==View.VISIBLE){
            setBarChartData();
        }


    }
    public  void setRomChartData(){
        ArrayList<Entry> romData=new ArrayList<>();

        for (int i=0;i<50;i++){
            romData.add(new Entry(i,Float.parseFloat(String.valueOf(Math.random()*10))));
        }

        XAxis xAxis=romChart.getXAxis();
        xAxis.setDrawGridLines(false);      // remove vertical grid lines
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);      // set bottom x-axis
        xAxis.setTextColor(Color.GRAY);
        xAxis.setAxisLineWidth(5);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceMin(2f);
        //xAxis.setGranularity(1);

        romChart.getAxisRight().setEnabled(false);

        YAxis yAxis=romChart.getAxisLeft();
        yAxis.setTextColor(Color.GRAY);

        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);
        yAxis.setAxisMaximum(10);
        yAxis.setAxisMinimum(0.5f);
        yAxis.setAxisLineWidth(5);
        yAxis.setSpaceMin(2f);


        LineDataSet set =new LineDataSet(romData,"ROM of Fingers");
        set.setDrawHighlightIndicators(false);
        set.setFillAlpha(30);
        set.setLineWidth(5);

        //set.setColor(Color.BLUE);
        set.setDrawValues(false);
        set.setDrawCircles(false);

        ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        dataSets.add(set);

        LineData data= new LineData(dataSets);
        data.setDrawValues(false);

        romChart.setData(data);
        romChart.getDescription().setEnabled(false);
        romChart.setTouchEnabled(false);
        romChart.animateX(1500);

        Legend legend=romChart.getLegend();
        legend.setTextSize(20);

    }

    public void setBarChartData(){
        ArrayList<BarEntry> barData=new ArrayList<>();

        for (int i=0;i<10;i++){
            barData.add(new BarEntry(i,Float.parseFloat(String.valueOf(Math.random()*10))));
        }
        XAxis xAxis=barChart.getXAxis();
        xAxis.setDrawGridLines(false);      // remove vertical grid lines
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);      // set bottom x-axis
        xAxis.setTextColor(Color.GRAY);
        xAxis.setAxisLineWidth(5);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceMin(1f);
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


        BarDataSet set=new BarDataSet(barData,"HR");
        set.setColor(Color.parseColor("#DC6868"));
        set.setDrawValues(false);


        ArrayList<IBarDataSet> dataSets=new ArrayList<>();
        dataSets.add(set);

        BarData data=new BarData(dataSets);

        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        barChart.setTouchEnabled(false);
        barChart.animateY(700);

        Legend legend=barChart.getLegend();
        legend.setTextSize(20);

    }

    public void setDateTime(TextView date,TextView day){
        try {
            Date currentDate=new Date();
            SimpleDateFormat format=new SimpleDateFormat("dd MMM");
            date.setText( format.format(currentDate).toString());
            //Toast.makeText(getContext(),format.format(currentDate).toString(),Toast.LENGTH_SHORT).show();
            DayOfWeek dayOfWeek= LocalDate.now().getDayOfWeek();
            day.setText(dayOfWeek.toString());

        }catch (Exception ex){
            Toast.makeText(getContext(),ex.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}