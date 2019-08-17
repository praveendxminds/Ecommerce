package com.nisarga.nisargaveggiez.wallet;

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
import android.widget.EditText;

import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.payment.PayMentGateWay;
import com.mindorks.placeholderview.PlaceHolderView;

public class AddMoneyToWallet extends AppCompatActivity {

    SessionManager session;
    Toolbar toolbar;
    private EditText etAmount;
    PlaceHolderView phvAddtoMoney;
    private Button btnProceed;
    private String strAmount;
    private String getFirstName,getPhone,getEmail,getAmount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_money_to_wallet);
        session = new SessionManager(getApplicationContext());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        etAmount = findViewById(R.id.etAmount);
        phvAddtoMoney = findViewById(R.id.phvAddtoMoney);
        btnProceed  = findViewById(R.id.btnProceed);
        final String[] strArrAmount = {"100","200","500","1000"} ;
        phvAddtoMoney.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        for(int i=0;i<strArrAmount.length;i++)
        {
                phvAddtoMoney.addView(new AddMoneyItems(getApplicationContext(),strArrAmount[i],etAmount));

        }
        getFirstName = session.getFirstName();
        getPhone = session.getPhoneNumber();
        getEmail = session.getEmailAddress();
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strAmount = etAmount.getText().toString();
                Intent intent = new Intent(getApplicationContext(), PayMentGateWay.class);
                intent.putExtra("FIRST_NAME",getFirstName);
                intent.putExtra("PHONE_NUMBER",getPhone);
                intent.putExtra("EMAIL_ADDRESS",getEmail);
                intent.putExtra("RECHARGE_AMT",strAmount);
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
            case android.R.id.home:
                onBackPressed();
                return true;

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
