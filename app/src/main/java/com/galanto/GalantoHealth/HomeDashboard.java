package com.galanto.GalantoHealth;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.scales.DateTime;
import com.example.newapp.R;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;


public class HomeDashboard extends Fragment {

    TextView currentDay,tvUserName,tvFingerNames,tvMcpScore,tvPIPScore,tvMcpTilte,tvPipTitle,tvPipCartTitle,tvMcpChartTitle;
    TextView currentDate,noticeTv,lastSessionTime;
    LineChart romChart,mcpLineChart;
    BarChart barChart,successRateBarChart;
    CardView gameCard,timeUsagesCard,romCard,hrCard,game1Card,calibrateCard;
    Animate animate;
    ImageView ivThumbIndicator,ivIndexIndicator,ivMiddleIndicator,ivRingIndicator,ivLittleIndicator,ivWristIndicator;
    ImageView ivThumbPIPInd,ivIndexPIPInd,ivMiddlePIPInd,ivRingPIPInd,ivLittlePIPInd;
    ImageView ivThumbMCPInd,ivIndexMCPInd,ivMiddleMCPInd,ivRingMCPInd,ivLittleMCPInd;
    LinearLayout cardParent,pipLayout,mcpLayout;
    ScrollView svCardScroll;
    boolean isTextClicked=false;
    GraphPlot graphPlot =new GraphPlot();
    Button refreshBtn;
    int p_id=0;
    FileDataBase fileDataBase;
    ArrayList<Float> timeStamp,mcpThumb,mcpIndex,mcpMiddle,mcpRing,mcpLittle,pipThumb,pipIndex,pipMiddle,pipRing,pipLittle,wrist;
    Long timeElapsedSession;
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


//        hrCard=view.findViewById(R.id.hrCard);
        romCard.setVisibility(View.INVISIBLE);
//        hrCard.setVisibility(View.INVISIBLE);
        tvUserName=view.findViewById(R.id.usrName);
        Intent intent=this.getActivity().getIntent();
        p_id= Integer.parseInt(intent.getStringExtra("p_id"));

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
        successRateBarChart=view.findViewById(R.id.successRateBarChart);
        game1Card=view.findViewById(R.id.game1Card);
        refreshBtn=view.findViewById(R.id.refreshBtn);
        calibrateCard=view.findViewById(R.id.calibrateCard);

        ivThumbIndicator =view.findViewById(R.id.thumbIndicator);
        ivIndexIndicator=view.findViewById(R.id.indexFgIndicator);
        ivMiddleIndicator=view.findViewById(R.id.middleFgIndicator);
        ivRingIndicator=view.findViewById(R.id.ringFgIndicator);
        ivLittleIndicator=view.findViewById(R.id.littleFgIndicator);
        ivWristIndicator=view.findViewById(R.id.wristIndicator);
        tvFingerNames=view.findViewById(R.id.fingName);
        lastSessionTime=view.findViewById(R.id.lastSsnTime);

        tvMcpTilte=view.findViewById(R.id.mcpTitle);
        tvPipTitle=view.findViewById(R.id.pipTitle);
        tvMcpScore=view.findViewById(R.id.mcpScore);
        tvPIPScore=view.findViewById(R.id.pipScore);
//        tvMcpChartTitle=view.findViewById(R.id.tvMcpChartTitle);
//        tvPipCartTitle=view.findViewById(R.id.tvPipChartTitle);

        cardParent=view.findViewById(R.id.cardParent);
        svCardScroll=view.findViewById(R.id.cardScrollview);

