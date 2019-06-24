package com.app.ecommerce;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ecommerce.Home.HomePage;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        final TextView splastitle =(TextView)  findViewById(R.id.splash_title);
        final Animation rotatte = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        final Animation antirotate = AnimationUtils.loadAnimation(getBaseContext(),R.anim.antirotate);
        final Animation animation_3 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);
        final Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);


        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                splastitle.setVisibility(View.VISIBLE);
                splastitle.startAnimation(animSlideDown);
            }
        }, 1000);


        imageView.startAnimation(antirotate);
        antirotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(rotatte);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        rotatte.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(animation_3);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}