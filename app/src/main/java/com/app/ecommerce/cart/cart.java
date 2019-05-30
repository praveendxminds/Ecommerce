package com.app.ecommerce.cart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mindorks.placeholderview.PlaceHolderView;

import com.app.ecommerce.login;
import com.app.ecommerce.R;
import com.app.ecommerce.billing.billingAddress;

/**
 * Created by praveen on 15/11/18.
 */


public class cart extends AppCompatActivity {

    Toolbar toolbar;
    private PlaceHolderView mCartView;
    public static final String MyPREFERENCES = "sessiondata" ;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);



        mCartView = (PlaceHolderView)findViewById(R.id.recycler_cart);


        mCartView
                .addView(new cartItem(getResources().getDrawable(R.drawable.home)))
                .addView(new cartItem(getResources().getDrawable(R.drawable.call)))
                .addView(new cartItem(getResources().getDrawable(R.drawable.tap)))
                .addView(new cartItem(getResources().getDrawable(R.drawable.home)))
                .addView(new cartItem(getResources().getDrawable(R.drawable.tap)))
                .addView(new cartItem(getResources().getDrawable(R.drawable.call)))
                .addView(new cartItem(getResources().getDrawable(R.drawable.call)))
                .addView(new cartItem(getResources().getDrawable(R.drawable.tap)))
                .addView(new cartItem(getResources().getDrawable(R.drawable.call)));

        mCartView.addView(new cartItem_footer());


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void billing(View v)
    {
        Boolean login_st_session = sharedpreferences.getBoolean("status",false);
        if(login_st_session == true)
        {
            Intent myIntent = new Intent(getBaseContext(), billingAddress.class);
            startActivity(myIntent);
        }
        else
        {
            Intent myIntent = new Intent(getBaseContext(), login.class);
            myIntent.putExtra("Login_INTENT", "cart");
            startActivity(myIntent);
        }

    }

}