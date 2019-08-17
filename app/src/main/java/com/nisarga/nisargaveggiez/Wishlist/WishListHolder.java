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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WishListHolder extends AppCompatActivity {

    APIInterface apiInterface;
    private PlaceHolderView list_items;
    private TextView veggiesWishListTitle, totalWishList;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    public Context mContext;
    private static WishListHolder instance;
    public static BottomNavigationView bottomNavigationView;
    Integer name_session;
    SessionManager session;
    SharedPreferences sharedpreferences;
    public static String MyPREFERENCES = "sessiondata";
    View view_count;
    Integer scount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist_main);
        session = new SessionManager(getApplicationContext());

        list_items = (PlaceHolderView) findViewById(R.id.list_items);
        veggiesWishListTitle = findViewById(R.id.veggiesWishListTitle);
        totalWishList = findViewById(R.id.totalWishList);
        AndroidNetworking.initialize(getApplicationContext());
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        instance = this;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
       /* mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pIntent = new Intent(getBaseContext(), HomePage.class);
                startActivity(pIntent);
            }
        });*/

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final GetWishList wishListItems = new GetWishList("1");
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<GetWishList> call = apiInterface.getWishList(wishListItems);
            call.enqueue(new Callback<GetWishList>() {
                @Override
                public void onResponse(Call<GetWishList> call, Response<GetWishList> response) {
                    GetWishList resource = response.body();
                    List<GetWishList.WishListDatum> datumList = resource.result;
                    for (GetWishList.WishListDatum imgs : datumList) {
                        list_items.addView(new WishListItem(WishListHolder.this, imgs.product_id, imgs.image, imgs.name,
                                imgs.price, imgs.quantity, list_items));
                        scount = resource.count;
                        if (scount == 0) {
                            totalWishList.setText("No Items");
                        } else if (scount == 1) {
                            totalWishList.setText(scount + " " + "Item");
                        } else {
                            totalWishList.setText(scount + " " + "Items");
                        }

                    }
                }

                @Override
                public void onFailure(Call<GetWishList> call, Throwable t) {
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(WishListHolder.this, "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
        //------------------------------Bottom Navigation---------------------------------------------------------------------
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
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
                               finish();
                                break;

                            case R.id.navigation_wallet:
                                break;
                        }
                        return true;
                    }
                });
        bottomNavigationView.setItemIconSize(40);


    }

    public static WishListHolder getInstance() {
        return instance;
    }

    //----------------------------------------------------------------------for go back to previous page-----------------
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater miWishlist =  getMenuInflater();
        miWishlist.inflate(R.menu.notifi_and_info_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {

            case android.R.id.home:
                onBackPressed();
                return true;


            case R.id.menu_notifi:
                Intent intentNotifications = new Intent(getBaseContext(),MyNotifications.class);
                startActivity(intentNotifications);
                break;

            case R.id.menu_info:
                break;
        }
        return true;
    }
}

