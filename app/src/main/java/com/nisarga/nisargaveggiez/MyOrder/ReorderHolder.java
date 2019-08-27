package com.nisarga.nisargaveggiez.MyOrder;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.billing.billingAddress;
import com.nisarga.nisargaveggiez.cart.DeliverydateAdapter;
import com.nisarga.nisargaveggiez.cart.cart;
import com.nisarga.nisargaveggiez.notifications.MyNotifications;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.ReorderItemsModel;
import com.mindorks.placeholderview.PlaceHolderView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
    String str_custid,str_TotalAmnt;
    SessionManager session;
    private TextView tv_total, tvtotalAmount;
    private String order_id;

    APIInterface apiInterface;
    public static TextView textCartItemCount;
    public String select_item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reorder_holder);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        order_id = getIntent().getExtras().getString("order_id", null);
        session = new SessionManager(getApplicationContext());
        str_custid = session.getCustomerId();

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
        tvtotalAmount = findViewById(R.id.tvtotalAmount);
        buttonChkOut = findViewById(R.id.buttonChkOut);

        //-----delivery day link------------------
        linkDeliveryDay = (TextView) findViewById(R.id.tvDeliveryDay);
        SpannableString spannable = new SpannableString("Delivery Day");
        spannable.setSpan(new UnderlineSpan(), 0, spannable.length(), 0);
        linkDeliveryDay.setText(spannable);
        select_item = "Select";

        final String[] Day = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        linkDeliveryDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                final List<String> categories = new ArrayList<String>();
                final List<String> categories_dtes = new ArrayList<String>();
                categories.add("Select");
                categories_dtes.add("Select");

                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                if(hour>=21)
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                    for (int i = 2; i < 5; i++) {
                        Calendar calendars = new GregorianCalendar();
                        calendars.add(Calendar.DAY_OF_WEEK, i);
                        String catdays = sdf.format(calendars.getTime());
                        String days = sdf1.format(calendars.getTime());
                        Log.i("daysddddds", days);
                        categories.add(catdays);
                        categories_dtes.add(days);
                    }
                }
                else
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                    for (int i = 1; i < 4; i++) {
                        Calendar calendars = new GregorianCalendar();
                        calendars.add(Calendar.DAY_OF_WEEK, i);
                        String catdays = sdf.format(calendars.getTime());
                        String days = sdf1.format(calendars.getTime());
                        Log.i("daysddddds", days);
                        categories.add(catdays);
                        categories_dtes.add(days);
                    }
                }

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.delivery_time_popup, null);
                final TextView deliveryTimeDialog = alertLayout.findViewById(R.id.deliveryTimeDialog);
                //----day spinner--------
                final Spinner dayspinner = alertLayout.findViewById(R.id.dayspinner);
                final Button schedule = alertLayout.findViewById(R.id.btnSchedule);

                dayspinner.setAdapter(new DeliverydateAdapter(getApplicationContext(), categories, categories));
                dayspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                      //  Toast.makeText(getApplicationContext(), categories.get(position), Toast.LENGTH_LONG).show();
                        session.setDeliverydate(categories_dtes.get(position));
                        session.setDeliveryweek(categories.get(position));
                        select_item = categories.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                final AlertDialog alertDialog = new AlertDialog.Builder(ReorderHolder.this).create();
                schedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //storeDayTime = dayspinner.getSelectedItem().toString();
                        storeDayTime = select_item;
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
        /*  mCartView.addView(new cartItem_footer());*/
        showListView();
    }

    public void showListView() {
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            ReorderItemsModel reorderModel = new ReorderItemsModel(session.getCustomerId(), order_id);
            Call<ReorderItemsModel> callReorderItems = apiInterface.showReorderItems(reorderModel);
            callReorderItems.enqueue(new Callback<ReorderItemsModel>() {
                @Override
                public void onResponse(Call<ReorderItemsModel> call, Response<ReorderItemsModel> response) {
                    ReorderItemsModel resourcesReorder = response.body();
                    if (resourcesReorder.status.equals("success")) {
                        List<ReorderItemsModel.ReorderResult> result = resourcesReorder.result;
                        if(result.size()>0) {

                            for (ReorderItemsModel.ReorderResult reorderData : result) {

                                mCartView.addView(new ReorderItems(getApplicationContext(), reorderData.order_id,
                                        reorderData.order_product_id, reorderData.image, reorderData.name,
                                        reorderData.quantity, reorderData.discount_price, reorderData.price,
                                        reorderData.weight_classes, reorderData.revised_price));
                            }

                            if((resourcesReorder.TotalProduct).equals("1"))
                            {
                                tv_total.setText(resourcesReorder.TotalProduct + " " + "Item");
                            }
                            else
                            {
                                tv_total.setText(resourcesReorder.TotalProduct + " " + "Items");

                            }

                            double dbl_Price_2 = Double.parseDouble(resourcesReorder.totalMoney);
                            str_TotalAmnt = String.format("%.2f", dbl_Price_2);
                            tvtotalAmount.setText("Total" + " " + "\u20B9 " + str_TotalAmnt);
                        }
                        else {

                            Toast.makeText(getApplicationContext(), "No items", Toast.LENGTH_LONG).show();
                        }

                    } else if (resourcesReorder.status.equals("failure")) {
                        Toast.makeText(getApplicationContext(), resourcesReorder.message, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ReorderItemsModel> call, Throwable t) {
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

    public void billing(View v)
    {
        if (select_item.equals("Select")) {
            Toast.makeText(getApplicationContext(), "Please Select Delivery Day", Toast.LENGTH_SHORT).show();
        } else {
            Intent myIntent = new Intent(getBaseContext(), billingAddress.class);
            startActivity(myIntent);
        }

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
                Intent infoIntent = new Intent(getBaseContext(), cart.class);
                infoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(infoIntent);
                break;
        }
        return true;
    }

}
