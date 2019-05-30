package com.app.ecommerce.MyOrder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mindorks.placeholderview.PlaceHolderView;

import com.app.ecommerce.R;

/**
 * Created by praveen on 15/11/18.
 */



public class MyOrders extends AppCompatActivity {

    Toolbar toolbar;
    private PlaceHolderView mCartView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mCartView = (PlaceHolderView)findViewById(R.id.recycler_order);


        mCartView
                .addView(new orderItemHeader())
                .addView(new orderItem(getApplicationContext(),getResources().getDrawable(R.drawable.home)))
                .addView(new orderItem(getApplicationContext(),getResources().getDrawable(R.drawable.call)))
                .addView(new orderItem(getApplicationContext(),getResources().getDrawable(R.drawable.tap)))
                .addView(new orderItem(getApplicationContext(),getResources().getDrawable(R.drawable.home)))
                .addView(new orderItem(getApplicationContext(),getResources().getDrawable(R.drawable.tap)))
                .addView(new orderItem(getApplicationContext(),getResources().getDrawable(R.drawable.call)))
                .addView(new orderItem(getApplicationContext(),getResources().getDrawable(R.drawable.call)))
                .addView(new orderItem(getApplicationContext(),getResources().getDrawable(R.drawable.tap)));


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}