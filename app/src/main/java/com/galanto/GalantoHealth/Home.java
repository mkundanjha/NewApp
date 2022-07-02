package com.galanto.GalantoHealth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newapp.R;
import com.galanto.GalantoHealth.ui.SessionsLogic;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

import app.futured.donut.DonutProgressView;

public class Home extends Fragment {

    View view;
    ImageView animImage,ivRomChange;
    ConstraintLayout setGoalCard;
    DonutProgressView goalGraph,mvScGoalGraph;
    TextView tvGameScore,tvMovementScore,tvRomLast,tvRomBest,tvUsername,tvDay,tvDate,tvLastSessionTime,tvTotalSessionsTime,tvGoalstext
            , tvResetGoals,romPercentageChange,tvUserId,goalHeader;
    Button btnMvScore,btnRom,btnSpeed,btnSetGoals, btnSetGoalValue,cancelGoals;
    Button[] buttons;
    EditText etGetGoalValue;
    private FragmentActivity myContext;
    private LineChart homePageGraph;
    //private PieChart homePieChart;
    SessionsLogic sessionsLogic;
    ArrayList<Float> movementScoreArray,averageRomArray,timeDiffArray;
    ArrayList<Integer> gameScoreArray;
    ArrayList<LocalDateTime> startTimeArray, endTimeArray;
    ArrayList<LocalDate> dailyRomDate;
    CardView game1card,game2card,calibrateCard;

    int p_id=1,totalGameScore=0,totalMovementScore=0,bestRom=0;
    Float lastRom=0f,totalTime,lastTime;
    GraphPlot graphPlot;
    Logics logics;
    Boolean isGameSarted=false,isMovGoalSet=false;
    int romGoal=0,movValue=0,romValue=0;
    String romGoalValue="",movGoalValue="";
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initialize Classes
        logics=new Logics(getActivity().getApplicationContext());
        sessionsLogic=new SessionsLogic(getActivity().getApplicationContext());
        graphPlot=new GraphPlot(getContext());

        // Get p_id from Intent
        Intent intent = this.getActivity().getIntent();
        p_id = Integer.parseInt(intent.getStringExtra("p_id"));
        String userName = intent.getStringExtra("user_name");

