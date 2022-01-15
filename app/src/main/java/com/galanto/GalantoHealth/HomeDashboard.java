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
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.text.Layout;
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
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class HomeDashboard extends Fragment {

    TextView currentDay,tvUserName,tvFingerNames,tvMcpScore,tvPIPScore,tvMcpTilte,tvPipTitle,tvPipCartTitle,tvMcpChartTitle,statsHeader,romIncreaseText,totalPlayTime;
    TextView currentDate,noticeTv,lastSessionTime,handImpairedValue,ageValueText,genderValueText,welcome_message,welcome_header,romPointerScore,gameScoreText,pidText;
    LineChart romChart,mcpLineChart;
    BarChart barChart,successRateBarChart;
    PieChart successScorePieChart,romPC;
    CardView gameCard,timeUsagesCard,romCard,hrCard,game1Card,game2Card,calibrateCard,newUserCard,statsCard,barGraphCard;
    CardView patient_info_card,mcpChartCard,gameScoreCard;
    Animate animate;
    ImageView ivThumbIndicator,ivIndexIndicator,ivMiddleIndicator,ivRingIndicator,ivLittleIndicator,ivWristIndicator;
    ImageView ivThumbPIPInd,ivIndexPIPInd,ivMiddlePIPInd,ivRingPIPInd,ivLittlePIPInd;
    ImageView ivThumbMCPInd,ivIndexMCPInd,ivMiddleMCPInd,ivRingMCPInd,ivLittleMCPInd,romIncreaseIcon;
    LinearLayout cardParent,pipLayout,mcpLayout,pipPointer,mcpPointer,pipChartLayout,mcpChartLayout,gameScoreLayout;
    ScrollView svCardScroll;
    boolean isTextClicked=false,isNewUser=true;
    GraphPlot graphPlot =new GraphPlot();
    ImageButton refreshBtn;
    Spinner sessionSpinner;
    int p_id=0,totalFiles=0,gmScore1=0,gmScore2=0,gmScore3=0,fingerIndex=1;
    ArrayList<Integer> gameScore,successRate;
    ArrayList<String> handSegment;
    int tPipScore=0,iPipScore=0,mPipScore=0,rPipScore=0,lPipScore=0,tMcpScore=0,iMcpScore=0,mMcpScore=0,rMcpScore=0,lMcpScore=0,wScore=0;
    int[] pipScoreList,mcpScoreList;
    FileDataBase fileDataBase;
    ArrayList<Float> timeStamp,mcpThumb,mcpIndex,mcpMiddle,mcpRing,mcpLittle,pipThumb,pipIndex,pipMiddle,pipRing,pipLittle,wrist;
    Long timeElapsedSession,totalTimePlayed;
    Float hitRate,avRomScore;
    int movementScore1=0,movementScore2=0,movementScore3=0,romPercChange=0;
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
        romPC=view.findViewById(R.id.romPiechart);
        mcpChartCard=view.findViewById(R.id.mcpCard);
        romCard=view.findViewById(R.id.romCard);
        pipPointer=view.findViewById(R.id.pipPointerLayout);
        mcpPointer=view.findViewById(R.id.mcpPointerLayout);
        romPointerScore=view.findViewById(R.id.romPointerScore);
        statsHeader=view.findViewById(R.id.yourStatsHeader);
        pipChartLayout=view.findViewById(R.id.pipChartLayout);
        mcpChartLayout=view.findViewById(R.id.mcpChartLayout);
        gameScoreText=view.findViewById(R.id.gameScoreText);
        romIncreaseIcon=view.findViewById(R.id.romIncreaseIcon);
        romIncreaseText=view.findViewById(R.id.romIncreaseText);
        gameScoreLayout=view.findViewById(R.id.gameScoreLayout);
        totalPlayTime=view.findViewById(R.id.totalPlayTime);
        sessionSpinner=view.findViewById(R.id.sessionSpinner);

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
        pidText=view.findViewById(R.id.pidText);

        pipLayout=view.findViewById(R.id.pip_score_layot);
        mcpLayout=view.findViewById(R.id.mcp_score_layout);
        newUserCard=view.findViewById(R.id.newUserCard);
        barGraphCard=view.findViewById(R.id.barGraphCard);
        statsCard=view.findViewById(R.id.statsCard);
        gameScoreCard=view.findViewById(R.id.gameScoreCard);

        fileDataBase=new FileDataBase(getActivity().getApplicationContext());


        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Environment.isExternalStorageManager();
            }

            tvUserName = view.findViewById(R.id.usrName);

            Intent intent = this.getActivity().getIntent();

