package com.example.newapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


public class HomeDashboard extends Fragment {

    TextView currentDay,tvUserName,tvFingerNames,tvMcpScore,tvPIPScore,tvMcpTilte,tvPipTitle;
    TextView currentDate;
    LineChart romChart,mcpLineChart;
    BarChart barChart;
    CardView gameCard,timeUsagesCard,romCard,hrCard;
    Animate animate;
    ImageView ivThumbIndicator,ivIndexIndicator,ivMiddleIndicator,ivRingIndicator,ivLittleIndicator,ivWristIndicator;


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
        mcpLineChart=view.findViewById(R.id.mcpChart);

        hrCard=view.findViewById(R.id.hrCard);
        romCard.setVisibility(View.INVISIBLE);
        hrCard.setVisibility(View.INVISIBLE);
        tvUserName=view.findViewById(R.id.usrName);
        Intent intent=this.getActivity().getIntent();


        tvUserName.setText(intent.getStringExtra("user_name"));

        //setDateTime(currentDate,currentDay);


        return inflater.inflate(R.layout.fragment_home_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentDate=view.findViewById(R.id.tvCurrentDate);
        currentDay=(TextView) view.findViewById(R.id.tvCurrentDay);
        romChart=view.findViewById(R.id.romChart);
        mcpLineChart=view.findViewById(R.id.mcpChart);

        ivThumbIndicator =view.findViewById(R.id.thumbIndicator);
        ivIndexIndicator=view.findViewById(R.id.indexFgIndicator);
        ivMiddleIndicator=view.findViewById(R.id.middleFgIndicator);
        ivRingIndicator=view.findViewById(R.id.ringFgIndicator);
        ivLittleIndicator=view.findViewById(R.id.littleFgIndicator);
        ivWristIndicator=view.findViewById(R.id.wristIndicator);
        tvFingerNames=view.findViewById(R.id.fingName);

        tvMcpTilte=view.findViewById(R.id.mcpTitle);
        tvPipTitle=view.findViewById(R.id.pipTitle);
        tvMcpScore=view.findViewById(R.id.mcpScore);
        tvPIPScore=view.findViewById(R.id.pipScore);

        //barChart=view.findViewById(R.id.barChart);
        gameCard=view.findViewById(R.id.gameCard);
        //timeUsagesCard=view.findViewById(R.id.timeUsageCard);
        tvUserName=view.findViewById(R.id.usrName);

        Intent intent=this.getActivity().getIntent();
        tvUserName.setText(", "+intent.getStringExtra("user_name"));

        animate=new Animate(getContext());


        setPiPData("PIP of Index");
        setMcpData("MCP of Index");


        setDateTime(currentDate,currentDay);

        ivThumbIndicator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setClickAnimation(ivThumbIndicator,event,55,90,55,90);
                setPiPData("PIP of Thumb");
                setMcpData("MCP of Thumb");
                tvFingerNames.setText("Thumb\nFinger");
                tvFingerNames.setTextSize(30);
                tvMcpTilte.setText("MCP");
                tvPipTitle.setText("PIP");

                tvMcpScore.setText("34");
                tvPIPScore.setText("16");
                return true;
            }
        });

        ivIndexIndicator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setClickAnimation(ivIndexIndicator,event,55,90,55,90);
                setPiPData("PIP of Index");
                setMcpData("MCP of Index");
                tvFingerNames.setText("Index\nFinger");
                tvFingerNames.setTextSize(30);
                tvMcpTilte.setText("MCP");
                tvPipTitle.setText("PIP");
                tvMcpScore.setText("23");
                tvPIPScore.setText("35");
                return true;
            }
        });

        ivMiddleIndicator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setClickAnimation(ivMiddleIndicator,event,55,90,55,90);
                setPiPData("PIP of Middle");
                setMcpData("MCP of Middle");
                tvFingerNames.setText("Middle\nFinger");
                tvFingerNames.setTextSize(30);
                tvMcpTilte.setText("MCP");
                tvPipTitle.setText("PIP");
                tvMcpScore.setText("11");
                tvPIPScore.setText("16");
                return true;
            }
        });

        ivRingIndicator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setClickAnimation(ivRingIndicator,event,55,80,55,80);
                setPiPData("PIP of Ring");
                setMcpData("MCP of Ring");
                tvFingerNames.setText("Ring\nFinger");
                tvFingerNames.setTextSize(30);
                tvMcpTilte.setText("MCP");
                tvPipTitle.setText("PIP");
                tvMcpScore.setText("9");
                tvPIPScore.setText("17");
                return true;
            }
        });

        ivLittleIndicator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setClickAnimation(ivLittleIndicator,event,55,75,55,75);
                setPiPData("PIP of Little");
                setMcpData("MCP of Little");
                tvFingerNames.setText("Little\nFinger");
                tvFingerNames.setTextSize(30);
                tvMcpTilte.setText("MCP");
                tvPipTitle.setText("PIP");
                tvMcpScore.setText("46");
                tvPIPScore.setText("67");
                return true;
            }
        });

        ivWristIndicator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setClickAnimation(ivWristIndicator,event,55,100,55,100);
                setPiPData("Flex of Wrist");
                //setMcpData("MCP of Wrist");
                mcpLineChart.setVisibility(View.INVISIBLE);
                tvFingerNames.setText("Wrist");
                tvFingerNames.setTextSize(30);
                tvMcpTilte.setText("Flex");
                tvPipTitle.setText("");
                tvMcpScore.setText("13");
                tvPIPScore.setText("");
                return true;
            }
        });



    }
        public void setClickAnimation(ImageView figIndicator,MotionEvent event,int minHeight,int maxHeight,int minWidth,int maxWidth){
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                figIndicator.requestLayout();
                figIndicator.getLayoutParams().height=maxHeight;
                figIndicator.getLayoutParams().width=minWidth;
            }else if (event.getAction() == MotionEvent.ACTION_UP) {
                figIndicator.requestLayout();
                figIndicator.getLayoutParams().height=maxHeight;
                figIndicator.getLayoutParams().width=maxWidth;

            }
        }

    public void setPiPData(String label){
        ArrayList<Entry> romData=new ArrayList<>();

        for (int i=0;i<50;i++){
            romData.add(new Entry(i,Float.parseFloat(String.valueOf(Math.random()*10))));
        }
        romChart.setVisibility(View.VISIBLE);
        createLineChart(romChart,romData,label,Color.RED);
    }
    public void setMcpData(String label){
        ArrayList<Entry> romData=new ArrayList<>();

        for (int i=0;i<50;i++){
            romData.add(new Entry(i,Float.parseFloat(String.valueOf(Math.random()*10))));
        }
        mcpLineChart.setVisibility(View.VISIBLE);
        createLineChart(mcpLineChart,romData,label,Color.GREEN);
    }

    public  void createLineChart(LineChart chart,ArrayList<Entry> graphData,String legendText,int graphColor){


        XAxis xAxis=chart.getXAxis();
        xAxis.setDrawGridLines(false);      // remove vertical grid lines
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);      // set bottom x-axis
        xAxis.setTextColor(Color.GRAY);
        xAxis.setAxisLineWidth(5);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceMin(2f);
        //xAxis.setGranularity(1);

        chart.getAxisRight().setEnabled(false);

        YAxis yAxis=chart.getAxisLeft();
        yAxis.setTextColor(Color.GRAY);

        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);
        yAxis.setAxisMaximum(10);
        yAxis.setAxisMinimum(0.01f);
        yAxis.setAxisLineWidth(5);
        yAxis.setSpaceMin(2f);


        LineDataSet set =new LineDataSet(graphData,legendText);
        set.setDrawHighlightIndicators(false);
        set.setFillAlpha(30);
        set.setLineWidth(3);

        set.setColor(graphColor);
        set.setDrawValues(false);
        set.setDrawCircles(false);

        ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        dataSets.add(set);

        LineData data= new LineData(dataSets);
        data.setDrawValues(false);

        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(false);
        chart.animateX(1000);

        Legend legend=chart.getLegend();
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