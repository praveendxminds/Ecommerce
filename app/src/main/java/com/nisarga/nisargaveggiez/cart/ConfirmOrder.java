package com.nisarga.nisargaveggiez.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;

public class ConfirmOrder extends AppCompatActivity {

    Toolbar toolbar;
    private LinearLayout llSubmitFeedback;
    APIInterface apiInterface;
    SessionManager session;
    private TextView tvPaymentStatus,tvSubTotal,tvDelivCharge,tvOrdAmount,tvTotalSaving;
    private TextView tvApartmentName,tvAddressDetails,tvMsg;
    private RatingBar ratingBar;
    private EditText etFeedback;
    private TextView btnSubmit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_order);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
        {
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
        tvSubTotal = findViewById(R.id.tvSubTotal);
        tvPaymentStatus = findViewById(R.id.tvPaymentStatus);
        tvMsg = findViewById(R.id.tvMsg);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());
        session.setReferalStatus();

        submitFeedback();


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public  void submitFeedback()
    {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSubmitBack = new Intent(ConfirmOrder.this, HomePage.class);
                intentSubmitBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentSubmitBack);
                Toast.makeText(getApplicationContext(),"Your feedback has submitted successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
