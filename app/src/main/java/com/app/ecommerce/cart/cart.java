package com.app.ecommerce.cart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecommerce.notifications.MyNotifications;
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
    private String storeDayTime;


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
                final TextView closeDialog1 = alertLayout.findViewById(R.id.closeDialog);
//----day spinner--------
                final Spinner dayspinner = alertLayout.findViewById(R.id.dayspinner);
                final Button schedule = alertLayout.findViewById(R.id.btnSchedule);
                final SpinnerAdapter adapterDay = new SpinnerAdapter(cart.this, android.R.layout.simple_list_item_1);
                adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapterDay.addAll(Day);
                adapterDay.add("Select");
                dayspinner.setAdapter(adapterDay);
                dayspinner.setSelection(adapterDay.getCount());
                dayspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {


                        if(dayspinner.getSelectedItem() == "Select")
                        {

                            Toast.makeText(cart.this,"you have selected nothing",Toast.LENGTH_LONG).show();
                            //Do nothing.
                        }
                        else{

                            Log.e("-----day selected-----","----day selected---");
                            Toast.makeText(cart.this, dayspinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }

                });

//---time spinner-----
              final Spinner timespinner = alertLayout.findViewById(R.id.timespinner);
                SpinnerAdapter adapterTime = new SpinnerAdapter(cart.this, android.R.layout.simple_list_item_1);
                adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapterTime.addAll(Time);
                adapterTime.add("Select");
                timespinner.setAdapter(adapterTime);
                timespinner.setSelection(adapterTime.getCount());
                timespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {


                        if(timespinner.getSelectedItem() == "Select")
                        {
                            Toast.makeText(cart.this,"no time selected",Toast.LENGTH_LONG).show();
                            //Do nothing.
                        }
                        else{
                            Log.e("-----time selected-----","----time selected---");
                            Toast.makeText(cart.this, timespinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }

                });
                //-----------
                final AlertDialog alertDialog = new AlertDialog.Builder(cart.this).create();
                schedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        storeDayTime = dayspinner.getSelectedItem().toString();
                        storeDayTime = storeDayTime+" "+timespinner.getSelectedItem().toString();
                        alertDialog.dismiss();
                    }
                });


                alertDialog.setView(alertLayout);
                closeDialog1.setOnClickListener(new Button.OnClickListener() {
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.notify_and_cart_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification:
                Intent notificationIntent = new Intent(getBaseContext(), MyNotifications.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(notificationIntent);
                break;

            case R.id.cart:
                Intent DeliveryIntent = new Intent(getBaseContext(), cart.class);
                DeliveryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(DeliveryIntent);
                break;
        }
        return true;
    }
}