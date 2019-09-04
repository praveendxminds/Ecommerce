package com.nisarga.nisargaveggiez.MyOrder;

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

import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.Home.HomePage;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.cart.ConfirmOrder;
import com.nisarga.nisargaveggiez.wallet.MyWalletActivity;

public class OrderPaymentSuccess extends AppCompatActivity {

    Toolbar toolbar;
    Button btnGoBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_payment_success);
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
                Intent intentGoBack = new Intent(getBaseContext(),HomePage.class);
                intentGoBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentGoBack);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp()
    {
        Intent intentSubmitBack = new Intent(OrderPaymentSuccess.this, HomePage.class);
        intentSubmitBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentSubmitBack);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInfo = getMenuInflater();
        menuInfo.inflate(R.menu.instruction_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent intentSubmitBack = new Intent(OrderPaymentSuccess.this, HomePage.class);
                intentSubmitBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentSubmitBack);

                return true;

            case R.id.help_menu_item:
                Intent intentInfo = new Intent(getBaseContext(),DeliveryInformation.class);
                startActivity(intentInfo);
                break;
        }
        return true;
    }
}
