package com.app.ecommerce.MyOrder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.ecommerce.DeliveryInformation;
import com.app.ecommerce.R;
import com.app.ecommerce.cart.CheckOutMyCart;
import com.app.ecommerce.payment.PaymentDetails;

public class CheckoutOrder extends AppCompatActivity {
    Toolbar toolbar;
    private Button btnItems;
    private LinearLayout llPayNow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_order);

        btnItems = findViewById(R.id.btnItems);
        llPayNow = findViewById(R.id.llPayNow);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //addPaymentOption(4);
        btnItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReorder = new Intent(CheckoutOrder.this, ReorderHolder.class);
                startActivity(intentReorder);
            }
        });
        llPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPayNow = new Intent(CheckoutOrder.this, PaymentDetails.class);
                startActivity(intentPayNow);
            }
        });
    }

   // public String[] strPayArr = {"Wallet", "G-pay", "Paytm", "Phonepe"};

    /*  public void addPaymentOption(int number)
      {
          for(int i=0;i<4 ; i++)
          {
              RadioGroup ll = new RadioGroup(this);
              ll.setOrientation(LinearLayout.VERTICAL);

                  RadioButton radioBtn = new RadioButton(this);
                  radioBtn.setId(View.generateViewId());
                  radioBtn.setText(strPayArr[i]+radioBtn.getId());
                  ll.addView(radioBtn);


          }
          ((ViewGroup) findViewById(R.id.radiogroup)).addView(ll);
      }*/
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

}
