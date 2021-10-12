package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public EditText userIdEditText;
    public EditText passwordEditText;
    public Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userIdEditText=findViewById(R.id.username);
        passwordEditText=findViewById(R.id.passwordText);
        signInButton=findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyId()==Boolean.TRUE){
                    Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                    gotoDashboard();

                }else {
                    Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Boolean verifyId(){
        String userName=userIdEditText.getText().toString().trim();
        String password=passwordEditText.getText().toString().trim();
        Boolean isLoginVerified=Boolean.FALSE;
        if ((userName.equals("Kundan") && password.equals("kun123")) || (userName.equals("Ansh") && password.equals("bauna9365"))){
            isLoginVerified=Boolean.TRUE;
        }
        return isLoginVerified;
    }
    private void gotoDashboard(){
        Intent intent=new Intent(getApplicationContext(),Dashboard.class);
//        ActivityOptions options=ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.slide_in_left,
//                R.anim.fade_out);
        MainActivity.this.startActivity(intent);
    }
}