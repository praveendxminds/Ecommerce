package com.nisarga.nisargaveggiez.ProfileSection;

import android.app.ProgressDialog;
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

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nisarga.nisargaveggiez.Home.CategoriesBottomNav;
import com.nisarga.nisargaveggiez.Home.HomePage;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.Wishlist.WishListHolder;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfile_act extends AppCompatActivity {

    APIInterface apiInterface;
    SessionManager session;
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_act);

        progressdialog = new ProgressDialog(MyProfile_act.this);
        progressdialog.setMessage("Please Wait....");

        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());
        init();
    }

    Toolbar toolbar;
    CircularImageView ivProfile;
    ImageView ivEditProfile;
    TextView tvName, tvMobileNo, tvEmail, tvApartmentName, tvNearBy, tvDoorNo, tvArea, tvAddress, tvPinCode;
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
        tvNearBy = findViewById(R.id.tvNearBy);

        bottom_navigation = findViewById(R.id.bottom_navigation);

        ivEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditProfile = new Intent(MyProfile_act.this, EditProfile_act.class);
                startActivity(intentEditProfile);
            }
        });

        bottom_navigation.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent intentHome = new Intent(MyProfile_act.this, HomePage.class);
                                startActivity(intentHome);
                                break;

                            case R.id.navigation_categories:
                                Intent intentCateg = new Intent(MyProfile_act.this, CategoriesBottomNav.class);
                                startActivity(intentCateg);
                                break;

                            case R.id.navigation_wishlist:
                                Intent intentWishlist = new Intent(MyProfile_act.this, WishListHolder.class);
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


        //---------------------------My Profile API Call-----------------
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            try {
                progressdialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            final MyProfileModel myProfileModel = new MyProfileModel(session.getCustomerId());
            Call<MyProfileModel> call = apiInterface.showMyProfile(myProfileModel);
            call.enqueue(new Callback<MyProfileModel>() {
                @Override
                public void onResponse(Call<MyProfileModel> call, Response<MyProfileModel> response) {
                    MyProfileModel resourceMyProfile = response.body();
                    if (resourceMyProfile.status.equals("success")) {
                        List<MyProfileModel.Datum> mpmDatum = resourceMyProfile.resultdata;
                        for (MyProfileModel.Datum mpmResult : mpmDatum) {
                            if (String.valueOf(mpmResult.image) == "null") {
                                ivProfile.setImageResource(R.drawable.camera);
                            } else {
                                Glide.with(MyProfile_act.this).load(mpmResult.image).fitCenter().dontAnimate()
                                        .into(ivProfile);
                            }
                            tvName.setText(mpmResult.firstname + " " + mpmResult.lastname);
                            tvEmail.setText(mpmResult.email);
                            tvMobileNo.setText(mpmResult.telephone);
                            tvApartmentName.setText(mpmResult.apartment);
                            tvDoorNo.setText(mpmResult.door);
                            tvArea.setText(mpmResult.area);
                            tvAddress.setText(mpmResult.address_1);
                            tvPinCode.setText(mpmResult.postcode);

                        }
                    }
                    progressdialog.dismiss();
                }

                @Override
                public void onFailure(Call<MyProfileModel> call, Throwable t) {
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
