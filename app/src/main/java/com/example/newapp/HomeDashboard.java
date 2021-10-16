package com.example.newapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;


public class HomeDashboard extends Fragment {

    TextView currentDay;
    TextView currentDate;

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

        //setDateTime(currentDate,currentDay);


        return inflater.inflate(R.layout.fragment_home_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentDate=view.findViewById(R.id.tvCurrentDate);
        currentDay=(TextView) view.findViewById(R.id.tvCurrentDay);
        setDateTime(currentDate,currentDay);
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