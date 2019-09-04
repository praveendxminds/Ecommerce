package com.nisarga.nisargaveggiez.Wishlist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.Home.CategoriesBottomNav;
import com.nisarga.nisargaveggiez.Home.HomePage;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.notifications.MyNotifications;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.GetWishList;
import com.mindorks.placeholderview.PlaceHolderView;
import com.nisarga.nisargaveggiez.wallet.MyWalletActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WishListHolder extends AppCompatActivity {

    APIInterface apiInterface;
    SessionManager session;

    TextView veggiesWishListTitle;
    DrawerLayout mDrawer;
    Toolbar mToolbar;
    BottomNavigationView bottomNavigationView;

    public static TextView totalWishList;
    public static TextView tvEmptyWishlist;
    public static PlaceHolderView list_items;
    SwipeRefreshLayout pullToRefresh;

    Integer scount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist_main);
        session = new SessionManager(getApplicationContext());

        list_items = (PlaceHolderView) findViewById(R.id.list_items);
        veggiesWishListTitle = findViewById(R.id.veggiesWishListTitle);
        totalWishList = findViewById(R.id.totalWishList);
        tvEmptyWishlist = findViewById(R.id.tvEmptyWishlist);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        AndroidNetworking.initialize(getApplicationContext());

        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getwishlist();
        pullToRefresh = findViewById(R.id.refresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list_items.removeAllViews();
                getwishlist();
                pullToRefresh.setRefreshing(false);

            }
        });

        //------------------------------Bottom Navigation---------------------------------------------------------------------
        bottomNavigationView.getMenu().findItem(R.id.navigation_wishlist).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent intentHome = new Intent(WishListHolder.this, HomePage.class);
                                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentHome);
                                break;

                            case R.id.navigation_categories:
                                Intent intentCateg = new Intent(WishListHolder.this, CategoriesBottomNav.class);
                                intentCateg.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentCateg);
                                break;

                            case R.id.navigation_wishlist:
                                Intent intentWishlist = new Intent(WishListHolder.this, WishListHolder.class);
                                intentWishlist.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentWishlist);
                                break;

                            case R.id.navigation_wallet:
                                Intent intentWallet = new Intent(WishListHolder.this, MyWalletActivity.class);
                                intentWallet.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentWallet);
                                break;

                        }
                        return true;
                    }
                });
        bottomNavigationView.setItemIconSize(40);
    }

    //----------------------------------------------------------------------for go back to previous page-----------------
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(WishListHolder.this, HomePage.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater miWishlist = getMenuInflater();
        miWishlist.inflate(R.menu.notifi_and_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_notifi:
                Intent intentNotifications = new Intent(getBaseContext(), MyNotifications.class);
                startActivity(intentNotifications);
                break;

            case R.id.menu_info:
                Intent intentDeliveryInfo = new Intent(getBaseContext(), DeliveryInformation.class);
                startActivity(intentDeliveryInfo);
                break;
        }
        return true;
    }

    public void getwishlist() {
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final GetWishList wishListItems = new GetWishList(session.getCustomerId());
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<GetWishList> call = apiInterface.getWishList(wishListItems);
            call.enqueue(new Callback<GetWishList>() {
                @Override
                public void onResponse(Call<GetWishList> call, Response<GetWishList> response) {
                    GetWishList resource = response.body();
                    scount = resource.count;
                    if (scount == 0) {
                        totalWishList.setText("0 Items");
                    } else if (scount == 1) {
                        totalWishList.setText(scount + " " + "Item");
                    } else {
                        totalWishList.setText(scount + " " + "Items");
                    }
                    List<GetWishList.WishListDatum> datumList = resource.result;
                    if ((resource.status).equals("success")) {
                        for (GetWishList.WishListDatum imgs : datumList) {
                            list_items.addView(new WishListItem(WishListHolder.this, imgs.product_id, imgs.image,
                                    imgs.name, imgs.price, imgs.quantity, imgs.discount_price, list_items));
                        }
                    } else if (resource.status.equals("failure")) {
                        tvEmptyWishlist.setVisibility(View.VISIBLE);
                        list_items.setVisibility(View.GONE);
                    }
                    list_items.refresh();
                }

                @Override
                public void onFailure(Call<GetWishList> call, Throwable t) {
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(WishListHolder.this, "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }

    }
}

