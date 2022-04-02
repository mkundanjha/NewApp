package com.galanto.GalantoHealth;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.anychart.scales.DateTime;
import com.galanto.GalantoHealth.ui.SessionsLogic;
import com.github.mikephil.charting.formatter.ValueFormatter;


import java.io.Console;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyXAxisValueFormatter extends ValueFormatter  {
    Context context;
    Date currentDate=new Date();
    SimpleDateFormat format=new SimpleDateFormat("d MMMM");
    String oldDate=format.format(currentDate);
    ArrayList<String> dates =new ArrayList<String>();
//    SessionsLogic sessionsLogic=new SessionsLogic(context);
    ArrayList<LocalDate> romDates;

    public MyXAxisValueFormatter(ArrayList<LocalDate> dates) {
        this.romDates = dates;
    }

    @Override
    public String getFormattedValue(float value) {
//        dates.add("2022-01-20");
//        dates.add("2022-01-21");
//        dates.add("2022-01-22");
        for(int i=0;i<romDates.size();i++){
            dates.add(romDates.get(i).toString());
            Log.d("dates + i:",dates.get(i).toString()+" "+i);
        }
//        romDates=sessionsLogic.getDailyRomDates();
        oldDate=romDates.get(0).toString();
        Log.d("Value",String.valueOf(value));
        SimpleDateFormat sdf=new SimpleDateFormat("yyy-MM-dd");
//        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyy");
        SimpleDateFormat formater=new SimpleDateFormat("dd MMM");
        Calendar c=Calendar.getInstance();
        try {
            c.setTime(sdf.parse((dates.get(((int)value)).toString())));
            //c.setTime(sdf.parse((romDates.get(((int)value)).toString())));
//            c.setTime(sdf.parse(oldDate));

        }catch (ParseException f){
            f.printStackTrace();
        }
//        c.add(Calendar.DAY_OF_MONTH,(int) value);
        String newDate=formater.format(c.getTime());




        return String.valueOf(newDate);
    }
}