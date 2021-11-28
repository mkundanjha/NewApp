package com.galanto.GalantoHealth;

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
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;


public class HomeDashboard extends Fragment {

    TextView currentDay,tvUserName,tvFingerNames,tvMcpScore,tvPIPScore,tvMcpTilte,tvPipTitle,tvPipCartTitle,tvMcpChartTitle,statsHeader;
    TextView currentDate,noticeTv,lastSessionTime,handImpairedValue,ageValueText,genderValueText,welcome_message,welcome_header,romPointerScore;
    LineChart romChart,mcpLineChart;
    BarChart barChart,successRateBarChart;
    PieChart successScorePieChart,mvScorePC,romPC;
    CardView gameCard,timeUsagesCard,romCard,hrCard,game1Card,game2Card,calibrateCard,newUserCard,statsCard,barGraphCard;
    CardView patient_info_card,mcpChartCard;
    Animate animate;
    ImageView ivThumbIndicator,ivIndexIndicator,ivMiddleIndicator,ivRingIndicator,ivLittleIndicator,ivWristIndicator;
    ImageView ivThumbPIPInd,ivIndexPIPInd,ivMiddlePIPInd,ivRingPIPInd,ivLittlePIPInd;
    ImageView ivThumbMCPInd,ivIndexMCPInd,ivMiddleMCPInd,ivRingMCPInd,ivLittleMCPInd;
    LinearLayout cardParent,pipLayout,mcpLayout,pipPointer,mcpPointer,pipChartLayout,mcpChartLayout;
    ScrollView svCardScroll;
    boolean isTextClicked=false,isNewUser=true;
    GraphPlot graphPlot =new GraphPlot();
    ImageButton refreshBtn;
    int p_id=0,totalFiles=0,gmScore1=0,gmScore2=0,gmScore3=0,fingerIndex=1;

    int tPipScore=0,iPipScore=0,mPipScore=0,rPipScore=0,lPipScore=0,tMcpScore=0,iMcpScore=0,mMcpScore=0,rMcpScore=0,lMcpScore=0,wScore=0;
    int[] pipScoreList,mcpScoreList;
    FileDataBase fileDataBase;
    ArrayList<Float> timeStamp,mcpThumb,mcpIndex,mcpMiddle,mcpRing,mcpLittle,pipThumb,pipIndex,pipMiddle,pipRing,pipLittle,wrist;
    Long timeElapsedSession;
    Float hitRate,movementScore,avRomScore;
    String handSegment;
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
        currentDate=view.findViewById(R.id.tvCurrentDate);
        currentDay=(TextView) view.findViewById(R.id.tvCurrentDay);
        romChart=view.findViewById(R.id.romChart);
        mcpLineChart=view.findViewById(R.id.mcpChart);
        successRateBarChart=view.findViewById(R.id.successRateBarChart);
        game1Card=view.findViewById(R.id.game1Card);
        game2Card=view.findViewById(R.id.game2Card);
        refreshBtn=view.findViewById(R.id.refreshBtn);
        calibrateCard=view.findViewById(R.id.calibrateCard);
        ageValueText=view.findViewById(R.id.ageValue);
        genderValueText=view.findViewById(R.id.genderValueText);
        welcome_header=view.findViewById(R.id.welcome_header);
        welcome_message=view.findViewById(R.id.welcome_message);
        patient_info_card=view.findViewById(R.id.infoCard);
        successScorePieChart =view.findViewById(R.id.scorePiechart);
        mvScorePC=view.findViewById(R.id.mvScorePiechart);
        romPC=view.findViewById(R.id.romPiechart);
        mcpChartCard=view.findViewById(R.id.mcpCard);
        romCard=view.findViewById(R.id.romCard);
        pipPointer=view.findViewById(R.id.pipPointerLayout);
        mcpPointer=view.findViewById(R.id.mcpPointerLayout);
        romPointerScore=view.findViewById(R.id.romPointerScore);
        statsHeader=view.findViewById(R.id.yourStatsHeader);
        pipChartLayout=view.findViewById(R.id.pipChartLayout);
        mcpChartLayout=view.findViewById(R.id.mcpChartLayout);

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
        newUserCard=view.findViewById(R.id.newUserCard);
        barGraphCard=view.findViewById(R.id.barGraphCard);
        statsCard=view.findViewById(R.id.statsCard);

