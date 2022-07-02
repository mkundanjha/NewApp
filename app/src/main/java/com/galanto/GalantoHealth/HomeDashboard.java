package com.galanto.GalantoHealth;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newapp.R;
import com.galanto.GalantoHealth.ui.SessionsLogic;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.psambit9791.jdsp.signal.Smooth;
import com.github.psambit9791.jdsp.signal.peaks.FindPeak;
import com.github.psambit9791.jdsp.signal.peaks.Peak;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class HomeDashboard extends Fragment {

    TextView movScore,tvUserName,tvFingerNames,tvMcpScore,tvPIPScore,tvMcpTilte,tvPipTitle,tvPipCartTitle,tvMcpChartTitle,romIncreaseText,totalPlayTime;
    TextView currentDate,noticeTv, tvSessionTime,handImpairedValue,ageValueText,genderValueText,welcome_message,welcome_header,romPointerScore,gameScoreText,pidText;
    LineChart romChart,fingerRomChart;
    BarChart barChart,successRateBarChart;
    PieChart successScorePieChart,romPC;
    CardView gameCard,timeUsagesCard,romCard,hrCard,game1Card,game2Card,calibrateCard,statsCard,barGraphCard;
    CardView patient_info_card,mcpChartCard,gameScoreCard,deleteSessionCard;
    Animate animate;
    ImageView ivThumbIndicator,ivIndexIndicator,ivMiddleIndicator,ivRingIndicator,ivLittleIndicator,ivWristIndicator;
    ImageView ivThumbPIPInd,ivIndexPIPInd,ivMiddlePIPInd,ivRingPIPInd,ivLittlePIPInd;
    ImageView ivThumbMCPInd,ivIndexMCPInd,ivMiddleMCPInd,ivRingMCPInd,ivLittleMCPInd,romIncreaseIcon;
    LinearLayout cardParent,pipLayout,mcpLayout,pipPointer,mcpPointer,pipChartLayout,mcpChartLayout,gameScoreLayout;
    ScrollView svCardScroll;
    boolean isTextClicked=false,isNewUser=true;
    GraphPlot graphPlot =new GraphPlot();
    ImageButton refreshBtn;
    Spinner sessionSpinner,dateSpinner;
    int p_id=0,totalFiles=0,gmScore1=0,gmScore2=0,gmScore3=0,fingerIndex=1;
    ArrayList<Integer> gameScore,successRate;
    ArrayList<String> handSegment,sessionStartTimeArray;
    int tPipScore=0,iPipScore=0,mPipScore=0,rPipScore=0,lPipScore=0,tMcpScore=0,iMcpScore=0,mMcpScore=0,rMcpScore=0,lMcpScore=0,wScore=0;
    int[] pipScoreList,mcpScoreList;
    FileDataBase fileDataBase;
    ArrayList<Float> timeStamp,mcpThumb,mcpIndex,mcpMiddle,mcpRing,mcpLittle,pipThumb,pipIndex,pipMiddle,pipRing,pipLittle,wrist;
    Long timeElapsedSession, sessionPlayTime;
    Float hitRate,avRomScore;
    int movementScore1=0,movementScore2=0,movementScore3=0,romPercChange=0;
    boolean isGameSarted=false;
    RomPerSession romPerSession;
    ArrayList<RomPerSession> romPerSessionsArray;
    ArrayList<String> dateSpinnerArray,sessionSpinnerArray;
    int totalSessionsCount=0;
    String[] jointNames=new String[]{"wrist","thumb","index","middle","ring","pinky"};
    Map<Integer,ArrayList<Float>> mcpJoints;//=new HashMap<Integer,ArrayList<Float>>() {};
    Map<Integer,ArrayList<Float>> pipJoints;//=new HashMap<Integer,ArrayList<Float>>() {};
    int[] romPip;
    int[] romMcp;
    int gameScoreData=0;
    int successScoreData=0;
    String handSegmentData="";
    ArrayList<Float> pipTrend,mcpTrend;
    SessionsLogic sessionsLogic;
    ArrayList<LocalDate> romDates;
    Logics logics;
    String mcpScore="0",pipScore="0";

    //String handSegment;
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
        return inflater.inflate(R.layout.fragment_home_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        romChart=view.findViewById(R.id.romChart);
//        mcpLineChart=view.findViewById(R.id.mcpChart);
        successRateBarChart=view.findViewById(R.id.successRateBarChart);
        game1Card=view.findViewById(R.id.game1Card);
        game2Card=view.findViewById(R.id.game2Card);
        refreshBtn=view.findViewById(R.id.refreshBtn);
        calibrateCard=view.findViewById(R.id.calibrateCard);
        fingerRomChart=view.findViewById(R.id.fingerRomChart);


        successScorePieChart =view.findViewById(R.id.scorePiechart);


        romCard=view.findViewById(R.id.romCard);
        pipPointer=view.findViewById(R.id.pipPointerLayout);
        mcpPointer=view.findViewById(R.id.mcpPointerLayout);
        romPointerScore=view.findViewById(R.id.romPointerScore);

        pipChartLayout=view.findViewById(R.id.pipChartLayout);

        gameScoreText=view.findViewById(R.id.gameScoreText);



        sessionSpinner=view.findViewById(R.id.sessionSpinner);

        ivThumbIndicator =view.findViewById(R.id.thumbIndicator);
        ivIndexIndicator=view.findViewById(R.id.indexFgIndicator);
        ivMiddleIndicator=view.findViewById(R.id.middleFgIndicator);
        ivRingIndicator=view.findViewById(R.id.ringFgIndicator);
        ivLittleIndicator=view.findViewById(R.id.littleFgIndicator);
        ivWristIndicator=view.findViewById(R.id.wristIndicator);
        tvFingerNames=view.findViewById(R.id.fingName);
        tvSessionTime =view.findViewById(R.id.ssnTime);

        movScore=view.findViewById(R.id.movScore);


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
        pidText=view.findViewById(R.id.pidText);


        barGraphCard=view.findViewById(R.id.barGraphCard);
        statsCard=view.findViewById(R.id.statsCard);

        dateSpinner=view.findViewById(R.id.dateSpinner);
        deleteSessionCard=view.findViewById(R.id.deleteSessionCard);



        fileDataBase=new FileDataBase(getActivity().getApplicationContext());
        logics=new Logics(getActivity().getApplicationContext());
        sessionsLogic=new SessionsLogic(getActivity().getApplicationContext());

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Environment.isExternalStorageManager();
            }



            Intent intent = this.getActivity().getIntent();


            p_id = Integer.parseInt(intent.getStringExtra("p_id"));



            pidText.setText("UsrID: " + p_id);

            getActivity().getIntent().setAction("Already created");

            animate = new Animate(getContext());
            hitRate = 0f;
            movementScore1 = 0;

            readSessionsData();
            setRomData(p_id);
            setSpinnerData();

            if (hitRate <= 0) {
                hitRate = 0.4f;
            }



            if (isNewUser) {

                romCard.setVisibility(View.INVISIBLE);

                ivThumbIndicator.setVisibility(View.INVISIBLE);
                ivIndexIndicator.setVisibility(View.INVISIBLE);
                ivMiddleIndicator.setVisibility(View.INVISIBLE);
                ivRingIndicator.setVisibility(View.INVISIBLE);
                ivLittleIndicator.setVisibility(View.INVISIBLE);
                ivWristIndicator.setVisibility(View.INVISIBLE);

                barGraphCard.setVisibility(View.INVISIBLE);

                pipPointer.setClickable(false);
                mcpPointer.setClickable(false);
                fingerIndex = 0;
                mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));

            }

            ivThumbIndicator.setVisibility(View.INVISIBLE);
            ivIndexIndicator.setVisibility(View.INVISIBLE);
            ivMiddleIndicator.setVisibility(View.INVISIBLE);
            ivRingIndicator.setVisibility(View.INVISIBLE);
            ivLittleIndicator.setVisibility(View.INVISIBLE);
            ivWristIndicator.setVisibility(View.INVISIBLE);