        ivThumbPIPInd=view.findViewById(R.id.ivThumbPIP);
        ivThumbMCPInd=view.findViewById(R.id.ivThumbMCP);
        ivIndexPIPInd=view.findViewById(R.id.ivIndexPIP);
        ivIndexMCPInd=view.findViewById(R.id.ivIndexMCP);
        ivMiddlePIPInd=view.findViewById(R.id.ivMiddlePIP);
        ivMiddleMCPInd=view.findViewById(R.id.ivMiddleMCP);
        ivRingPIPInd=view.findViewById(R.id.ivRingPIP);
        ivRingMCPInd=view.findViewById(R.id.ivRingMCP);
        ivLittlePIPInd=view.findViewById(R.id.ivLittlePIP);
        ivLittleMCPInd=view.findViewById(R.id.ivLittleMCP);

        pipLayout=view.findViewById(R.id.pip_score_layot);
        mcpLayout=view.findViewById(R.id.mcp_score_layout);

        fileDataBase=new FileDataBase(getActivity().getApplicationContext());

        setVisibility(2);
        setAlpha(0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager();
        }

        tvUserName=view.findViewById(R.id.usrName);

        Intent intent=this.getActivity().getIntent();
        tvUserName.setText(", "+intent.getStringExtra("user_name"));
        p_id= Integer.parseInt(intent.getStringExtra("p_id"));

        animate=new Animate(getContext());



        readSessionsData();

        setLineChart("PIP of Index",pipIndex);
        setMcpData("MCP of Index");
        graphPlot.setBarChartData(successRateBarChart);

