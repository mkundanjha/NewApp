package com.galanto.GalantoHealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.newapp.R;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard2);
//        replaceFragment(new Home(), android.R.animator.fade_in, android.R.animator.fade_out);



    }

//    public void replaceFragment(Fragment fragment, int animEnter, int animExit) {
//
//        FragmentManager fragmentManager=getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(animEnter, animExit);
//        fragmentTransaction.replace(R.id.l1,fragment);
//        fragmentTransaction.commit();
//
//    }


}