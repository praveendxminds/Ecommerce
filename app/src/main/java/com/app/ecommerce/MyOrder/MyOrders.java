package com.app.ecommerce.MyOrder;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.ecommerce.Home.CategoriesBottomNav;
import com.app.ecommerce.Home.HomePage;
import com.app.ecommerce.Utils;
import com.app.ecommerce.Wishlist.WishListHolder;
import com.app.ecommerce.notifications.MyNotifications;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.MyOrderList;
import com.mindorks.placeholderview.PlaceHolderView;

import com.app.ecommerce.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by praveen on 15/11/18.
 */


public class MyOrders extends AppCompatActivity {

    Toolbar toolbar;
    private PlaceHolderView mCartView;
    public static BottomNavigationView navigationMyOrders;
    View view_count;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationMyOrders = findViewById(R.id.navigationMyOrders);
        mCartView = findViewById(R.id.recycler_order);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        if (Utils.CheckInternetConnection(getApplicationContext())) {
//-------------------------------------image slider view----------------------------------------------------------------------
            final MyOrderList get_order_list = new MyOrderList("1");
            Call<MyOrderList> call = apiInterface.getMyOrdersList(get_order_list);
            call.enqueue(new Callback<MyOrderList>() {
                @Override
                public void onResponse(Call<MyOrderList> call, Response<MyOrderList> response) {

                    MyOrderList resource = response.body();

                    List<MyOrderList.MyOrderListDatum> datumList1 = resource.result;

                    for (MyOrderList.MyOrderListDatum orderList : datumList1) {

                        mCartView.addView(new orderItem(getApplicationContext(),orderList.sorder_id,orderList.sdate_added,
                                orderList.sstatus,orderList.scancel ));

                        Log.e("-----OrdersList--",orderList.sfirstname+" "+orderList.scancel);


                    }
                }


                @Override
                public void onFailure(Call<MyOrderList> call, Throwable t) {
                    call.cancel();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

        //----------bottom navigation-----------------------------
        navigationMyOrders.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent intentHome = new Intent(getBaseContext(),HomePage.class);
                                startActivity(intentHome);
                                break;

                            case R.id.navigation_categories:
                                Intent intentCateg = new Intent(getBaseContext(),CategoriesBottomNav.class);
                                startActivity(intentCateg);
                                break;

                            case R.id.navigation_wishlist:
                                Intent intentWishlist = new Intent(getBaseContext(),WishListHolder.class);
                                startActivity(intentWishlist);
                                break;

                            case R.id.navigation_wallet:
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater miMyOrders = getMenuInflater();
        miMyOrders.inflate(R.menu.notifi_and_info_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_notifi:
                Intent intentNotifi = new Intent(getBaseContext(),MyNotifications.class);
                startActivity(intentNotifi);
                break;
            case R.id.menu_info:
                finish();
                break;
        }
        return true;
    }
}