//            statsCard.setVisibility(View.INVISIBLE);
            barGraphCard.setVisibility(View.INVISIBLE);
//            svCardScroll.setVisibility(View.INVISIBLE);


            if (!isNewUser) {



                //Data for pie charts
                ArrayList<PieEntry> entries = new ArrayList<>();
                ArrayList<PieEntry> mvEntries = new ArrayList<>();
                ArrayList<PieEntry> avRomEntries = new ArrayList<>();

                entries.add(new PieEntry(hitRate));
                entries.add(new PieEntry(100 - hitRate));


                avRomEntries.add(new PieEntry(avRomScore));
                avRomEntries.add(new PieEntry(100 - avRomScore));

                //game score
//                startCountAnimation(gmScore3, gameScoreText);


                ivThumbIndicator.setVisibility(View.VISIBLE);
                ivIndexIndicator.setVisibility(View.VISIBLE);
                ivMiddleIndicator.setVisibility(View.VISIBLE);
                ivRingIndicator.setVisibility(View.VISIBLE);
                ivLittleIndicator.setVisibility(View.VISIBLE);
                ivWristIndicator.setVisibility(View.VISIBLE);

                setVisibility(2);
                setAlpha(0);
                statsCard.setVisibility(View.VISIBLE);
                barGraphCard.setVisibility(View.VISIBLE);



                //handSegment.add("W");
                tvFingerNames.setTextSize(20);
                int colour=Color.parseColor("#cc5656");
                //set rom percentage change



                pipScoreList = new int[]{wScore, tPipScore, iPipScore, mPipScore, rPipScore, lPipScore};
                mcpScoreList = new int[]{wScore, tMcpScore, iMcpScore, mMcpScore, rMcpScore, lMcpScore};

                if (handSegment.size() != 0) {
                    setFingerDataOnScreenLoad(handSegment.get(handSegment.size() - 1));
                } else {
                    setFingerDataOnScreenLoad("W");
                }



                romPointerScore.setText(mcpScore);

//                graphPlot.setBarChartData(successRateBarChart, "Last 3 game score", barData, maxYaxis);
                graphPlot.createPieChart(successScorePieChart, entries, String.valueOf(Math.round(hitRate)), Color.parseColor("#4287f5"));

            }



            refreshBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshActivity();

                }
            });


            pipPointer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fingerIndex == 0) {
                        return;
                    }
//                    setPIP();
                    setAlpha(0);


//                    romPointerScore.setText(String.valueOf(romPip[fingerIndex]));
                    romPointerScore.setText(pipScore);
                    setLineChart(romChart, "Thumb (MCP)", pipJoints.get(fingerIndex), Color.parseColor("#e85f56"));
                    pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));
                    mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                    plotAllFigureRom("pip",Color.parseColor("#DC6868"));
                }
            });

            mcpPointer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fingerIndex == 0) {
                        return;
                    }
//                    setMcp();
                    setAlpha(1);

//                    romPointerScore.setText(String.valueOf(getPeakData(mcpJoints.get(fingerIndex))));
//                    romPointerScore.setText(String.valueOf(romMcp[fingerIndex]));
                    romPointerScore.setText(mcpScore);
                    setLineChart(romChart, "Thumb (MCP)", mcpJoints.get(fingerIndex), Color.parseColor("#4a9150"));
//                    popChartLayout(mcpChartLayout, 275, 465);
//                    popChartLayout(pipChartLayout, 250, 450);
                    //romPointerScore.setText("88");
                    mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));
                    pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                    plotAllFigureRom("mcp",Color.parseColor("#68b9dc"));
                }
            });





            ivThumbIndicator.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //pipLayout.setVisibility(View.VISIBLE);
                    setClickAnimation(ivThumbIndicator, event, 55, 105, 55, 105);
                    ivIndexIndicator.setColorFilter(null);
                    ivThumbIndicator.setColorFilter(Color.RED);
                    ivMiddleIndicator.setColorFilter(null);
                    ivRingIndicator.setColorFilter(null);
                    ivLittleIndicator.setColorFilter(null);
                    ivWristIndicator.setColorFilter(null);

                    setFingerDataOnScreenLoad("T");
                    tvFingerNames.setText("Thumb\nFinger");
                    tvFingerNames.setTextSize(20);


                    setVisibility(1);
                    return true;
                }
            });

            ivIndexIndicator.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //pipLayout.setVisibility(View.VISIBLE);
                    setClickAnimation(ivIndexIndicator, event, 55, 90, 55, 90);
                    ivIndexIndicator.setColorFilter(Color.RED);
                    ivThumbIndicator.setColorFilter(null);
                    ivMiddleIndicator.setColorFilter(null);
                    ivRingIndicator.setColorFilter(null);
                    ivLittleIndicator.setColorFilter(null);
                    ivWristIndicator.setColorFilter(null);
                    setFingerDataOnScreenLoad("I");

//                setLineChart("PIP of Index",pipIndex);
//                setMcpData("MCP of Index");
                    tvFingerNames.setText("Index\nFinger");
                    tvFingerNames.setTextSize(20);

                    setVisibility(2);
                    return true;
                }
            });

            ivMiddleIndicator.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //pipLayout.setVisibility(View.VISIBLE);
                    setClickAnimation(ivMiddleIndicator, event, 55, 90, 55, 90);
                    ivIndexIndicator.setColorFilter(null);
                    ivThumbIndicator.setColorFilter(null);
                    ivMiddleIndicator.setColorFilter(Color.RED);
                    ivRingIndicator.setColorFilter(null);
                    ivLittleIndicator.setColorFilter(null);
                    ivWristIndicator.setColorFilter(null);

                    setFingerDataOnScreenLoad("M");
