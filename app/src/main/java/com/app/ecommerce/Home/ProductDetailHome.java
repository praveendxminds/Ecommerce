package com.app.ecommerce.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecommerce.R;
import com.app.ecommerce.Utils;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.InsertWishListItems;
import com.app.ecommerce.retrofit.ProductDetailsModel;
import com.app.ecommerce.retrofit.ProductslHomePage;
import com.app.ecommerce.retrofit.RemoveWishListItem;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.PlaceHolderView;

import q.rorbin.badgeview.QBadgeView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.app.ecommerce.Home3.ImageTypeSmallList;
import com.app.ecommerce.cart.cart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by praveen on 15/11/18.
 */


public class ProductDetailHome extends AppCompatActivity {

    private Toolbar toolbar;
    private PlaceHolderView similar_prd_view;
    private ImageView pro_img;
    private TextView tv_title, tv_original_price, tv_review, tv_points;
    private ImageButton btn_addtoWishlist;
    private Spinner qtyPrdDetail;
    APIInterface apiInterface;
    private PlaceHolderView mPlaceHolderView;
    String sname, sprice, sqty, simage, sreviews, spoints;
    int cart_count = 0;
    public boolean state=true;

    android.view.View menuItemView;
    public static String MyPREFERENCES = "sessiondata";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_home);
        btn_addtoWishlist = findViewById(R.id.btn_addtoWishlistPrdDetail);
        toolbar = (Toolbar) findViewById(R.id.toolbarPrdDetail);
        similar_prd_view = findViewById(R.id.similarPrdDetail);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        final String prd_id = getIntent().getExtras().getString("prd_id", "defaultKey");


        pro_img = (ImageView) findViewById(R.id.prd_imgPrdDetail);
        tv_title = (TextView) findViewById(R.id.titlePrdDetail);
        tv_original_price = (TextView) findViewById(R.id.originalPricePrdDetail);
        qtyPrdDetail = findViewById(R.id.qntyPrdDetail);
        mPlaceHolderView = (PlaceHolderView) findViewById(R.id.similarPrdDetail);
        tv_review = findViewById(R.id.reviewPrdDetail);
        tv_points = findViewById(R.id.ratingsPrdDetail);

        mPlaceHolderView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.HORIZONTAL, false));

        apiInterface = APIClient.getClient().create(APIInterface.class);
        if (Utils.CheckInternetConnection(getApplicationContext())) {
//-------------------------------------------------------------------------add to wishlist--------------------------------------

            btn_addtoWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {


                        final InsertWishListItems add_item = new InsertWishListItems("1", "49");
                        Call<InsertWishListItems> callAdd = apiInterface.addtoWishList(add_item);
                        callAdd.enqueue(new Callback<InsertWishListItems>() {
                            @Override
                            public void onResponse(Call<InsertWishListItems> call, Response<InsertWishListItems> response) {
                                InsertWishListItems resource = response.body();
                                if (resource.status.equals("success")) {
                                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                                    v.setBackgroundResource(R.drawable.add_24dp);

                                }
                                else {
                                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<InsertWishListItems> call, Throwable t) {
                                call.cancel();
                            }
                        });


                       /* final RemoveWishListItem remove_item = new RemoveWishListItem("1", "49");
                        Call<RemoveWishListItem> callRemove = apiInterface.removeWishListItem(remove_item);
                        callRemove.enqueue(new Callback<RemoveWishListItem>() {
                            @Override
                            public void onResponse(Call<RemoveWishListItem> call, Response<RemoveWishListItem> response) {
                                RemoveWishListItem resource = response.body();
                                if (resource.status.equals("success")) {
                                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                                    mPlaceHolderView.removeView(this);
                                    v.setBackgroundResource(R.drawable.remove_24dp);
                                } else {
                                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<RemoveWishListItem> call, Throwable t) {
                                call.cancel();
                            }
                        });


                    }*/

                }

            });
            //-----------------------------------------------------------for product details ---------------------------------
            final ProductDetailsModel productDetail = new ProductDetailsModel("42");

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
                            spoints = imgs.getPoints();
                            sreviews = imgs.getReviews();

                            Glide.with(getApplication()).load(simage).into(pro_img);
                            tv_title.setText(sname);
                            tv_original_price.setText(sprice);
                            tv_points.setText(spoints + " " + "Ratings");
                            tv_review.setText(sreviews);
                            //qtyPrdDetail.setAdapter(sqty);

                        }
                    }
                }

                @Override
                public void onFailure(Call<ProductDetailsModel> call, Throwable t) {
                    call.cancel();
                }
            });
            //-------------------------------------------------------------------------------------------------------------------------
            //----------------------------------------------------------similar product response---------------------------------------

            Call<ProductslHomePage> callSimilarProducts = apiInterface.getHomePageProducts();
            callSimilarProducts.enqueue(new Callback<ProductslHomePage>() {
                @Override
                public void onResponse(Call<ProductslHomePage> call, Response<ProductslHomePage> response) {

                    ProductslHomePage resource = response.body();
                    List<ProductslHomePage.DealOfDayList> datumList = resource.data;

                    for (ProductslHomePage.DealOfDayList imgs : datumList) {
                        if (response.isSuccessful()) {

                            mPlaceHolderView.addView(new HomeCategoryItemGridView(getApplicationContext(), imgs.prd_id, imgs.image, imgs.name, imgs.price));

                        }
                    }

                }

                @Override
                public void onFailure(Call<ProductslHomePage> call, Throwable t) {
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