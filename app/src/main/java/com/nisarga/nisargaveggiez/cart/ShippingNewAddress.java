package com.nisarga.nisargaveggiez.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;

public class ShippingNewAddress extends AppCompatActivity {

    Toolbar toolbar;
    SessionManager session;
    private RelativeLayout llSave;
    private EditText etfName, etlName, etEmail, etPhone, etApartmentName, etBlock, etDoorNumber, etFloorNumber, etArea, etCity, etAddress, etPincode;
    private Button btnNearBy;
    private String strfName, strlName, strEmail, strPhone, strApartmentName, strBlock, strDoorNumber, strFloorNumber, strArea, strCity, strAddress, strPincode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping_new_address);
        session = new SessionManager(getApplicationContext());
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);   // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        etfName = findViewById(R.id.etfName);
        etlName = findViewById(R.id.etlName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etApartmentName = findViewById(R.id.etApartmentName);
        etBlock = findViewById(R.id.etBlock);
        etDoorNumber = findViewById(R.id.etDoorNumber);
        etFloorNumber = findViewById(R.id.etFloorNumber);
        etArea = findViewById(R.id.etArea);
        etCity = findViewById(R.id.etCity);
        etAddress = findViewById(R.id.etAddress);
        etPincode = findViewById(R.id.etPincode);
        btnNearBy = findViewById(R.id.btnNearBy);
        llSave = findViewById(R.id.llSave);

        strfName = etfName.getText().toString();
        strlName = etlName.getText().toString();
        strEmail = etEmail.getText().toString();
        strPhone = etPhone.getText().toString();
        strApartmentName = etApartmentName.getText().toString();
        strBlock = etBlock.getText().toString();
        strDoorNumber = etDoorNumber.getText().toString();
        strFloorNumber = etFloorNumber.getText().toString();
        strArea = etArea.getText().toString();
        strCity = etCity.getText().toString();
        strAddress = etAddress.getText().toString();
        strPincode = etPincode.getText().toString();

        llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddressDetails();
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
            case R.id.help_menu_item:
                Intent intentHelp = new Intent(getBaseContext(), DeliveryInformation.class);
                startActivity(intentHelp);
                break;
        }
        return true;
    }

    private boolean validateDetails(String firstname, String lastname, String emailid, String mobile,
                                    String apartment, String block, String doorno,
                                    String floorno, String area, String city, String address, String pincode) {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (firstname == null || firstname.trim().length() == 0) {
            etfName.requestFocus();
            etfName.setError("First name is Required");
            return false;

        }
        if (lastname == null || lastname.trim().length() == 0) {
            etlName.requestFocus();
            etlName.setError("Last Name is Required");
            return false;

        }
        if (mobile == null || mobile.trim().length() == 0) {
            etPhone.requestFocus();
            etPhone.setError("Please enter valid mobile number");
            return false;

        }
        if (mobile.matches("[0-9]+")) {
            if (mobile.trim().length() != 10) {
                etPhone.requestFocus();
                etPhone.setError("Enter 10 digit Mobile Number");
                return false;
            } else {
                return true;
            }
        }
        if (!(emailid.matches(emailPattern) && emailid.length() > 0)) {
            etEmail.requestFocus();
            etEmail.setError("Invalid email address");
            return false;
        }

        if (block == null || block.trim().length() == 0) {
            etBlock.requestFocus();
            etBlock.setError("Block/Wing is required");
            return false;

        }

        if (apartment == null || apartment.trim().length() == 0) {
            etApartmentName.requestFocus();
            etApartmentName.setError("Apartment Name is required");
            return false;

        }

        if (floorno == null || floorno.trim().length() == 0) {
            etFloorNumber.requestFocus();
            etFloorNumber.setError("Floor Number is required");
            return false;

        }
        if (doorno == null || doorno.trim().length() == 0) {
            etDoorNumber.requestFocus();
            etDoorNumber.setError("Door Number is required");
            return false;

        }
        if (area == null || area.trim().length() == 0) {
            etArea.requestFocus();
            etArea.setError("Area is required");
            return false;

        }
        if (address == null || address.trim().length() == 0) {
            etAddress.requestFocus();
            etAddress.setError("Address is required");
            return false;

        }
        if (pincode == null || pincode.trim().length() == 0) {
            etPincode.requestFocus();
            etPincode.setError("Pincode is required");
            return false;

        }
        if (pincode.trim().length() != 6) {
            etPincode.requestFocus();
            etPincode.setError("Pincode should be in 6 digit");
            return false;

        }
        if (city == null || city.trim().length() == 0) {
            etCity.requestFocus();
            etCity.setError("City is required");
            return false;
        }
        return true;
    }

    public void saveAddressDetails() {
        if (validateDetails(strfName, strlName, strEmail, strPhone, strApartmentName,
                strBlock, strDoorNumber, strFloorNumber, strArea, strCity, strAddress, strPincode)) {
            session.saveNewAddress(strfName, strlName, strEmail, strPhone, strApartmentName,
                    strBlock, strDoorNumber, strFloorNumber, strArea, strCity, strAddress, strPincode);
            Toast.makeText(getApplicationContext(), "Successfully Saved Details", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
