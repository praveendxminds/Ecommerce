package com.app.ecommerce.billing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.app.ecommerce.R;
import com.app.ecommerce.ccavenue.ccavenue;

/**
 * Created by praveen on 15/11/18.
 */


public class billingAddress extends AppCompatActivity {

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billing);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }






    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void payment(View view)
    {
        Intent accountIntent = new Intent(getBaseContext(), ccavenue.class);
        startActivity(accountIntent);

    }

}