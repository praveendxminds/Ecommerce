package com.app.ecommerce.cart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.mindorks.placeholderview.PlaceHolderView;
import com.app.ecommerce.R;
import com.app.ecommerce.billing.billingAddress;

/**
 * Created by sushmita on 25/06/2019
 */


public class cart extends AppCompatActivity {

    Toolbar toolbar;
    private PlaceHolderView mCartView;
    public static String MyPREFERENCES = "sessiondata";
    SharedPreferences sharedpreferences;
    TextView linkDeliveryTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //-----delivery time link------------------
        linkDeliveryTime = (TextView) findViewById(R.id.linkDeliveryTime);
       /* String str_links = "<a href='http://google.com'>Delivery Time</a>";
        linkDeliveryTime.setLinksClickable(true);
        linkDeliveryTime.setMovementMethod(LinkMovementMethod.getInstance());
        linkDeliveryTime.setText( Html.fromHtml( str_links ) );
*/
        final String[] Company = {"Apple", "Genpack", "Microsoft", "HP", "HCL", "Ericsson"};
        linkDeliveryTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.delivery_time_popup, null);
                final TextView title = alertLayout.findViewById(R.id.title);
                final ImageView image = alertLayout.findViewById(R.id.image);
                final Spinner spinner = alertLayout.findViewById(R.id.popupspinner);
                final Button dismiss = alertLayout.findViewById(R.id.dismiss);
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(cart.this,
                                android.R.layout.simple_spinner_item, Company);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                final AlertDialog alertDialog = new AlertDialog.Builder(cart.this).create();
                alertDialog.setView(alertLayout);
                dismiss.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        mCartView = (PlaceHolderView) findViewById(R.id.recycler_cart);


        for (int i = 0; i <= 10; i++) {
            mCartView.addView(new cartItem(getApplicationContext()));
        }
        mCartView.addView(new cartItem_footer());


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void billing(View v) {
        Intent myIntent = new Intent(getBaseContext(), billingAddress.class);
        startActivity(myIntent);
        
       /* Boolean login_st_session = sharedpreferences.getBoolean("status", false);
        if (login_st_session == true) {
            Intent myIntent = new Intent(getBaseContext(), billingAddress.class);
            startActivity(myIntent);
        } else {
            Intent myIntent = new Intent(getBaseContext(), login.class);
            myIntent.putExtra("Login_INTENT", "cart");
            startActivity(myIntent);
        }*/
    }
}