package com.nisarga.nisargaveggiez.billing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.ccavenue.ccavenue;

/**
 * Created by sushmita on 28th June 2019
 */


public class ShippingDetailsMyCart extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping_details_mycart);
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