        fileDataBase=new FileDataBase(getActivity().getApplicationContext());



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager();
        }

        tvUserName=view.findViewById(R.id.usrName);

        Intent intent=this.getActivity().getIntent();

        tvUserName.setText(intent.getStringExtra("user_name"));
        p_id= Integer.parseInt(intent.getStringExtra("p_id"));
//        ageValueText.setText(intent.getStringExtra("age")+" yrs, ");
//        genderValueText.setText(intent.getStringExtra("gender"));
        setPatient_info();


        animate=new Animate(getContext());
        hitRate=0f;
        movementScore=0f;

        readSessionsData();

        if(hitRate<=0){
            hitRate=0.4f;
        }
        if(movementScore<=0){
            movementScore=0.7f;
        }


        if (isNewUser) {
            animate.runAnimation(welcome_header, 1000);
            animate.runAnimation(welcome_message, 1200);
            animate.runAnimation(game1Card, 200);
            animate.runAnimation(game2Card, 400);
            animate.runAnimation(calibrateCard, 600);
            newUserCard.setVisibility(View.VISIBLE);
            romCard.setVisibility(View.INVISIBLE);
            mcpChartCard.setVisibility(View.INVISIBLE);
            statsHeader.setVisibility(View.INVISIBLE);

            romPointerScore.setVisibility(View.INVISIBLE);
            ivThumbIndicator.setVisibility(View.INVISIBLE);
            ivIndexIndicator.setVisibility(View.INVISIBLE);
            ivMiddleIndicator.setVisibility(View.INVISIBLE);
            ivRingIndicator.setVisibility(View.INVISIBLE);
            ivLittleIndicator.setVisibility(View.INVISIBLE);
            ivWristIndicator.setVisibility(View.INVISIBLE);
            pipLayout.setVisibility(View.INVISIBLE);
            mcpLayout.setVisibility(View.INVISIBLE);

            newUserCard.setVisibility(View.VISIBLE);
            statsCard.setVisibility(View.INVISIBLE);
            barGraphCard.setVisibility(View.INVISIBLE);
            svCardScroll.setVisibility(View.INVISIBLE);
            //animate.runAnimation(patient_info_card, 800);
        }

        ivThumbIndicator.setVisibility(View.INVISIBLE);
        ivIndexIndicator.setVisibility(View.INVISIBLE);
        ivMiddleIndicator.setVisibility(View.INVISIBLE);
        ivRingIndicator.setVisibility(View.INVISIBLE);
        ivLittleIndicator.setVisibility(View.INVISIBLE);
        ivWristIndicator.setVisibility(View.INVISIBLE);
        pipLayout.setVisibility(View.INVISIBLE);
        mcpLayout.setVisibility(View.INVISIBLE);

        newUserCard.setVisibility(View.VISIBLE);
        statsCard.setVisibility(View.INVISIBLE);
        barGraphCard.setVisibility(View.INVISIBLE);
        svCardScroll.setVisibility(View.INVISIBLE);


        if(!isNewUser) {
            ArrayList<BarEntry> barData=new ArrayList<>();
            barData.add(new BarEntry(0,gmScore1));
            barData.add(new BarEntry(1,gmScore2));
            barData.add(new BarEntry(2,gmScore3));

            int maxYaxis=0;
            if(gmScore1>gmScore2 ){
                if(gmScore1>gmScore3){
                    maxYaxis=gmScore1;
                }else {
                    maxYaxis=gmScore3;
                }
            }else if(gmScore2>gmScore3){
                maxYaxis=gmScore2;
            }else {
                maxYaxis=gmScore3;
            }


            //Data for pie charts
            ArrayList<PieEntry> entries=new ArrayList<>();
            ArrayList<PieEntry> mvEntries=new ArrayList<>();
            ArrayList<PieEntry> avRomEntries=new ArrayList<>();
            Float score=0.9f;
            entries.add(new PieEntry(hitRate));
            entries.add(new PieEntry(1-hitRate));


            mvEntries.add(new PieEntry(movementScore));
            mvEntries.add(new PieEntry(1-movementScore));

            //demo if rom file is blank
            avRomScore=score;
            setRomData(p_id);
            avRomEntries.add(new PieEntry(avRomScore));
            avRomEntries.add(new PieEntry(1-avRomScore));

            pipLayout.setVisibility(View.VISIBLE);
            mcpLayout.setVisibility(View.VISIBLE);
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
            svCardScroll.setVisibility(View.VISIBLE);
            newUserCard.setVisibility(View.INVISIBLE);

            handSegment="W";
            setFingerDataOnScreenLoad(handSegment);
            pipScoreList=new int[]{wScore,tPipScore,iPipScore,mPipScore,rPipScore,lPipScore};
            mcpScoreList=new int[]{wScore,iMcpScore,mMcpScore,rMcpScore,lMcpScore};

            romPointerScore.setText(String.valueOf(pipScoreList[fingerIndex]));
//            setLineChart("PIP of Index", pipIndex);
//            setMcpData("MCP of Index");
            graphPlot.setBarChartData(successRateBarChart,"Last 3 game score",barData,maxYaxis);
            graphPlot.createPieChart(successScorePieChart,entries,String.valueOf((int)( hitRate*100))+" %",Color.parseColor("#48a36c"));
            graphPlot.createPieChart(mvScorePC,mvEntries,String.valueOf((int)( movementScore*100))+" %",Color.parseColor("#486ca3"));
            graphPlot.createPieChart(romPC,avRomEntries,String.valueOf((int)( score*100))+" %",Color.parseColor("#cc5656"));

            lastSessionTime.setText(String.valueOf(timeElapsedSession)+" min");
        }
        pipLayout.setVisibility(View.INVISIBLE);
        mcpLayout.setVisibility(View.INVISIBLE);
        setDateTime(currentDate,currentDay);

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshActivity();

            }
        });




        game1Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkCalibrateExist(p_id)){
                    showCalibrateDialoge();
                    return;
                }
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

        pipPointer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPIP();
                romPointerScore.setText(String.valueOf(pipScoreList[fingerIndex]));
                popChartLayout(pipChartLayout,275,465);
                popChartLayout(mcpChartLayout,250,450);

                pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));
                mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
            }
        });

        mcpPointer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMcp();
                romPointerScore.setText(String.valueOf(mcpScoreList[fingerIndex]));
                popChartLayout(mcpChartLayout,275,465);
                popChartLayout(pipChartLayout,250,450);
                //romPointerScore.setText("88");
                mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));
                pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
            }
        });

        pipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setPIP();
                isTextClicked=true;
                svCardScroll.smoothScrollTo(0,cardParent.getTop());

            }
        });

        mcpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setMcp();
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
                pipLayout.setVisibility(View.VISIBLE);
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
                //pipLayout.setVisibility(View.VISIBLE);
                setClickAnimation(ivThumbIndicator,event,55,130,55,115);
                ivIndexIndicator.setColorFilter(null);
                ivThumbIndicator.setColorFilter(Color.RED);
                ivMiddleIndicator.setColorFilter(null);
                ivRingIndicator.setColorFilter(null);
                ivLittleIndicator.setColorFilter(null);
                ivWristIndicator.setColorFilter(null);
