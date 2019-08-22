package com.nisarga.nisargaveggiez.cart;

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

import com.nisarga.nisargaveggiez.Home.HomePage;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.notifications.MyNotifications;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.mindorks.placeholderview.PlaceHolderView;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.billing.billingAddress;
import com.nisarga.nisargaveggiez.retrofit.CartListModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sushmita on 25/06/2019
 */


public class cart extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    private PlaceHolderView mCartView;
    public static String MyPREFERENCES = "sessiondata";
    SharedPreferences sharedpreferences;
    TextView linkDeliveryDay, tvtotalAmount,tv_total;
    private String storeDayTime;
    SessionManager session;

    APIInterface apiInterface;
    public static TextView textCartItemCount;
    public String slct_itm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
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
                Intent pIntent = new Intent(getBaseContext(), HomePage.class);
                startActivity(pIntent);
            }
        });

        //-----delivery day link------------------
        linkDeliveryDay = (TextView) findViewById(R.id.tvDeliveryDay);
        tvtotalAmount = (TextView) findViewById(R.id.tvtotalAmount);
        tv_total = (TextView) findViewById(R.id.tv_total);
        SpannableString spannable = new SpannableString("Delivery Day");
        spannable.setSpan(new UnderlineSpan(), 0, spannable.length(), 0);
        linkDeliveryDay.setText(spannable);

        linkDeliveryDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {



                final List<String> categories = new ArrayList<String>();
                final List<String> categories_dtes = new ArrayList<String>();
                categories.add("Select");
                categories_dtes.add("Select");

                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
                for (int i = 1; i < 4; i++)
                {
                    Calendar calendars = new GregorianCalendar();
                    calendars.add(Calendar.DAY_OF_WEEK, i);
                    String catdays = sdf.format(calendars.getTime());
                    String days = sdf1.format(calendars.getTime());
                    Log.i("daysddddds", days);
                    categories.add(catdays);
                    categories_dtes.add(days);
                }



                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.delivery_time_popup, null);
                final TextView deliveryTimeDialog = alertLayout.findViewById(R.id.deliveryTimeDialog);
                //----day spinner--------
                final Spinner dayspinner = alertLayout.findViewById(R.id.dayspinner);
                final Button schedule = alertLayout.findViewById(R.id.btnSchedule);
               // final SpinnerAdapter adapterDay = new SpinnerAdapter(cart.this, android.R.layout.simple_list_item_1);
              //  adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //adapterDay.addAll(categories);
               // adapterDay.add("Select Day");
               // dayspinner.setAdapter(adapterDay);

                dayspinner.setAdapter(new DeliverydateAdapter(getApplicationContext(),categories , categories));


                dayspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        Toast.makeText(getApplicationContext(), categories.get(position), Toast.LENGTH_LONG).show();
                        session.setDeliverydate(categories_dtes.get(position));
                        session.setDeliveryweek(categories.get(position));
                        slct_itm = categories.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent)
                    {

                    }
                });

               // dayspinner.setSelection(adapterDay.getCount());


//                dayspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//                {
//
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//                    {
//                        if (dayspinner.getSelectedItem() == "Select")
//                        {
//                            Toast.makeText(cart.this, "you have selected nothing", Toast.LENGTH_LONG).show();
//                        }
//                        else
//                        {
//                            session.setDeliverydate(dayspinner.getSelectedItem().toString());
//                            Log.e("-----day selected-----", "----day selected---");
//                            Toast.makeText(cart.this, dayspinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent)
//                    {
//
//                    }
//                });


                final AlertDialog alertDialog = new AlertDialog.Builder(cart.this).create();
                schedule.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //storeDayTime = dayspinner.getSelectedItem().toString();
                        storeDayTime = slct_itm;
                        alertDialog.dismiss();
                        linkDeliveryDay.setText(storeDayTime);
                    }
                });
                alertDialog.setView(alertLayout);
                alertDialog.show();
            }
        });

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mCartView = (PlaceHolderView) findViewById(R.id.recycler_cart);
//        for (int i = 0; i <= 10; i++) {
//            mCartView.addView(new cartItem(getApplicationContext()));
//        }
        /*  mCartView.addView(new cartItem_footer());*/
        showListView();
    }

    public void showListView() {
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            //final CartListModel cartListModel = new CartListModel("api/cart/products","ea37ddb9108acd601b295e26fa");

            Log.d("getToken", String.valueOf(session.getToken()));


            Call<CartListModel> call = apiInterface.getCartList("api/cart/products", session.getToken());
            call.enqueue(new Callback<CartListModel>() {
                @Override
                public void onResponse(Call<CartListModel> call, Response<CartListModel> response) {
                    CartListModel resource = response.body();
                    List<CartListModel.CartListDatum> datumList = resource.result;

                    Log.d("items",String.valueOf(datumList) );

                    tv_total.setText(String.valueOf(datumList.size())+" Items");

                    for (CartListModel.CartListDatum imgs : datumList)
                    {
                        if (response.isSuccessful())
                        {

                            mCartView.addView(new cartItem(getApplicationContext(), textCartItemCount,
                                    session.getCustomerId(), imgs.product_id, imgs.image, imgs.name,
                                    imgs.price, imgs.discount_price, imgs.quantity, mCartView));
                            tvtotalAmount.setText("Total"+" "+"\u20B9 "+imgs.total);

                        }
                    }

                    mCartView.sort(new Comparator<Object>() {
                        @Override
                        public int compare(Object item1, Object item2) {
                            if (item1 instanceof cartItem && item2 instanceof cartItem) {
                                cartItem view1 = (cartItem) item1;
                                cartItem view2 = (cartItem) item2;
                                return view1.getTitle().compareTo(view2.getTitle());
                            }
                            return 0;
                        }
                    });



                    mCartView.refresh();
                }

                @Override
                public void onFailure(Call<CartListModel> call, Throwable t) {
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