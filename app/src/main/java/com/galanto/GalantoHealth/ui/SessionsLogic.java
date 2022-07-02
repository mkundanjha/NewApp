package com.galanto.GalantoHealth.ui;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.galanto.GalantoHealth.FileDataBase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Formatter;

public class SessionsLogic {
    FileDataBase fileDataBase;
    Context context;

    DateTimeFormatter romDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");



    public SessionsLogic(Context context) {
        this.context = context;
        fileDataBase=new FileDataBase(context);
    }

    ArrayList<Float> timeStampPerSessionArray,mcpThumbArray, mcpIndexArray, mcpMiddleArray, mcpRingArray, mcpLittleArray
            , pipThumbArray, pipIndexArray, pipMiddleArray, pipRingArray, pipLittleArray, wristArray, hitRateArray,avRomScoreArray;
    ArrayList<String> handSegmentUsedArray;  // Portion of Hand last exercised
    ArrayList<Integer> movementScoreArray, gameScoreArray;
    ArrayList<LocalDateTime> sessionStartTimeArray,sessionEndTimeArray;
    ArrayList<LocalDate> dailyRomDates;

    //GETTERS METHODS
    public ArrayList<Float> getTimeStampPerSessionArray() {
        return timeStampPerSessionArray;
    }

    public ArrayList<Float> getMcpThumbArray() {
        return mcpThumbArray;
    }

    public ArrayList<Float> getMcpIndexArray() {
        return mcpIndexArray;
    }

    public ArrayList<Float> getMcpMiddleArray() {
        return mcpMiddleArray;
    }

    public ArrayList<Float> getMcpRingArray() {
        return mcpRingArray;
    }

    public ArrayList<Float> getMcpLittleArray() {
        return mcpLittleArray;
    }

    public ArrayList<Float> getPipThumbArray() {
        return pipThumbArray;
    }

    public ArrayList<Float> getPipIndexArray() {
        return pipIndexArray;
    }

    public ArrayList<Float> getPipMiddleArray() {
        return pipMiddleArray;
    }

    public ArrayList<Float> getPipRingArray() {
        return pipRingArray;
    }

    public ArrayList<Float> getPipLittleArray() {
        return pipLittleArray;
    }

    public ArrayList<Float> getWristArray() {
        return wristArray;
    }

    public ArrayList<Float> getHitRateArray() {
        return hitRateArray;
    }

    public ArrayList<String> getHandSegmentUsedArray() {
        return handSegmentUsedArray;
    }

    public ArrayList<Integer> getMovementScoreArray() {
        return movementScoreArray;
    }

    public ArrayList<Integer> getGameScoreArray() {
        return gameScoreArray;
    }

    public ArrayList<LocalDateTime> getSessionStartTimeArray() {
        return sessionStartTimeArray;
    }

    public ArrayList<LocalDateTime> getSessionEndTimeArray() { return sessionEndTimeArray; }

    public ArrayList<Float> getAvRomScoreArray() { return avRomScoreArray; }

    public ArrayList<LocalDate> getDailyRomDates(){return dailyRomDates;}

