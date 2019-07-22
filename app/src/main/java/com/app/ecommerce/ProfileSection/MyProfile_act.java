package com.app.ecommerce.ProfileSection;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ecommerce.Home.CategoriesBottomNav;
import com.app.ecommerce.Home.HomePage;
import com.app.ecommerce.R;
import com.app.ecommerce.Wishlist.WishListHolder;

public class MyProfile_act extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_act);
        init();
    }

    Toolbar toolbar;
    ImageView ivProfile, ivEditProfile;
    TextView tvName, tvMobileNo, tvEmail, tvApartmentName, tvDoorNo, tvArea,
            tvAddress, tvPinCode;
    Button btnNearBy;
    public static BottomNavigationView bottom_navigation;

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Profile");

        ivProfile = findViewById(R.id.ivProfile);
        ivEditProfile = findViewById(R.id.ivEditProfile);
        tvName = findViewById(R.id.tvName);
        tvMobileNo = findViewById(R.id.tvMobileNo);
        tvEmail = findViewById(R.id.tvEmail);
        tvApartmentName = findViewById(R.id.tvApartmentName);
        tvDoorNo = findViewById(R.id.tvDoorNo);
        tvArea = findViewById(R.id.tvArea);
        tvAddress = findViewById(R.id.tvAddress);
        tvPinCode = findViewById(R.id.tvPinCode);

        btnNearBy = findViewById(R.id.btnNearBy);
        bottom_navigation= findViewById(R.id.bottom_navigation);

        //---------------------------------------------------
        bottom_navigation.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent intentHome = new Intent(getBaseContext(), HomePage.class);
                                startActivity(intentHome);
                                break;

                            case R.id.navigation_categories:
                                Intent intentCateg = new Intent(getBaseContext(), CategoriesBottomNav.class);
                                startActivity(intentCateg);
                                break;

                            case R.id.navigation_wishlist:
                                Intent intentWishlist = new Intent(getBaseContext(), WishListHolder.class);
                                intentWishlist.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentWishlist);
                                break;

                            case R.id.navigation_wallet:
                                break;

                        }
                        return true;
                    }
                });
        bottom_navigation.setItemIconSize(40);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
