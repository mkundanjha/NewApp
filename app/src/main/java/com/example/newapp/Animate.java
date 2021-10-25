package com.example.newapp;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.newapp.ui.Activity;

public class Animate {

    Context context;
    public Animate(Context context ){
        this.context=context;
    }

    public void runAnimation(View t, long delay)
    {
        Animation a = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        a.reset();
        a.setStartOffset(delay);
        t.setVisibility(View.VISIBLE);
        t.clearAnimation();

        t.startAnimation(a);
    }
}
