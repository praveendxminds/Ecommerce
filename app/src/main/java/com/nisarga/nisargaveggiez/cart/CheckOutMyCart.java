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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.billing.billingAddress;

public class CheckOutMyCart extends AppCompatActivity {

    SessionManager session;
    Toolbar toolbar;
    private LinearLayout llConfirmOrder;
    private ImageButton imgBtnEditAddress;
    private TextView tvChkoutDelvInstruct, tvChkoutAprtDetails, tvChkoutAprtName, tvChkoutPhnNo, tvChkoutCustName, tvPayableAmount, tvTotalSaving, tvDeliveryCharge, tvCartValue, tvChkoutDeliveryDay;
    private String strCustFName, strCustLName, strPhoneNo, strAprtName, strAprtDetails, strInstruct, strDeliveryDay;
    private String strBackCustFName, strBackCustLName, strBackPhoneNo, strBackAprtName, strBackAprtDetails, strBackInstruct, strBackDeliveryDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_mycart);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        session = new SessionManager(getApplicationContext());

        tvChkoutDeliveryDay = findViewById(R.id.tvChkoutDeliveryDay);
        tvChkoutDelvInstruct = findViewById(R.id.tvChkoutDelvInstruct);
        tvChkoutAprtDetails = findViewById(R.id.tvChkoutAprtDetails);
        tvChkoutAprtName = findViewById(R.id.tvChkoutAprtName);
        tvChkoutPhnNo = findViewById(R.id.tvChkoutPhnNo);
        tvChkoutCustName = findViewById(R.id.tvChkoutCustName);
        tvPayableAmount = findViewById(R.id.tvPayableAmount);
        tvTotalSaving = findViewById(R.id.tvTotalSaving);
        tvDeliveryCharge = findViewById(R.id.tvDeliveryCharge);
        tvCartValue = findViewById(R.id.tvCartValue);
        llConfirmOrder = findViewById(R.id.llConfirmOrder);
        imgBtnEditAddress = findViewById(R.id.imgBtnEditAddress);

       /* strCustFName = session.getShipFirstName();
        strCustLName = session.getShipLastName();
        strPhoneNo = session.getShipPhone();
        strAprtName = session.getShipApartmentName();
        strAprtDetails = session.getShipApartmentDetails();
        strInstruct = session.getShipInstruct();
        strDeliveryDay = session.getShipDeliveryDay();

        tvChkoutCustName.setText(strCustFName + " " + strCustLName);
        tvChkoutPhnNo.setText(strPhoneNo);
        tvChkoutAprtName.setText(strAprtName);
        tvChkoutAprtDetails.setText(strAprtDetails);
        tvChkoutDelvInstruct.setText(strInstruct);
        tvChkoutDeliveryDay.setText(strDeliveryDay);*/

        confirmOrder();
        moveToEditAddress();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

    public void confirmOrder() {
        llConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentConfirmOrder = new Intent(CheckOutMyCart.this, ConfirmOrder.class);
                intentConfirmOrder.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentConfirmOrder);
            }
        });
    }

    public void moveToEditAddress() {
        imgBtnEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* strBackCustFName = strCustFName;
                strBackCustLName = strCustLName;
                strBackPhoneNo = tvChkoutPhnNo.getText().toString();
                strBackAprtName = tvChkoutAprtName.getText().toString();
                strBackAprtDetails = tvChkoutAprtDetails.getText().toString();
                strBackInstruct = tvChkoutDelvInstruct.getText().toString();
                strBackDeliveryDay = tvChkoutDeliveryDay.getText().toString();*/


                Intent intentEditAddress = new Intent(CheckOutMyCart.this, billingAddress.class);
                startActivity(intentEditAddress);
            }
        });
    }
}
