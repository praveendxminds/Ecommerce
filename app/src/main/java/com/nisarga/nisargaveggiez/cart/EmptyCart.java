package com.nisarga.nisargaveggiez.cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;


import com.nisarga.nisargaveggiez.R;

public class EmptyCart extends AppCompatActivity {


    Toolbar toolbarEmptyCart;
    private LinearLayout llShopNowEmptyCart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_cart_activity);
        toolbarEmptyCart=findViewById(R.id.toolbarEmptyCart);

        setSupportActionBar(toolbarEmptyCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        llShopNowEmptyCart= findViewById(R.id.llShopNowEmptyCart);

        llShopNowEmptyCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //some activity
            }
        });

    }
}
