package com.galanto.GalantoHealth;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newapp.R;
import com.galanto.GalantoHealth.ui.Activity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    ConstraintLayout scrollViewContainer;
    ImageView rightArrowIv, leftArrowIv;
    NestedScrollView usrIconScrollview;
    TextView username1, username2, username3;
    File file;
    String PATIENT_DATA = "PatientData";
    FloatingActionButton goToAddUsrBtn;
    SearchView serach;
    RecyclerView recyclerView;
    ArrayList<Patients> arrayList;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.splashscreen);
        setContentView(R.layout.activity_login);

        try {
            recyclerView = findViewById(R.id.recyclerView);
            usrIconScrollview = findViewById(R.id.usrIconsScrollView);
            rightArrowIv = findViewById(R.id.rightArrow);
            leftArrowIv = findViewById(R.id.leftArrow);
            rightArrowIv.setVisibility(View.INVISIBLE);
            leftArrowIv.setVisibility(View.INVISIBLE);
            //scrollViewContainer=findViewById(R.id.constraintLayout);
            activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            });

            checkExternalStoragePermission();
//        int scrollViewHeight=usrIconScrollview.getHeight();
//        int containerHeight=usrIconScrollview.getChildAt(0).getHeight();

//        if (containerHeight==scrollViewHeight || scrollViewHeight>containerHeight){
//            leftArrowIv.setVisibility(View.INVISIBLE);
//        }else{
//            leftArrowIv.setVisibility(View.VISIBLE);
//        }

//        if (canScroll()){
//            leftArrowIv.setVisibility(View.VISIBLE);
//        }else{
//            leftArrowIv.setVisibility(View.INVISIBLE);
//        }


            arrayList = new ArrayList<>();

            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            //add data to arraylist
            setUserProfileData();

            CustomAdaptar adapter = new CustomAdaptar(this, arrayList);
            recyclerView.setAdapter(adapter);


//        usr2=findViewById(R.id.userCard2);
//        usr3=findViewById(R.id.userCard3);
//        username1=findViewById(R.id.usrFirstName);
//        username2=findViewById(R.id.usrSecondName);
//        username3=findViewById(R.id.usrThirdName);
            goToAddUsrBtn = findViewById(R.id.gotoAddUsrBtn);
            //file = new File(LoginActivity.this.getFilesDir(), PATIENT_DATA);
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
            Log.d("Error", e.toString());
        }


//        usr1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                goToDashboard();
//            }
//        });

        usrIconScrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                rightArrowIv.setVisibility(View.VISIBLE);
            }
        });

        goToAddUsrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddUser();
            }
        });
    }

    public void goToDashboard() {
        Intent intent = new Intent(LoginActivity.this, Dashboard.class);
        intent.putExtra("user_name", username1.getText().toString());
        startActivity(intent);
    }

    public void goToAddUser() {
        Intent intent = new Intent(this, AddNewUser.class);
        startActivity(intent);
    }

    public void setUserProfileData() {
        FileDataBase fileDataBase = new FileDataBase(this);

        try {
            String response = fileDataBase.readFile("Galanto/RehabRelive", "patients.json");
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject;

            if (jsonArray.length() > 4) {
                leftArrowIv.setVisibility(View.VISIBLE);
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Patients patients = new Patients(jsonObject.getString("name"), jsonObject.getInt("p_id"), jsonObject.getInt("weight"), jsonObject.getInt("age"), jsonObject.getString("gender"), jsonObject.getString("handImp"));

                arrayList.add(patients);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setUsrIconScrollviewArrowIndicator() {
        int maxScrollX = usrIconScrollview.getChildAt(0).getMeasuredWidth() - usrIconScrollview.getMeasuredWidth();

        if (usrIconScrollview.getScrollX() == 0) {
            rightArrowIv.setVisibility(View.INVISIBLE);
            leftArrowIv.setVisibility(View.VISIBLE);
        } else {
            rightArrowIv.setVisibility(View.VISIBLE);
            leftArrowIv.setVisibility(View.VISIBLE);
        }

        if (usrIconScrollview.getScrollX() == maxScrollX) {
            leftArrowIv.setVisibility(View.INVISIBLE);
            rightArrowIv.setVisibility(View.VISIBLE);
        }

    }



    @RequiresApi(api = Build.VERSION_CODES.R)
    public void checkExternalStoragePermission(){


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
        try{
        if(!Environment.isExternalStorageManager()){
            Intent intent=new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse(String.format("package:%s",new Object[]{getApplicationContext().getPackageName()})));
            activityResultLauncher.launch(intent);
        }
        }catch (Exception ex){
            Intent intent=new Intent();
            intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            activityResultLauncher.launch(intent);
            ex.printStackTrace();
        }}
    }

//    private boolean canScroll() {
//        View child = usrIconScrollview.getChildAt(0);
//        if (child != null) {
//            int childHeight = child.getHeight();
//            return usrIconScrollview.getHeight() < childHeight;
//        }
//        return false;
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}