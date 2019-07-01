package com.app.ecommerce.billing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.app.ecommerce.R;
import com.app.ecommerce.ccavenue.ccavenue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by praveen on 15/11/18.
 */


public class billingAddress extends AppCompatActivity {

    Toolbar toolbar;
    private Spinner spnr_delivTym;
    String[] str_time={"6AM","7AM","8AM","9AM","10AM","11AM","12PM","1PM","2PM"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping_details_mycart);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spnr_delivTym = findViewById(R.id.spnr_delivTym);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        final List<String> listSpnr = new ArrayList<>(Arrays.asList(str_time));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_product_qtylist_home_two,listSpnr);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_product_qtylist_home_two);
        spnr_delivTym.setAdapter(arrayAdapter);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void payment(View view)
    {
        Intent accountIntent = new Intent(getBaseContext(), ccavenue.class);
        startActivity(accountIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.instruction_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.help_menu_item:
            Intent intentHelp = new Intent(getBaseContext(),billingAddress.class);
            intentHelp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentHelp);
            break;
        }
        return true;
    }
}