package com.app.ecommerce.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.ecommerce.DeliveryInformation;
import com.app.ecommerce.R;
import com.app.ecommerce.payment.PayMentGateWay;
import com.mindorks.placeholderview.PlaceHolderView;

public class AddMoneyToWallet extends AppCompatActivity {

    Toolbar toolbar;
    private TextView tvAmount;
    PlaceHolderView phvAddtoMoney;
    Button Paynow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_money_to_wallet);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tvAmount = findViewById(R.id.tvAmount);
        phvAddtoMoney = findViewById(R.id.phvAddtoMoney);
        String[] strArrAmount = {"100","200","500","1000"} ;
        phvAddtoMoney.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        for(int i=0;i<strArrAmount.length;i++)
        {
                phvAddtoMoney.addView(new AddMoneyItems(getApplicationContext(),strArrAmount[i]));
        }
        tvAmount.setText(getIntent().getStringExtra("textAmount"));



        Paynow       = (Button)findViewById(R.id.Paynow);

        Paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getAmt   = "10";//rechargeAmt.getText().toString().trim();

                Intent intent = new Intent(getApplicationContext(), PayMentGateWay.class);
                intent.putExtra("FIRST_NAME","Varun");
                intent.putExtra("PHONE_NUMBER","9037007648");
                intent.putExtra("EMAIL_ADDRESS","admin@gmail.com");
                intent.putExtra("RECHARGE_AMT",getAmt);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuAddtoMoney = getMenuInflater();
        menuAddtoMoney.inflate(R.menu.nav_toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.info:
                Intent intentAddtoMoney = new Intent(getBaseContext(),DeliveryInformation.class);
                startActivity(intentAddtoMoney);
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
