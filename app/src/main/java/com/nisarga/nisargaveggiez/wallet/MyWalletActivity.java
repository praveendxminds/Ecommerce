package com.nisarga.nisargaveggiez.wallet;

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
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.Home.CategoriesBottomNav;
import com.nisarga.nisargaveggiez.Home.HomePage;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.Wishlist.WishListHolder;
import com.nisarga.nisargaveggiez.notifications.MyNotifications;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.LoyalityPointsModel;
import com.nisarga.nisargaveggiez.retrofit.ReedemLoyalityPoints;
import com.nisarga.nisargaveggiez.retrofit.WalletBlncModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWalletActivity extends AppCompatActivity {

    APIInterface apiInterface;
    SessionManager sessionManager;
    Toolbar toolbar;
    private Button btnAddMoney, btnLoyalityPoints, btnTXnHistory;
    private TextView tvAmntLoyalityPoints,tvWalletOptnAmount;
    public static BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallet_activity);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        sessionManager = new SessionManager(MyWalletActivity.this);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        tvAmntLoyalityPoints = findViewById(R.id.tvAmntLoyalityPoints);
        tvWalletOptnAmount = findViewById(R.id.tvWalletOptnAmount);
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

    public void getData() {
        if (Utils.CheckInternetConnection(getApplicationContext())) {
//-------------------------------------Loyality points----------------------------------------------------------------------
            final LoyalityPointsModel get_loyltyPoints = new LoyalityPointsModel(sessionManager.getCustomerId());
            Call<LoyalityPointsModel> call = apiInterface.getLoyalityPoints(get_loyltyPoints);
            call.enqueue(new Callback<LoyalityPointsModel>() {
                @Override
                public void onResponse(Call<LoyalityPointsModel> call, Response<LoyalityPointsModel> response) {

                    LoyalityPointsModel resource = response.body();
                    tvAmntLoyalityPoints.setText(resource.data+" "+"Points");

                }

                @Override
                public void onFailure(Call<LoyalityPointsModel> call, Throwable t) {
                    call.cancel();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
        //-------------------------------------Wallet Balance-----------------------------
        final WalletBlncModel get_wallet_amnt = new WalletBlncModel(sessionManager.getCustomerId());
        Call<WalletBlncModel> call = apiInterface.getWalletBlnc(get_wallet_amnt);
        call.enqueue(new Callback<WalletBlncModel>() {
            @Override
            public void onResponse(Call<WalletBlncModel> call, Response<WalletBlncModel> response) {

                WalletBlncModel resource = response.body();
                tvWalletOptnAmount.setText("Rs." + " " + resource.data);
                if ((resource.data).equals("null")) {
                    tvWalletOptnAmount.setText("Rs." + " " + "0");
                }


            }


            @Override
            public void onFailure(Call<WalletBlncModel> call, Throwable t) {
                call.cancel();
            }
        });

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
