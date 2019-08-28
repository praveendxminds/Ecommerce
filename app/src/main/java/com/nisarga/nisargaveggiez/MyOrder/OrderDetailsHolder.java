package com.nisarga.nisargaveggiez.MyOrder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.billing.billingAddress;
import com.nisarga.nisargaveggiez.cart.cart;
import com.nisarga.nisargaveggiez.notifications.MyNotifications;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.ReorderItemsModel;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsHolder extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    private PlaceHolderView mCartView;
    public static String MyPREFERENCES = "sessiondata";
    SharedPreferences sharedpreferences;
    private TextView linkDeliveryDay;
    private String storeDayTime;
    String str_custid;
    SessionManager session;
    private TextView tv_total,tvtotalAmount;
    String order_id;

    APIInterface apiInterface;
    public static TextView textCartItemCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_holder);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());
        order_id = getIntent().getExtras().getString("order_id", null);
        str_custid = session.getCustomerId();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mCartView = (PlaceHolderView) findViewById(R.id.recycler_order);
        tv_total = findViewById(R.id.tv_total);
        /*  mCartView.addView(new cartItem_footer());*/

        showListView();
    }

    public void showListView() {
        if(Utils.CheckInternetConnection(getApplicationContext()))
        {
            ReorderItemsModel reorderModel = new ReorderItemsModel(session.getCustomerId(),order_id);
            Call<ReorderItemsModel> callReorderItems = apiInterface.showReorderItems(reorderModel);
            callReorderItems.enqueue(new Callback<ReorderItemsModel>() {
                @Override
                public void onResponse(Call<ReorderItemsModel> call, Response<ReorderItemsModel> response) {
                    ReorderItemsModel resourcesReorder = response.body();
                    if(resourcesReorder.status.equals("success")) {
                        List<ReorderItemsModel.ReorderResult> result = resourcesReorder.result;
                        if(result.size()>0) {
                            for (ReorderItemsModel.ReorderResult reorderData : result) {
                                mCartView.addView(new OrderDetailsItems(getApplicationContext(), reorderData.order_id,
                                        reorderData.order_product_id, reorderData.image, reorderData.name,
                                        reorderData.quantity, reorderData.discount_price, reorderData.price,
                                        reorderData.weight_classes));
                            }
                           if((resourcesReorder.TotalProduct).equals("1"))
                            {
                                tv_total.setText(resourcesReorder.TotalProduct + " " + "Item");
                            }
                            else
                            {
                                tv_total.setText(resourcesReorder.TotalProduct + " " + "Items");

                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "No Items", Toast.LENGTH_LONG).show();
                        }
                    }
                    else if(resourcesReorder.status.equals("failure"))
                    {
                        Toast.makeText(getApplicationContext(), resourcesReorder.message, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ReorderItemsModel> call, Throwable t) {
                    call.cancel();
                }
            });

        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void billing(View v) {
        Intent myIntent = new Intent(getBaseContext(), billingAddress.class);
        startActivity(myIntent);

       /* Boolean login_st_session = sharedpreferences.getBoolean("status", false);
        if (login_st_session == true) {
            Intent myIntent = new Intent(getBaseContext(), billingAddress.class);
            startActivity(myIntent);
        } else {
            Intent myIntent = new Intent(getBaseContext(), login.class);
            myIntent.putExtra("Login_INTENT", "cart");
            startActivity(myIntent);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.notifi_and_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.menu_notifi:
                Intent notificationIntent = new Intent(getBaseContext(), MyNotifications.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(notificationIntent);
                break;

            case R.id.menu_info:
                Intent infoIntent = new Intent(getBaseContext(), DeliveryInformation.class);
                infoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(infoIntent);
                break;
        }
        return true;
    }

}
