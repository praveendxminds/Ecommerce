package com.app.ecommerce.wallet;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.app.ecommerce.DeliveryInformation;
import com.app.ecommerce.Home.CategoriesBottomNav;
import com.app.ecommerce.Home.HomePage;
import com.app.ecommerce.ProfileSection.Faqs_act;
import com.app.ecommerce.R;
import com.app.ecommerce.Wishlist.WishListHolder;
import com.app.ecommerce.notifications.MyNotifications;
import com.app.ecommerce.notifications.NotificationItem;
import com.mindorks.placeholderview.PlaceHolderView;

public class MyWalletActivity extends AppCompatActivity {

    Toolbar toolbar;
    private Button btnAddMoney, btnLoyalityPoints, btnTXnHistory;
    public static BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallet_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        btnAddMoney = findViewById(R.id.btnWalletOptnBtns);
        btnLoyalityPoints = findViewById(R.id.btnLoyalityPoints);
        btnTXnHistory = findViewById(R.id.btntxnHistory);

        btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddMoney = new Intent(MyWalletActivity.this, AddMoneyToWallet.class);
                startActivity(intentAddMoney);
            }
        });
        btnLoyalityPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoyalityPoints = new Intent(MyWalletActivity.this, LoyalityPoints.class);
                startActivity(intentLoyalityPoints);
            }
        });
        btnTXnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTxnHistory = new Intent(MyWalletActivity.this, PaymentHistory.class);
                startActivity(intentTxnHistory);
            }
        });

        setFooter();
    }

    void setFooter() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bNav_MyWallet);
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
                                Intent intentWallet = new Intent(getBaseContext(), WishListHolder.class);
                                intentWallet.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentWallet);
                                break;
                        }
                        return true;
                    }
                });
        bottomNavigationView.setItemIconSize(40);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuMyWallet = getMenuInflater();
        menuMyWallet.inflate(R.menu.notifi_and_info_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.menu_notifi:
                Intent intentNotifiMywallet = new Intent(getBaseContext(), MyNotifications.class);
                startActivity(intentNotifiMywallet);
                break;

            case R.id.menu_info:
                Intent intentInfoMywallet = new Intent(getBaseContext(), DeliveryInformation.class);
                startActivity(intentInfoMywallet);
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
