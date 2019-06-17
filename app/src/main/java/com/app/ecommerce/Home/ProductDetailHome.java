package com.app.ecommerce.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecommerce.R;
import com.app.ecommerce.Utils;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.ProductDetailsModel;
import com.app.ecommerce.retrofit.ProductslHomePage;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.PlaceHolderView;

import q.rorbin.badgeview.QBadgeView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.app.ecommerce.Home3.ImageTypeSmallList;
import com.app.ecommerce.cart.cart;

import java.util.List;

/**
 * Created by praveen on 15/11/18.
 */


public class ProductDetailHome extends AppCompatActivity {

    Toolbar toolbar;
    private ImageView pro_img;
    private TextView title, original_price,qtyGrid;
    APIInterface apiInterface;
    private PlaceHolderView mPlaceHolderView;
    String sname, sprice, sqty, simage;
    int cart_count = 0;

    android.view.View menuItemView;
    public static String MyPREFERENCES = "sessiondata";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_home);
        toolbar = (Toolbar) findViewById(R.id.toolbarGrid);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        String prd_id = getIntent().getExtras().getString("prd_id", "defaultKey");


        pro_img = (ImageView) findViewById(R.id.prd_imgGrid);
        title = (TextView) findViewById(R.id.titleGrid);
        original_price = (TextView) findViewById(R.id.original_priceGrid);
        qtyGrid = findViewById(R.id.qntyGrid);
        mPlaceHolderView = (PlaceHolderView) findViewById(R.id.galleryViewGrid);



        apiInterface = APIClient.getClient().create(APIInterface.class);

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final ProductDetailsModel productDetail = new ProductDetailsModel(prd_id);

            Call<ProductDetailsModel> call = apiInterface.getProductDetails(productDetail);
            call.enqueue(new Callback<ProductDetailsModel>() {
                @Override
                public void onResponse(Call<ProductDetailsModel> call, Response<ProductDetailsModel> response) {
                    ProductDetailsModel productResponse = response.body();
                    List<ProductDetailsModel.Datum> datumList = productResponse.result;
                    for (ProductDetailsModel.Datum imgs : datumList) {
                        if (response.isSuccessful()) {
                            sname = imgs.getName();
                            simage = imgs.getImage();
                            sprice = imgs.getPrice();
                            sqty = imgs.getQuantity();
                            Glide.with(getApplication()).load(simage).into(pro_img);
                            title.setText(sname);
                            original_price.setText(sprice);
                            qtyGrid.setText(sqty);


                        }
                    }


                }

                @Override
                public void onFailure(Call<ProductDetailsModel> call, Throwable t) {
                    call.cancel();
                }
            });


        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }


        // mPlaceHolderView.addView(new ImageTypeSmallList(getApplicationContext(),0));


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cart_menu, menu);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                menuItemView = findViewById(R.id.cart_menu_item);

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                Integer name_session = sharedpreferences.getInt("count", 0);

                new QBadgeView(getApplicationContext()).bindTarget(menuItemView).setBadgeTextColor(getApplicationContext().getResources().getColor(R.color.white)).setGravityOffset(15, -5, true).setBadgeNumber(name_session).setBadgeBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));


            }
        }, 200);


        return true;
    }


    public void buynow(android.view.View v) {
        Intent cartIntent = new Intent(getBaseContext(), cart.class);
        startActivity(cartIntent);
    }


    public void addcart(android.view.View v) {
        // cart_count = cart_count + 1;
        //  countCartDisplay(cart_count);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Integer name_session = sharedpreferences.getInt("count", 0);


        name_session = name_session + 1;

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("count", name_session);
        editor.commit();


        countCartDisplay(name_session);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                finish();
                return true;

            case R.id.cart_menu_item:

                Intent myctIntent = new Intent(getBaseContext(), cart.class);
                startActivity(myctIntent);


                break;
        }
        return true;
    }


    private void countCartDisplay(int number) {


        new QBadgeView(getApplicationContext()).bindTarget(menuItemView).setBadgeTextColor(getApplicationContext().getResources().getColor(R.color.white)).setGravityOffset(15, -5, true).setBadgeNumber(number).setBadgeBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));


    }


}