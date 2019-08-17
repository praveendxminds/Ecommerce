package com.nisarga.nisargaveggiez.wallet;

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
import android.widget.TextView;

import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.R;
import com.mindorks.placeholderview.PlaceHolderView;

public class LoyalityPoints extends AppCompatActivity {

    Toolbar toolbar;
    private TextView tvReedem,tvPoints;
    PlaceHolderView phvLoyalityPoints;
    Button btnProceed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loyality_points_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tvReedem = findViewById(R.id.tvReedem);
        tvPoints = findViewById(R.id.tvPoints);
        phvLoyalityPoints = findViewById(R.id.phvLoyalityPoints);
        btnProceed = findViewById(R.id.btnProceed);
        String[] strArrNotes = {"1.","2.","3.","4.","5."} ;
        for(int i=0;i<strArrNotes.length;i++)
        {
            phvLoyalityPoints.addView(new LoyalityPointNotes(getApplicationContext(),strArrNotes[i]));
        }

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRedeemptionSuccess = new Intent(LoyalityPoints.this,LoyalityPointSuccessAck.class);
                startActivity(intentRedeemptionSuccess);
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
                break;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