//                setLineChart("PIP of Middle",pipMiddle);
//                setMcpData("MCP of Middle");
                    tvFingerNames.setText("Middle\nFinger");
                    tvFingerNames.setTextSize(20);

                    setVisibility(3);
                    return true;
                }
            });

            ivRingIndicator.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //pipLayout.setVisibility(View.VISIBLE);
                    setClickAnimation(ivRingIndicator, event, 55, 80, 55, 80);
                    ivIndexIndicator.setColorFilter(null);
                    ivThumbIndicator.setColorFilter(null);
                    ivMiddleIndicator.setColorFilter(null);
                    ivRingIndicator.setColorFilter(Color.RED);
                    ivLittleIndicator.setColorFilter(null);
                    ivWristIndicator.setColorFilter(null);
                    setFingerDataOnScreenLoad("R");

                    tvFingerNames.setText("Ring\nFinger");
                    tvFingerNames.setTextSize(20);

                    setVisibility(4);
                    return true;
                }
            });

            ivLittleIndicator.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //pipLayout.setVisibility(View.VISIBLE);
                    setClickAnimation(ivLittleIndicator, event, 55, 75, 55, 75);
                    ivIndexIndicator.setColorFilter(null);
                    ivThumbIndicator.setColorFilter(null);
                    ivMiddleIndicator.setColorFilter(null);
                    ivRingIndicator.setColorFilter(null);
                    ivLittleIndicator.setColorFilter(Color.RED);
                    ivWristIndicator.setColorFilter(null);
                    setFingerDataOnScreenLoad("P");
                    tvFingerNames.setText("Little\nFinger");
                    tvFingerNames.setTextSize(20);

                    setVisibility(5);
                    return true;
                }
            });

            ivWristIndicator.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                    mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));

                    setClickAnimation(ivWristIndicator, event, 55, 100, 55, 100);
                    ivIndexIndicator.setColorFilter(null);
                    ivThumbIndicator.setColorFilter(null);
                    ivMiddleIndicator.setColorFilter(null);
                    ivRingIndicator.setColorFilter(null);
                    ivLittleIndicator.setColorFilter(null);
                    ivWristIndicator.setColorFilter(Color.RED);
                    setFingerDataOnScreenLoad("W");

                    tvFingerNames.setText("Wrist");
                    tvFingerNames.setTextSize(20);

                    setVisibility(0);
                    return true;
                }
            });

            sessionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        setSessionData(sessionSpinner.getSelectedItem().toString());
                    String indexSession[] = sessionSpinner.getSelectedItem().toString().split("_");
                    int index = Integer.parseInt(indexSession[indexSession.length - 1]);
                        setSessionDetails(sessionSpinner.getSelectedItem().toString().trim(),p_id);
                        String arr[] = handSegmentData.split(",", 2);
                        setRomPerJointData(index-1);
                        setFingerDataOnScreenLoad(arr[0]);

                    }catch (Exception ex){
                        Toast.makeText(getContext(),"Error in session spinner: "+ex.toString(),Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    setSessionSpinner(dateSpinner.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            deleteSessionCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialoge("Delete Session","Do you really want to delete "+sessionSpinner.getSelectedItem().toString().trim()+" ?");

                }
            });
        }catch (Exception ex){
            Toast.makeText(getContext(),"Error in main HomeDasboard:"+ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }



    private void setRomPerJointData(int index){
        String response="";
        try{
            response= fileDataBase.readFile("Galanto/RehabRelive/patient_"+String.valueOf(p_id),"all_sessions.json");
            if (response.isEmpty()){
                return;
            }

            wScore=20;
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("allSessionDatas");
            JSONObject romObject=jsonArray.getJSONObject(index);
            romPerSession=new RomPerSession(romObject.getInt("thumb_mcp_max"),romObject.getInt("thumb_mcp_min"),romObject.getInt("thumb_pip_max"),romObject.getInt("thumb_pip_min"),
                    romObject.getInt("index_mcp_max"),romObject.getInt("index_mcp_min"),romObject.getInt("index_pip_max"),romObject.getInt("index_pip_min"),
                    romObject.getInt("middle_mcp_max"),romObject.getInt("middle_mcp_min"),romObject.getInt("middle_pip_max"),romObject.getInt("middle_pip_min"),
                    romObject.getInt("ring_mcp_max"),romObject.getInt("ring_mcp_min"),romObject.getInt("ring_pip_max"),romObject.getInt("ring_pip_min"),
                    romObject.getInt("pinky_mcp_max"),romObject.getInt("pinky_mcp_min"),romObject.getInt("pinky_pip_max"),romObject.getInt("pinky_pip_min"),
                    romObject.getInt("wrist_max"),romObject.getInt("wrist_min"));

        }catch (Exception e){
            Toast.makeText(getContext(),"Error in setRomperJointData: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void  setSpinnerData(){
        String response="";
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyy   HH:mm");
        SimpleDateFormat formater=new SimpleDateFormat("dd MMM");
        //sessionSpinner.setBackgroundColor(Color.RED);
        try{
            response= fileDataBase.readFile("Galanto/RehabRelive/patient_"+String.valueOf(p_id),"all_sessions.json");
            if (response.isEmpty()){
                return;
            }
            //isNewUser=false;
            JSONObject jsonObject=new JSONObject(response);

            JSONArray jsonArray=jsonObject.getJSONArray("allSessionDatas");
            dateSpinnerArray=new ArrayList<>();
            sessionSpinnerArray=new ArrayList<>();
//            JSONObject lastJsonObject=jsonArray.getJSONObject(jsonArray.length()-1);

            for (int i=jsonArray.length()-1;i>=0;i--){
                String date=formater.format(sdf.parse(jsonArray.getJSONObject(i).getString("start_time_stamp")));
                String sessionId=jsonArray.getJSONObject(i).getString("session_id");
                dateSpinnerArray.add(date);
                sessionSpinnerArray.add(sessionId);
            }
            setRomPerJointData(jsonArray.length()-1);
            ArrayList<String> dateSpinnerData=new ArrayList<>();

            for (int i=0;i<dateSpinnerArray.size();i++){
                if(i>0){
                    if(dateSpinnerArray.get(i).equals(dateSpinnerArray.get(i-1)) ){
                        continue;
                    }
                }
                    dateSpinnerData.add(dateSpinnerArray.get(i));

            }
            ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(getContext(), R.layout.spinner_item2,dateSpinnerData);
            dateSpinner.setAdapter(spinnerAdapter);


        }catch (Exception ex){
            Toast.makeText(getContext(),"Error in setSpinnerData: "+ex.toString(),Toast.LENGTH_SHORT).show();
        }

    }

    public void setSessionSpinner(String dateValue){

        try{
            ArrayList<String> sessionSpinnerData=new ArrayList<>();

            //sessionSpinner.setSelection(sessionSpinnerData.size()-1);
            for(int i=0;i<dateSpinnerArray.size();i++){
                if (dateSpinnerArray.get(i).equals(dateValue)){
                    sessionSpinnerData.add(sessionSpinnerArray.get(i));
                }
                ArrayAdapter<String> spinnerAdapter2=new ArrayAdapter<String>(getContext(), R.layout.spinner_item2,sessionSpinnerData);
                sessionSpinner.setAdapter(spinnerAdapter2);
            }
        }catch (Exception e){

        }
    }


    private void setSessionData(String sessionName){
        String lastSessionData=fileDataBase.readFile("Galanto/RehabRelive/patient_"+String.valueOf(p_id),sessionName+".json");
        if (lastSessionData.isEmpty()){
            return;
        }
        try{
        JSONObject object=new JSONObject(lastSessionData);
        JSONArray array=object.getJSONArray("fineSessionDatas");

        // Only read 100 data points
        int increment=1;
        if (array.length()>100){
            increment=array.length()/100;
            //arrayLength=array.length();
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

        for (int i =0;i<array.length();i+=increment){
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
            pipLittle.add(Float.parseFloat(arrayObject.getString("pinky_pip")));
            pipThumb.add(Float.parseFloat(arrayObject.getString("thumb_pip")));

            wrist.add(Float.parseFloat(arrayObject.getString("wrist")));

        }
            mcpJoints=new HashMap<Integer,ArrayList<Float>>() {};
            pipJoints=new HashMap<Integer,ArrayList<Float>>() {};
            mcpJoints.put(1,mcpThumb);
            mcpJoints.put(2,mcpIndex);
            mcpJoints.put(3,mcpMiddle);
            mcpJoints.put(4,mcpRing);
            mcpJoints.put(5,mcpLittle);

            pipJoints.put(1,pipThumb);
            pipJoints.put(2,pipIndex);
            pipJoints.put(3,pipMiddle);
            pipJoints.put(4,pipRing);
            pipJoints.put(5,pipLittle);

            romPip=new int[]{getPeakData(wrist),getPeakData(pipThumb),getPeakData(pipIndex),getPeakData(pipMiddle),getPeakData(pipRing),getPeakData(pipLittle)};
            romMcp=new int[]{getPeakData(wrist),getPeakData(mcpThumb),getPeakData(mcpIndex),getPeakData(mcpMiddle),getPeakData(mcpRing),getPeakData(mcpLittle)};




        }catch (Exception ex){

            ex.printStackTrace();
        }
    }

    public void createSessionFile(){
        try{
            File file= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            String allSessionData= fileDataBase.readFile("Galanto/RehabRelive/patient_"+String.valueOf(p_id),"all_sessions.json");

            // For new user
            if (allSessionData.isEmpty()){
                File file1=new File(file.getAbsolutePath()+"/Galanto/RehabRelive/patient_"+String.valueOf(p_id)+"/","session_1.json");
                if (!file1.exists()){
                    // create session1 file
                    fileDataBase.createFile("Galanto/RehabRelive/patient_"+String.valueOf(p_id),"session_1.json","");
                }
            }else {
                //create new session file
                JSONObject jsonObject=new JSONObject(allSessionData);
                JSONArray jsonArray=jsonObject.getJSONArray("allSessionDatas");
                int session_id=jsonArray.length()+1;
                    File file3=new File(file.getAbsolutePath()+"/Galanto/RehabRelive/patient_"+String.valueOf(p_id)+"/","session_"+String.valueOf(session_id)+".json");
                    if (!file3.exists()){
                        fileDataBase.createFile("Galanto/RehabRelive/patient_"+String.valueOf(p_id),"session_"+String.valueOf(session_id)+".json","");
                    }
        }

    }catch (Exception ex){
            Toast.makeText(getContext(),"Error in createSessionFile: "+ex.toString(),Toast.LENGTH_SHORT).show();
        }}

    public static String format(float value) {
        if(value < 1000) {
            return format("###", value);
        } else {
            double hundreds = value % 1000;
            int other = (int) (value / 1000);
            return format(",##", other) + ',' + format("000", hundreds);
        }
    }

    public static String format(Double value) {
        if(value < 1000) {
            return format("###", value);
        } else {
            double hundreds = value % 1000;
            int other = (int) (value / 1000);
            return format(",##", other) + ',' + format("000", hundreds);
        }
    }

    private static String format(String pattern, Object value) {

        return new DecimalFormat(pattern).format(value);
    }

    public static void startCountAnimation(int leng, final TextView view){
        int  animateNumber= leng;
        int numOfDigits = (int) (Math.log10(leng) + 1);

        if (numOfDigits>4){
            //animateNumber=Float.parseFloat(df.format((float) (leng/1000.0)));
            animateNumber=Math.round(leng/1000);
        }
        final ValueAnimator animator=ValueAnimator.ofInt(0,animateNumber);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

//                if (numOfDigits>3){
//                    view.setTextSize(40-(numOfDigits-3)*13);
//                }else {
//                    view.setTextSize(40);
//                }
                if (numOfDigits==4){
                    view.setTextSize(26);
                }else {
                    view.setTextSize(40);
                }

                if(numOfDigits>4){
                    view.setText((format((Integer) animator.getAnimatedValue()))+"k");
                }else {
                    view.setText(format((Integer) animator.getAnimatedValue()));
                }


            }
        });
        animator.start();
    }

    public void setFingerRom(int fingerIndex){
        try{
            String romData= fileDataBase.readFile("Galanto/RehabRelive/patient_"+String.valueOf(p_id),"all_rom.json");
            if(!romData.isEmpty()){
                JSONObject jsonObject1=new JSONObject(romData);
                JSONArray jsonArray=jsonObject1.getJSONArray("allRomDatas");

                sessionsLogic.getAverageRom(p_id);
                romDates=sessionsLogic.getDailyRomDates();
                int start=0;
                String fingerNamePip="";
                String fingerNameMcp="";
                if (fingerIndex!=0){
                    fingerNamePip=jointNames[fingerIndex]+"PipRom";
                    fingerNameMcp=jointNames[fingerIndex]+"McpRom";
                }else{
                    fingerNamePip=jointNames[fingerIndex]+"Rom";
                }

                mcpTrend=new ArrayList<>();
                pipTrend=new ArrayList<>();

//                if (jsonArray.length()>100)
                for(int i=0;i<jsonArray.length();i++){
                        if(!fingerNamePip.isEmpty()) {
                            pipTrend.add(Float.parseFloat(jsonArray.getJSONObject(i).getString(fingerNamePip)));
                        }
                        if(!fingerNameMcp.isEmpty()){
                            mcpTrend.add(Float.parseFloat(jsonArray.getJSONObject(i).getString(fingerNameMcp)));
                        }
                }
                if(fingerNameMcp.isEmpty()){
                    mcpTrend=null;
                }
                logics.setLineChart(fingerRomChart,"PIP",romDates,pipTrend,mcpTrend,Color.parseColor("#7818c7"),"Trends");
//                graphPlot.createLineChart(fingerRomChart,romDates,);
            }
        }catch (Exception e){
            Toast.makeText(getContext(),"Error in setFingerRom(): "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }


    public void setRomData(int p_id){
        try{

            String romData= fileDataBase.readFile("Galanto/RehabRelive/patient_"+String.valueOf(p_id),"all_rom.json");
            if(!romData.isEmpty()){
                JSONObject jsonObject1=new JSONObject(romData);
                JSONArray jsonArray=jsonObject1.getJSONArray("allRomDatas");
                JSONObject jsonObject=jsonArray.getJSONObject(jsonArray.length()-1);
                avRomScore=(Float.parseFloat(jsonObject.getString("averageRom"))/88)*100;
                tPipScore=Math.round(Float.parseFloat(jsonObject.getString("thumbPipRom")));
                tMcpScore=Math.round(Float.parseFloat(jsonObject.getString("thumbMcpRom")));
                iPipScore=Math.round(Float.parseFloat(jsonObject.getString("indexPipRom")));
                iMcpScore=Math.round(Float.parseFloat(jsonObject.getString("indexMcpRom")));
                mPipScore=Math.round(Float.parseFloat(jsonObject.getString("middlePipRom")));
                mMcpScore=Math.round(Float.parseFloat(jsonObject.getString("middleMcpRom")));
                rPipScore=Math.round(Float.parseFloat(jsonObject.getString("ringPipRom")));
                rMcpScore=Math.round(Float.parseFloat(jsonObject.getString("ringMcpRom")));
                lPipScore=Math.round(Float.parseFloat(jsonObject.getString("pinkyPipRom")));
                lMcpScore=Math.round(Float.parseFloat(jsonObject.getString("pinkyMcpRom")));
                wScore=Math.round(Float.parseFloat(jsonObject.getString("wristRom")));

                if (jsonArray.length()>1){
                    int firstAvRom=Math.round((Float.parseFloat(jsonArray.getJSONObject(0).getString("averageRom"))/88)*100);
                    romPercChange=Math.round(avRomScore)-firstAvRom;
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(getContext(),"Error in setRomData(): "+ex.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void popChartLayout(LinearLayout linearLayout,int height,int width){
        ViewGroup.LayoutParams params=linearLayout.getLayoutParams();
        params.height=height;
        params.width=width;
        linearLayout.setLayoutParams(params);
    }

    public void setSessionDetails(String sessionId,int p_id){
        String response="";
        LocalDateTime ssnStTime2,ssnEndTime2;


        FileDataBase fileDataBase=new FileDataBase(getContext());
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyy   HH:mm");

        try {
            response = fileDataBase.readFile("Galanto/RehabRelive/patient_" + String.valueOf(p_id), "all_sessions.json");
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("allSessionDatas");
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i).get("session_id").equals(sessionId)) {
                    gameScoreData=jsonArray.getJSONObject(i).getInt("game_score");
                    successScoreData=jsonArray.getJSONObject(i).getInt("hit_rate");
                    handSegmentData=jsonArray.getJSONObject(i).getString("hand_segments");
                    ssnStTime2=LocalDateTime.parse(jsonArray.getJSONObject(i).getString("start_time_stamp"),formatter1);
                    ssnEndTime2=LocalDateTime.parse(jsonArray.getJSONObject(i).getString("stop_time_stamp"),formatter1);
                    sessionPlayTime =ssnStTime2.until(ssnEndTime2, ChronoUnit.MINUTES);
                    movScore.setText(jsonArray.getJSONObject(i).getString("movement_score"));
                    break;
                }
            }
//            startCountAnimation(gameScoreData, gameScoreText);
            gameScoreText.setText(String.valueOf(gameScoreData));
            tvSessionTime.setText(String.valueOf(sessionPlayTime));
//            startCountAnimation(Math.toIntExact(sessionPlayTime),tvSessionTime);
            ArrayList<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry(successScoreData));
            entries.add(new PieEntry(100 - successScoreData));
            graphPlot.createPieChart(successScorePieChart, entries, String.valueOf(Math.round(successScoreData)), Color.parseColor("#4a6fe8"));


        }catch (Exception e){
            Toast.makeText(getContext(), "Error in setSessionDetails:"+e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteSession(String sessionId,int p_id){
        String response="";
        FileDataBase fileDataBase=new FileDataBase(getContext());
        try{
            File file=new File("Galanto/RehabRelive/patient_"+String.valueOf(p_id)+sessionId);
            response=fileDataBase.readFile("Galanto/RehabRelive/patient_"+String.valueOf(p_id),"all_sessions.json");
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("allSessionDatas");
            for (int i=0;i<jsonArray.length();i++){
                if(jsonArray.getJSONObject(i).get("session_id").equals(sessionId)){
                    jsonArray.remove(i);
                    break;
                }
            }
            totalSessionsCount=jsonArray.length();
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("allSessionDatas",jsonArray);
            file.delete();

            fileDataBase.updateFile("Galanto/RehabRelive/patient_"+String.valueOf(p_id),"all_sessions.json",jsonObject1.toString());
//            startActivity(new Intent(getContext(),HomeDashboard.class));
        }catch (Exception e){
            Toast.makeText(getContext(), "Error in deleteSession:"+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void showDialoge(String title,String message){
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSession(sessionSpinner.getSelectedItem().toString().trim(),p_id);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.drawable.ic_delete_outline)
                .setCancelable(true)
                .show();
    }
    public boolean checkCalibrateExist(int p_id){
        try {
            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File dir = new File(file.getAbsolutePath() + "/Galanto/RehabRelive/patient_" + String.valueOf(p_id), "calibration.json");
            if (dir.exists()) {
                return true;
            } else {
                return false;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

    }
    public int getPeakData(ArrayList<Float> data){
        int rom=0;

        try{
            int dataLength=data.size();
            double[] inputData=new double[data.size()];
            for (int i=0;i<inputData.length;i++){
                inputData[i]=data.get(i);
            }
            String mode = "triangular";
            int wsize = 11;
            Smooth s1 = new Smooth(inputData, wsize, mode);
            double[] out = s1.smoothSignal();
            FindPeak findPeak=new FindPeak(out);
            Peak highPeak=findPeak.detectPeaks();
            Peak lowPeak=findPeak.detectTroughs();

            double[] highPeak1=highPeak.getHeights();
            double[] lowPeak1=lowPeak.getHeights();

            org.apache.commons.math3.stat.descriptive.rank.Median median=new org.apache.commons.math3.stat.descriptive.rank.Median();
            Mean mean=new Mean();
            double p1=mean.evaluate(highPeak1,0,highPeak1.length);
            double p2=mean.evaluate(lowPeak1,0,lowPeak1.length);

            double[] highPeak2=getProcessedPeaks(highPeak1,p1,true);
            double[] lowPeak2=getProcessedPeaks(lowPeak1,p2,false);


            int angleMax=(int) median.evaluate(highPeak2);
            int angleMin=(int) median.evaluate(lowPeak2);
            rom=angleMax-angleMin;

        }catch (Exception ex){
            return 0;
        }
        return  rom;
    }

    public double[] getProcessedPeaks(double[] inputData,double mean,boolean isPeak){
        double[] returnArray=null;
        try {
            ArrayList<Double> tempList = new ArrayList<>();
            for (int i = 0; i < inputData.length; i++) {
                if (isPeak==true){
                    if (inputData[i] >= mean) {
                        tempList.add(inputData[i]);
                    }
                }else {
                    if (inputData[i] <= mean) {
                        tempList.add(inputData[i]);
                    }
                }
            }

             returnArray = new double[tempList.size()];
            for (int i = 0; i < tempList.size(); i++) {
                returnArray[i] = tempList.get(i);
            }
        }catch (Exception ex){

        }
        return returnArray;
    }

    public void plotAllFigureRom(String joint,int color){
        try{
            int thumb=0,index=0,middle=0,ring=0,pinky=0,wrist=0;
            int max=Integer.MIN_VALUE;
            int min=Integer.MAX_VALUE;
            ArrayList<BarEntry> barData = new ArrayList<>();
            if (joint.toLowerCase().equals("pip")) {
                thumb=romPerSession.getThumbPipMax()-romPerSession.getThumbPipMin();
                index=romPerSession.getIndexPipMax()-romPerSession.getIndexPipMin();
                middle=romPerSession.getMiddlePipMax()-romPerSession.getMiddlePipMin();
                ring=romPerSession.getRingPipMax()-romPerSession.getRingPipMin();
                pinky=romPerSession.getLittlePipMax()-romPerSession.getLittlePipMin();
                wrist=romPerSession.getWristMax()-romPerSession.getWristMin();
//

            }else {
                thumb=romPerSession.getThumbMcpMax()-romPerSession.getThumbMcpMin();
                index=romPerSession.getIndexMcpMax()-romPerSession.getIndexMcpMin();
                middle=romPerSession.getMiddleMcpMax()-romPerSession.getMiddleMcpMin();
                ring=romPerSession.getRingMcpMax()-romPerSession.getRingMcpMin();
                pinky=romPerSession.getLittleMcpMax()-romPerSession.getRingMcpMin();
                wrist=romPerSession.getWristMax()-romPerSession.getWristMin();
            }
            max=Math.max(thumb,Math.max(index,Math.max(middle,Math.max(ring,Math.max(pinky,wrist)))));
            min=Math.min(thumb,Math.min(index,Math.min(middle,Math.min(ring,Math.min(pinky,wrist)))));

            barData.add(new BarEntry(0, thumb));
            barData.add(new BarEntry(1, index));
            barData.add(new BarEntry(2, middle));
            barData.add(new BarEntry(3, ring));
            barData.add(new BarEntry(4, pinky));
            barData.add(new BarEntry(5, wrist));
            graphPlot.setBarChartData(successRateBarChart, "", barData, 90,color,min,max);

        }catch (Exception e){

        }
    }



    public  void  setFingerDataOnScreenLoad(String handSegment){
        try{
            setAlpha(0);
            plotAllFigureRom("pip",Color.parseColor("#DC6868"));
        switch (handSegment) {
            case "T":
                setLineChart(romChart, "Thumb (PIP)", pipThumb, Color.parseColor("#e85f56"));
                fingerIndex = 1;
                setVisibility(1);
                romPointerScore.setText(String.valueOf(romPerSession.getThumbPipMax()-romPerSession.getThumbPipMin()));
                mcpScore=String.valueOf(romPerSession.getThumbMcpMax()-romPerSession.getThumbMcpMin());
                pipScore=String.valueOf(romPerSession.getThumbPipMax()-romPerSession.getThumbPipMin());
//                romPointerScore.setText(String.valueOf(getPeakData(pipThumb)));
//                romPointerScore.setText(String.valueOf(pipScoreList[fingerIndex]));
                ivIndexIndicator.setColorFilter(null);
                ivThumbIndicator.setColorFilter(Color.RED);
                ivMiddleIndicator.setColorFilter(null);
                ivRingIndicator.setColorFilter(null);
                ivLittleIndicator.setColorFilter(null);
                ivWristIndicator.setColorFilter(null);
                tvFingerNames.setText("Thumb\nFinger");
                 pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));
                mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));

                break;
            case "I":
                setLineChart(romChart, "Index (PIP)", pipIndex, Color.parseColor("#e85f56"));
//                setLineChart(mcpLineChart, "Index (MCP)", mcpIndex, Color.parseColor("#4a9150"));
                fingerIndex = 2;
                setVisibility(2);
//                romPointerScore.setText(String.valueOf(getPeakData(pipIndex)));
//                romPointerScore.setText(String.valueOf(pipScoreList[fingerIndex]));
                romPointerScore.setText(String.valueOf(romPerSession.getIndexPipMax()-romPerSession.getIndexPipMin()));
                mcpScore=String.valueOf(romPerSession.getIndexMcpMax()-romPerSession.getIndexMcpMin());
                pipScore=String.valueOf(romPerSession.getIndexPipMax()-romPerSession.getIndexPipMin());
                ivIndexIndicator.setColorFilter(Color.RED);
                ivThumbIndicator.setColorFilter(null);
                ivMiddleIndicator.setColorFilter(null);
                ivRingIndicator.setColorFilter(null);
                ivLittleIndicator.setColorFilter(null);
                ivWristIndicator.setColorFilter(null);
                tvFingerNames.setText("Index\nFinger");
//                romPointerScore.setText(String.valueOf(romPerSession.getIndexPipMax()-romPerSession.getIndexPipMin()));
                pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));
                mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                //setMcpData("Index (MCP)");
                break;
            case "M":
                setLineChart(romChart, "Middle (PIP)", pipMiddle, Color.parseColor("#e85f56"));
//                setLineChart(mcpLineChart, "Middle (MCP)", mcpMiddle, Color.parseColor("#4a9150"));
                fingerIndex = 3;
                setVisibility(3);
                romPointerScore.setText(String.valueOf(romPerSession.getMiddlePipMax()-romPerSession.getMiddlePipMin()));
                mcpScore=String.valueOf(romPerSession.getMiddleMcpMax()-romPerSession.getMiddleMcpMin());
                pipScore=String.valueOf(romPerSession.getMiddlePipMax()-romPerSession.getMiddlePipMin());
//                romPointerScore.setText(String.valueOf(getPeakData(pipMiddle)));
//                romPointerScore.setText(String.valueOf(pipScoreList[fingerIndex]));
                ivIndexIndicator.setColorFilter(null);
                ivThumbIndicator.setColorFilter(null);
                ivMiddleIndicator.setColorFilter(Color.RED);
                ivRingIndicator.setColorFilter(null);
                ivLittleIndicator.setColorFilter(null);
                ivWristIndicator.setColorFilter(null);
                tvFingerNames.setText("Middle\nFinger");
                pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));
                mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                //setMcpData("Middle (MCP)");
                break;
            case "R":
                setLineChart(romChart, "Ring (PIP)", pipRing, Color.parseColor("#e85f56"));
//                setLineChart(mcpLineChart, "Ring (MCP)", mcpRing, Color.parseColor("#4a9150"));
                fingerIndex = 4;
                setVisibility(4);
                romPointerScore.setText(String.valueOf(romPerSession.getRingPipMax()-romPerSession.getRingPipMin()));
                mcpScore=String.valueOf(romPerSession.getRingMcpMax()-romPerSession.getRingMcpMin());
                pipScore=String.valueOf(romPerSession.getRingPipMax()-romPerSession.getRingPipMin());
//                romPointerScore.setText(String.valueOf(romPerSession.getRingPipMax()-romPerSession.getRingPipMin()));
//                romPointerScore.setText(String.valueOf(getPeakData(pipRing)));
//                romPointerScore.setText(String.valueOf(pipScoreList[fingerIndex]));
                ivIndexIndicator.setColorFilter(null);
                ivThumbIndicator.setColorFilter(null);
                ivMiddleIndicator.setColorFilter(null);
                ivRingIndicator.setColorFilter(Color.RED);
                ivLittleIndicator.setColorFilter(null);
                ivWristIndicator.setColorFilter(null);
                tvFingerNames.setText("Ring\nFinger");
                pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));
                mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                //setMcpData("Ring (MCP)");
                break;
            case "P":
                setLineChart(romChart, "Pinky (PIP)", pipLittle, Color.parseColor("#e85f56"));
                fingerIndex = 5;
                setVisibility(5);
                romPointerScore.setText(String.valueOf(romPerSession.getLittlePipMax()-romPerSession.getLittlePipMin()));
                mcpScore=String.valueOf(romPerSession.getLittleMcpMax()-romPerSession.getLittleMcpMin());
                pipScore=String.valueOf(romPerSession.getLittlePipMax()-romPerSession.getLittlePipMin());
//                romPointerScore.setText(String.valueOf(getPeakData(pipLittle)));
                ivIndexIndicator.setColorFilter(null);
                ivThumbIndicator.setColorFilter(null);
                ivMiddleIndicator.setColorFilter(null);
                ivRingIndicator.setColorFilter(null);
                ivLittleIndicator.setColorFilter(Color.RED);
                ivWristIndicator.setColorFilter(null);
                tvFingerNames.setText("Little\nFinger");
                pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));
                mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                //setMcpData("Pinky (MCP)");
                break;
            case "W":
                setLineChart(romChart, "Wrist (Flex)", wrist, Color.parseColor("#e85f56"));
//                mcpLineChart.setVisibility(View.INVISIBLE);
                fingerIndex = 0;
                setVisibility(0);
                romPointerScore.setText(String.valueOf(romPerSession.getWristMax()-romPerSession.getWristMin()));
//                mcpScore=String.valueOf(romPerSession.getMiddleMcpMax()-romPerSession.getMiddleMcpMin());

                ivIndexIndicator.setColorFilter(null);
                ivThumbIndicator.setColorFilter(null);
                ivMiddleIndicator.setColorFilter(null);
                ivRingIndicator.setColorFilter(null);
                ivLittleIndicator.setColorFilter(null);
                ivWristIndicator.setColorFilter(Color.RED);
                tvFingerNames.setText("Wrist");
                ivWristIndicator.performClick();
//                romPointerScore.setText(String.valueOf(getPeakData(wrist)));
//                romPointerScore.setText(String.valueOf(pipScoreList[fingerIndex]));
                pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                break;

        }

        setFingerRom(fingerIndex);
        }catch(Exception ex){
            Toast.makeText(getContext(),"Error in setFingerDataOnScreenLoad()"+ex.toString(),Toast.LENGTH_SHORT).show();

        }
    }

