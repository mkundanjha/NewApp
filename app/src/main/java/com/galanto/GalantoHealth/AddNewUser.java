package com.galanto.GalantoHealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.newapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class AddNewUser extends AppCompatActivity {

    Spinner selectGender,hndImp;
    FloatingActionButton btnAdd;
    EditText inpName,inpAge,inpWeight;
    File file;
    String PATIENT_DATA="PatientData";

    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);
        selectGender=findViewById(R.id.selectGender);
        hndImp=findViewById(R.id.hndImp);
        inpName=findViewById(R.id.inpName);
        inpAge=findViewById(R.id.inpAge);
        inpWeight=findViewById(R.id.inpWeight);
        backButton=findViewById(R.id.backButton);
        file=new File(this.getFilesDir(), PATIENT_DATA);

        btnAdd=findViewById(R.id.addUsrBtn);

        FileDataBase fileDataBase=new FileDataBase(this);
        String response=fileDataBase.readFile("Galanto/RehabRelive","patients.json");
        Toast.makeText(this,response,Toast.LENGTH_SHORT);
        File file=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File dir=new File(file.getAbsolutePath()+"/Galanto/RehabRelive/","patients.json");
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("Male");
        arrayList.add("Female");
        arrayList.add("Others");
        arrayList.add("Gender");
        ArrayList<String> arrayList2=new ArrayList<>();
        arrayList2.add("Left");
        arrayList2.add("Right");
        arrayList2.add("Both");
        arrayList2.add("Hand Impaired");






        SpinnerAdapter adapter= new ArrayAdapter(this,R.layout.spinner_item,arrayList){
            @Override
            public int getCount() {
                // to show hint "Select Gender" and don't able to select
                return arrayList.size()-1;
            }
        };
        selectGender.setAdapter(adapter);
        selectGender.setSelection(arrayList.size()-1);


        SpinnerAdapter adapter2= new ArrayAdapter(this,R.layout.spinner_item,arrayList2){
            @Override
            public int getCount() {
                // to show hint "Select Gender" and don't able to select
                return arrayList2.size()-1;
            }

        };

        hndImp.setAdapter(adapter2);
        hndImp.setSelection(arrayList2.size()-1);






        backButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                backButton.requestLayout();
                backButton.getLayoutParams().height=70;
                backButton.getLayoutParams().width=50;
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    backButton.requestLayout();
                    backButton.getLayoutParams().height=100;
                    backButton.getLayoutParams().width=80;
                    onBackPressed();
                    finish();
                }
                return true;
            }


        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(TextUtils.isEmpty(inpName.getText().toString())) {
                        inpName.setError("Please Enter Name");
                        return;
                    }
                    if(TextUtils.isEmpty(inpAge.getText().toString())) {
                        inpAge.setError("Please Enter Age");
                        return;
                    }
                    if(TextUtils.isEmpty(inpWeight.getText().toString())) {
                        inpWeight.setError("Please Enter Weight");
                        return;
                    }

                    if (!(selectGender.getSelectedItem()=="Male" || selectGender.getSelectedItem()=="Female" || selectGender.getSelectedItem()=="Others")){
                        Toast.makeText(getApplicationContext(),"Please select Gender",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(!(hndImp.getSelectedItem()=="Left" || hndImp.getSelectedItem()=="Right" || hndImp.getSelectedItem()=="Both" )){
                        Toast.makeText(getApplicationContext(),"Please select Hand Impairment ",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (dir.exists()){
                        fileDataBase.updateFile("Galanto/RehabRelive","patients.json",appendData());
                        Toast.makeText(getApplicationContext(),"New User Added",Toast.LENGTH_SHORT).show();
                        inpName.setText("");
                        inpAge.setText("");
                        inpWeight.setText("");
                    }else {
                        fileDataBase.createFile("Galanto/RehabRelive","patients.json",appendData());
                        Toast.makeText(getApplicationContext(),"New User Added",Toast.LENGTH_SHORT).show();
                        inpName.setText("");
                        inpAge.setText("");
                        inpWeight.setText("");
                    }


                }catch (Exception e){
                    Toast.makeText(AddNewUser.this,e.toString(),Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public String getDataInput(){
        String name,gender,handImp;
        int age,weight;
        JSONArray jsonArray=new JSONArray();

        JSONObject jsonObject=new JSONObject();
        try {
            name = inpName.getText().toString();
            age = Integer.parseInt(inpAge.getText().toString());
            weight = Integer.parseInt(inpWeight.getText().toString());
            gender = selectGender.getSelectedItem().toString();
            handImp=hndImp.getSelectedItem().toString();

            Patients patient = new Patients(name, 1, age, weight, gender,handImp);



            jsonObject.put("p_id",1);
            jsonObject.put("name",name);
            jsonObject.put("age",age);
            jsonObject.put("weight",weight);
            jsonObject.put("gender",gender);
            jsonObject.put("handImp",handImp);

            jsonArray.put(jsonObject);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return jsonArray.toString();
    }

    public String appendData(){
        String name,gender,handImp;
        int age,weight;
        int lastId=0;
        JSONArray jsonArray=null;
        FileDataBase fileDataBase=new FileDataBase(this);
        String response=fileDataBase.readFile("Galanto/RehabRelive","patients.json");
        try {
            //read json array from stored data
            if (response!=""){
                jsonArray=new JSONArray(response);
                if (jsonArray.length()>0) {
                    lastId = jsonArray.getJSONObject(jsonArray.length()-1).getInt("p_id");
                }
            }else {
                jsonArray=new JSONArray();
            }
            JSONObject jsonObject =new JSONObject();
            name = inpName.getText().toString();
            age = Integer.parseInt(inpAge.getText().toString());
            weight = Integer.parseInt(inpWeight.getText().toString());
            gender = selectGender.getSelectedItem().toString();
            handImp=hndImp.getSelectedItem().toString();

            Patients patient = new Patients(name, 1, age, weight, gender,handImp);

            jsonObject.put("p_id",lastId+1);
            jsonObject.put("name",name);
            jsonObject.put("age",age);
            jsonObject.put("weight",weight);
            jsonObject.put("gender",gender);
            jsonObject.put("handImp",handImp);

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