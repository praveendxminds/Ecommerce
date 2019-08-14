package com.app.ecommerce.wallet;

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


import com.app.ecommerce.DeliveryInformation;
import com.app.ecommerce.R;

public class AddtoMoneySuccessAck extends AppCompatActivity {

    Toolbar toolbar;
    Button btnGoBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtomoney_success_ack);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        btnGoBack = findViewById(R.id.btnGoBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGoBack = new Intent(getBaseContext(),MyWalletActivity.class);
                intentGoBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentGoBack);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInfo = getMenuInflater();
        menuInfo.inflate(R.menu.nav_toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.info:
                Intent intentInfo = new Intent(getBaseContext(),DeliveryInformation.class);
                startActivity(intentInfo);
                break;
        }
        return true;
    }
}
