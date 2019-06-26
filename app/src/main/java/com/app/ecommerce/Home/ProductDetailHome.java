package com.app.ecommerce.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
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
import com.app.ecommerce.SessionManager;
import com.app.ecommerce.Utils;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.InsertWishListItems;
import com.app.ecommerce.retrofit.ProductDetailsModel;
import com.app.ecommerce.retrofit.ProductslHomePage;
import com.app.ecommerce.retrofit.RemoveWishListItem;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.PlaceHolderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.app.ecommerce.cart.cart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sushmita 25/06/2019
 */


public class ProductDetailHome extends AppCompatActivity {

    private Toolbar toolbar;
    private PlaceHolderView similar_prd_view;
    private ImageView pro_img;
    private TextView tv_title, tv_original_price, tv_review, tv_points;
    private ImageButton btn_addtoWishlist;
    private Spinner qtyPrdDetail;
    APIInterface apiInterface;
    private PlaceHolderView mPlaceHolderView,img_list_PrdDetails;
    private ViewPager viewPagerProductDetail;
    private TabLayout tabLayoutProductDetail;
    String sname, sprice, sqty, simage, sreviews, spoints;
    int cart_count = 0;
    public boolean state = true;
    SessionManager session;

    android.view.View menuItemView;

    //--------------------
    ImageFragmentPagerAdapter imageFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_home);
        //btn_addtoWishlist = findViewById(R.id.btn_addtoWishlistPrdDetail);
        img_list_PrdDetails=findViewById(R.id.img_list_PrdDetails);
        toolbar = (Toolbar) findViewById(R.id.toolbarPrdDetail);
        similar_prd_view = findViewById(R.id.similarPrdDetail);
        tabLayoutProductDetail = findViewById(R.id.tabLayoutProductDetail);
        viewPagerProductDetail= findViewById(R.id.viewPagerProductDetail);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        final String prd_id = getIntent().getExtras().getString("prd_id", "defaultKey");


        //pro_img = (ImageView) findViewById(R.id.prd_imgPrdDetail);
        tv_title = (TextView) findViewById(R.id.titlePrdDetail);
        tv_original_price = (TextView) findViewById(R.id.originalPricePrdDetail);
        qtyPrdDetail = findViewById(R.id.qntyPrdDetail);
        mPlaceHolderView = (PlaceHolderView) findViewById(R.id.similarPrdDetail);
        tv_review = findViewById(R.id.reviewPrdDetail);
        tv_points = findViewById(R.id.ratingsPrdDetail);

        session = new SessionManager(getApplicationContext());

        mPlaceHolderView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.HORIZONTAL, false));

        apiInterface = APIClient.getClient().create(APIInterface.class);
        if (Utils.CheckInternetConnection(getApplicationContext())) {
//-------------------------------------------------------------------------add to wishlist--------------------------------------

            //------------------------imageview_product----------------
            String[] imgarray ={"https://www.gstatic.com/webp/gallery3/1.png",
                    "https://www.gstatic.com/webp/gallery3/2.png",
                    "https://www.gstatic.com/webp/gallery3/4.png"};
            img_list_PrdDetails.addView(new ProductDetailsImageSlider(getApplicationContext(),imgarray));
            //---------------------------------------------------------
          /*  btn_addtoWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (state == false) {
                        //------------------------------------------for adding to wishlist-----------------------------
                        final InsertWishListItems add_item = new InsertWishListItems("1", "46");
                        Call<InsertWishListItems> callAdd = apiInterface.addtoWishList(add_item);
                        callAdd.enqueue(new Callback<InsertWishListItems>() {
                            @Override
                            public void onResponse(Call<InsertWishListItems> call, Response<InsertWishListItems> response) {
                                InsertWishListItems resource = response.body();
                                if (resource.status.equals("success")) {
                                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                                    v.setBackgroundResource(R.drawable.like);

                                } else {
                                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<InsertWishListItems> call, Throwable t) {
                                call.cancel();
                            }
                        });
                        state = true;
                    } else {
                        //---------------------for removing from wishlist---------------------------
                        final RemoveWishListItem remove_item = new RemoveWishListItem("1", "46");
                        Call<RemoveWishListItem> callRemove = apiInterface.removeWishListItem(remove_item);
                        callRemove.enqueue(new Callback<RemoveWishListItem>() {
                            @Override
                            public void onResponse(Call<RemoveWishListItem> call, Response<RemoveWishListItem> response) {
                                RemoveWishListItem resource = response.body();
                                if (resource.status.equals("success")) {
                                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                                    mPlaceHolderView.removeView(this);
                                    v.setBackgroundResource(R.drawable.addwishlist);
                                } else {
                                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<RemoveWishListItem> call, Throwable t) {
                                call.cancel();
                            }
                        });
                        state = false;

                    }

                }

            });*/
            //-----------------------------------------------------------for product details ---------------------------------


            final ProductDetailsModel productDetail = new ProductDetailsModel("46");
            Call<ProductDetailsModel> call = apiInterface.getProductDetails(productDetail);
            call.enqueue(new Callback<ProductDetailsModel>() {
                @Override
                public void onResponse(Call<ProductDetailsModel> call, Response<ProductDetailsModel> response) {
                    ProductDetailsModel productResponse = response.body();
                    List<ProductDetailsModel.Datum> datumList = productResponse.result;
                    for (ProductDetailsModel.Datum imgs : datumList) {
                        if (response.isSuccessful()) {

                           // sname = imgs.getName();
                            simage = imgs.getImage();
                            sprice = imgs.getPrice();
                            sqty = imgs.getQuantity();
                            spoints = imgs.getPoints();
                            sreviews = imgs.getReviews();

                               // Glide.with(getApplication()).load(sname).into(pro_img);

                            tv_title.setText(sname);
                            tv_original_price.setText("₹"+" "+sprice);
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
            final ProductslHomePage productslHomePage = new ProductslHomePage("7");
            Call<ProductslHomePage> callSimilarProducts = apiInterface.getHomePageProducts(productslHomePage);
            callSimilarProducts.enqueue(new Callback<ProductslHomePage>() {
                @Override
                public void onResponse(Call<ProductslHomePage> call, Response<ProductslHomePage> response) {

                    ProductslHomePage resource = response.body();
                    List<ProductslHomePage.DealOfDayList> datumList = resource.dealoftheday;

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
        //----tbfragment about and benefits-----
        tabLayoutProductDetail.addTab(tabLayoutProductDetail.newTab().setText("About"));
        tabLayoutProductDetail.addTab(tabLayoutProductDetail.newTab().setText("Benefits"));

        tabLayoutProductDetail.setTabMode(TabLayout.MODE_SCROLLABLE);

        final TabAdapterProductDetail adapter = new TabAdapterProductDetail(getSupportFragmentManager(), tabLayoutProductDetail.getTabCount());
        viewPagerProductDetail.setAdapter(adapter);
        viewPagerProductDetail.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutProductDetail));
        tabLayoutProductDetail.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerProductDetail.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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


        return true;
    }


    public void buynow(android.view.View v) {
        Intent cartIntent = new Intent(getBaseContext(), cart.class);
        startActivity(cartIntent);
    }


    public void addcart(android.view.View v) {
        Integer cnt = session.getCartCount();
        cnt = cnt + 1;
        session.cartcount(cnt);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                finish();
                return true;

            case R.id.help_menu_item:


                break;

            case R.id.cart_menu_item:

                Intent myctIntent = new Intent(getBaseContext(), cart.class);
                startActivity(myctIntent);


                break;
        }
        return true;
    }


}