package com.nisarga.nisargaveggiez;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.nisarga.nisargaveggiez.Home.HomePage;
import com.nisarga.nisargaveggiez.ProfileSection.Login_act;

public class Splash extends AppCompatActivity {

    ImageView ivVehicle, ivOuterRing;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        sessionManager = new SessionManager(Splash.this);

        ivVehicle = findViewById(R.id.ivVehicle);
        ivOuterRing = findViewById(R.id.ivOuterRing);

        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        ivVehicle.startAnimation(animation1);

        final Animation rotatte = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation antirotate = AnimationUtils.loadAnimation(getBaseContext(), R.anim.antirotate);
        final Animation animation_3 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);

        ivOuterRing.startAnimation(antirotate);
        antirotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivOuterRing.startAnimation(rotatte);
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
                ivOuterRing.startAnimation(animation_3);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!sessionManager.isLoggedIn()) {
                    Intent i = new Intent(Splash.this, Login_act.class);
                    startActivity(i);
                } else {
                    Intent intent = new Intent(Splash.this, HomePage.class);
                    startActivity(intent);
                }
            }
        }, 3000);
    }
}