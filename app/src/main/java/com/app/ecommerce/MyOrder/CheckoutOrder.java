package com.app.ecommerce.MyOrder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.ecommerce.R;

public class CheckoutOrder extends AppCompatActivity {
    Toolbar toolbar;
    private Button btnItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_order);

        btnItems = findViewById(R.id.btnItems);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        addPaymentOption(4);
        btnItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReorder = new Intent(getBaseContext(),ReorderHolder.class);
                startActivity(intentReorder);
            }
        });
    }
    public String[] strPayArr = {"Wallet","G-pay","Paytm","Phonepe"};
    public void addPaymentOption(int number)
    {
        for(int i=0;i<number ; i++)
        {
            RadioGroup ll = new RadioGroup(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            for(int j=0;j<number;j++)
            {
                RadioButton radioBtn = new RadioButton(this);
                radioBtn.setId(View.generateViewId());
                radioBtn.setText(strPayArr[j]+radioBtn.getId());
                ll.addView(radioBtn);
            }
            ((ViewGroup) findViewById(R.id.radiogroup)).addView(ll);
        }
    }
    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}
