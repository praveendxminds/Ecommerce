package com.nisarga.nisargaveggiez.billing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.MyOrder.MyOrders;
import com.nisarga.nisargaveggiez.MyOrder.orderItem;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.cart.CheckOutMyCart;
import com.nisarga.nisargaveggiez.cart.ShippingNewAddress;
import com.nisarga.nisargaveggiez.cart.cartItem;
import com.nisarga.nisargaveggiez.ccavenue.ccavenue;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.CartListModel;
import com.nisarga.nisargaveggiez.retrofit.CustomerDetails;
import com.nisarga.nisargaveggiez.retrofit.MyOrderList;
import com.nisarga.nisargaveggiez.retrofit.ShippingAddrModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private TextView tvContinue;
    private String strFirstName, strLastName, strEmail, strMobile, strInstruct, strDeliveryDay, strApartmentName, strApartmentDetails;
    private String strBlock, strDoor, strFloor, strArea, strAddress, strCity, strPincode, strCountryId, strZoneId;
    private String strTotal, strTotalSaving;
    APIInterface apiInterface;
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping_details_mycart);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        progressdialog = new ProgressDialog(billingAddress.this);
        progressdialog.setMessage("Please Wait....");

        session = new SessionManager(getApplicationContext());
        apiInterface = APIClient.getClient().create(APIInterface.class);

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
        llContinue = findViewById(R.id.llContinue);
       // tvContinue = findViewById(R.id.tvContinue);
        //for aprtment section getting from Login response
        strFirstName = session.getFirstName();
        strLastName = session.getLastName();
        strEmail = session.getEmail();
        strMobile = session.getTelephone();
        strApartmentName = session.getApartment();
        strBlock = session.getBlockNo();
        strDoor = session.getDoorNo();
        strFloor = session.getFloor();
        strArea = session.getAddrSecond();
        strAddress = session.getAddrFirst();
        strCity = session.getCity();
        strPincode = session.getPincode();
        strDeliveryDay = session.getDeliveryweek();
        strCountryId = session.getCountryId();
        strZoneId = session.getZoneId();

        //------------------------------------------------
        etFirstName.setText(strFirstName);
        etLastName.setText(strLastName);
        etMobileNo.setText(strMobile);
        tvApartmentName.setText(strApartmentName);
        tvApartmentDetails.setText(strDoor + "," + " " + strFloor + "," + " " + strBlock +
                "," + " " + strAddress + "," + " " + strArea + "," + " " + strCity + "," + " " + strPincode);
        etDelivery.setText(strDeliveryDay);

        imgBtnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNewAddress = new Intent(billingAddress.this, ShippingNewAddress.class);
                startActivity(intentNewAddress);

            }
        });

        llContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToChkOut();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void moveToChkOut() {
        Log.d("moveToChkOut", session.getDeliverydate());


        strFirstName = etFirstName.getText().toString();
        strLastName = etLastName.getText().toString();
        strMobile = etMobileNo.getText().toString();
        strInstruct = etInstructions.getText().toString();
        strDeliveryDay = etDelivery.getText().toString();
        strApartmentDetails = tvApartmentDetails.getText().toString();
        strApartmentName = tvApartmentName.getText().toString();
        //api call-----
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            //  final ShippingAddrModel getAddress = new ShippingAddrModel(strFirstName, strLastName, strAddress, strCity, strCountryId, strZoneId, strApartmentName,  strPincode,strInstruct);
            try {
                progressdialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d("strArea", String.valueOf(strArea));
            final ShippingAddrModel getAddress = new ShippingAddrModel(strFirstName, strLastName, strAddress, strCity, strCountryId, strZoneId, strApartmentName, strArea, strPincode, strInstruct);
            Call<ShippingAddrModel> call = apiInterface.addShippingAddress("api/shipping/address_android", session.getToken(), getAddress);
            call.enqueue(new Callback<ShippingAddrModel>() {
                @Override
                public void onResponse(Call<ShippingAddrModel> call, Response<ShippingAddrModel> response) {

                    ShippingAddrModel resource = response.body();

                    List<ShippingAddrModel.ShippingDatum> datumList1 = resource.data;
                    if ((resource.status).equals("success")) {

                        for (ShippingAddrModel.ShippingDatum dataList : datumList1) {

                            session.saveShippingDetails(dataList.firstname, dataList.lastname, dataList.address_1, dataList.city, dataList.country_id, dataList.zone_id, dataList.company, dataList.address_2, dataList.postcode, dataList.custom_field);

                        }
                        session.saveTotal(resource.total, resource.total_savings);


                        Intent intentChkout = new Intent(billingAddress.this, CheckOutMyCart.class);
                        intentChkout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentChkout);
                    } else {
                        Toast.makeText(billingAddress.this, resource.message, Toast.LENGTH_SHORT).show();
                    }
                    progressdialog.dismiss();
                }

                @Override
                public void onFailure(Call<ShippingAddrModel> call, Throwable t) {
                    call.cancel();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


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
                Intent intentHelp = new Intent(billingAddress.this, DeliveryInformation.class);
                startActivity(intentHelp);
                break;
        }
        return true;
    }


}