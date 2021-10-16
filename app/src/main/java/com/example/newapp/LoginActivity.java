package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        goToAddUsrBtn=findViewById(R.id.gotoAddUsrBtn);
        file=new File(LoginActivity.this.getFilesDir(), PATIENT_DATA);

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
        startActivity(intent);
    }

    public void goToAddUser(){
        Intent intent=new Intent(this,AddNewUser.class);
        startActivity(intent);
    }


}