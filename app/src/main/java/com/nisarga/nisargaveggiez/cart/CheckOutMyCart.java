package com.nisarga.nisargaveggiez.cart;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.billing.AddOrder;
import com.nisarga.nisargaveggiez.billing.billingAddress;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutMyCart extends AppCompatActivity {


    SessionManager session;
    Toolbar toolbar;
    private TextView llConfirmOrder;
    private LinearLayout cnflyt;
    private ImageButton imgBtnEditAddress;
    private TextView tvChkoutDelvInstruct, tvChkoutAprtDetails, tvChkoutAprtName;
    private TextView tvChkoutPhnNo, tvChkoutCustName, tvPayableAmount, tvTotalSaving, tvCartValue, tvChkoutDeliveryDay;
    private String strCustFName, strCustLName, strPhoneNo, strAprtName, strAprtDetails, strInstruct, strDeliveryDay;
    private String strBlock, strDoor, strFloor, strArea, strAddress, strCity, strPincode;
    APIInterface apiInterface;
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_mycart);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressdialog = new ProgressDialog(CheckOutMyCart.this);
        progressdialog.setMessage("Please Wait....");

        session = new SessionManager(getApplicationContext());
        apiInterface = APIClient.getClient().create(APIInterface.class);

        tvChkoutDeliveryDay = findViewById(R.id.tvChkoutDeliveryDay);
        tvChkoutDelvInstruct = findViewById(R.id.tvChkoutDelvInstruct);
        tvChkoutAprtDetails = findViewById(R.id.tvChkoutAprtDetails);
        tvChkoutAprtName = findViewById(R.id.tvChkoutAprtName);
        tvChkoutPhnNo = findViewById(R.id.tvChkoutPhnNo);
        tvChkoutCustName = findViewById(R.id.tvChkoutCustName);
        tvPayableAmount = findViewById(R.id.tvPayableAmount);
        tvTotalSaving = findViewById(R.id.tvTotalSaving);
        tvCartValue = findViewById(R.id.tvCartValue);
        llConfirmOrder = findViewById(R.id.llConfirmOrder);
        cnflyt = findViewById(R.id.cnflyt);
        imgBtnEditAddress = findViewById(R.id.imgBtnEditAddress);

        strCustFName = session.getFirstName();
        strCustLName = session.getLastName();
        strPhoneNo = session.getPhoneNumber();
        strAprtName = session.getCompany();
        strBlock = session.getBlockNo()+"," + " ";
        strDoor = session.getDoorNo()+"," + " ";
        strFloor = session.getFloor()+ "," + " ";
        strArea = session.getAddrSecond()+ "," + " ";
        strAddress = session.getAddrFirst()+ "," + " ";
        strCity = session.getCity()+ "," + " ";
        strPincode = session.getPincode();
        strInstruct = session.getShipInstruct();
        strDeliveryDay = session.getDeliveryweek();

        tvChkoutCustName.setText(strCustFName + " " + strCustLName);
        tvChkoutPhnNo.setText(strPhoneNo);
        tvChkoutAprtName.setText(strAprtName);
        tvChkoutAprtDetails.setText(strDoor + strFloor + strBlock + strAddress + strArea + strCity+ strPincode);
        tvChkoutDelvInstruct.setText(strInstruct);
        tvChkoutDeliveryDay.setText(strDeliveryDay);


        tvTotalSaving.setText("Rs."+" "+session.getTotalSavingAmount());
        tvCartValue.setText("Rs."+" "+session.getTotalAmount());
        tvPayableAmount.setText("Rs."+" "+session.getTotalAmount());

        confirmOrder();
        moveToEditAddress();

        Log.d("ord address", session.addorder_total());
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
        cnflyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                addorder();
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


    public void addorder() {
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            //final CartListModel cartListModel = new CartListModel("api/cart/products","ea37ddb9108acd601b295e26fa");
            try {
                progressdialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("getToken", String.valueOf(session.getToken()));

            final AddOrder ordadddetails = new AddOrder(session.getDeliverydate());

            Call<AddOrder> call = apiInterface.AddOrder("api/order/add", session.getToken(), ordadddetails);
            call.enqueue(new Callback<AddOrder>() {
                @Override
                public void onResponse(Call<AddOrder> call, Response<AddOrder> response) {
                    AddOrder resource = response.body();

                    List<AddOrder.AddOrderList> datumList = resource.data;
                    if ((resource.status).equals("success")) {
                        //  Log.d("Addorderlist", String.valueOf(datumList));
                        for (AddOrder.AddOrderList lsss : datumList)
                        {
                            Log.d("addressdddd", String.valueOf(lsss.address));
                            // session.addorder(lsss.address,String.valueOf(lsss.savings),lsss.delivery_charges,String.valueOf(lsss.total));
                            progressdialog.dismiss();
                            Intent intentConfirmOrder = new Intent(CheckOutMyCart.this, ConfirmOrder.class);
                            intentConfirmOrder.putExtra("address",lsss.address);
                            intentConfirmOrder.putExtra("savings",String.valueOf(lsss.savings));
                            intentConfirmOrder.putExtra("delivery_charges",lsss.delivery_charges);
                            intentConfirmOrder.putExtra("total",String.valueOf(lsss.total));
                            intentConfirmOrder.putExtra("order_id",String.valueOf(lsss.order_id));
                            startActivity(intentConfirmOrder);

                        }
                    }

                }

                @Override
                public void onFailure(Call<AddOrder> call, Throwable t) {
                    call.cancel();
                }
            });


        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
    }


}