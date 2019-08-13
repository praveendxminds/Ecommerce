package com.app.ecommerce.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.ecommerce.DeliveryInformation;
import com.app.ecommerce.R;
import com.mindorks.placeholderview.PlaceHolderView;

public class LoyalityPoints extends AppCompatActivity {

    Toolbar toolbar;
    private TextView tvReedem,tvPoints;
    PlaceHolderView phvLoyalityPoints;

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
        String[] strArrNotes = {"1.","2.","3.","4.","5."} ;
        for(int i=0;i<strArrNotes.length;i++)
        {
            phvLoyalityPoints.addView(new LoyalityPointNotes(getApplicationContext(),strArrNotes[i]));
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
