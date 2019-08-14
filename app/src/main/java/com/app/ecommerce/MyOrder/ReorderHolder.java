package com.app.ecommerce.MyOrder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecommerce.R;
import com.app.ecommerce.SessionManager;
import com.app.ecommerce.Utils;
import com.app.ecommerce.billing.billingAddress;
import com.app.ecommerce.cart.cart;
import com.app.ecommerce.notifications.MyNotifications;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.ProductListModel;
import com.app.ecommerce.retrofit.ReorderItemsModel;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReorderHolder extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    private PlaceHolderView mCartView;
    public static String MyPREFERENCES = "sessiondata";
    SharedPreferences sharedpreferences;
    private TextView linkDeliveryDay;
    private Button buttonChkOut;
    private String storeDayTime;
    String str_custid;
    SessionManager session;
    private TextView tv_total,tvtotalAmount;

    APIInterface apiInterface;
    public static TextView textCartItemCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reorder_holder);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pIntent = new Intent(getBaseContext(), MyOrders.class);
                startActivity(pIntent);
            }
        });
        tv_total = findViewById(R.id.tv_total);
        buttonChkOut = findViewById(R.id.buttonChkOut);

        //-----delivery day link------------------
        linkDeliveryDay = (TextView) findViewById(R.id.tvDeliveryDay);
        SpannableString spannable = new SpannableString("Delivery Day");
        spannable.setSpan(new UnderlineSpan(), 0, spannable.length(), 0);
        linkDeliveryDay.setText(spannable);

        final String[] Day = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        linkDeliveryDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.delivery_time_popup, null);
                final TextView deliveryTimeDialog = alertLayout.findViewById(R.id.deliveryTimeDialog);
                //----day spinner--------
                final Spinner dayspinner = alertLayout.findViewById(R.id.dayspinner);
                final Button schedule = alertLayout.findViewById(R.id.btnSchedule);
                final SpinnerAdapterReorder adapterDay = new SpinnerAdapterReorder(ReorderHolder.this, android.R.layout.simple_list_item_1);
                adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapterDay.addAll(Day);
                adapterDay.add("Select Day");
                dayspinner.setAdapter(adapterDay);
                dayspinner.setSelection(adapterDay.getCount());
                dayspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (dayspinner.getSelectedItem() == "Select") {

                            Toast.makeText(ReorderHolder.this, "you have selected nothing", Toast.LENGTH_LONG).show();
                            //Do nothing.
                        } else {

                            Log.e("-----day selected-----", "----day selected---");
                            Toast.makeText(ReorderHolder.this, dayspinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                final AlertDialog alertDialog = new AlertDialog.Builder(ReorderHolder.this).create();
                schedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        storeDayTime = dayspinner.getSelectedItem().toString();
                        alertDialog.dismiss();
                        linkDeliveryDay.setText(storeDayTime);
                    }
                });
                alertDialog.setView(alertLayout);
                alertDialog.show();
            }
        });

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mCartView = (PlaceHolderView) findViewById(R.id.recycler_order);
        for (int i = 0; i <= 10; i++) {
            mCartView.addView(new ReorderItems(getApplicationContext()));
        }
        /*  mCartView.addView(new cartItem_footer());*/
        //showListView();
    }

    public void showListView() {
        mCartView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        session = new SessionManager(getApplicationContext());
        if(Utils.CheckInternetConnection(getApplicationContext()))
        {
            final String order_id = getIntent().getExtras().getString("order_id", "1");
            str_custid = session.getCustomerId();
            ReorderItemsModel reorderModel = new ReorderItemsModel(str_custid,order_id);
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<ReorderItemsModel> callReorderItems = apiInterface.showReorderItems(reorderModel);
            callReorderItems.enqueue(new Callback<ReorderItemsModel>() {
                @Override
                public void onResponse(Call<ReorderItemsModel> call, Response<ReorderItemsModel> response) {
                    ReorderItemsModel resourcesReorder = response.body();
                    if(resourcesReorder.status.equals("success")) {
                        Toast.makeText(getApplicationContext(), resourcesReorder.message, Toast.LENGTH_LONG).show();
                        List<ReorderItemsModel.ReorderResult> result = resourcesReorder.result;
                        for (ReorderItemsModel.ReorderResult reorderData : result )
                        {
                            mCartView.addView(new ReorderItems(getApplicationContext(), reorderData.order_id, reorderData.order_product_id,
                                    reorderData.image, reorderData.name, reorderData.quantity,reorderData.price,reorderData.price));
                        }
                        tv_total.setText(resourcesReorder.TotalProduct);
                        tvtotalAmount.setText(resourcesReorder.totalMoney);

                    }
                    else if(resourcesReorder.status.equals("failure"))
                    {
                        Toast.makeText(getApplicationContext(), resourcesReorder.message, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ReorderItemsModel> call, Throwable t) {

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
            case R.id.menu_notifi:
                Intent notificationIntent = new Intent(getBaseContext(), MyNotifications.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(notificationIntent);
                break;

            case R.id.menu_info:
                Intent infoIntent = new Intent(getBaseContext(), cart.class);
                infoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(infoIntent);
                break;
        }
        return true;
    }

}