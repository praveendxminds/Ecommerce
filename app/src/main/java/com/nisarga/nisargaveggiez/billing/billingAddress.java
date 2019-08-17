package com.nisarga.nisargaveggiez.billing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.cart.CheckOutMyCart;
import com.nisarga.nisargaveggiez.ccavenue.ccavenue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by praveen on 15/11/18.
 */


public class billingAddress extends AppCompatActivity {

    SessionManager session;
    Toolbar toolbar;
    private LinearLayout llContinue;
    private EditText etFirstName, etLastName, etMobileNo, etInstructions, etDelivery;
    private TextView tvApartmentName, tvApartmentDetails;
    private ImageButton imgBtnAddAddress;
    private String strFirstName, strLastName, strMobile, strInstruct, strDeliveryDay, strApartmentName, strApartmentDetails;
    private String strBlock, strDoor, strFloor, strArea, strAddress, strCity, strPincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping_details_mycart);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        session = new SessionManager(getApplicationContext());

        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etFirstName = findViewById(R.id.etFName);
        etLastName = findViewById(R.id.etLName);
        etMobileNo = findViewById(R.id.etMobile_No);
        etInstructions = findViewById(R.id.etInstructions);
        etDelivery = findViewById(R.id.etDeliveryDay);
        tvApartmentName = findViewById(R.id.tvApartmentName);
        tvApartmentDetails = findViewById(R.id.tvAddressDetails);
        imgBtnAddAddress = findViewById(R.id.imgBtnEditAddress);

        //getting from new address page-------------------
        strApartmentName = session.getApartmentName();
        strBlock = session.getBlockNumber();
        strDoor = session.getDoorNumber();
        strFloor = session.getFloorNumber();
        strArea = session.getUsrArea();
        strAddress = session.getUsrAddress();
        strCity = session.getUsrCity();
        strPincode = session.getUsrPincode();
        //------------------------------------------------
        tvApartmentName.setText(strApartmentName);
        tvApartmentDetails.setText(strDoor + "," + " " + strFloor + "," + " " + strBlock +
                "," + " " + strArea + "," + " " + strAddress + "," + " " + strCity + "," + " " + strPincode);


        moveToChkOut();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void payment(View view) {
        Intent accountIntent = new Intent(getBaseContext(), ccavenue.class);
        startActivity(accountIntent);

    }

    public void addNewAddress() {

        imgBtnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    public void moveToChkOut() {
        llContinue = findViewById(R.id.llContinue);
        llContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strFirstName = etFirstName.getText().toString();
                strLastName = etLastName.getText().toString();
                strMobile = etMobileNo.getText().toString();
                strInstruct = etInstructions.getText().toString();
                strDeliveryDay = etDelivery.getText().toString();
                strApartmentDetails = tvApartmentDetails.getText().toString();
                strApartmentName = tvApartmentName.getText().toString();
                //set response of api on database for checkout address details and call it their and call here also from database in onResume method for reference purpose
                Intent intentChkout = new Intent(billingAddress.this, CheckOutMyCart.class);
                intentChkout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentChkout);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.instruction_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;


            case R.id.help_menu_item:
                Intent intentHelp = new Intent(getBaseContext(), DeliveryInformation.class);
                startActivity(intentHelp);
                break;
        }
        return true;
    }
}