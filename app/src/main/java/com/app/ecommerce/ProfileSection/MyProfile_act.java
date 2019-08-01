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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecommerce.Home.CategoriesBottomNav;
import com.app.ecommerce.Home.HomePage;
import com.app.ecommerce.R;
import com.app.ecommerce.SessionManager;
import com.app.ecommerce.Utils;
import com.app.ecommerce.Wishlist.WishListHolder;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfile_act extends AppCompatActivity {

    APIInterface apiInterface;
    String custId;
    SessionManager session;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_act);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());
        init();
    }

    Toolbar toolbar;
    ImageView ivProfile;
    ImageButton ivEditProfile;;
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
        //---------------------------My Profile API Call-----------------
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            //------------------------------------- My profile view section------------------------------------------------
            custId = session.getCustomerId();
            final MyProfileModel myProfileModel = new MyProfileModel(custId);
            Call<MyProfileModel> call = apiInterface.showMyProfile(myProfileModel);
            call.enqueue(new Callback<MyProfileModel>() {
                @Override
                public void onResponse(Call<MyProfileModel> call, Response<MyProfileModel> response) {
                    MyProfileModel resourceMyProfile = response.body();
                    if(resourceMyProfile.status.equals("success"))
                    {
                        // Toast.makeText(getApplicationContext(),resourceMyProfile.message,Toast.LENGTH_SHORT).show();
                        List<MyProfileModel.Datum> mpmDatum = resourceMyProfile.resultdata;
                        for(MyProfileModel.Datum mpmResult : mpmDatum)
                        {
                            tvName.setText(mpmResult.firstname+" "+mpmResult.lastname);
                            tvEmail.setText(mpmResult.email);
                            tvMobileNo.setText(mpmResult.telephone);
                            tvApartmentName.setText(mpmResult.apartment);
                            tvDoorNo.setText(mpmResult.door);
                            tvArea.setText(mpmResult.area);
                            tvAddress.setText(mpmResult.address_1);
                            tvPinCode.setText(mpmResult.postcode);

                        }

                    }
                    else if(resourceMyProfile.status.equals("error"))
                    {
                        Toast.makeText(getApplicationContext(),resourceMyProfile.message,Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MyProfileModel> call, Throwable t) {

                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
        //------------------------------Edit Profile----------
        ivEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditProfile = new Intent(getBaseContext(),EditProfile_act.class);
                startActivity(intentEditProfile);
            }
        });
        //---------------------------------------------------------------
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