//        tvUserName.setText(intent.getStringExtra("user_name"));
            p_id = Integer.parseInt(intent.getStringExtra("p_id"));
//        ageValueText.setText(intent.getStringExtra("age")+" yrs, ");
//        genderValueText.setText(intent.getStringExtra("gender"));
            setPatient_info();
            String userName = intent.getStringExtra("user_name");
            tvUserName.setText(userName.substring(0, 1).toUpperCase() + userName.substring(1));
            pidText.setText("UsrID: " + p_id);


            animate = new Animate(getContext());
            hitRate = 0f;
            movementScore1 = 0;

            readSessionsData();
            setRomData(p_id);
            setSpinnerData();

            if (hitRate <= 0) {
                hitRate = 0.4f;
            }
//        if(movementScore1<=0){
//            movementScore1=0;
//        }


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
                gameScoreCard.setVisibility(View.INVISIBLE);
                pipPointer.setClickable(false);
                mcpPointer.setClickable(false);
                fingerIndex = 0;
                mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));
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


            if (!isNewUser) {
                ArrayList<BarEntry> barData = new ArrayList<>();
                barData.add(new BarEntry(0, Math.round(movementScore1)));
                barData.add(new BarEntry(1, Math.round(movementScore2)));
                barData.add(new BarEntry(2, Math.round(movementScore3)));

                int maxYaxis = 0;
                if (movementScore1 > movementScore2) {
                    if (movementScore1 > movementScore3) {
                        maxYaxis = movementScore1;
                    } else {
                        maxYaxis = movementScore3;
                    }
                } else if (movementScore2 > movementScore3) {
                    maxYaxis = movementScore2;
                } else {
                    maxYaxis = movementScore3;
                }


                //Data for pie charts
                ArrayList<PieEntry> entries = new ArrayList<>();
                ArrayList<PieEntry> mvEntries = new ArrayList<>();
                ArrayList<PieEntry> avRomEntries = new ArrayList<>();

                entries.add(new PieEntry(hitRate));
                entries.add(new PieEntry(100 - hitRate));


                //demo if rom file is blank


                avRomEntries.add(new PieEntry(avRomScore));
                avRomEntries.add(new PieEntry(100 - avRomScore));

                //game score
                startCountAnimation(gmScore3, gameScoreText);

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

                //handSegment.add("W");

                //set rom percentage change
                if (romPercChange != 0) {
                    romIncreaseIcon.setVisibility(View.VISIBLE);
                    romIncreaseText.setVisibility(View.VISIBLE);
                    romIncreaseText.setText(String.valueOf(romPercChange) + "%");

                    if (romPercChange > 0) {
                        romIncreaseIcon.setBackground(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_up_24));
                    } else {
                        romIncreaseIcon.setBackground(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24));
                    }
                }


                pipScoreList = new int[]{wScore, tPipScore, iPipScore, mPipScore, rPipScore, lPipScore};
                mcpScoreList = new int[]{wScore, tMcpScore, iMcpScore, mMcpScore, rMcpScore, lMcpScore};

                if (handSegment.size() != 0) {
                    setFingerDataOnScreenLoad(handSegment.get(handSegment.size() - 1));
                } else {
                    setFingerDataOnScreenLoad("W");
                }
                popChartLayout(pipChartLayout, 275, 465);
                romPointerScore.setText(String.valueOf(pipScoreList[fingerIndex]));
