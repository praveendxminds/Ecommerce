package com.nisarga.nisargaveggiez.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.Home.HomePage;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.billing.AddOrder;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.OrderFeedback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOrder extends AppCompatActivity {

    Toolbar toolbar;
    private LinearLayout llSubmitFeedback;
    APIInterface apiInterface;
    SessionManager session;
    private TextView tvPaymentStatus, tvSubTotal, tvDelivCharge, tvOrdAmount, tvTotalSaving;
    private TextView tvApartmentName, tvAddressDetails, tvMsg;
    private RatingBar ratingBar;
    private EditText etFeedback;
    private TextView btnSubmit;
    private String order_id;
    private String feedback_txt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_order);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        llSubmitFeedback = findViewById(R.id.llSubmitFeedback);
        btnSubmit = findViewById(R.id.btnSubmit);
        etFeedback = findViewById(R.id.etFeedback);
        ratingBar = findViewById(R.id.ratingBar);
        tvAddressDetails = findViewById(R.id.tvAddressDetails);
        tvApartmentName = findViewById(R.id.tvApartmentName);
        tvTotalSaving = findViewById(R.id.tvTotalSaving);
        tvOrdAmount = findViewById(R.id.tvOrdAmount);
        tvDelivCharge = findViewById(R.id.tvDelivCharge);
        tvPaymentStatus = findViewById(R.id.tvPaymentStatus);
        tvMsg = findViewById(R.id.tvMsg);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());
        session.setReferalStatus();

        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        String savings = intent.getStringExtra("savings");
        String delivery_charges = intent.getStringExtra("delivery_charges");
        String total = intent.getStringExtra("total");
        order_id = intent.getStringExtra("total");

        tvDelivCharge.setText(delivery_charges);
        tvAddressDetails.setText(address);
        tvOrdAmount.setText(total);
        tvTotalSaving.setText(savings);


        submitFeedback();


    }


    @Override
    public boolean onSupportNavigateUp() {
        //onBackPressed();

        Intent intentSubmitBack = new Intent(ConfirmOrder.this, HomePage.class);
        intentSubmitBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentSubmitBack);

        return true;
    }

    public void submitFeedback() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sendfeedback();
            }
        });
    }


    public void sendfeedback()
    {

        if (ratingBar.getRating() > 0)
        {
            if (Utils.CheckInternetConnection(getApplicationContext())) {
                //final CartListModel cartListModel = new CartListModel("api/cart/products","ea37ddb9108acd601b295e26fa");

                Log.d("getToken", String.valueOf(session.getToken()));

                final OrderFeedback feedbackddetails = new OrderFeedback(order_id, session.getCustomerId(), (int) Math.round(ratingBar.getRating()), String.valueOf(etFeedback.getText()));
                Call<OrderFeedback> call = apiInterface.orderfeedback(feedbackddetails);
                call.enqueue(new Callback<OrderFeedback>() {
                    @Override
                    public void onResponse(Call<OrderFeedback> call, Response<OrderFeedback> response) {
                        OrderFeedback resource = response.body();
                        if ((resource.status).equals("success"))
                        {

                            Intent intentSubmitBack = new Intent(ConfirmOrder.this, HomePage.class);
                            intentSubmitBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intentSubmitBack);
                            Toast.makeText(getApplicationContext(), "Your feedback has submitted successfully", Toast.LENGTH_SHORT).show();


                        }


                    }

                    @Override
                    public void onFailure(Call<OrderFeedback> call, Throwable t) {
                        call.cancel();
                    }
                });


            } else {
                Toast.makeText(getApplicationContext(), "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Intent intentSubmitBack = new Intent(ConfirmOrder.this, HomePage.class);
            intentSubmitBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentSubmitBack);
        }


    }
}