        lastSessionTime.setText(String.valueOf(timeElapsedSession)+" min");

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshActivity();

            }
        });


        setDateTime(currentDate,currentDay);

        game1Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame("com.Galanto.Game1");
                noticeTv.setText("Please refresh to get the latest data.");
            }
        });

        calibrateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame("com.Galanto.Calibration");
                noticeTv.setText("Please refresh to get the latest data.");
            }
        });

        pipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPIP();
                isTextClicked=true;
                svCardScroll.smoothScrollTo(0,cardParent.getTop());

            }
        });

        mcpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMcp();
                isTextClicked=true;
                svCardScroll.smoothScrollTo(0,cardParent.getBottom());

            }
        });




        // check if scrollview has been triggered by touch
        svCardScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isTextClicked=false;
                return false;
            }
        });


        svCardScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // for scrollview triggered by title click then return
                if (isTextClicked){
                    return;
                }

                int x = scrollY - oldScrollY;
                if (x > 0) {
                    //scroll up
                    setAlpha(0);
                    tvPipTitle.setTextColor(getResources().getColor(R.color.black));
                    tvMcpTilte.setTextColor(getResources().getColor(R.color.light_grey));

                    tvPIPScore.setTextColor(Color.parseColor("#ff6666"));
                    tvMcpScore.setTextColor(getResources().getColor(R.color.light_grey));

                } else if (x < 0) {
                    //scroll down
                    setAlpha(1);
                    tvPipTitle.setTextColor(getResources().getColor(R.color.light_grey));
                    tvMcpTilte.setTextColor(getResources().getColor(R.color.black));

                    tvPIPScore.setTextColor(getResources().getColor(R.color.light_grey));
                    tvMcpScore.setTextColor(Color.parseColor("#ff6666"));
                } else {
                    tvPipCartTitle.setTextColor(getResources().getColor(R.color.light_grey));
                    tvMcpChartTitle.setTextColor(getResources().getColor(R.color.light_grey));
                }

            }
        });

        ivThumbIndicator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setClickAnimation(ivThumbIndicator,event,55,90,55,90);
                ivIndexIndicator.setColorFilter(null);
                ivThumbIndicator.setColorFilter(Color.RED);
                ivMiddleIndicator.setColorFilter(null);
                ivRingIndicator.setColorFilter(null);
                ivLittleIndicator.setColorFilter(null);
                ivWristIndicator.setColorFilter(null);
                setLineChart("PIP of Thumb",pipThumb);
                setMcpData("MCP of Thumb");
                tvFingerNames.setText("Thumb\nFinger");
                tvFingerNames.setTextSize(30);
                tvMcpTilte.setText("MCP");
                tvPipTitle.setText("PIP");
                tvMcpScore.setText("34");
                tvPIPScore.setText("16");
                setVisibility(1);
                return true;
            }
        });

        ivIndexIndicator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setClickAnimation(ivIndexIndicator,event,55,90,55,90);
                ivIndexIndicator.setColorFilter(Color.RED);
                ivThumbIndicator.setColorFilter(null);
                ivMiddleIndicator.setColorFilter(null);
                ivRingIndicator.setColorFilter(null);
                ivLittleIndicator.setColorFilter(null);
                ivWristIndicator.setColorFilter(null);

                setLineChart("PIP of Index",pipIndex);
                setMcpData("MCP of Index");
                tvFingerNames.setText("Index\nFinger");
                tvFingerNames.setTextSize(30);
                tvMcpTilte.setText("MCP");
                tvPipTitle.setText("PIP");
                tvMcpScore.setText("23");
                tvPIPScore.setText("35");
                setVisibility(2);
                return true;
            }
        });

        ivMiddleIndicator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setClickAnimation(ivMiddleIndicator,event,55,90,55,90);
                ivIndexIndicator.setColorFilter(null);
                ivThumbIndicator.setColorFilter(null);
                ivMiddleIndicator.setColorFilter(Color.RED);
                ivRingIndicator.setColorFilter(null);
                ivLittleIndicator.setColorFilter(null);
                ivWristIndicator.setColorFilter(null);
                setLineChart("PIP of Middle",pipMiddle);
                setMcpData("MCP of Middle");
                tvFingerNames.setText("Middle\nFinger");
                tvFingerNames.setTextSize(30);
                tvMcpTilte.setText("MCP");
                tvPipTitle.setText("PIP");
                tvMcpScore.setText("11");
                tvPIPScore.setText("16");
                setVisibility(3);
                return true;
            }
        });

        ivRingIndicator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setClickAnimation(ivRingIndicator,event,55,80,55,80);
                ivIndexIndicator.setColorFilter(null);
                ivThumbIndicator.setColorFilter(null);
                ivMiddleIndicator.setColorFilter(null);
                ivRingIndicator.setColorFilter(Color.RED);
                ivLittleIndicator.setColorFilter(null);
                ivWristIndicator.setColorFilter(null);
                setLineChart("PIP of Ring",pipRing);
                setMcpData("MCP of Ring");
                tvFingerNames.setText("Ring\nFinger");
                tvFingerNames.setTextSize(30);
                tvMcpTilte.setText("MCP");
                tvPipTitle.setText("PIP");
                tvMcpScore.setText("9");
                tvPIPScore.setText("17");
                setVisibility(4);
                return true;
            }
        });

        ivLittleIndicator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setClickAnimation(ivLittleIndicator,event,55,75,55,75);
                ivIndexIndicator.setColorFilter(null);
                ivThumbIndicator.setColorFilter(null);
                ivMiddleIndicator.setColorFilter(null);
                ivRingIndicator.setColorFilter(null);
                ivLittleIndicator.setColorFilter(Color.RED);
                ivWristIndicator.setColorFilter(null);
                setLineChart("PIP of Little",pipLittle);
                setMcpData("MCP of Little");
                tvFingerNames.setText("Little\nFinger");
                tvFingerNames.setTextSize(30);
                tvMcpTilte.setText("MCP");
                tvPipTitle.setText("PIP");
                tvMcpScore.setText("46");
                tvPIPScore.setText("67");
                setVisibility(5);
                return true;
            }
        });

        ivWristIndicator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                svCardScroll.smoothScrollTo(0,cardParent.getTop());
                setClickAnimation(ivWristIndicator,event,55,100,55,100);
                ivIndexIndicator.setColorFilter(null);
                ivThumbIndicator.setColorFilter(null);
                ivMiddleIndicator.setColorFilter(null);
                ivRingIndicator.setColorFilter(null);
                ivLittleIndicator.setColorFilter(null);
                ivWristIndicator.setColorFilter(Color.RED);
                setLineChart("Flex of Wrist",wrist);
                //setMcpData("MCP of Wrist");
                mcpLineChart.setVisibility(View.INVISIBLE);
                tvFingerNames.setText("Wrist");
                tvFingerNames.setTextSize(30);
                tvMcpTilte.setText("Flex");
                tvPipTitle.setText("");
                tvMcpScore.setText("13");
                tvPIPScore.setText("");
                setMcp();
                setVisibility(0);
                return true;
            }
        });



    }

    public void setPIP(){
        tvPIPScore.setTextColor(Color.parseColor("#ff6666"));
        tvMcpScore.setTextColor(getResources().getColor(R.color.light_grey));
        tvPipTitle.setTextColor(getResources().getColor(R.color.black));
        tvMcpTilte.setTextColor(getResources().getColor(R.color.light_grey));
//        tvPipCartTitle.setTextColor(getResources().getColor(R.color.dark_blue));
//        tvMcpChartTitle.setTextColor(getResources().getColor(R.color.light_grey));
        setAlpha(0);
        isTextClicked=true;

    }
    public void setMcp(){
        tvPIPScore.setTextColor(getResources().getColor(R.color.light_grey));
        tvMcpScore.setTextColor(Color.parseColor("#ff6666"));
        tvPipTitle.setTextColor(getResources().getColor(R.color.light_grey));
        tvMcpTilte.setTextColor(getResources().getColor(R.color.black));
//        tvPipCartTitle.setTextColor(getResources().getColor(R.color.light_grey));
//        tvMcpChartTitle.setTextColor(getResources().getColor(R.color.dark_blue));
        setAlpha(1);
    }


        // to make click animation of fingers touch points
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

        public void setAlpha(int jointsNumber){
            // 0: for PIP
            // 1 : for MCP
            if (jointsNumber==0){


                ivThumbPIPInd.setImageAlpha(255);
                ivThumbMCPInd.setImageAlpha(128);
                ivIndexPIPInd.setImageAlpha(255);
                ivIndexMCPInd.setImageAlpha(128);
                ivMiddlePIPInd.setImageAlpha(255);
                ivMiddleMCPInd.setImageAlpha(128);
                ivRingPIPInd.setImageAlpha(255);
                ivRingMCPInd.setImageAlpha(128);
                ivLittlePIPInd.setImageAlpha(255);
                ivLittleMCPInd.setImageAlpha(128);


            }else if (jointsNumber==1){
                ivThumbPIPInd.setImageAlpha(128);
                ivThumbMCPInd.setImageAlpha(255);
                ivIndexPIPInd.setImageAlpha(128);
                ivIndexMCPInd.setImageAlpha(255);
                ivMiddlePIPInd.setImageAlpha(128);
                ivMiddleMCPInd.setImageAlpha(255);
                ivRingPIPInd.setImageAlpha(128);
                ivRingMCPInd.setImageAlpha(255);
                ivLittlePIPInd.setImageAlpha(128);
                ivLittleMCPInd.setImageAlpha(255);

            }

        }

        public void setVisibility(int fingerNumber){

        switch (fingerNumber){
            case 1:
                ivThumbPIPInd.setVisibility(View.VISIBLE);
                ivThumbMCPInd.setVisibility(View.VISIBLE);
                ivIndexPIPInd.setVisibility(View.INVISIBLE);
                ivIndexMCPInd.setVisibility(View.INVISIBLE);
                ivMiddlePIPInd.setVisibility(View.INVISIBLE);
                ivMiddleMCPInd.setVisibility(View.INVISIBLE);
                ivRingPIPInd.setVisibility(View.INVISIBLE);
                ivRingMCPInd.setVisibility(View.INVISIBLE);
                ivLittlePIPInd.setVisibility(View.INVISIBLE);
                ivLittleMCPInd.setVisibility(View.INVISIBLE);
                break;

            case 2:
                ivThumbPIPInd.setVisibility(View.INVISIBLE);
                ivThumbMCPInd.setVisibility(View.INVISIBLE);
                ivIndexPIPInd.setVisibility(View.VISIBLE);
                ivIndexMCPInd.setVisibility(View.VISIBLE);
                ivMiddlePIPInd.setVisibility(View.INVISIBLE);
                ivMiddleMCPInd.setVisibility(View.INVISIBLE);
                ivRingPIPInd.setVisibility(View.INVISIBLE);
                ivRingMCPInd.setVisibility(View.INVISIBLE);
                ivLittlePIPInd.setVisibility(View.INVISIBLE);
                ivLittleMCPInd.setVisibility(View.INVISIBLE);
                break;

            case 3:
                ivThumbPIPInd.setVisibility(View.INVISIBLE);
                ivThumbMCPInd.setVisibility(View.INVISIBLE);
                ivIndexPIPInd.setVisibility(View.INVISIBLE);
                ivIndexMCPInd.setVisibility(View.INVISIBLE);
                ivMiddlePIPInd.setVisibility(View.VISIBLE);
                ivMiddleMCPInd.setVisibility(View.VISIBLE);
                ivRingPIPInd.setVisibility(View.INVISIBLE);
                ivRingMCPInd.setVisibility(View.INVISIBLE);
                ivLittlePIPInd.setVisibility(View.INVISIBLE);
                ivLittleMCPInd.setVisibility(View.INVISIBLE);
                break;

            case 4:
                ivThumbPIPInd.setVisibility(View.INVISIBLE);
                ivThumbMCPInd.setVisibility(View.INVISIBLE);
                ivIndexPIPInd.setVisibility(View.INVISIBLE);
                ivIndexMCPInd.setVisibility(View.INVISIBLE);
                ivMiddlePIPInd.setVisibility(View.INVISIBLE);
                ivMiddleMCPInd.setVisibility(View.INVISIBLE);
                ivRingPIPInd.setVisibility(View.VISIBLE);
                ivRingMCPInd.setVisibility(View.VISIBLE);
                ivLittlePIPInd.setVisibility(View.INVISIBLE);
                ivLittleMCPInd.setVisibility(View.INVISIBLE);
                break;

            case 5:
                ivThumbPIPInd.setVisibility(View.INVISIBLE);
                ivThumbMCPInd.setVisibility(View.INVISIBLE);
                ivIndexPIPInd.setVisibility(View.INVISIBLE);
                ivIndexMCPInd.setVisibility(View.INVISIBLE);
                ivMiddlePIPInd.setVisibility(View.INVISIBLE);
                ivMiddleMCPInd.setVisibility(View.INVISIBLE);
                ivRingPIPInd.setVisibility(View.INVISIBLE);
                ivRingMCPInd.setVisibility(View.INVISIBLE);
                ivLittlePIPInd.setVisibility(View.VISIBLE);
                ivLittleMCPInd.setVisibility(View.VISIBLE);
                break;

            default:
                ivThumbPIPInd.setVisibility(View.INVISIBLE);
                ivThumbMCPInd.setVisibility(View.INVISIBLE);
                ivIndexPIPInd.setVisibility(View.INVISIBLE);
                ivIndexMCPInd.setVisibility(View.INVISIBLE);
                ivMiddlePIPInd.setVisibility(View.INVISIBLE);
                ivMiddleMCPInd.setVisibility(View.INVISIBLE);
                ivRingPIPInd.setVisibility(View.INVISIBLE);
                ivRingMCPInd.setVisibility(View.INVISIBLE);
                ivLittlePIPInd.setVisibility(View.INVISIBLE);
                ivLittleMCPInd.setVisibility(View.INVISIBLE);

        }

        }

    public void setLineChart(String label,ArrayList<Float> arrayList){
        ArrayList<Entry> romData=new ArrayList<>();
        Float xValues;
        Float minXaxis,minYaxis,maxXaxis,maxYaxis;
        minXaxis=Float.MAX_VALUE;
        minYaxis=Float.MAX_VALUE;
        maxXaxis=Float.MIN_VALUE;
        maxYaxis=Float.MIN_VALUE;

        for (int i=0;i<arrayList.size();i++){
            if(timeStamp.size()>0){
                xValues=(Float) timeStamp.get(i);
            }else {
                xValues=Float.parseFloat(String.valueOf(i));
            }
            if (xValues<minXaxis){
                minXaxis=xValues;
            }

            if(xValues>maxXaxis){
                maxXaxis=xValues;
            }

            if (arrayList.get(i)<minYaxis){
                minYaxis=arrayList.get(i);
            }

            if(arrayList.get(i)>maxYaxis){
                maxYaxis=arrayList.get(i);
            }


           // romData.add(new Entry(i,Float.parseFloat(String.valueOf(Math.random()*10))));
            romData.add(new Entry(xValues,arrayList.get(i)));
        }
        romChart.setVisibility(View.VISIBLE);

        graphPlot.createLineChart(romChart,romData,label,Color.WHITE,minXaxis,minYaxis,maxXaxis,maxYaxis);
        //createLineChart(romChart,romData,label,Color.WHITE);
    }
    public void setMcpData(String label){
        ArrayList<Entry> romData=new ArrayList<>();

        for (int i=0;i<50;i++){
            romData.add(new Entry(i,Float.parseFloat(String.valueOf(Math.random()*10))));
        }
        mcpLineChart.setVisibility(View.VISIBLE);
        //createLineChart(mcpLineChart,romData,label,Color.WHITE);
        graphPlot.createLineChart(mcpLineChart,romData,label,Color.WHITE,0f,0f,50f,10f);
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

    public void openGame(String packageName){
        Intent intent =getActivity().getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent!=null){
            Toast.makeText(getActivity().getApplicationContext().getApplicationContext(),"Opening Game",Toast.LENGTH_SHORT).show();
            startActivity(intent);
            getActivity().finishAndRemoveTask();
        }else
        {
            Toast.makeText(getActivity().getApplicationContext(), "App not installed",Toast.LENGTH_SHORT).show();
        }
    }

    public void refreshActivity(){
        getActivity().finish();
        getActivity().startActivity(getActivity().getIntent());
    }

    public void  readSessionsData(){
        String lastSession;
        int lastGameId=0;
        fileDataBase=new FileDataBase(getActivity().getApplicationContext());
        String folderName="patient_"+String.valueOf(p_id);
        LocalDateTime ssnStTime,ssnEndTime;

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyy   HH:mm");

        try {
            String allSessionData= fileDataBase.readFile("Galanto/RehabRelive/"+folderName,"all_sessions.json");

            JSONObject jsonObject=new JSONObject(allSessionData);
            JSONArray jsonArray=jsonObject.getJSONArray("allSessionDatas");
            JSONObject lastJsonObject=jsonArray.getJSONObject(jsonArray.length()-1);
            lastSession=lastJsonObject.getString("session_id");
            lastGameId=lastJsonObject.getInt("game_id");
            ssnStTime= LocalDateTime.parse(lastJsonObject.getString("start_time_stamp"),formatter1);
            ssnEndTime=LocalDateTime.parse(lastJsonObject.getString("stop_time_stamp"),formatter1);
            timeElapsedSession= ssnStTime.until(ssnEndTime, ChronoUnit.MINUTES);

            if (jsonArray.length()>20){
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject arrayObject=jsonArray.getJSONObject(i);
                    //timeStamp.add(Float.parseFloat(arrayObject.getString("time_since_startup")));

                    mcpThumb.add(Float.parseFloat(arrayObject.getString("thumb_mcp_max"))
                            -Float.parseFloat(arrayObject.getString("thumb_mcp_min")));
                    mcpIndex.add(Float.parseFloat(arrayObject.getString("index_mcp_max"))
                            -Float.parseFloat(arrayObject.getString("index_mcp_min")));
                    mcpMiddle.add(Float.parseFloat(arrayObject.getString("middle_mcp_max"))
                            -Float.parseFloat(arrayObject.getString("middle_mcp_min")));
                    mcpRing.add(Float.parseFloat(arrayObject.getString("ring_mcp_max"))
                            -Float.parseFloat(arrayObject.getString("ring_mcp_min")));
                    mcpLittle.add(Float.parseFloat(arrayObject.getString("pinky_mcp_max"))
                            -Float.parseFloat(arrayObject.getString("pinky_mcp_min")));

                    pipIndex.add(Float.parseFloat(arrayObject.getString("index_pip_max"))
                            -Float.parseFloat(arrayObject.getString("index_pip_min")));
                    pipMiddle.add(Float.parseFloat(arrayObject.getString("middle_pip_max"))
                            -Float.parseFloat(arrayObject.getString("middle_pip_min")));
                    pipRing.add(Float.parseFloat(arrayObject.getString("ring_pip_max"))
                            -Float.parseFloat(arrayObject.getString("ring_pip_min")));
                    pipLittle.add(Float.parseFloat(arrayObject.getString("pinky_mcp_max"))
                            -Float.parseFloat(arrayObject.getString("pinky_mcp_min")));
                    pipThumb.add(Float.parseFloat(arrayObject.getString("thumb_pip_max"))
                            -Float.parseFloat(arrayObject.getString("thumb_pip_min")));

                    wrist.add(Float.parseFloat(arrayObject.getString("wrist_max"))
                            -Float.parseFloat(arrayObject.getString("wrist_min")));


                }

            }else{
                String lastSessionData=fileDataBase.readFile("Galanto/RehabRelive/"+folderName,lastSession+".json");
                JSONObject object=new JSONObject(lastSessionData);
                JSONArray array=object.getJSONArray("fineSessionDatas");

                // Only read 100 data points
                int arrayLength=0;
                if (array.length()>100){
                    arrayLength=100;
                }else{
                    arrayLength=array.length();
                }

                timeStamp=new ArrayList<>();
                mcpThumb  =new ArrayList<>();
                mcpIndex	=new ArrayList<>();
                mcpMiddle=new ArrayList<>();
                mcpRing		=new ArrayList<>();
                mcpLittle=new ArrayList<>();
                pipIndex	=new ArrayList<>();
                pipMiddle=new ArrayList<>();
                pipRing		=new ArrayList<>();
                pipLittle=new ArrayList<>();
                pipThumb	=new ArrayList<>();
                wrist		=new ArrayList<>();

                for (int i =0;i<arrayLength;i++){
                    JSONObject arrayObject=array.getJSONObject(i);


                    timeStamp.add(Float.parseFloat(arrayObject.getString("time_since_startup")));

                    mcpThumb.add(Float.parseFloat(arrayObject.getString("thumb_mcp")));
                    mcpIndex.add(Float.parseFloat(arrayObject.getString("index_mcp")));
                    mcpMiddle.add(Float.parseFloat(arrayObject.getString("middle_mcp")));
                    mcpRing.add(Float.parseFloat(arrayObject.getString("ring_mcp")));
                    mcpLittle.add(Float.parseFloat(arrayObject.getString("pinky_mcp")));

                    pipIndex.add(Float.parseFloat(arrayObject.getString("index_pip")));
                    pipMiddle.add(Float.parseFloat(arrayObject.getString("middle_pip")));
                    pipRing.add(Float.parseFloat(arrayObject.getString("ring_pip")));
                    pipLittle.add(Float.parseFloat(arrayObject.getString("ring_mcp")));
                    pipThumb.add(Float.parseFloat(arrayObject.getString("pinky_pip")));

                    wrist.add(Float.parseFloat(arrayObject.getString("wrist")));

                }
            }

        }catch (Exception ex){
            Toast.makeText(getContext(),"Error in read session data: "+ex.toString(),Toast.LENGTH_SHORT).show();
        }
    }

}