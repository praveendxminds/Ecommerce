package com.nisarga.nisargaveggiez.addresbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mindorks.placeholderview.PlaceHolderView;

import com.nisarga.nisargaveggiez.R;

/**
 * Created by praveen on 15/11/18.
 */



public class AddressBook extends AppCompatActivity {

    Toolbar toolbar;
    public PlaceHolderView mCartView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_book);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mCartView = (PlaceHolderView)findViewById(R.id.recycler_address);


        mCartView
                .addView(new AddressItemHeader(getApplicationContext()))
                .addView(new addressItem(getApplicationContext(),mCartView));


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}