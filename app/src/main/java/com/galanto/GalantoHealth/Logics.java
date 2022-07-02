package com.galanto.GalantoHealth;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class Logics {
    GraphPlot graphPlot;
    Context context;

    public Logics(Context context) {
        this.context = context;
    }

    // Create a Line graph
    public void setLineChart(LineChart lineChart, String label,ArrayList<LocalDate> dateData ,ArrayList<Float> arrayList,ArrayList<Float> arrayList2, int graphColor,String description){
        ArrayList<Entry> romData=new ArrayList<>();
        ArrayList<Entry> dataSet2 = new ArrayList<>();
        graphPlot=new GraphPlot();
        Float xValues;
        Float minXaxis,minYaxis,maxXaxis,maxYaxis,minYaxis2,maxYaxis2;
        minXaxis=Float.MAX_VALUE;
        minYaxis=Float.MAX_VALUE;
        maxXaxis=Float.MIN_VALUE;
        maxYaxis=Float.MIN_VALUE;
        minYaxis2=Float.MAX_VALUE;
        maxYaxis2=Float.MIN_VALUE;

        minXaxis=0f;
        maxXaxis=10f;
        maxYaxis=15f;
        try {


        for (int i=0;i<arrayList.size();i++){

            if (arrayList.get(i)<minYaxis){
                minYaxis=arrayList.get(i);
            }

            if(arrayList.get(i)>maxYaxis){
                maxYaxis=arrayList.get(i);
            }

            romData.add(new Entry(i,arrayList.get(i)));
        }

        if(arrayList2!=null) {
            for (int i = 0; i < arrayList2.size(); i++) {

                if (arrayList2.get(i) < minYaxis2) {
                    minYaxis2 = arrayList.get(i);
                }

                if (arrayList2.get(i) > maxYaxis2) {
                    maxYaxis2 = arrayList.get(i);
                }

                dataSet2.add(new Entry(i, arrayList2.get(i)));
            }
            maxYaxis2= Collections.max(arrayList2);
            minYaxis2=Collections.min(arrayList2);
        }else {
            dataSet2=null;
        }



        maxYaxis= Collections.max(arrayList);
        minYaxis=Collections.min(arrayList);
        maxYaxis=Math.max(maxYaxis,maxYaxis2);
        minYaxis=Math.min(minYaxis,minYaxis2);
            //romChart.setVisibility(View.VISIBLE);

            graphPlot.createLineChart(lineChart, dateData, romData, dataSet2, label, graphColor, minXaxis, minYaxis, maxXaxis, maxYaxis,description);
            //createLineChart(romChart,romData,label,Color.WHITE);
        }catch (Exception e){
            Toast.makeText(lineChart.getContext(), e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    //To calculate Max of Array
    public int findMax(ArrayList<Float> arrayList){
        float max=Float.MIN_VALUE;
        for(int i=0;i<arrayList.size();i++){
            if (arrayList.get(i)>max){
                max=arrayList.get(i);
            }
        }
        return Math.round(max);
    }

    //To calculate sum of elements of Array of type Int
    public int sumOfArrayInt(ArrayList<Integer> arrayList){
        int sum=0;
        for(int i=0;i<arrayList.size();i++){
            sum+=arrayList.get(i);
        }
        return sum;
    }

    //To calculate sum of elements of Array of type Float
    public float sumOfArrayFloat(ArrayList<Float> arrayList){
        float sum=0f;
        for(int i=0;i<arrayList.size();i++){
            sum+=arrayList.get(i);
        }
        return sum;
    }

    // Convert an Integer array to Float Arraylist
    public ArrayList<Float> convertIntToFloatArray(ArrayList<Integer> arrayList){
        //final float[] arr = new float[arrayList.size()];
        ArrayList<Float> arr=new ArrayList<>();
        for (int i=0;i<arrayList.size();i++){
            arr.add((float) arrayList.get(i));
        }

        return arr;
    }

    public void createSessionFile(int p_id){
        try{
            FileDataBase fileDataBase=new FileDataBase(context);
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
            Toast.makeText(context,"Error in createSessionFile: "+ex.toString(),Toast.LENGTH_SHORT).show();
        }}


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

    public  boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    //To dynamically set numbers data formant e.g. 2k, 11M,etc.
    public String formatNumber(int inputNumber){
        String returnFormat="";
        try{
            if(inputNumber>=1000 && inputNumber<100000){

            }
        }catch (Exception ex){

        }
        return returnFormat;
    }

    // To open a game from dashboard
    public void openGame(String packageName){
        Intent intent =context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent!=null){
            Toast.makeText(context.getApplicationContext(),"Opening Game",Toast.LENGTH_SHORT).show();
            context.startActivity(intent);
            //getActivity().finishAndRemoveTask();
        }else
        {
            Toast.makeText(context, "App not installed",Toast.LENGTH_SHORT).show();
        }
        //isGameSarted=true;
    }

    // To get difference between two time stamps
    public float getTimeDifference(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit timeUnit){
        //e.g. of ChronUnit MINUTES,MONTHS,DAYS etc.
        long timeDifference = 0;
        try {
            timeDifference=startTime.until(endTime, timeUnit);
        }catch (Exception e){
            //Toast.makeText(context,"Error in getTimeDifference : "+e.toString(),Toast.LENGTH_SHORT).show();
        }
        return timeDifference;
    }

    public boolean isNumber(String  number){
        try {
            Integer.parseInt(number);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    // Ste the current data and time
    public void setDateTime(TextView date, TextView day){
        try {
            Date currentDate=new Date();
            SimpleDateFormat format=new SimpleDateFormat("d MMMM");
            date.setText( format.format(currentDate).toString());
            //Toast.makeText(getContext(),format.format(currentDate).toString(),Toast.LENGTH_SHORT).show();
            DayOfWeek dayOfWeek= LocalDate.now().getDayOfWeek();
            day.setText(dayOfWeek.toString());

        }catch (Exception ex){
            Toast.makeText(context,ex.toString(),Toast.LENGTH_SHORT).show();
        }
    }

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


}