        try {
            // Reference views
            mvScGoalGraph = view.findViewById(R.id.mvScGoalGraph);
            goalGraph = view.findViewById(R.id.goalsGraph);
            btnMvScore = view.findViewById(R.id.mvScoreBtn);
            btnRom = view.findViewById(R.id.romBtn);
            btnSpeed = view.findViewById(R.id.speedBtn);
            homePageGraph = view.findViewById(R.id.home_graph);
            tvGameScore = view.findViewById(R.id.tvGameScore);
            tvMovementScore = view.findViewById(R.id.tvMovScore);
            tvRomLast = view.findViewById(R.id.tvRomToday);
            tvRomBest = view.findViewById(R.id.tvRomAll);
            //homePieChart=view.findViewById(R.id.homePieChart);
            tvUsername = view.findViewById(R.id.tvUsername);
            tvDate = view.findViewById(R.id.tvDate);
            tvDay = view.findViewById(R.id.tvDay);
            tvLastSessionTime = view.findViewById(R.id.tvLastSessionTime);
            tvTotalSessionsTime = view.findViewById(R.id.tvTotalSessionTime);
            btnSetGoals = view.findViewById(R.id.goalButton);
            animImage = view.findViewById(R.id.animImage);
            game1card = view.findViewById(R.id.game1Card);
            game2card = view.findViewById(R.id.game2Card);
            calibrateCard = view.findViewById(R.id.calibrateCard);
            setGoalCard = view.findViewById(R.id.goalPopupCard);
            etGetGoalValue = view.findViewById(R.id.getGoalText);
            btnSetGoalValue = view.findViewById(R.id.setGoalValue);
            tvGoalstext=view.findViewById(R.id.goalDataText);
            tvResetGoals =view.findViewById(R.id.resetGoals);
            cancelGoals=view.findViewById(R.id.cancelGoalValue);
            romPercentageChange=view.findViewById(R.id.romPerChange);
            ivRomChange=view.findViewById(R.id.ivRomChange);
            tvUserId=view.findViewById(R.id.userId);
            goalHeader=view.findViewById(R.id.goalHeader);


            tvUsername.setText(userName);
            logics.setDateTime(tvDate, tvDay);
            mvScGoalGraph.setAlpha(0.1f);
            goalGraph.setAlpha(.1f);


            //Array of buttons used
            buttons = new Button[]{btnSpeed, btnRom, btnMvScore};
            boolean isNewUser;
            // Read the data of All session file
            isNewUser = sessionsLogic.readAllSessionsData(p_id);

            // Set Button attributes on load
            setButtonAttributes(btnRom);



            if (isNewUser == false) {
                try {
                    tvUserId.setText("ID: "+String.valueOf(p_id));
                    // Read the data of All rom file
                    sessionsLogic.getAverageRom(p_id);


                    // Initialize values to array
                    movementScoreArray = logics.convertIntToFloatArray(sessionsLogic.getMovementScoreArray());
                    averageRomArray = sessionsLogic.getAvRomScoreArray();
                    gameScoreArray = sessionsLogic.getGameScoreArray();
                    startTimeArray = sessionsLogic.getSessionStartTimeArray();
                    endTimeArray = sessionsLogic.getSessionEndTimeArray();
                    dailyRomDate = sessionsLogic.getDailyRomDates();


                    // Get sum of Movement Score and Game Score
                    totalGameScore = logics.sumOfArrayInt(gameScoreArray);
                    totalMovementScore = (int) logics.sumOfArrayFloat(movementScoreArray);
                    bestRom = (int) logics.findMax(averageRomArray);
                    lastRom = averageRomArray.get((averageRomArray.size() - 1));
                    int romPerChange= Math.round((lastRom-averageRomArray.get(0))/averageRomArray.get(0))*100;
                    initializeTimeDiffArray();
                    totalTime = logics.sumOfArrayFloat(timeDiffArray);
                    lastTime = timeDiffArray.get(timeDiffArray.size() - 1);




                    //Set values of text views
                    tvMovementScore.setText(String.valueOf(totalMovementScore));
                    tvGameScore.setText(String.valueOf(totalGameScore));
                    tvRomLast.setText(String.valueOf(Math.round(lastRom)));
                    tvRomBest.setText(String.valueOf(bestRom));


                    tvTotalSessionsTime.setText(String.valueOf(Math.round(totalTime)) + " min");
                    tvLastSessionTime.setText(String.valueOf(Math.round(lastTime)) + " min");

                    romPercentageChange.setText(String.valueOf(romPerChange));
                    if (romPerChange>0){

                        ivRomChange.setImageDrawable(getResources().getDrawable(R.drawable.ic_increase));
                    }else if(romPerChange<0){

                        ivRomChange.setImageDrawable(getResources().getDrawable(R.drawable.ic_decrease));
                    }

                    // Set value for a pie chart
                    ArrayList<PieEntry> entries = new ArrayList<>();
                    Float score = 0.4f;
                    entries.add(new PieEntry(score));
                    entries.add(new PieEntry(1 - score));

                    //Rom graph
                    logics.setLineChart(homePageGraph, "", dailyRomDate, averageRomArray,null, Color.RED,"");
                    romGoal=readGoalData(p_id);
//                    readGoalData(p_id);
                    mvScGoalGraph.setVisibility(View.INVISIBLE);
                    if (romGoal>0) {
                        tvResetGoals.setVisibility(View.VISIBLE);
                        goalGraph.setAlpha(1);
//                        mvScGoalGraph.setAlpha(1);
                        btnSetGoals.setVisibility(View.INVISIBLE);
                        tvGoalstext.setText("  ROM:\n" + Math.round(lastRom)+"/"+romGoal);
                        graphPlot.setupDonutChart(goalGraph, (float) Math.round(lastRom), (float) romGoal, Color.parseColor("#5493f7"));

                    }else {
                        graphPlot.setupDonutChart(goalGraph, 0f, (float) romValue, Color.parseColor("#5493f7"));

                    }
//                    if (movValue>0){
//                        mvScGoalGraph.setVisibility(View.VISIBLE);
//                        mvScGoalGraph.setAlpha(1);
//                        graphPlot.setupDonutChart(mvScGoalGraph, 40f, (float) movValue, Color.parseColor("#54c4b0"));
//
//                    }else {
//                        mvScGoalGraph.setVisibility(View.INVISIBLE);
//                    }
//                    graphPlot.setupDonutChart(mvScGoalGraph, 0f, 0f, Color.parseColor("#ff636d"));
//                graphPlot.createPieChart(homePieChart,entries,String.valueOf(score), getResources().getColor(R.color.accent_blue));
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            // Buttons click functions
            btnMvScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setButtonAttributes(btnMvScore);
                    //Movement Graph
                    logics.setLineChart(homePageGraph, "", null, movementScoreArray,null, Color.BLUE,"");
                }
            });

