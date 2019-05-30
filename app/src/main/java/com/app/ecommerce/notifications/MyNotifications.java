package com.app.ecommerce.notifications;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mindorks.placeholderview.PlaceHolderView;

import com.app.ecommerce.R;

/**
 * Created by praveen on 15/11/18.
 */



public class MyNotifications extends AppCompatActivity {

    Toolbar toolbar;
    private PlaceHolderView mnotificationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_notifications);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mnotificationView = (PlaceHolderView)findViewById(R.id.recycler_notify);


        mnotificationView
                .addView(new NotificationItem(getApplicationContext()))
                .addView(new NotificationItem(getApplicationContext()))
                .addView(new NotificationItem(getApplicationContext()))
                .addView(new NotificationItem(getApplicationContext()))
                .addView(new NotificationItem(getApplicationContext()))
                .addView(new NotificationItem(getApplicationContext()));


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}