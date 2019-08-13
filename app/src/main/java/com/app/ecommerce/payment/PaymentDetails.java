package com.app.ecommerce.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.app.ecommerce.DeliveryInformation;
import com.app.ecommerce.R;
import com.mindorks.placeholderview.PlaceHolderView;

import java.io.Console;
import java.util.ArrayList;

public class PaymentDetails extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPagerPayment;
    TabLayout cIndicatorPayment;
    PlaceHolderView phvNetBanking;
    RadioGroup radioGroup;
    LinearLayout llMainBody,llMainBody_1,llNetBanking,llBhimUpi,llProceed;
    private ArrayList<Integer> ImageArray = new ArrayList<Integer>();
    public static Integer[] ImagesList = {R.drawable.cardsaved, R.drawable.cardsaved, R.drawable.cardsaved};
    private int defaultGap = 30;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        viewPagerPayment = findViewById(R.id.viewPagerPayment);
        cIndicatorPayment = findViewById(R.id.cIndicatorPayment);
        phvNetBanking = findViewById(R.id.phvNetBanking);
        radioGroup = findViewById(R.id.radioGrpPayOption);
        llMainBody = findViewById(R.id.llMainBody);
        llMainBody_1 = findViewById(R.id.llMainBody_1);
        llNetBanking = findViewById(R.id.llNetBanking);
        llBhimUpi = findViewById(R.id.llBhimUpi);
        llProceed = findViewById(R.id.llProceed);

        setViewPager();
        for (int i = 0; i < 5; i++) {
            phvNetBanking.addView(new NetBankingList(getApplicationContext()));
        }
        selectPaymentOption();
        llProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProceed  = new Intent(PaymentDetails.this,OTPVerificationPayment.class);
                startActivity(intentProceed);
            }
        });
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
            case R.id.help_menu_item:
                Intent intentHelp = new Intent(getBaseContext(), DeliveryInformation.class);
                startActivity(intentHelp);
                break;
        }
        return true;
    }

    public void setViewPager() {
        for (int i = 0; i < ImagesList.length; i++) {
            ImageArray.add(ImagesList[i]);
            Log.e("---images----", String.valueOf(ImagesList[i]));
        }
        viewPagerPayment.setPadding(defaultGap, 0, defaultGap, 0);
        viewPagerPayment.setClipToPadding(false);
        viewPagerPayment.setPageMargin(15);//should be half of defaultGap variable value
        viewPagerPayment.setAdapter(new PaymentImageAdapter(getApplicationContext(), ImageArray));

        cIndicatorPayment.setupWithViewPager(viewPagerPayment);

    }

    public void selectPaymentOption() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioBtnWallet) {

                    llMainBody.setVisibility(View.VISIBLE);
                    llMainBody_1.setVisibility(View.GONE);
                    llNetBanking.setVisibility(View.GONE);
                    llBhimUpi.setVisibility(View.GONE);

                } else if (checkedId == R.id.radioBtnGPay) {

                    llMainBody.setVisibility(View.GONE);
                    llMainBody_1.setVisibility(View.VISIBLE);
                    llNetBanking.setVisibility(View.GONE);
                    llBhimUpi.setVisibility(View.GONE);

                } else if (checkedId == R.id.radioBtnPaytm) {

                    llMainBody.setVisibility(View.GONE);
                    llMainBody_1.setVisibility(View.GONE);
                    llNetBanking.setVisibility(View.VISIBLE);
                    llBhimUpi.setVisibility(View.GONE);

                } else {

                    llMainBody.setVisibility(View.GONE);
                    llMainBody_1.setVisibility(View.GONE);
                    llNetBanking.setVisibility(View.GONE);
                    llBhimUpi.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
