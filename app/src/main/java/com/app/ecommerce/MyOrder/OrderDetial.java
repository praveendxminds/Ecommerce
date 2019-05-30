package com.app.ecommerce.MyOrder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.app.ecommerce.R;

/**
 * Created by praveen on 15/11/18.
 */


public class OrderDetial extends AppCompatActivity {

    Toolbar toolbar;

    private ImageView pro_img;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        pro_img = (ImageView) findViewById(R.id.prd_img);

        Glide.with(getApplication()).load("https://5.imimg.com/data5/FQ/QY/MY-56156518/cashew-nut-500x500.jpg").into(pro_img);





    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }




}