//                setLineChart("PIP of Thumb",pipThumb);
//                setMcpData("MCP of Thumb");
                setFingerDataOnScreenLoad("Tpip");
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
                //pipLayout.setVisibility(View.VISIBLE);
                setClickAnimation(ivIndexIndicator,event,55,90,55,90);
                ivIndexIndicator.setColorFilter(Color.RED);
                ivThumbIndicator.setColorFilter(null);
                ivMiddleIndicator.setColorFilter(null);
                ivRingIndicator.setColorFilter(null);
                ivLittleIndicator.setColorFilter(null);
                ivWristIndicator.setColorFilter(null);
                setFingerDataOnScreenLoad("Ipip");
//                setLineChart("PIP of Index",pipIndex);
//                setMcpData("MCP of Index");
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
                //pipLayout.setVisibility(View.VISIBLE);
                setClickAnimation(ivMiddleIndicator,event,55,90,55,90);
                ivIndexIndicator.setColorFilter(null);
                ivThumbIndicator.setColorFilter(null);
                ivMiddleIndicator.setColorFilter(Color.RED);
                ivRingIndicator.setColorFilter(null);
                ivLittleIndicator.setColorFilter(null);
                ivWristIndicator.setColorFilter(null);

                setFingerDataOnScreenLoad("Mpip");
//                setLineChart("PIP of Middle",pipMiddle);
//                setMcpData("MCP of Middle");
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
                //pipLayout.setVisibility(View.VISIBLE);
                setClickAnimation(ivRingIndicator,event,55,80,55,80);
                ivIndexIndicator.setColorFilter(null);
                ivThumbIndicator.setColorFilter(null);
                ivMiddleIndicator.setColorFilter(null);
                ivRingIndicator.setColorFilter(Color.RED);
                ivLittleIndicator.setColorFilter(null);
                ivWristIndicator.setColorFilter(null);
                setFingerDataOnScreenLoad("Rpip");