            btnRom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setButtonAttributes(btnRom);
                    //Rom Graph
                    logics.setLineChart(homePageGraph, "", dailyRomDate, averageRomArray,null, Color.RED,"");
                }
            });

            btnSpeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setButtonAttributes(btnSpeed);
                }
            });
            btnSetGoals.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                mvScGoalGraph.setAlpha(1f);
//                goalGraph.setAlpha(1f);
                    btnSetGoals.setVisibility(View.INVISIBLE);
                    tvGoalstext.setVisibility(View.INVISIBLE);
                    mvScGoalGraph.setVisibility(View.INVISIBLE);
                    goalGraph.setVisibility(View.INVISIBLE);
                setGoalCard.setVisibility(View.VISIBLE);
                    cancelGoals.setEnabled(false);
                    cancelGoals.setBackgroundColor(Color.GRAY);

                }
            });
            tvResetGoals.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goalGraph.setAlpha(0.1f);
                    btnSetGoals.setVisibility(View.VISIBLE);
                    tvResetGoals.setVisibility(View.INVISIBLE);
                    romGoalValue="";
                    movGoalValue="";
                    isMovGoalSet=false;
                }
            });

        btnSetGoalValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goal=etGetGoalValue.getText().toString().trim();
                if (!goal.isEmpty()){
                    if(Integer.parseInt(goal)!=0 ) {
                        saveGoalData(p_id,goal);
                    }else {
                        Toast.makeText(myContext,"Goals cannot be non numeric or zero",Toast.LENGTH_SHORT).show();
                    }
                }
//                String goal=etGetGoalValue.getText().toString().trim();
////                romGoalValue="";
//
//                    if(isMovGoalSet==false) {
//                        if(!goal.isEmpty()){
//                            romGoalValue = goal.trim();
//                        }else {
//                            romGoalValue="0";
//                        }
//
//
//
//                        etGetGoalValue.setHint("Movement Goal");
//                        goalHeader.setText("\nSet Daily Movement Goal\n");
//                        etGetGoalValue.setText("");
//                        cancelGoals.setEnabled(true);
//                        cancelGoals.setBackgroundColor(Color.parseColor("#db515a"));
//                    }else{
//                        if(!goal.isEmpty()){
//                            movGoalValue=goal.trim();
//                        }else {
//                            movGoalValue="0";
//                        }
//
//                        etGetGoalValue.setHint("Daily Rom Goal");
//                        goalHeader.setText("\nSet Daily Rom Goals\n\tMin:0\t\t Max:88");
//                    }
//
//
//
//                if(isMovGoalSet==false){
//                    isMovGoalSet=true;
//                    return;
//                }
//
//                try{
////                    romGoalValue=goal;
//                    if(Integer.parseInt(romGoalValue)!=0 && Integer.parseInt(movGoalValue)!=0) {
//                        saveGoalData(p_id,romGoalValue,movGoalValue);
//                    }else {
//                        Toast.makeText(myContext,"Goals cannot be non numeric or zero",Toast.LENGTH_SHORT).show();
//
//                    }
//                }catch (Exception e){
//                        Toast.makeText(myContext,"Goals cannot be non numeric or zero",Toast.LENGTH_SHORT).show();
//                }

//                mvScGoalGraph.setVisibility(View.VISIBLE);
                goalGraph.setVisibility(View.VISIBLE);
                tvGoalstext.setVisibility(View.VISIBLE);
                mvScGoalGraph.setAlpha(1f);
                goalGraph.setAlpha(1f);
                setGoalCard.setVisibility(View.INVISIBLE);
                romGoal=readGoalData(p_id);
//                readGoalData(p_id);
                Date currentDate=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("yyy-MM-dd");
                try {
                    if (sdf.parse(dailyRomDate.get(dailyRomDate.size() - 1).toString()).toString()==sdf.format(currentDate)){
                        Toast.makeText(myContext,"Date matched",Toast.LENGTH_SHORT).show();
                    }else {
//                        Toast.makeText(myContext,"Date does not match: D1: "+dailyRomDate.get(dailyRomDate.size() - 1).toString()+" D2:"+sdf.format(currentDate),Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){

                }
                if (romGoal>0) {
                    btnSetGoals.setVisibility(View.INVISIBLE);
                    tvGoalstext.setText("  ROM:\n" + Math.round(lastRom)+"/"+romGoal);
                    graphPlot.setupDonutChart(goalGraph, (float) Math.round(lastRom), (float) romGoal, Color.parseColor("#5493f7"));
                }
                tvResetGoals.setVisibility(View.VISIBLE);

            }
        });

        cancelGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalGraph.setVisibility(View.VISIBLE);
                tvGoalstext.setVisibility(View.VISIBLE);
                mvScGoalGraph.setAlpha(1f);
                goalGraph.setAlpha(1f);
                setGoalCard.setVisibility(View.INVISIBLE);
                romGoal= readGoalData(p_id);
