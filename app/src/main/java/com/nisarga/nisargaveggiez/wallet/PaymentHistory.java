package com.nisarga.nisargaveggiez.wallet;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.Home.CategoriesBottomNav;
import com.nisarga.nisargaveggiez.Home.HomePage;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.Wishlist.WishListHolder;
import com.nisarga.nisargaveggiez.cart.cartItem;
import com.nisarga.nisargaveggiez.notifications.MyNotifications;
import com.mindorks.placeholderview.PlaceHolderView;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.CartListModel;
import com.nisarga.nisargaveggiez.retrofit.TxnHistoryModel;

import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentHistory extends AppCompatActivity {

    Toolbar toolbar;
    private PlaceHolderView recycler_payHistory;
    public static BottomNavigationView bottomNavigationView;
    SessionManager session;
    APIInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_history);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recycler_payHistory = (PlaceHolderView) findViewById(R.id.recycler_payHistory);
        showListView();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bnav_PayHistory);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent intentHomePage = new Intent(getBaseContext(), HomePage.class);
                                intentHomePage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentHomePage);
                                break;

                            case R.id.navigation_categories:
                                Intent intentCategories = new Intent(getBaseContext(), CategoriesBottomNav.class);
                                intentCategories.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentCategories);
                                break;

                            case R.id.navigation_wishlist:
                                Intent intentWishlist = new Intent(getBaseContext(), WishListHolder.class);
                                intentWishlist.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentWishlist);
                                break;

                            case R.id.navigation_wallet:
                                Intent intentWallet = new Intent(getBaseContext(), MyWalletActivity.class);
                                intentWallet.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentWallet);
                                break;
                        }
                        return true;
                    }
                });
        bottomNavigationView.setItemIconSize(40);
    }

    public void showListView()
    {
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final TxnHistoryModel paymentHistoryModel = new TxnHistoryModel("97");
            Call<TxnHistoryModel> call = apiInterface.getHistory(paymentHistoryModel);
           call.enqueue(new Callback<TxnHistoryModel>() {
                @Override
                public void onResponse(Call<TxnHistoryModel> call, Response<TxnHistoryModel> response) {
                    TxnHistoryModel resource = response.body();
                    List<TxnHistoryModel.TxnHistoryDatum> datumList = resource.data;
                    for (TxnHistoryModel.TxnHistoryDatum imgs : datumList) {
                        if (response.isSuccessful()) {

                            recycler_payHistory.addView(new PaymentHistoryItems(getApplicationContext(),imgs.date, imgs.transaction_type,
                                    imgs.description, imgs.amount,imgs.balance, imgs.type));
                        }
                    }
                }

                @Override
                public void onFailure(Call<TxnHistoryModel> call, Throwable t) {
                    call.cancel();
                }
            });


        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
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
