package com.nisarga.nisargaveggiez.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.Home.HomePage;
import com.nisarga.nisargaveggiez.R;

public class ConfirmOrder extends AppCompatActivity {

    Toolbar toolbar;
    private LinearLayout llSubmitFeedback;
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
        submitFeedback();


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public  void submitFeedback()
    {
        llSubmitFeedback.setOnClickListener(new View.OnClickListener() {
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
