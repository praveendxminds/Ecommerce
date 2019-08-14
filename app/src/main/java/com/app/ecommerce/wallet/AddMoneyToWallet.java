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
import android.widget.EditText;
import android.widget.TextView;

import com.app.ecommerce.DeliveryInformation;
import com.app.ecommerce.R;
import com.mindorks.placeholderview.PlaceHolderView;

public class AddMoneyToWallet extends AppCompatActivity {

    Toolbar toolbar;
    private EditText etAmount;
    PlaceHolderView phvAddtoMoney;

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

        etAmount = findViewById(R.id.etAmount);
        phvAddtoMoney = findViewById(R.id.phvAddtoMoney);
        String[] strArrAmount = {"100","200","500","1000"} ;
        phvAddtoMoney.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        for(int i=0;i<strArrAmount.length;i++)
        {
                phvAddtoMoney.addView(new AddMoneyItems(getApplicationContext(),strArrAmount[i],etAmount));
        }


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
