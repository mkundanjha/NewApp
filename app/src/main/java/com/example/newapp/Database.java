package com.example.newapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Database {
    File file;

    public Database(File file) {
        this.file=file;
    }


    // Private method which is used to create a json array of patient
    public void insertData(Patients patient){
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("Name",patient.getName());
            jsonObject.put("Id",patient.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(jsonObject);
        setDataToFile(jsonArray);
    }

    // This method is used to insert Patient data to File
    private void setDataToFile(JSONArray array) {
        String userString=array.toString();
        try {
            FileWriter fileWriter=new FileWriter(file);
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
            bufferedWriter.write(userString);
            bufferedWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // Method to get json data as string from file
    public String readFileData(){
        String response="";
        try {
            StringBuilder stringBuilder=new StringBuilder();
            FileReader fileReader=new FileReader(file);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String line=bufferedReader.readLine();
            while (line!=null){
                stringBuilder.append(line).append("\n");
                line=bufferedReader.readLine();
            }
            bufferedReader.close();
            response=stringBuilder.toString();

        }catch(IOException e){
            e.printStackTrace();
        }
        return response;
    }

    // method to return a Patient array with data from file
    public ArrayList<Patients> getPatientArrayList(){
        String response=readFileData();
        ArrayList<Patients> patients=new ArrayList<Patients>();

        try{
            JSONArray jsonArray=new JSONArray(response);
            JSONObject jsonObject;
            for(int i=0;i<jsonArray.length();i++){
                jsonObject=jsonArray.getJSONObject(i);
                //Patients patient=new Patients(jsonObject.getString("Name"),jsonObject.getInt("Id"));
                //patients.add(patient);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return patients;
    }

    // get Patients data and update it in json file
    public  void  updateData(Patients patient){
        String response=readFileData();
        try{
            JSONArray array=new JSONArray(response);

            JSONObject jsonObject=new JSONObject();
            jsonObject.put("Name",patient.getName());
            jsonObject.put("Id",patient.getId());

            array.put(jsonObject);
            setDataToFile(array);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    // check if the file with filename exist or not
    public boolean checkFileExist(){
        if(file.exists()){
            return true;
        }else {
            return false;
        }
    }



}
