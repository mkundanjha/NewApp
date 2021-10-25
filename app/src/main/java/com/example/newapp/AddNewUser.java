package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class AddNewUser extends AppCompatActivity {

    Spinner selectGender;
    FloatingActionButton btnAdd;
    EditText inpName,inpAge,inpWeight;
    File file;
    String PATIENT_DATA="PatientData";
    Patients patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);
        selectGender=findViewById(R.id.selectGender);
        inpName=findViewById(R.id.inpName);
        inpAge=findViewById(R.id.inpAge);
        inpWeight=findViewById(R.id.inpWeight);
        file=new File(this.getFilesDir(), PATIENT_DATA);

        btnAdd=findViewById(R.id.addUsrBtn);

        FileDataBase fileDataBase=new FileDataBase(this);
        String response=fileDataBase.readFile("Galanto","data.json");
        Toast.makeText(this,response,Toast.LENGTH_SHORT);
        File file=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File dir=new File(file.getAbsolutePath()+"/Galanto/","data.json");




//        SpinnerAdapter adapter= new ArrayAdapter<String>(this, R.layout.spinner_item, new String[]{"Male","Female","Others"});
//        selectGender.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                   // patient=new Patients(inpName.getText().toString(),1);
                    if (dir.exists()){
                        fileDataBase.updateFile("Galanto","data.json",appendData());
                    }else {
                        fileDataBase.createFile("Galanto","data.json",appendData());

                    }


                }catch (Exception e){
                    Toast.makeText(AddNewUser.this,e.toString(),Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public String getDataInput(){
        String name,gender;
        int age,weight;
        JSONArray jsonArray=new JSONArray();

        JSONObject jsonObject=new JSONObject();
        try {
            name = inpName.getText().toString();
            age = Integer.parseInt(inpAge.getText().toString());
            weight = Integer.parseInt(inpWeight.getText().toString());
            gender = selectGender.getSelectedItem().toString();
            Patients patient = new Patients(name, 1, age, weight, gender);



            jsonObject.put("p_id",1);
            jsonObject.put("name",name);
            jsonObject.put("age",age);
            jsonObject.put("weight",weight);
            jsonObject.put("gender",gender);

            jsonArray.put(jsonObject);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return jsonArray.toString();
    }

    public String appendData(){
        String name,gender;
        int age,weight;
        JSONArray jsonArray=null;
        FileDataBase fileDataBase=new FileDataBase(this);
        String response=fileDataBase.readFile("Galanto","data.json");
        try {
            //read json array from stored data
            if (response!=""){
                jsonArray=new JSONArray(response);
            }else {
                jsonArray=new JSONArray();
            }
            JSONObject jsonObject =new JSONObject();
            name = inpName.getText().toString();
            age = Integer.parseInt(inpAge.getText().toString());
            weight = Integer.parseInt(inpWeight.getText().toString());
            gender = selectGender.getSelectedItem().toString();
            Patients patient = new Patients(name, 1, age, weight, gender);

            jsonObject.put("p_id",1);
            jsonObject.put("name",name);
            jsonObject.put("age",age);
            jsonObject.put("weight",weight);
            jsonObject.put("gender",gender);

            jsonArray.put(jsonObject);


        }catch (Exception ex){
            Toast.makeText(this,"Error in appendData: "+ex.toString(),Toast.LENGTH_SHORT).show();
        }
        return jsonArray.toString();
    }

    @Override
    public void onBackPressed(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }


}