//    public void setPIP(){
//
//        tvPIPScore.setTextColor(Color.parseColor("#ff6666"));
//        tvMcpScore.setTextColor(getResources().getColor(R.color.grey));
//        tvPipTitle.setTextColor(Color.parseColor("#cf5151"));
//        tvMcpTilte.setTextColor(getResources().getColor(R.color.grey));
////        tvPipCartTitle.setTextColor(getResources().getColor(R.color.dark_blue));
////        tvMcpChartTitle.setTextColor(getResources().getColor(R.color.light_grey));
//        setAlpha(0);
//        isTextClicked=true;
//
//    }
//    public void setMcp(){
//        tvPIPScore.setTextColor(getResources().getColor(R.color.grey));
//        tvMcpScore.setTextColor(Color.parseColor("#4a9150"));
//        tvPipTitle.setTextColor(getResources().getColor(R.color.grey));
//        tvMcpTilte.setTextColor(Color.parseColor("#2ea638"));
////        tvPipCartTitle.setTextColor(getResources().getColor(R.color.light_grey));
////        tvMcpChartTitle.setTextColor(getResources().getColor(R.color.dark_blue));
//        setAlpha(1);
//    }


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

    public void setLineChart(LineChart lineChart,String label,ArrayList<Float> arrayList,int graphColor){
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

            romData.add(new Entry(xValues,arrayList.get(i)));
        }


        graphPlot.createLineChart(lineChart,null,romData,null,label,graphColor,minXaxis,minYaxis,maxXaxis,maxYaxis,"");

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

        try {
            String allSessionData= fileDataBase.readFile("Galanto/RehabRelive/"+folderName,"all_sessions.json");
            if (allSessionData.isEmpty()){
                return;
            }
            isNewUser=false;
            JSONObject jsonObject=new JSONObject(allSessionData);

            JSONArray jsonArray=jsonObject.getJSONArray("allSessionDatas");
            JSONObject lastJsonObject=jsonArray.getJSONObject(jsonArray.length()-1);
            lastSession=lastJsonObject.getString("session_id");

            handSegment=new ArrayList<>();
            romPerSessionsArray=new ArrayList<>();
            handSegment.add(lastJsonObject.getString("hand_segments"));
            setRomPerJointData(jsonArray.length()-1);

//            if(jsonArray.length()>0){
////                sessionPlayTime =0l;
//                for(int i=0;i<jsonArray.length();i++){
////                    ssnStTime= LocalDateTime.parse(jsonArray.getJSONObject(i).getString("start_time_stamp"),formatter1);
////                    ssnEndTime=LocalDateTime.parse(jsonArray.getJSONObject(i).getString("stop_time_stamp"),formatter1);
//
////
////                    sessionPlayTime +=ssnStTime.until(ssnEndTime, ChronoUnit.MINUTES);
////                    gameScore.add(Math.round(Float.parseFloat(jsonArray.getJSONObject(i).getString("game_score"))));
////                    successRate.add(Math.round(Float.parseFloat(jsonArray.getJSONObject(i).getString("hit_rate"))));
////                    handSegment.add(jsonArray.getJSONObject(i).getString("hand_segments"));
//                    romPerSessionsArray.add(new RomPerSession(jsonArray.getJSONObject(i).getInt("thumb_mcp_max"),jsonArray.getJSONObject(i).getInt("thumb_mcp_min"),jsonArray.getJSONObject(i).getInt("thumb_pip_max"),jsonArray.getJSONObject(i).getInt("thumb_pip_min"),
//                            jsonArray.getJSONObject(i).getInt("index_mcp_max"),jsonArray.getJSONObject(i).getInt("index_mcp_min"),jsonArray.getJSONObject(i).getInt("index_pip_max"),jsonArray.getJSONObject(i).getInt("index_pip_min"),
//                            jsonArray.getJSONObject(i).getInt("middle_mcp_max"),jsonArray.getJSONObject(i).getInt("middle_mcp_min"),jsonArray.getJSONObject(i).getInt("middle_pip_max"),jsonArray.getJSONObject(i).getInt("middle_pip_min"),
//                            jsonArray.getJSONObject(i).getInt("ring_mcp_max"),jsonArray.getJSONObject(i).getInt("ring_mcp_min"),jsonArray.getJSONObject(i).getInt("ring_pip_max"),jsonArray.getJSONObject(i).getInt("ring_pip_min"),
//                            jsonArray.getJSONObject(i).getInt("pinky_mcp_max"),jsonArray.getJSONObject(i).getInt("pinky_mcp_min"),jsonArray.getJSONObject(i).getInt("pinky_pip_max"),jsonArray.getJSONObject(i).getInt("pinky_pip_min"),
//                            jsonArray.getJSONObject(i).getInt("wrist_max"),jsonArray.getJSONObject(i).getInt("wrist_min")));
//                }
//            }

//            if (jsonArray.length()>50){
//                for (int i=0;i<jsonArray.length();i++){
//                    JSONObject arrayObject=jsonArray.getJSONObject(i);
//                    //timeStamp.add(Float.parseFloat(arrayObject.getString("time_since_startup")));
//
//                    mcpThumb.add(Float.parseFloat(arrayObject.getString("thumb_mcp_max"))
//                            -Float.parseFloat(arrayObject.getString("thumb_mcp_min")));
//                    mcpIndex.add(Float.parseFloat(arrayObject.getString("index_mcp_max"))
//                            -Float.parseFloat(arrayObject.getString("index_mcp_min")));
//                    mcpMiddle.add(Float.parseFloat(arrayObject.getString("middle_mcp_max"))
//                            -Float.parseFloat(arrayObject.getString("middle_mcp_min")));
//                    mcpRing.add(Float.parseFloat(arrayObject.getString("ring_mcp_max"))
//                            -Float.parseFloat(arrayObject.getString("ring_mcp_min")));
//                    mcpLittle.add(Float.parseFloat(arrayObject.getString("pinky_mcp_max"))
//                            -Float.parseFloat(arrayObject.getString("pinky_mcp_min")));
//
//                    pipIndex.add(Float.parseFloat(arrayObject.getString("index_pip_max"))
//                            -Float.parseFloat(arrayObject.getString("index_pip_min")));
//                    pipMiddle.add(Float.parseFloat(arrayObject.getString("middle_pip_max"))
//                            -Float.parseFloat(arrayObject.getString("middle_pip_min")));
//                    pipRing.add(Float.parseFloat(arrayObject.getString("ring_pip_max"))
//                            -Float.parseFloat(arrayObject.getString("ring_pip_min")));
//                    pipLittle.add(Float.parseFloat(arrayObject.getString("pinky_mcp_max"))
//                            -Float.parseFloat(arrayObject.getString("pinky_mcp_min")));
//                    pipThumb.add(Float.parseFloat(arrayObject.getString("thumb_pip_max"))
//                            -Float.parseFloat(arrayObject.getString("thumb_pip_min")));
//
//                    wrist.add(Float.parseFloat(arrayObject.getString("wrist_max"))
//                            -Float.parseFloat(arrayObject.getString("wrist_min")));
//
//
//                }
//
//            }
//            else{
//
                String lastSessionData=fileDataBase.readFile("Galanto/RehabRelive/"+folderName,lastSession+".json");
                if (lastSessionData.isEmpty()){
                    return;
                }
                JSONObject object=new JSONObject(lastSessionData);
                JSONArray array=object.getJSONArray("fineSessionDatas");
////
////                // Only read 100 data points
                int increment=1;
                if (array.length()>100){
                    increment=array.length()/100;
                    //arrayLength=array.length();
                }
//
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
                mcpJoints=new HashMap<Integer,ArrayList<Float>>() {};
                pipJoints=new HashMap<Integer,ArrayList<Float>>() {};


                for (int i =0;i<array.length();i+=increment){
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
                    pipLittle.add(Float.parseFloat(arrayObject.getString("pinky_pip")));
                    pipThumb.add(Float.parseFloat(arrayObject.getString("thumb_pip")));

                    wrist.add(Float.parseFloat(arrayObject.getString("wrist")));

                }
////                mcpJoints.put(1,mcpThumb);
////                mcpJoints.put(2,mcpIndex);
////                mcpJoints.put(3,mcpMiddle);
////                mcpJoints.put(4,mcpRing);
////                mcpJoints.put(5,mcpLittle);
////
////                pipJoints.put(1,pipThumb);
////                pipJoints.put(2,pipIndex);
////                pipJoints.put(3,pipMiddle);
////                pipJoints.put(4,pipRing);
////                pipJoints.put(5,pipLittle);
////
////                romPip=new int[]{getPeakData(wrist),getPeakData(pipThumb),getPeakData(pipIndex),getPeakData(pipMiddle),getPeakData(pipRing),getPeakData(pipLittle)};
////                romMcp=new int[]{getPeakData(wrist),getPeakData(mcpThumb),getPeakData(mcpIndex),getPeakData(mcpMiddle),getPeakData(mcpRing),getPeakData(mcpLittle)};
//
//
//            }

        }catch (Exception ex){
            Toast.makeText(getContext(),"Error in read session data: "+ex.toString(),Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//     if(isGameSarted){
//            getActivity().startActivity(getActivity().getIntent());
//        }
//        isGameSarted=false;
//
//    }

}