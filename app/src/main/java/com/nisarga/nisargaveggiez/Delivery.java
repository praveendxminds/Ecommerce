package com.nisarga.nisargaveggiez;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;


/**
 * Created by praveen on 15/11/18.
 */



public class Delivery extends AppCompatActivity {

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


    }

    public void onLocationBtn(View v)
    {
        Intent accountIntent = new Intent(getBaseContext(), location.class);
        accountIntent.putExtra("LOCATION_INTENT", "current");
        startActivity(accountIntent);
    }


    public void onSavedBtn(View v)
    {
        Intent accountIntent = new Intent(getBaseContext(), location.class);
        accountIntent.putExtra("LOCATION_INTENT", "saved");
        startActivity(accountIntent);
    }


    public void onAddBtn(View v)
    {
        Intent accountIntent = new Intent(getBaseContext(), location.class);
        accountIntent.putExtra("LOCATION_INTENT", "manually");
        startActivity(accountIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                finish();
                return true;


        }
        return true;
    }


}