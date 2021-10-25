package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class LoginActivity extends AppCompatActivity {

    CardView usr1,usr2,usr3;
    TextView username1,username2,username3;
    File file;
    String PATIENT_DATA="PatientData";
    FloatingActionButton goToAddUsrBtn;
    SearchView serach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        usr1=findViewById(R.id.usrCard);
        usr2=findViewById(R.id.userCard2);
        usr3=findViewById(R.id.userCard3);
        username1=findViewById(R.id.usrFirstName);
        username2=findViewById(R.id.usrSecondName);
        username3=findViewById(R.id.usrThirdName);
        goToAddUsrBtn=findViewById(R.id.gotoAddUsrBtn);
        file=new File(LoginActivity.this.getFilesDir(), PATIENT_DATA);

        setUserProfileData();

        usr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashboard();
            }
        });

        goToAddUsrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddUser();
            }
        });
    }

    public void goToDashboard(){
        Intent intent=new Intent(LoginActivity.this, Dashboard.class);
        intent.putExtra("user_name",username1.getText().toString());
        startActivity(intent);
    }

    public void goToAddUser(){
        Intent intent=new Intent(this,AddNewUser.class);
        startActivity(intent);
    }
    public void setUserProfileData(){
        FileDataBase fileDataBase=new FileDataBase(this);
        try {
            String response = fileDataBase.readFile("Galanto", "data.json");
            JSONArray jsonArray=new JSONArray(response);
            JSONObject jsonObject1,jsonObject2,jsonObject3;
            jsonObject1= jsonArray.getJSONObject(0);
            //jsonObject2= jsonArray.getJSONObject(1);
            //jsonObject3= jsonArray.getJSONObject(2);
            username1.setText(jsonObject1.getString("name"));
            //username2.setText(jsonObject2.getString("name"));
            //username3.setText(jsonObject3.getString("name"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


}