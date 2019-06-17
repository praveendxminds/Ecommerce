package com.app.ecommerce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.PlaceHolderView;

import q.rorbin.badgeview.QBadgeView;
import com.app.ecommerce.Home3.ImageTypeSmallList;
import com.app.ecommerce.cart.cart;

/**
 * Created by praveen on 15/11/18.
 */


public class ProductDetails_act extends AppCompatActivity {

    Toolbar toolbar;
    private ImageView pro_img;
    private TextView title,original_price;

    private PlaceHolderView mPlaceHolderView;

    int cart_count = 0;

    android.view.View menuItemView;
    public static String MyPREFERENCES = "sessiondata" ;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        Intent intent = getIntent();
        String temp = intent.getStringExtra("id");



        pro_img = (ImageView) findViewById(R.id.prd_img);
        title = (TextView) findViewById(R.id.title);
        original_price = (TextView) findViewById(R.id.original_price);
        mPlaceHolderView = (PlaceHolderView) findViewById(R.id.galleryView);

        Glide.with(getApplication()).load("https://5.imimg.com/data5/FQ/QY/MY-56156518/cashew-nut-500x500.jpg").into(pro_img);



       // mPlaceHolderView.addView(new ImageTypeSmallList(getApplicationContext(),0));





    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cart_menu, menu);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                menuItemView = findViewById(R.id.cart_menu_item);

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                Integer name_session = sharedpreferences.getInt("count", 0);

                new QBadgeView(getApplicationContext()).bindTarget(menuItemView).setBadgeTextColor(getApplicationContext().getResources().getColor(R.color.white)).setGravityOffset(15, -5, true).setBadgeNumber(name_session).setBadgeBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));


            }
        }, 200);






        return true;
    }


    public void buynow(android.view.View v)
    {
        Intent cartIntent = new Intent(getBaseContext(), cart.class);
        startActivity(cartIntent);
    }


    public void addcart(android.view.View v)
    {
       // cart_count = cart_count + 1;
      //  countCartDisplay(cart_count);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Integer name_session = sharedpreferences.getInt("count", 0);


        name_session = name_session +1;

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("count", name_session);
        editor.commit();


        countCartDisplay(name_session);


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                finish();
                return true;

            case R.id.cart_menu_item:

                        Intent myctIntent = new Intent(getBaseContext(), cart.class);
                        startActivity(myctIntent);


                break;
        }
        return true;
    }


    private void countCartDisplay(int number)
    {



        new QBadgeView(getApplicationContext()).bindTarget(menuItemView).setBadgeTextColor(getApplicationContext().getResources().getColor(R.color.white)).setGravityOffset(15, -5, true).setBadgeNumber(number).setBadgeBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));


    }


}