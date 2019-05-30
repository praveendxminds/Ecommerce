package com.app.ecommerce;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import com.app.ecommerce.R;
import com.app.ecommerce.mail.SendMail;

/**
 * Created by praveen on 15/11/18.
 */



public class location extends AppCompatActivity {

    Toolbar toolbar;

    private FusedLocationProviderClient fusedLocationClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;

    private LocationAddressResultReceiver addressResultReceiver;

    private EditText currentAddTv,date_picker_editText,name_editText,mobile_editText;

    private Location currentLocation;

    private LocationCallback locationCallback;

    Spinner dynamicSpinner;

    Calendar myCalendar;

    DatePickerDialog.OnDateSetListener date;

    public static final String MyPREFERENCES = "sessiondata" ;
    SharedPreferences sharedpreferences;
    Bundle intent_extras;
    String loc_intent_val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        intent_extras = getIntent().getExtras();
        loc_intent_val = intent_extras.getString("LOCATION_INTENT");


        if(loc_intent_val.equals("current"))
        {

        }




        name_editText = (EditText) findViewById(R.id.name);
        mobile_editText = (EditText) findViewById(R.id.mobile);



        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Boolean login_st_session = sharedpreferences.getBoolean("status",false);
        String name_session = sharedpreferences.getString("name", null);
        String phone_session = sharedpreferences.getString("phone", null);

        if(login_st_session == true)
        {
            name_editText.setText(name_session);
            mobile_editText.setText(phone_session);
        }





        addressResultReceiver = new LocationAddressResultReceiver(new Handler());

        currentAddTv = (EditText) findViewById(R.id.current_address);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                if(loc_intent_val.equals("current"))
                {
                    getAddress();
                }
            };
        };

        startLocationUpdates();


        dynamicSpinner = (Spinner) findViewById(R.id.spinner);

        String[] items = new String[] { "Select", "8am to 10am", "10am to 1pm", "1pm to 2pm", "2pm to 4pm" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        dynamicSpinner.setAdapter(adapter);

        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        date_picker_editText = (EditText) findViewById(R.id.date);
        myCalendar = Calendar.getInstance();


         date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };



        date_picker_editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(location.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });




    }

    public void onRequest(View v)
    {


        String email = "karthik@groceryfactory.in";
        String subject = "GFT Request";
        String message = "name : "+name_editText.getText().toString().trim()+System.getProperty("line.separator")+"Mobile : "+mobile_editText.getText().toString().trim()+System.getProperty("line.separator")+"Date : "+date_picker_editText.getText().toString().trim()+System.getProperty("line.separator")+"Address : "+currentAddTv.getText().toString().trim();

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();

    }


    public void datepick(View v) {
        //Add some logic
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date_picker_editText.setText(sdf.format(myCalendar.getTime()));
    }





    @SuppressWarnings("MissingPermission")
    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    null);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void getAddress() {

        if (!Geocoder.isPresent()) {
            Toast.makeText(location.this,
                    "Can't find current address, ",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, GetAddressIntentService.class);
        intent.putExtra("add_receiver", addressResultReceiver);
        intent.putExtra("add_location", currentLocation);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {
                    Toast.makeText(this, "Location permission not granted, " +
                                    "restart the app if you want the feature",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }
    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultCode == 0) {
                //Last Location can be null for various reasons
                //for example the api is called first time
                //so retry till location is set
                //since intent service runs on background thread, it doesn't block main thread
                Log.d("Address", "Location null retrying");
                getAddress();
            }

            if (resultCode == 1) {
                Toast.makeText(location.this,
                        "Address not found, " ,
                        Toast.LENGTH_SHORT).show();
            }

            String currentAdd = resultData.getString("address_result");

            showResults(currentAdd);
        }
    }

    private void showResults(String currentAdd){
        currentAddTv.setText(currentAdd);
    }


    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
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