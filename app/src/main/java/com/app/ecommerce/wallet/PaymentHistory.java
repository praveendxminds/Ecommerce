package com.app.ecommerce.wallet;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.app.ecommerce.DeliveryInformation;
import com.app.ecommerce.Home.CategoriesBottomNav;
import com.app.ecommerce.Home.HomePage;
import com.app.ecommerce.R;
import com.app.ecommerce.Wishlist.WishListHolder;
import com.app.ecommerce.notifications.MyNotifications;
import com.mindorks.placeholderview.PlaceHolderView;

public class PaymentHistory extends AppCompatActivity {

    Toolbar toolbar;
    private PlaceHolderView mnotificationView;
    public static BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_history);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mnotificationView = (PlaceHolderView) findViewById(R.id.recycler_notify);


        mnotificationView
                .addView(new PaymentHistoryItems(getApplicationContext()))
                .addView(new PaymentHistoryItems(getApplicationContext()))
                .addView(new PaymentHistoryItems(getApplicationContext()))
                .addView(new PaymentHistoryItems(getApplicationContext()))
                .addView(new PaymentHistoryItems(getApplicationContext()))
                .addView(new PaymentHistoryItems(getApplicationContext()));

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bnav_Notifications);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent intentHomePage = new Intent(getBaseContext(), HomePage.class);
                                intentHomePage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentHomePage);
                                break;

                            case R.id.navigation_categories:
                                Intent intentCategories = new Intent(getBaseContext(), CategoriesBottomNav.class);
                                intentCategories.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentCategories);
                                break;

                            case R.id.navigation_wishlist:
                                Intent intentWishlist = new Intent(getBaseContext(), WishListHolder.class);
                                intentWishlist.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentWishlist);
                                break;

                            case R.id.navigation_wallet:
                                Intent intentWallet = new Intent(getBaseContext(), MyWalletActivity.class);
                                intentWallet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentWallet);
                                break;
                        }
                        return true;
                    }
                });
        bottomNavigationView.setItemIconSize(40);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater miNotification = getMenuInflater();
        miNotification.inflate(R.menu.notifi_and_info_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.menu_notifi:
                Intent intentNotifi = new Intent(getBaseContext(),MyNotifications.class);
                startActivity(intentNotifi);
                break;
            case R.id.menu_info:
                Intent intentInfo = new Intent(getBaseContext(),DeliveryInformation.class);
                startActivity(intentInfo);
                break;
        }
        return true;
    }
}
