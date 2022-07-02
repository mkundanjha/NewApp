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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
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
import java.util.Locale;

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
    RecyclerView.LayoutManager layoutManager;
    Parcelable mListState;
    SearchView svUserSearch;
    CustomAdaptar adapter;
    Logics logics;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.splashscreen);
        setContentView(R.layout.activity_login);

        try {
            recyclerView = findViewById(R.id.recyclerView);
            usrIconScrollview = findViewById(R.id.usrIconsScrollView);
//            rightArrowIv = findViewById(R.id.rightArrow);
//            leftArrowIv = findViewById(R.id.leftArrow);
//            rightArrowIv.setVisibility(View.INVISIBLE);
//            leftArrowIv.setVisibility(View.INVISIBLE);
            svUserSearch=findViewById(R.id.searchView);

            //scrollViewContainer=findViewById(R.id.constraintLayout);
            activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            });

            checkExternalStoragePermission();
            logics=new Logics(getApplicationContext());

            arrayList = new ArrayList<>();
//            layoutManager=new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

            if(logics.isTablet(getApplicationContext())){
                layoutManager=new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            }else {

                 layoutManager=new GridLayoutManager(getApplicationContext(),2);
            }

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            //add data to arraylist
            setUserProfileData();

            adapter = new CustomAdaptar(this, arrayList);
            recyclerView.setAdapter(adapter);
            goToAddUsrBtn = findViewById(R.id.gotoAddUsrBtn);
            //file = new File(LoginActivity.this.getFilesDir(), PATIENT_DATA);
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
            Log.d("Error", e.toString());
        }

        svUserSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        usrIconScrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                rightArrowIv.setVisibility(View.VISIBLE);
            }
        });

        goToAddUsrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddUser();
            }
        });
    }

    private void filter(String text){
        ArrayList<Patients> patients = new ArrayList<>();

        for(Patients patient:arrayList){
            if(patient.getName().toLowerCase().contains(text.toLowerCase())){
                patients.add(patient);
            }
        }

        adapter.filterList(patients);
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
//                leftArrowIv.setVisibility(View.VISIBLE);
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

//    @Override
//    protected void onSaveInstanceState(Bundle state) {
//        super.onSaveInstanceState(state);
//
//        // Save list state
//        mListState = layoutManager.onSaveInstanceState();
//        state.putParcelable("usrState", mListState);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle state) {
//        super.onRestoreInstanceState(state);
//
//        // Retrieve list state and list/item positions
//        if(state != null)
//            mListState = state.getParcelable("usrState");
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (mListState != null) {
//            layoutManager.onRestoreInstanceState(mListState);
//        }
//    }

    @Override
    protected void onPause() {
        super.onPause();
        mListState = recyclerView.getLayoutManager().onSaveInstanceState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.getLayoutManager().onRestoreInstanceState(mListState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}