//            setLineChart("PIP of Index", pipIndex);
//            setMcpData("MCP of Index");
                graphPlot.setBarChartData(successRateBarChart, "Last 3 game score", barData, maxYaxis);
                graphPlot.createPieChart(successScorePieChart, entries, String.valueOf(Math.round(hitRate)), Color.parseColor("#48a36c"));
                graphPlot.createPieChart(romPC, avRomEntries, String.valueOf(Math.round(avRomScore)), Color.parseColor("#cc5656"));

                lastSessionTime.setText(String.valueOf(timeElapsedSession) + " min");
                totalPlayTime.setText(String.valueOf(totalTimePlayed) + " min");
            }
            pipLayout.setVisibility(View.INVISIBLE);
            mcpLayout.setVisibility(View.INVISIBLE);
            setDateTime(currentDate, currentDay);

            refreshBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshActivity();

                }
            });


            game1Card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkCalibrateExist(p_id)) {
                        showCalibrateDialoge();
                        return;
                    }
                    createSessionFile();
                    openGame("com.Galanto.Game1");
                    //noticeTv.setText("Please refresh to get the latest data.");
                }
            });

            game2Card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkCalibrateExist(p_id)) {
                        showCalibrateDialoge();
                        return;
                    }
                    createSessionFile();
                    openGame("com.Galanto.Juice");
                    //noticeTv.setText("Please refresh to get the latest data.");
                }
            });

            calibrateCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createSessionFile();
                    openGame("com.Galanto.Calibration");
                    noticeTv.setText("Please refresh to get the latest data.");
                }
            });

            pipPointer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fingerIndex == 0) {
                        return;
                    }
                    setPIP();

                    popChartLayout(pipChartLayout, 275, 465);
                    popChartLayout(mcpChartLayout, 250, 450);
                    romPointerScore.setText(String.valueOf(pipScoreList[fingerIndex]));
                    pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));
                    mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                }
            });

            mcpPointer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fingerIndex == 0) {
                        return;
                    }
                    setMcp();
                    romPointerScore.setText(String.valueOf(mcpScoreList[fingerIndex]));
                    popChartLayout(mcpChartLayout, 275, 465);
                    popChartLayout(pipChartLayout, 250, 450);
                    //romPointerScore.setText("88");
                    mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));
                    pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                }
            });

            pipLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //setPIP();

                    isTextClicked = true;
                    svCardScroll.smoothScrollTo(0, cardParent.getTop());

                }
            });

            mcpLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //setMcp();
                    isTextClicked = true;
                    svCardScroll.smoothScrollTo(0, cardParent.getBottom());

                }
            });


            // check if scrollview has been triggered by touch
            svCardScroll.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    isTextClicked = false;
                    return false;
                }
            });


            svCardScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    // for scrollview triggered by title click then return
                    if (isTextClicked) {
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
                    setClickAnimation(ivThumbIndicator, event, 55, 130, 55, 115);
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
                    setClickAnimation(ivIndexIndicator, event, 55, 90, 55, 90);
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
                    setClickAnimation(ivMiddleIndicator, event, 55, 90, 55, 90);
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
                    setClickAnimation(ivRingIndicator, event, 55, 80, 55, 80);
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
                    setClickAnimation(ivLittleIndicator, event, 55, 75, 55, 75);
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
                    svCardScroll.smoothScrollTo(0, cardParent.getTop());
                    setClickAnimation(ivWristIndicator, event, 55, 100, 55, 100);
                    ivIndexIndicator.setColorFilter(null);
                    ivThumbIndicator.setColorFilter(null);
                    ivMiddleIndicator.setColorFilter(null);
                    ivRingIndicator.setColorFilter(null);
                    ivLittleIndicator.setColorFilter(null);
                    ivWristIndicator.setColorFilter(Color.RED);
                    popChartLayout(pipChartLayout, 275, 465);
                    popChartLayout(mcpChartLayout, 250, 450);

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

            sessionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    setSessionData(sessionSpinner.getSelectedItem().toString());
                    String indexSession[] = sessionSpinner.getSelectedItem().toString().split("_");
                    int index = Integer.parseInt(indexSession[indexSession.length - 1]);
                    setFingerDataOnScreenLoad("W");
                    startCountAnimation(gameScore.get(index-1), gameScoreText);
//
                    ArrayList<PieEntry> entries = new ArrayList<>();
//
                    entries.add(new PieEntry(successRate.get(index-1)));
                    entries.add(new PieEntry(100 - successRate.get(index-1)));
                    graphPlot.createPieChart(successScorePieChart, entries, String.valueOf(Math.round(successRate.get(index-1))), Color.parseColor("#48a36c"));


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }catch (Exception ex){
            Toast.makeText(getContext(),"Error in main HomeDasboard:"+ex.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void  setSpinnerData(){
        String response="";
        //sessionSpinner.setBackgroundColor(Color.RED);
        try{
            response= fileDataBase.readFile("Galanto/RehabRelive/patient_"+String.valueOf(p_id),"all_sessions.json");
            if (response.isEmpty()){
                return;
            }
            //isNewUser=false;
            JSONObject jsonObject=new JSONObject(response);

            JSONArray jsonArray=jsonObject.getJSONArray("allSessionDatas");
//            JSONObject lastJsonObject=jsonArray.getJSONObject(jsonArray.length()-1);

            ArrayList<String> sessionSpinnerData=new ArrayList<>();
            for (int i=jsonArray.length()-1;i>=0;i--){
                sessionSpinnerData.add(jsonArray.getJSONObject(i).getString("session_id"));
            }
            ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,sessionSpinnerData);
            sessionSpinner.setAdapter(spinnerAdapter);
            //sessionSpinner.setSelection(sessionSpinnerData.size()-1);


        }catch (Exception ex){
            Toast.makeText(getContext(),"Error in setSpinnerData: "+ex.toString(),Toast.LENGTH_SHORT).show();
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
            pipLittle.add(Float.parseFloat(arrayObject.getString("ring_mcp")));
            pipThumb.add(Float.parseFloat(arrayObject.getString("pinky_pip")));

            wrist.add(Float.parseFloat(arrayObject.getString("wrist")));

        }
        }catch (Exception ex){

            ex.printStackTrace();
        }
    }

    public void createSessionFile(){
        try{
            File file= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            String allSessionData= fileDataBase.readFile("Galanto/RehabRelive/patient_"+String.valueOf(p_id),"all_sessions.json");
            if (allSessionData.isEmpty()){
                File file1=new File(file.getAbsolutePath()+"/Galanto/RehabRelive/patient_"+String.valueOf(p_id)+"/","session_1.json");
                if (!file1.exists()){
                    fileDataBase.createFile("Galanto/RehabRelive/patient_"+String.valueOf(p_id),"session_1.json","");
                }
            }else {
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
        final ValueAnimator animator=ValueAnimator.ofInt(0,leng);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setText(format((Integer) animator.getAnimatedValue()));
            }
        });
        animator.start();
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
        try{

        switch (handSegment) {
            case "Tpip":
                setLineChart(romChart, "Thumb (PIP)", pipThumb, Color.parseColor("#b02a21"));
                setLineChart(mcpLineChart, "Thumb (MCP)", mcpThumb, Color.parseColor("#4a9150"));
                fingerIndex = 1;
                romPointerScore.setText(String.valueOf(pipScoreList[fingerIndex]));
                pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));
                mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                //setMcpData("Thumb (MCP)");
                break;
            case "Ipip":
                setLineChart(romChart, "Index (PIP)", pipIndex, Color.parseColor("#b02a21"));
                setLineChart(mcpLineChart, "Index (MCP)", mcpIndex, Color.parseColor("#4a9150"));
                fingerIndex = 2;
                romPointerScore.setText(String.valueOf(pipScoreList[fingerIndex]));
                pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));
                mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                //setMcpData("Index (MCP)");
                break;
            case "Mpip":
                setLineChart(romChart, "Middle (PIP)", pipMiddle, Color.parseColor("#b02a21"));
                setLineChart(mcpLineChart, "Middle (MCP)", mcpMiddle, Color.parseColor("#4a9150"));
                fingerIndex = 3;
                romPointerScore.setText(String.valueOf(pipScoreList[fingerIndex]));
                pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));
                mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                //setMcpData("Middle (MCP)");
                break;
            case "Rpip":
                setLineChart(romChart, "Ring (PIP)", pipRing, Color.parseColor("#b02a21"));
                setLineChart(mcpLineChart, "Ring (MCP)", mcpRing, Color.parseColor("#4a9150"));
                fingerIndex = 4;
                romPointerScore.setText(String.valueOf(pipScoreList[fingerIndex]));
                pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));
                mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                //setMcpData("Ring (MCP)");
                break;
            case "Lpip":
                setLineChart(romChart, "Pinky (PIP)", pipLittle, Color.parseColor("#b02a21"));
                setLineChart(mcpLineChart, "Pinky (MCP)", mcpLittle, Color.parseColor("#4a9150"));
                fingerIndex = 5;
                romPointerScore.setText(String.valueOf(pipScoreList[fingerIndex]));
                pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab));
                mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                //setMcpData("Pinky (MCP)");
                break;
            case "W":
                setLineChart(romChart, "Wrist (Flex)", wrist, Color.parseColor("#b02a21"));
                mcpLineChart.setVisibility(View.INVISIBLE);
                fingerIndex = 0;
                romPointerScore.setText(String.valueOf(pipScoreList[fingerIndex]));

                pipPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                mcpPointer.setBackground(getResources().getDrawable(R.drawable.pip_tab_grey));
                break;


        }
        }catch(Exception ex){
            Toast.makeText(getContext(),"Error in setFingerDataOnScreenLoad()"+ex.toString(),Toast.LENGTH_SHORT).show();

        }
    }


    public void setPatient_info(){
        try {
            String response=fileDataBase.readFile("Galanto/RehabRelive","current_patient.json");
            if(!response.isEmpty()){
                JSONObject jsonObject=new JSONObject(response);
                ageValueText.setText(jsonObject.getString("age")+" yrs, ");
                genderValueText.setText(jsonObject.getString("gender"));
                p_id=jsonObject.getInt("p_id");
            }

        }catch (Exception ex){
            Toast.makeText(getContext(),"Error in setPatient_info(): "+ex.toString(),Toast.LENGTH_SHORT).show();
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
            //getActivity().finishAndRemoveTask();
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
            if (allSessionData.isEmpty()){
                return;
            }
            isNewUser=false;
            JSONObject jsonObject=new JSONObject(allSessionData);

            JSONArray jsonArray=jsonObject.getJSONArray("allSessionDatas");
            JSONObject lastJsonObject=jsonArray.getJSONObject(jsonArray.length()-1);

            if(lastJsonObject.has("hit_rate")){
                hitRate=Float.parseFloat(lastJsonObject.getString("hit_rate"));
            }
//            if(lastJsonObject.has("movement_score")){
//                movementScore1=Float.parseFloat(lastJsonObject.getString("movement_score"));
//            }
//            if(lastJsonObject.has("hand_segments")){
//                handSegment=lastJsonObject.getString("hand_segments");
//            }
            if(jsonArray.length()>=3) {
                movementScore1 = Integer.parseInt(jsonArray.getJSONObject(jsonArray.length() - 3).getString("movement_score"));
                movementScore2 = Integer.parseInt(jsonArray.getJSONObject(jsonArray.length() - 2).getString("movement_score"));
                movementScore3 = Integer.parseInt(lastJsonObject.getString("movement_score"));
            }else if(jsonArray.length()>=2) {
                movementScore2 = Integer.parseInt(jsonArray.getJSONObject(jsonArray.length() - 2).getString("movement_score"));
                movementScore3 = Integer.parseInt(lastJsonObject.getString("movement_score"));
            }else {
                movementScore3 = Integer.parseInt(lastJsonObject.getString("movement_score"));
            }
            // Get last 3 game scores data
//            if(jsonArray.length()>=3) {
//                gmScore1 = Integer.parseInt(jsonArray.getJSONObject(jsonArray.length() - 3).getString("game_score"));
//                gmScore2 = Integer.parseInt(jsonArray.getJSONObject(jsonArray.length() - 2).getString("game_score"));
//                gmScore3 = Integer.parseInt(lastJsonObject.getString("game_score"));
//            }
            gmScore3 = Math.round(Float.parseFloat(lastJsonObject.getString("game_score")));


            lastSession=lastJsonObject.getString("session_id");
            lastGameId=lastJsonObject.getInt("game_id");
            ssnStTime= LocalDateTime.parse(lastJsonObject.getString("start_time_stamp"),formatter1);
            ssnEndTime=LocalDateTime.parse(lastJsonObject.getString("stop_time_stamp"),formatter1);
            timeElapsedSession= ssnStTime.until(ssnEndTime, ChronoUnit.MINUTES);

            gameScore=new ArrayList<>();
            successRate=new ArrayList<>();
            handSegment=new ArrayList<>();
            if(jsonArray.length()>0){
                totalTimePlayed=0l;
                for(int i=0;i<jsonArray.length();i++){
                    ssnStTime= LocalDateTime.parse(jsonArray.getJSONObject(i).getString("start_time_stamp"),formatter1);
                    ssnEndTime=LocalDateTime.parse(jsonArray.getJSONObject(i).getString("stop_time_stamp"),formatter1);
                    totalTimePlayed+=ssnStTime.until(ssnEndTime, ChronoUnit.MINUTES);
                    gameScore.add(Math.round(Float.parseFloat(jsonArray.getJSONObject(i).getString("game_score"))));
                    successRate.add(Math.round(Float.parseFloat(jsonArray.getJSONObject(i).getString("hit_rate"))));
                    handSegment.add(jsonArray.getJSONObject(i).getString("hand_segments"));
                }
            }else {
                totalTimePlayed=timeElapsedSession;
            }

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
                if (lastSessionData.isEmpty()){
                    return;
                }
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