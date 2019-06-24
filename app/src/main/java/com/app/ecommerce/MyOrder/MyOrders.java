package com.app.ecommerce.MyOrder;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.mindorks.placeholderview.PlaceHolderView;

import com.app.ecommerce.R;

/**
 * Created by praveen on 15/11/18.
 */



public class MyOrders extends AppCompatActivity {

    Toolbar toolbar;
    private PlaceHolderView mCartView;
   public static BottomNavigationView navigationMyOrders;
    View view_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationMyOrders = findViewById(R.id.navigationMyOrders);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mCartView = (PlaceHolderView)findViewById(R.id.recycler_order);
        for(int i=0;i<=10;i++) {
            mCartView.addView(new orderItem(getApplicationContext()));
        }
          /* mCartView
                .addView(new orderItemHeader())
                .addView(new orderItem(getApplicationContext(),getResources().getDrawable(R.drawable.home)))
                .addView(new orderItem(getApplicationContext(),getResources().getDrawable(R.drawable.call)))
                .addView(new orderItem(getApplicationContext(),getResources().getDrawable(R.drawable.tap)))
                .addView(new orderItem(getApplicationContext(),getResources().getDrawable(R.drawable.home)))
                .addView(new orderItem(getApplicationContext(),getResources().getDrawable(R.drawable.tap)))
                .addView(new orderItem(getApplicationContext(),getResources().getDrawable(R.drawable.call)))
                .addView(new orderItem(getApplicationContext(),getResources().getDrawable(R.drawable.call)))
                .addView(new orderItem(getApplicationContext(),getResources().getDrawable(R.drawable.tap)));
*/
          //----------bottom navigation-----------------------------
        navigationMyOrders.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                break;

                            case R.id.navigation_wishlist:
                                break;

                            case R.id.navigation_wallet:
                                break;

                            case R.id.navigation_profile:
                                break;
                        }
                        return true;
                    }
                });


    }

    @Override
    protected void onResume() {
        super.onResume();

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) navigationMyOrders.getChildAt(0);
        view_count = bottomNavigationMenuView.getChildAt(4);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}