//                readGoalData(p_id);
                if (romGoal>0) {
                    btnSetGoals.setVisibility(View.INVISIBLE);
                    tvResetGoals.setVisibility(View.VISIBLE);
                    tvGoalstext.setText("  ROM:\n" + Math.round(lastRom)+"/"+romGoal);
                    graphPlot.setupDonutChart(goalGraph, (float) Math.round(lastRom), (float) romGoal, Color.parseColor("#5493f7"));
                }
            }
        });

            game1card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isGameSarted = true;
                    sessionsLogic.createSessionFile(p_id);
//                    logics.createSessionFile(p_id);
                    logics.openGame("com.Galanto.Game1");

                }
            });
            game2card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isGameSarted = true;
                    sessionsLogic.createSessionFile(p_id);
//                    logics.createSessionFile(p_id);
                    logics.openGame("com.Galanto.Juice");

                }
            });

            calibrateCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logics.openGame("com.Galanto.Calibration");
                }
            });
        }catch (Exception ex){
            Toast.makeText(getContext(), "Error: " + ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeTimeDiffArray(){
        float timeDiff=0f;
        timeDiffArray=new ArrayList<>();
        for(int i=0;i<startTimeArray.size();i++){
            timeDiff=logics.getTimeDifference(startTimeArray.get(i),endTimeArray.get(i), ChronoUnit.MINUTES);
            timeDiffArray.add(timeDiff);
        }

    }

    private void setButtonAttributes(Button button){
        try {
            button.setBackgroundColor(Color.WHITE);
            button.setTextColor(getResources().getColor(R.color.accent_blue));
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i] != button) {
                    buttons[i].setBackgroundColor(getResources().getColor(R.color.accent_blue));
                    buttons[i].setTextColor(Color.WHITE);
                }
            }
        }catch (Exception e){
            Toast.makeText(getContext(),"Error in setButtonAttributes: "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void replaceFragment(Fragment fragment,int animEnter,int animExit) {

        FragmentManager fragmentManager=myContext.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(animEnter, animExit);
        fragmentTransaction.replace(R.id.linearLayout,fragment);
        fragmentTransaction.commit();

    }

    private void saveGoalData(int p_id,String romGoal){
        FileDataBase fileDataBase=new FileDataBase(getContext());
        File file= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File dir=new File(file.getAbsolutePath()+"/Galanto/RehabRelive/patient_"+String.valueOf(p_id),"goals.json");

        String dataToWrite="{\"romGoal\":"+romGoal+"}";
        if (dir.exists()){
            fileDataBase.updateFile("Galanto/RehabRelive/patient_"+String.valueOf(p_id),"goals.json",dataToWrite);
            Toast.makeText(getContext(),"Goals Added",Toast.LENGTH_SHORT).show();
        }else {
            fileDataBase.createFile("Galanto/RehabRelive/patient_"+String.valueOf(p_id),"goals.json",dataToWrite);
            Toast.makeText(getContext(),"Goals Added",Toast.LENGTH_SHORT).show();

        }
    }
    public int readGoalData(int p_id){
        FileDataBase fileDataBase=new FileDataBase(getContext());
        String result="";

        try {
            result=fileDataBase.readFile("Galanto/RehabRelive/patient_" + String.valueOf(p_id), "goals.json");
            if (!result.isEmpty()){
                JSONObject jsonObject=new JSONObject(result);
                romGoal=Integer.parseInt(jsonObject.getString("romGoal"));
//                movValue=Integer.parseInt(jsonObject.getString("movGoal"));
            }

        }catch (Exception e){
            Toast.makeText(getContext(),"Error in readGoalData: "+e.toString(),Toast.LENGTH_SHORT).show();

        }
        return  romGoal;
    }

    public void refreshActivity(){
        getActivity().finish();
        getActivity().startActivity(getActivity().getIntent());
    }

    @Override
    public void onResume(){
        super.onResume();
        if(isGameSarted){
            getActivity().startActivity(getActivity().getIntent());
        }
        isGameSarted=false;

    }
}