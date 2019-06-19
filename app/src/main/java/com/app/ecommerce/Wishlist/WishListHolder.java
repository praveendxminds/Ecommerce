package com.app.ecommerce.Wishlist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.app.ecommerce.Home.HomePage;
import com.app.ecommerce.Home.UseSharedPreferences;
import com.app.ecommerce.R;
import com.app.ecommerce.Utils;
import com.app.ecommerce.appIntro.WelcomeActivity;
import com.app.ecommerce.barcode.ScannerActivity;
import com.app.ecommerce.barcode.nfc;
import com.app.ecommerce.cart.cart;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.GetWishList;
import com.app.ecommerce.retrofit.ProductsHomeTwo;
import com.google.zxing.integration.android.IntentIntegrator;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.List;

import q.rorbin.badgeview.QBadgeView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CALL_PHONE;


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
    SharedPreferences sharedpreferences;
    public static String MyPREFERENCES = "sessiondata";
    View view_count;
    Integer scount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist_main);

        list_items = (PlaceHolderView) findViewById(R.id.list_items);
        veggiesWishListTitle = findViewById(R.id.veggiesWishListTitle);
        totalWishList = findViewById(R.id.totalWishList);
        AndroidNetworking.initialize(getApplicationContext());
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(WishListHolder.this, WelcomeActivity.class);
                startActivity(i);

            }
        });
        t.start();

        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
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
        // BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);


        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                //selectedFragment = ItemOneFragment.newInstance();
                                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();


                                break;
                            case R.id.navigation_scan:
                                //selectedFragment = ItemTwoFragment.newInstance();

                                new IntentIntegrator(WishListHolder.this).setCaptureActivity(ScannerActivity.class).initiateScan();
                                break;
                            case R.id.navigation_tap:
                                //selectedFragment = ItemThreeFragment.newInstance();

                                Intent nfcIntent = new Intent(getBaseContext(), nfc.class);
                                startActivity(nfcIntent);


                                break;
                            case R.id.navigation_call:
                                Intent i = new Intent(Intent.ACTION_CALL);
                                i.setData(Uri.parse("tel:18001231947"));
                                if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                    startActivity(i);
                                } else {
                                    requestPermissions(new String[]{CALL_PHONE}, 1);
                                }
                                break;
                            case R.id.navigation_cart:
                                Intent myIntent = new Intent(getBaseContext(), cart.class);
                                startActivity(myIntent);

                                break;
                        }
                        return true;
                    }
                });


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
}

