package com.nisarga.nisargaveggiez.notifications;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nisarga.nisargaveggiez.Delivery;
import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.Home.CategoriesBottomNav;
import com.nisarga.nisargaveggiez.Home.HomeCategory;
import com.nisarga.nisargaveggiez.Home.HomePage;
import com.nisarga.nisargaveggiez.ProfileSection.MyProfileModel;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.Wishlist.WishListHolder;
import com.mindorks.placeholderview.PlaceHolderView;

import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.wallet.MyWalletActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by praveen on 15/11/18.
 */


public class MyNotifications extends AppCompatActivity {

    Toolbar toolbar;
    private PlaceHolderView mnotificationView;
    public static BottomNavigationView bottomNavigationView;
    APIInterface apiInterface;
    SessionManager session;
    FrameLayout flNotifications;
    TextView tvEmptyNotifications;
    SwipeRefreshLayout pullToRefresh;
    ProgressBar progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_notifications);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        progressDialog = findViewById(R.id.pbLoading);
        progressDialog.setVisibility(View.VISIBLE);

        flNotifications = (FrameLayout) findViewById(R.id.flNotifications);
        mnotificationView = (PlaceHolderView) findViewById(R.id.recycler_notify);
        tvEmptyNotifications = (TextView) findViewById(R.id.tvEmptyNotifications);


//        mnotificationView
//                .addView(new NotificationItem(getApplicationContext()))
//                .addView(new NotificationItem(getApplicationContext()))
//                .addView(new NotificationItem(getApplicationContext()))
//                .addView(new NotificationItem(getApplicationContext()))
//                .addView(new NotificationItem(getApplicationContext()))
//                .addView(new NotificationItem(getApplicationContext()));


        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());

        getNotifications();
        pullToRefresh = findViewById(R.id.refresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                mnotificationView.removeAllViews();
                getNotifications();
                pullToRefresh.setRefreshing(false);

            }
        });



        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bnav_Notifications);
        bottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent intentHome = new Intent(MyNotifications.this, HomePage.class);
                                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentHome);
                                break;

                            case R.id.navigation_categories:
                                Intent intentCateg = new Intent(MyNotifications.this, CategoriesBottomNav.class);
                                intentCateg.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentCateg);
                                break;

                            case R.id.navigation_wishlist:
                                Intent intentWishlist = new Intent(MyNotifications.this, WishListHolder.class);
                                intentWishlist.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentWishlist);
                                break;

                            case R.id.navigation_wallet:
                                Intent intentWallet = new Intent(MyNotifications.this, MyWalletActivity.class);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater miNotification = getMenuInflater();
        miNotification.inflate(R.menu.instruction_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.help_menu_item:
                Intent i = new Intent(getApplicationContext(), DeliveryInformation.class);
                startActivity(i);
                break;
        }
        return true;
    }

    public void getNotifications() {

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            //------------------------------------- My profile view section------------------------------------------------
            final NotificationListModel cust_id = new NotificationListModel(session.getCustomerId());
            Call<NotificationListModel> call = apiInterface.getnotificationlist(cust_id);
            call.enqueue(new Callback<NotificationListModel>() {
                @Override
                public void onResponse(Call<NotificationListModel> call, Response<NotificationListModel> response) {
                    NotificationListModel resourceMyProfile = response.body();
                    if (resourceMyProfile.status.equals("success")) {
                        progressDialog.setVisibility(View.INVISIBLE);
                        List<NotificationListModel.NotificationListModelDatum> mpmDatum = resourceMyProfile.result;
                        for (NotificationListModel.NotificationListModelDatum mpmResult : mpmDatum) {
                            mnotificationView.addView(new NotificationItem(getApplicationContext(), mpmResult.date, mpmResult.title, mpmResult.body));
                        }

                    } else if (resourceMyProfile.status.equals("failure")) {
                        progressDialog.setVisibility(View.INVISIBLE);
                        tvEmptyNotifications.setVisibility(View.VISIBLE);
                        flNotifications.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<NotificationListModel> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }
}