//                setLineChart("PIP of Ring",pipRing);
//                setMcpData("MCP of Ring");
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
                //pipLayout.setVisibility(View.VISIBLE);
                setClickAnimation(ivLittleIndicator,event,55,75,55,75);
                ivIndexIndicator.setColorFilter(null);
                ivThumbIndicator.setColorFilter(null);
                ivMiddleIndicator.setColorFilter(null);
                ivRingIndicator.setColorFilter(null);
                ivLittleIndicator.setColorFilter(Color.RED);
                ivWristIndicator.setColorFilter(null);
                setFingerDataOnScreenLoad("Lpip");
//                setLineChart("PIP of Little",pipLittle);
//                setMcpData("MCP of Little");
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
                //pipLayout.setVisibility(View.INVISIBLE);
                pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                svCardScroll.smoothScrollTo(0,cardParent.getTop());
                setClickAnimation(ivWristIndicator,event,55,100,55,100);
                ivIndexIndicator.setColorFilter(null);
                ivThumbIndicator.setColorFilter(null);
                ivMiddleIndicator.setColorFilter(null);
                ivRingIndicator.setColorFilter(null);
                ivLittleIndicator.setColorFilter(null);
                ivWristIndicator.setColorFilter(Color.RED);

                setFingerDataOnScreenLoad("W");
                //setLineChart("Flex of Wrist",wrist);
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

    public void setRomData(int p_id){
        try{
            String romData= fileDataBase.readFile("Galanto/RehabRelive/patient_"+String.valueOf(p_id),"allRomData.json");
            if(romData!=""){
                JSONObject jsonObject=new JSONObject(romData);
                avRomScore=Float.parseFloat(jsonObject.getString("averageRom"));
                tPipScore=Integer.parseInt(jsonObject.getString("thumbPipRom"));
                tMcpScore=Integer.parseInt(jsonObject.getString("thumbMcpRom"));
                iPipScore=Integer.parseInt(jsonObject.getString("indexPipRom"));
                iMcpScore=Integer.parseInt(jsonObject.getString("indexMcpRom"));
                mPipScore=Integer.parseInt(jsonObject.getString("middlePipRom"));
                mMcpScore=Integer.parseInt(jsonObject.getString("middleMcpRom"));
                rPipScore=Integer.parseInt(jsonObject.getString("ringPipRom"));
                rMcpScore=Integer.parseInt(jsonObject.getString("ringMcpRom"));
                lPipScore=Integer.parseInt(jsonObject.getString("pinkyPipRom"));
                lMcpScore=Integer.parseInt(jsonObject.getString("pinkyMcpRom"));
                wScore=Integer.parseInt(jsonObject.getString("wristRom"));

            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void popChartLayout(LinearLayout linearLayout,int height,int width){
        ViewGroup.LayoutParams params=linearLayout.getLayoutParams();
        params.height=height;
        params.width=width;
        linearLayout.setLayoutParams(params);
    }

    public void showCalibrateDialoge(){
        new AlertDialog.Builder(getContext())
                .setTitle("Calibrate First")
                .setMessage("Please Calibrate before starting game!!")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
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

    public  void  setFingerDataOnScreenLoad(String handSegment){

        switch (handSegment){
            case "Tpip":
                setLineChart(romChart,"Thumb (PIP)", pipThumb,Color.parseColor("#b02a21"));
                setLineChart(mcpLineChart,"Thumb (MCP)",mcpThumb,Color.parseColor("#4a9150"));
                fingerIndex=1;
                //setMcpData("Thumb (MCP)");
                break;
            case "Ipip":
                setLineChart(romChart,"Index (PIP)", pipIndex,Color.parseColor("#b02a21"));
                setLineChart(mcpLineChart,"Index (MCP)",mcpIndex,Color.parseColor("#4a9150"));
                fingerIndex=2;
                //setMcpData("Index (MCP)");
                break;
            case "Mpip":
                setLineChart(romChart,"Middle (PIP)", pipMiddle,Color.parseColor("#b02a21"));
                setLineChart(mcpLineChart,"Middle (MCP)",mcpMiddle,Color.parseColor("#4a9150"));
                fingerIndex=3;
                //setMcpData("Middle (MCP)");
                break;
            case "Rpip":
                setLineChart(romChart,"Ring (PIP)", pipRing,Color.parseColor("#b02a21"));
                setLineChart(mcpLineChart,"Ring (MCP)",mcpRing,Color.parseColor("#4a9150"));
                fingerIndex=4;
                //setMcpData("Ring (MCP)");
                break;
            case "Lpip":
                setLineChart(romChart,"Pinky (PIP)", pipLittle,Color.parseColor("#b02a21"));
                setLineChart(mcpLineChart,"Pinky (MCP)",mcpLittle,Color.parseColor("#4a9150"));
                fingerIndex=5;
                //setMcpData("Pinky (MCP)");
                break;
            case "W":
                setLineChart(romChart,"Wrist (Flex)", wrist,Color.parseColor("#b02a21"));
                mcpLineChart.setVisibility(View.INVISIBLE);
                fingerIndex=0;
                break;
        }
    }


    public void setPatient_info(){
        try {
            String response=fileDataBase.readFile("Galanto/RehabRelive","current_patient.json");
            if(response!=""){
                JSONObject jsonObject=new JSONObject(response);
                ageValueText.setText(jsonObject.getString("age")+" yrs, ");
                genderValueText.setText(jsonObject.getString("gender"));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }



    public void setPIP(){

        tvPIPScore.setTextColor(Color.parseColor("#ff6666"));
        tvMcpScore.setTextColor(getResources().getColor(R.color.grey));
        tvPipTitle.setTextColor(Color.parseColor("#cf5151"));
        tvMcpTilte.setTextColor(getResources().getColor(R.color.grey));
//        tvPipCartTitle.setTextColor(getResources().getColor(R.color.dark_blue));
//        tvMcpChartTitle.setTextColor(getResources().getColor(R.color.light_grey));
        setAlpha(0);
        isTextClicked=true;

    }
    public void setMcp(){
        tvPIPScore.setTextColor(getResources().getColor(R.color.grey));
        tvMcpScore.setTextColor(Color.parseColor("#4a9150"));
        tvPipTitle.setTextColor(getResources().getColor(R.color.grey));
        tvMcpTilte.setTextColor(Color.parseColor("#2ea638"));
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


           // romData.add(new Entry(i,Float.parseFloat(String.valueOf(Math.random()*10))));
            mcpLineChart.setVisibility(View.VISIBLE);
            romData.add(new Entry(xValues,arrayList.get(i)));
        }
        //romChart.setVisibility(View.VISIBLE);

        graphPlot.createLineChart(lineChart,romData,label,graphColor,minXaxis,minYaxis,maxXaxis,maxYaxis);
        //createLineChart(romChart,romData,label,Color.WHITE);
    }
    public void setMcpData(String label){
        ArrayList<Entry> romData=new ArrayList<>();

        for (int i=0;i<50;i++){
            romData.add(new Entry(i,Float.parseFloat(String.valueOf(Math.random()*10))));
        }
        mcpLineChart.setVisibility(View.VISIBLE);
        //createLineChart(mcpLineChart,romData,label,Color.WHITE);
        graphPlot.createLineChart(mcpLineChart,romData,label,Color.parseColor("#4a9150"),0f,0f,50f,10f);
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
            if (allSessionData==""){
                return;
            }
            isNewUser=false;
            JSONObject jsonObject=new JSONObject(allSessionData);

            JSONArray jsonArray=jsonObject.getJSONArray("allSessionDatas");
            JSONObject lastJsonObject=jsonArray.getJSONObject(jsonArray.length()-1);

            if(lastJsonObject.has("hit_rate")){
                hitRate=Float.parseFloat(lastJsonObject.getString("hit_rate"));
            }
            if(lastJsonObject.has("movement_score")){
                movementScore=Float.parseFloat(lastJsonObject.getString("movement_score"));
            }
            if(lastJsonObject.has("hand_segments")){
                handSegment=lastJsonObject.getString("hand_segments");
            }

            // Get last 3 game scores data
            if(jsonArray.length()>=3) {
                gmScore1 = Integer.parseInt(jsonArray.getJSONObject(jsonArray.length() - 3).getString("game_score"));
                gmScore2 = Integer.parseInt(jsonArray.getJSONObject(jsonArray.length() - 2).getString("game_score"));
                gmScore3 = Integer.parseInt(lastJsonObject.getString("game_score"));
            }

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
                    //arrayLength=100;
                    arrayLength=array.length();
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