    // read all the sessions data of users
    public boolean  readAllSessionsData(int p_id){
        String lastSession;
        int lastGameId=0;
        fileDataBase=new FileDataBase(context);
        String folderName="patient_"+String.valueOf(p_id);
        LocalDateTime ssnStTime,ssnEndTime;

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyy   HH:mm");

        try {
            //Read all session.json file
            String allSessionData= fileDataBase.readFile("Galanto/RehabRelive/"+folderName,"all_sessions.json");

            if (allSessionData.isEmpty()){
                // Is a new user
                return true;
            }
            //isNewUser=false;
            JSONObject jsonObject=new JSONObject(allSessionData);

            JSONArray jsonArray=jsonObject.getJSONArray("allSessionDatas");
            JSONObject lastJsonObject=jsonArray.getJSONObject(jsonArray.length()-1);


            lastSession=lastJsonObject.getString("session_id");
            lastGameId=lastJsonObject.getInt("game_id");
//            timeElapsedSession= ssnStTime.until(ssnEndTime, ChronoUnit.MINUTES);

            // If there are at least 20 sessions then show data as per the session
            // If there are less than 20 session and show data of last session
            //if (jsonArray.length()>20){
                if (true){
                    // Initialize Array
                    mcpThumbArray=new ArrayList<>();
                    mcpIndexArray=new ArrayList<>();
                    mcpMiddleArray=new ArrayList<>();
                    mcpRingArray=new ArrayList<>();
                    mcpLittleArray=new ArrayList<>();
                    pipThumbArray=new ArrayList<>();
                    pipIndexArray=new ArrayList<>();
                    pipMiddleArray=new ArrayList<>();
                    pipRingArray=new ArrayList<>();
                    pipLittleArray=new ArrayList<>();
                    wristArray=new ArrayList<>();
                    gameScoreArray=new ArrayList<>();
                    movementScoreArray=new ArrayList<>();
                    handSegmentUsedArray=new ArrayList<>();
                    sessionStartTimeArray=new ArrayList<>();
                    sessionEndTimeArray=new ArrayList<>();
                    hitRateArray=new ArrayList<>();

                for (int i=0;i<jsonArray.length();i++){
                    JSONObject arrayObject=jsonArray.getJSONObject(i);




                    //MCP Data of All Fingers
                    mcpThumbArray.add(Float.parseFloat(arrayObject.getString("thumb_mcp_max"))
                            -Float.parseFloat(arrayObject.getString("thumb_mcp_min")));
                    mcpIndexArray.add(Float.parseFloat(arrayObject.getString("index_mcp_max"))
                            -Float.parseFloat(arrayObject.getString("index_mcp_min")));
                    mcpMiddleArray.add(Float.parseFloat(arrayObject.getString("middle_mcp_max"))
                            -Float.parseFloat(arrayObject.getString("middle_mcp_min")));
                    mcpRingArray.add(Float.parseFloat(arrayObject.getString("ring_mcp_max"))
                            -Float.parseFloat(arrayObject.getString("ring_mcp_min")));
                    mcpLittleArray.add(Float.parseFloat(arrayObject.getString("pinky_mcp_max"))
                            -Float.parseFloat(arrayObject.getString("pinky_mcp_min")));

                    //PIP Data of All Fingers
                    pipIndexArray.add(Float.parseFloat(arrayObject.getString("index_pip_max"))
                            -Float.parseFloat(arrayObject.getString("index_pip_min")));
                    pipMiddleArray.add(Float.parseFloat(arrayObject.getString("middle_pip_max"))
                            -Float.parseFloat(arrayObject.getString("middle_pip_min")));
                    pipRingArray.add(Float.parseFloat(arrayObject.getString("ring_pip_max"))
                            -Float.parseFloat(arrayObject.getString("ring_pip_min")));
                    pipLittleArray.add(Float.parseFloat(arrayObject.getString("pinky_mcp_max"))
                            -Float.parseFloat(arrayObject.getString("pinky_mcp_min")));
                    pipThumbArray.add(Float.parseFloat(arrayObject.getString("thumb_pip_max"))
                            -Float.parseFloat(arrayObject.getString("thumb_pip_min")));

                    //Wrist Data
                    wristArray.add(Float.parseFloat(arrayObject.getString("wrist_max"))
                            -Float.parseFloat(arrayObject.getString("wrist_min")));

                    //Game Scores
                    gameScoreArray.add((int) Float.parseFloat(arrayObject.getString("game_score")));

                    //Movement Score
                    movementScoreArray.add(Integer.parseInt(arrayObject.getString("movement_score")));

                    handSegmentUsedArray.add(arrayObject.getString("hand_segments"));

                    hitRateArray.add(Float.parseFloat(arrayObject.getString("hit_rate")));

                    //Start and End Time
                    sessionStartTimeArray.add(LocalDateTime.parse(arrayObject.getString("start_time_stamp"),formatter1));
                    sessionEndTimeArray.add(LocalDateTime.parse(arrayObject.getString("stop_time_stamp"),formatter1));


                }

            }else{
                //Read Last session
                readSession(lastSession,p_id);
            }

        }catch (Exception ex){
            Toast.makeText(context,"Error in read session data: "+ex.toString(),Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;

    }

    public void createSessionFile(int p_id){
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
            Toast.makeText(context,"Error in createSessionFile: "+ex.toString(),Toast.LENGTH_SHORT).show();
        }}

    public void readSession(String sessionName, int p_id){
        String fileName=sessionName;
        String folderName="patient_"+String.valueOf(p_id);
        try {
            String lastSessionData=fileDataBase.readFile("Galanto/RehabRelive/"+folderName,fileName+".json");
            if (lastSessionData.isEmpty()){
                return;
            }
            JSONObject object=new JSONObject(lastSessionData);
            JSONArray array=object.getJSONArray("fineSessionDatas");

            // Increment Frequency
            int increment=1;

            //if there are more than 100 data point we only increment in 100 multiple
            // For 500 data point the increment counter is 500/100 i.e. 5
            if (array.length()>100){
                increment=array.length()/100;
            }

            timeStampPerSessionArray=new ArrayList<>();
            mcpThumbArray =new ArrayList<>();
            mcpIndexArray =new ArrayList<>();
            mcpMiddleArray =new ArrayList<>();
            mcpRingArray =new ArrayList<>();
            mcpLittleArray =new ArrayList<>();
            pipIndexArray =new ArrayList<>();
            pipMiddleArray =new ArrayList<>();
            pipRingArray =new ArrayList<>();
            pipLittleArray =new ArrayList<>();
            pipThumbArray =new ArrayList<>();
            wristArray =new ArrayList<>();

            for (int i =0;i<array.length();i+=increment){
                JSONObject arrayObject=array.getJSONObject(i);

                timeStampPerSessionArray.add(Float.parseFloat(arrayObject.getString("time_since_startup")));

                mcpThumbArray.add(Float.parseFloat(arrayObject.getString("thumb_mcp")));
                mcpIndexArray.add(Float.parseFloat(arrayObject.getString("index_mcp")));
                mcpMiddleArray.add(Float.parseFloat(arrayObject.getString("middle_mcp")));
                mcpRingArray.add(Float.parseFloat(arrayObject.getString("ring_mcp")));
                mcpLittleArray.add(Float.parseFloat(arrayObject.getString("pinky_mcp")));

                pipIndexArray.add(Float.parseFloat(arrayObject.getString("index_pip")));
                pipMiddleArray.add(Float.parseFloat(arrayObject.getString("middle_pip")));
                pipRingArray.add(Float.parseFloat(arrayObject.getString("ring_pip")));
                pipLittleArray.add(Float.parseFloat(arrayObject.getString("ring_mcp")));
                pipThumbArray.add(Float.parseFloat(arrayObject.getString("pinky_pip")));

                wristArray.add(Float.parseFloat(arrayObject.getString("wrist")));

            }
        }catch (Exception e){
            Toast.makeText(context,"Error in readSession : "+e.toString(),Toast.LENGTH_SHORT).show();

        }

    }

    public void getAverageRom(int p_id){
        try{
            avRomScoreArray=new ArrayList<>();
            dailyRomDates=new ArrayList<>();
            String romData= fileDataBase.readFile("Galanto/RehabRelive/patient_"+String.valueOf(p_id),"all_rom.json");
            if(!romData.isEmpty()){
                JSONObject jsonObject1=new JSONObject(romData);
                JSONArray jsonArray=jsonObject1.getJSONArray("allRomDatas");
                JSONObject jsonObject=jsonArray.getJSONObject(jsonArray.length()-1);
                for (int i=0;i<jsonArray.length();i++){
                    //avRomScoreArray.add((Float.parseFloat(jsonArray.getJSONObject(i).getString("averageRom"))/88)*100);
                    avRomScoreArray.add(Float.parseFloat(jsonArray.getJSONObject(i).getString("averageRom")));
                    dailyRomDates.add(LocalDate.parse(jsonArray.getJSONObject(i).getString("date"),romDateFormatter));

                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(context,"Error in setRomData(): "+ex.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    



}
