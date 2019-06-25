package com.app.ecommerce.cart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.mindorks.placeholderview.PlaceHolderView;
import com.app.ecommerce.R;
import com.app.ecommerce.billing.billingAddress;

/**
 * Created by sushmita on 25/06/2019
 */


public class cart extends AppCompatActivity {

    Toolbar toolbar;
    private PlaceHolderView mCartView;
    public static String MyPREFERENCES = "sessiondata";
    SharedPreferences sharedpreferences;
    TextView linkDeliveryTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //-----delivery time link------------------
        linkDeliveryTime = (TextView) findViewById(R.id.linkDeliveryTime);
        final String[] Day = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday","Saturday"};
        final String[] Time = {"6-11AM","11-3PM","3-9PM","9-12AM"};
        linkDeliveryTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.delivery_time_popup, null);
                final TextView deliveryTimeDialog = alertLayout.findViewById(R.id.deliveryTimeDialog);
                final TextView closeDialog = alertLayout.findViewById(R.id.closeDialog);
//----day spinner--------
                final Spinner dayspinner = alertLayout.findViewById(R.id.dayspinner);
                final Button dismiss = alertLayout.findViewById(R.id.btnDismiss);
                ArrayAdapter<String> adapterDay =
                        new ArrayAdapter<String>(cart.this,
                                android.R.layout.simple_spinner_item, Day);
                adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dayspinner.setAdapter(adapterDay);
//---time spinner-----
                final Spinner timespinner = alertLayout.findViewById(R.id.timespinner);
                ArrayAdapter<String> adapterTime =
                        new ArrayAdapter<String>(cart.this,
                                android.R.layout.simple_spinner_item, Time);
                adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                timespinner.setAdapter(adapterTime);
                //-----------
                final AlertDialog alertDialog = new AlertDialog.Builder(cart.this).create();
                alertDialog.setView(alertLayout);
                closeDialog.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        mCartView = (PlaceHolderView) findViewById(R.id.recycler_cart);


        for (int i = 0; i <= 10; i++) {
            mCartView.addView(new cartItem(getApplicationContext()));
        }
        mCartView.addView(new cartItem_footer());


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void billing(View v) {
        Intent myIntent = new Intent(getBaseContext(), billingAddress.class);
        startActivity(myIntent);
        
       /* Boolean login_st_session = sharedpreferences.getBoolean("status", false);
        if (login_st_session == true) {
            Intent myIntent = new Intent(getBaseContext(), billingAddress.class);
            startActivity(myIntent);
        } else {
            Intent myIntent = new Intent(getBaseContext(), login.class);
            myIntent.putExtra("Login_INTENT", "cart");
            startActivity(myIntent);
        }*/
    }
}