package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        Database database=new Database(file);

        String response=database.readFileData();
        Toast.makeText(AddNewUser.this,response,Toast.LENGTH_SHORT).show();

//        SpinnerAdapter adapter= new ArrayAdapter<String>(this, R.layout.spinner_item, new String[]{"Male","Female","Others"});
//        selectGender.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    patient=new Patients(inpName.getText().toString(),1);

                    if (database.checkFileExist()){
                        database.updateData(patient);
                        Toast.makeText(AddNewUser.this,"User added",Toast.LENGTH_SHORT).show();


                    }else {
                        database.insertData(patient);
                        Toast.makeText(AddNewUser.this,"New User added",Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e){
                    Toast.makeText(AddNewUser.this,e.toString(),Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}