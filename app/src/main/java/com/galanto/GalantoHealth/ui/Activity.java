package com.galanto.GalantoHealth.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newapp.R;
import com.galanto.GalantoHealth.GameClass;
import com.galanto.GalantoHealth.GameListAdaptar;
import com.galanto.GalantoHealth.GameOnClick;
import com.galanto.GalantoHealth.Logics;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import app.futured.donut.DonutProgressView;
import app.futured.donut.DonutSection;


public class Activity extends Fragment {

    CardView gameScoreCard,durationCard,game1Card,game2Card;
    TextView tvGameScore,tvTotalTime,tvGameHeaderText;
    ImageView ivGameHeaderImage;
    RecyclerView gameListView,recommendedGamesView;
    ArrayList<GameClass> gameClassArrayList,recommendedGameArray;
    PieChart progresschart;
    DonutProgressView demoDonut;
    Button btnPlayGame;
    Logics logics;
    SessionsLogic sessionsLogic;
    ArrayList<Integer> gameScoreArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {

            gameScoreCard=view.findViewById(R.id.actGameScoreCard);
            durationCard=view.findViewById(R.id.durationCard);
            btnPlayGame=view.findViewById(R.id.btnPlayGame);
            tvGameScore=view.findViewById(R.id.actGameScore);
            tvTotalTime=view.findViewById(R.id.ActDuration);
            gameListView=view.findViewById(R.id.gameListView);
            recommendedGamesView=view.findViewById(R.id.recommendedGamesView);
            tvGameHeaderText=view.findViewById(R.id.GameHeaderTitle);
            ivGameHeaderImage=view.findViewById(R.id.GameHeaderImage);

            Intent intent = this.getActivity().getIntent();
            int p_id = Integer.parseInt(intent.getStringExtra("p_id"));

            logics=new Logics(getContext());
            sessionsLogic=new SessionsLogic(getContext());
            boolean isNewUser=sessionsLogic.readAllSessionsData(p_id);
            if (isNewUser){
                return;
            }

            gameScoreArray=sessionsLogic.getMovementScoreArray();

            gameScoreArray=sessionsLogic.getGameScoreArray();

            tvGameScore.setText(String.valueOf((logics.sumOfArrayInt(gameScoreArray))));

            gameClassArrayList=new ArrayList<>();
            recommendedGameArray=new ArrayList<>();

            gameClassArrayList.add(new GameClass("Runman",R.drawable.runman_icon));
            gameClassArrayList.add(new GameClass("Squeezy",R.drawable.squuzy_icon));
            gameClassArrayList.add(new GameClass("Game 3",R.drawable.game1_crp));
            gameClassArrayList.add(new GameClass("Game 4",R.drawable.game1_icon));
            gameClassArrayList.add(new GameClass("Game 4",R.drawable.game1_icon));
            gameClassArrayList.add(new GameClass("Game 4",R.drawable.game1_icon));
            gameClassArrayList.add(new GameClass("Game 3",R.drawable.game1_crp));
            gameClassArrayList.add(new GameClass("Game 4",R.drawable.game1_icon));
            gameClassArrayList.add(new GameClass("Game 4",R.drawable.game1_icon));
            gameClassArrayList.add(new GameClass("Game 4",R.drawable.game1_icon));

            recommendedGameArray.add(new GameClass("Runman",R.drawable.runman_icon));
            recommendedGameArray.add(new GameClass("Squeezy",R.drawable.squuzy_icon));
            recommendedGameArray.add(new GameClass("Game 3",R.drawable.game1_crp));
            recommendedGameArray.add(new GameClass("Game 4",R.drawable.game1_icon));


            GameListAdaptar adaptar=new GameListAdaptar(getContext(),gameClassArrayList);
            GameListAdaptar adaptar2=new GameListAdaptar(getContext(),recommendedGameArray);
            GridLayoutManager layoutManager=new GridLayoutManager(getContext(),5);

            gameListView.setLayoutManager(layoutManager);
            gameListView.setAdapter(adaptar);

            recommendedGamesView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
            recommendedGamesView.setAdapter(adaptar2);
            recommendedGamesView.getBackground().setAlpha(60);


            adaptar.setWhenClickListener(new GameOnClick() {
                @Override
                public void onClick(GameClass gameClass) {
                    setPlayButtonVisible();
                    tvGameHeaderText.setVisibility(View.VISIBLE);
                    ivGameHeaderImage.setVisibility(View.VISIBLE);
                    tvGameHeaderText.setText(gameClass.getGameTitle());
                    ivGameHeaderImage.setBackgroundResource(gameClass.getGameImage());
                }
            });

            adaptar2.setWhenClickListener(new GameOnClick() {
                @Override
                public void onClick(GameClass gameClass) {
                    setPlayButtonVisible();
                    tvGameHeaderText.setVisibility(View.VISIBLE);
                    ivGameHeaderImage.setVisibility(View.VISIBLE);
                    tvGameHeaderText.setText(gameClass.getGameTitle());
                    ivGameHeaderImage.setBackgroundResource(gameClass.getGameImage());
                }
            });



//            game1Card.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    setPlayButtonVisible();
//                }
//            });
//
//            game2Card.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    setPlayButtonVisible();
//                }
//            });
        }catch (Exception ex){
            Toast.makeText(getContext(),"Error: "+ex.toString(),Toast.LENGTH_LONG).show();
        }

    }

    public void setPlayButtonVisible(){
        btnPlayGame.setVisibility(View.VISIBLE);
        gameScoreCard.setVisibility(View.INVISIBLE);
        durationCard.setVisibility(View.INVISIBLE);
    }





}   
