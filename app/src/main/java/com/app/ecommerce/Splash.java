package com.app.ecommerce;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.app.ecommerce.Home.HomePage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class Splash extends AppCompatActivity {

    long animactionDuraction = 1000;
    ImageView ivVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        ivVehicle = findViewById(R.id.ivVehicle);


        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        ivVehicle.startAnimation(animation1);


//        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(ivVehicle);
//        Glide.with(this).load(R.raw.jop).into(imageViewTarget);



//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ObjectAnimator objectani = ObjectAnimator.ofFloat(ivVehicle, "x", 500f);
//                objectani.setDuration(animactionDuraction);
//                AnimatorSet animatorSet = new AnimatorSet();
//                animatorSet.playTogether(objectani);
//                animatorSet.start();
//
//            }
//        }, 800);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, HomePage.class);
                startActivity(i);
            }
        }, 3000);
    }

        /*final ImageView imageView = (ImageView) findViewById(R.id.imageView);

        final TextView splastitle = (TextView) findViewById(R.id.splash_title);
        final Animation rotatte = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation antirotate = AnimationUtils.loadAnimation(getBaseContext(), R.anim.antirotate);
        final Animation animation_3 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);
        final Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
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
        });*/
}