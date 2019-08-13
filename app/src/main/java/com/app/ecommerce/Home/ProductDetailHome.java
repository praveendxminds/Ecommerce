package com.app.ecommerce.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.app.ecommerce.retrofit.SimilarProductsModel;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.PlaceHolderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.app.ecommerce.cart.cart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by sushmita 25/06/2019
 */


public class ProductDetailHome extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView pro_img;
    private TextView tv_title, tv_original_price, tv_productCount, tvDisPricePrdDetail;
    private Spinner qtyPrdDetail;
    APIInterface apiInterface;
    private PlaceHolderView mPlaceHolderView, img_list_PrdDetails;
    private ImageButton imgBtn_decre, imgBtn_incre, btnAddItem;
    private LinearLayout llAddCartItem, llCountItem;
    private Button addtoWishListPrdDetail, addtoCartPrdDetail;
    private WebView webViewDesc;
    String sprd_id, getPrd_id, sname, sprice, sqty, simage, sreviews, spoints, sDisPrice, sDesc, sWishlistStatus, sAddQtyStatus;
    String[] qtyArray = {"qty", "100gm", "200gm", "300gm", "50gm", "500gm", "1kg"};
    SessionManager session;
    public static TextView mtextCartItemCount;
    int minteger = 0;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public String callPrdId, sCustId;
    private boolean state = false;
    private boolean status1 = true;
    int countVal = 0;
    private boolean cartStatus = true;
    private String str_priceValue, str_priceValue_1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_home);

        session = new SessionManager(getApplicationContext());
        callPrdId = getIntent().getStringExtra("product_id");
        sCustId = session.getCustomerId();
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        img_list_PrdDetails = findViewById(R.id.img_list_PrdDetails);
        toolbar = (Toolbar) findViewById(R.id.toolbarPrdDetail);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        tv_title = (TextView) findViewById(R.id.titlePrdDetail);
        tv_original_price = (TextView) findViewById(R.id.originalPricePrdDetail);
        tvDisPricePrdDetail = findViewById(R.id.tvDisPricePrdDetail);
        qtyPrdDetail = findViewById(R.id.qntyPrdDetail);
        mPlaceHolderView = (PlaceHolderView) findViewById(R.id.similarPrdDetail);
        llAddCartItem = findViewById(R.id.llAddToCart);
        llCountItem = findViewById(R.id.llCountItems);
        imgBtn_decre = findViewById(R.id.imgBtn_decre);
        imgBtn_incre = findViewById(R.id.imgBtn_incre);
        btnAddItem = findViewById(R.id.btnAddItem);
        tv_productCount = findViewById(R.id.tv_productCount);
        addtoWishListPrdDetail = findViewById(R.id.addtoWishListPrdDetail);
        addtoCartPrdDetail = findViewById(R.id.addtoCartPrdDetail);
        webViewDesc = findViewById(R.id.webViewDesc);

        mPlaceHolderView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.HORIZONTAL, false));

        apiInterface = APIClient.getClient().create(APIInterface.class);
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            //-----------------------------------------------------------for product details ---------------------------------

            final ProductDetailsModel productDetail = new ProductDetailsModel("1", callPrdId);
            Call<ProductDetailsModel> call = apiInterface.getProductDetails(productDetail);
            call.enqueue(new Callback<ProductDetailsModel>() {
                @Override
                public void onResponse(Call<ProductDetailsModel> call, Response<ProductDetailsModel> response) {
                    ProductDetailsModel productResponse = response.body();
                    List<String> imageArr = new ArrayList<String>();
                    List<ProductDetailsModel.Datum> datumList = productResponse.result;
                    for (ProductDetailsModel.Datum imgs : datumList) {
                        if (response.isSuccessful()) {
                            sprd_id = imgs.getProduct_id();
                            sname = imgs.getName();
                            sprice = imgs.getPrice();
                            sDisPrice = imgs.getDiscount();
                            sqty = imgs.getQuantity();
                            sDesc = imgs.getDesc();
                            sWishlistStatus = imgs.getWishlistStatus();
                            sAddQtyStatus = imgs.getAddPrdQty();
                            if (sWishlistStatus.equals("0")) {
                                addToWishList();
                            } else {
                               /* Intent intentAddedWishlist = new Intent(getBaseContext(),ProductDetailsImageSlider.class);
                                intentAddedWishlist.putExtra("wishlist_status","1");*/
                                getWishlistStatus();
                            }
                            if (sAddQtyStatus.equals("0")) {
                                llAddCartItem.setVisibility(android.view.View.VISIBLE);
                                llCountItem.setVisibility(android.view.View.GONE);
                            } else {
                                llAddCartItem.setVisibility(android.view.View.GONE);
                                llCountItem.setVisibility(android.view.View.VISIBLE);
                                try {
                                    countVal = Integer.parseInt(sAddQtyStatus);
                                } catch(NumberFormatException nfe) {
                                    System.out.println("Could not parse " + nfe);
                                }
                                display(countVal);
                            }

                            List<ProductDetailsModel.Datum.ImageArr> imageList = imgs.image;
                            for (ProductDetailsModel.Datum.ImageArr imgSlide : imageList) {
                                if (response.isSuccessful()) {
                                    imageArr.add(imgSlide.getImage());
                                }
                            }
                            img_list_PrdDetails.addView(new ProductDetailsImageSlider(getApplicationContext(), imageArr));

                            toolbar.setTitle(sname);
                            tv_title.setText(sname);

                            double dbl_Price = Double.parseDouble(sprice);//need to convert string to decimal
                            str_priceValue = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
                            tv_original_price.setText("₹" + " " + str_priceValue);

                            if (sDisPrice.equals("null")) {
                                tvDisPricePrdDetail.setVisibility(View.INVISIBLE);
                            } else {
                                double dbl_Price1 = Double.parseDouble(sDisPrice);//need to convert string to decimal
                                str_priceValue_1 = String.format("%.2f", dbl_Price1);//display only 2 decimal places of price
                                tvDisPricePrdDetail.setVisibility(android.view.View.VISIBLE);
                                tvDisPricePrdDetail.setText("₹" + " " + str_priceValue_1);
                            }
                            String res = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                res = Html.fromHtml(sDesc, Html.FROM_HTML_MODE_COMPACT).toString();
                            } else {
                                res = Html.fromHtml(sDesc).toString();
                            }
                            webViewDesc.loadDataWithBaseURL(null, res, "text/html", "utf-8", null);
                            qtyArray[0] = sqty;
                            final List<String> qtyList = new ArrayList<>(Arrays.asList(qtyArray));
                            //adding qty to spinner and adding response value as a first value
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spnr_listitem_categ, qtyList);
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_product_qtylist_home_two);
                            qtyPrdDetail.setAdapter(spinnerArrayAdapter);

                        }
                    }

                }

                @Override
                public void onFailure(Call<ProductDetailsModel> call, Throwable t) {
                    call.cancel();
                }
            });
            //------------------------------------------------------------number of products-------------------------------------------------------------
            addtoCartPrdDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cartStatus == true) {
                        countVal = countVal + 1;//display number in place of add to cart
                        Integer cnt = session.getCartCount();
                        cnt = cnt + 1;//display number in cart icon
                        session.cartcount(cnt);
                        display(countVal);
                        mtextCartItemCount.setText(String.valueOf(cnt));
                        addtoCartPrdDetail.setText("Added to Cart");
                        Toast.makeText(getApplicationContext(), "Added to cart successfully", Toast.LENGTH_LONG).show();
                        cartStatus = false;
                    } else {
                        countVal = countVal - 1;//display number in place of add to cart
                        Integer cnt = session.getCartCount();
                        cnt = cnt - 1;//display number in cart icon
                        session.cartcount(cnt);
                        display(countVal);
                        mtextCartItemCount.setText(String.valueOf(cnt));
                        addtoCartPrdDetail.setText("Add to Cart");
                        Toast.makeText(getApplicationContext(), "Removed from cart successfully", Toast.LENGTH_LONG).show();
                        cartStatus = true;
                    }
                }
            });

            btnAddItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (status1 == true) {
                        Integer cnt = session.getCartCount();
                        countVal = countVal + 1;//display number in place of add to cart
                        cnt = cnt + 1;//display number in cart icon
                        session.cartcount(cnt);
                        display(countVal);
                        mtextCartItemCount.setText(String.valueOf(cnt));
                        llAddCartItem.setVisibility(android.view.View.GONE);
                        llCountItem.setVisibility(android.view.View.VISIBLE);
                        status1 = false;
                    }
                }
            });

            imgBtn_incre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countVal = countVal + 1;//display number in place of add to cart
                    Integer cnt = session.getCartCount();
                    cnt = cnt + 1;//display number in cart icon
                    session.cartcount(cnt);
                    display(countVal);
                    mtextCartItemCount.setText(String.valueOf(cnt));
                }
            });
            imgBtn_decre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (countVal <= 1) {
                        countVal = countVal - 1;
                        Integer cnt = session.getCartCount();
                        cnt = cnt - 1;
                        session.cartcount(cnt);
                        display(countVal);
                        mtextCartItemCount.setText(String.valueOf(cnt));
                        llAddCartItem.setVisibility(android.view.View.VISIBLE);
                        llCountItem.setVisibility(android.view.View.GONE);
                        status1 = true;

                    } else {
                        countVal = countVal - 1;
                        Integer cnt = session.getCartCount();
                        cnt = cnt - 1;
                        session.cartcount(cnt);
                        display(countVal);
                        mtextCartItemCount.setText(String.valueOf(cnt));
                    }
                }
            });


            //----------------------------------------------------------similar product response---------------------------------------
            // getPrd_id = sharedpreferences.getString("product_id", "");
            final SimilarProductsModel productslSimilarPrd = new SimilarProductsModel(callPrdId);
            // Log.e("----------similar_products_prd_id--------",sprd_id);
            Call<SimilarProductsModel> callSimilarProducts = apiInterface.getSimilarProducts(productslSimilarPrd);
            callSimilarProducts.enqueue(new Callback<SimilarProductsModel>() {
                @Override
                public void onResponse(Call<SimilarProductsModel> call, Response<SimilarProductsModel> response) {

                    SimilarProductsModel resource = response.body();
                    List<SimilarProductsModel.SimilarPrdDatum> datumList = resource.result;
                    for (SimilarProductsModel.SimilarPrdDatum imgs : datumList) {
                        if (response.isSuccessful()) {

                            mPlaceHolderView.addView(new SimilarProductsListItem(getApplicationContext(), mtextCartItemCount,
                                    mPlaceHolderView, imgs.related_id, imgs.product_id, imgs.image, imgs.name, imgs.price, imgs.quantity));
                        }
                    }

                }

                @Override
                public void onFailure(Call<SimilarProductsModel> call, Throwable t) {
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
        addToWishList();
    }

    public void addToWishList() {
        addtoWishListPrdDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == false) {
                    //------------------------------------------for adding to wishlist-----------------------------
                    final InsertWishListItems add_item = new InsertWishListItems("1", callPrdId);
                    Call<InsertWishListItems> callAdd = apiInterface.addtoWishList(add_item);
                    callAdd.enqueue(new Callback<InsertWishListItems>() {
                        @Override
                        public void onResponse(Call<InsertWishListItems> call, Response<InsertWishListItems> response) {
                            InsertWishListItems resource = response.body();
                            if (resource.status.equals("success")) {
                                Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                                addtoWishListPrdDetail.setText("Added in Wishlist");

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
                    final RemoveWishListItem remove_item = new RemoveWishListItem("1", callPrdId);
                    Call<RemoveWishListItem> callRemove = apiInterface.removeWishListItem(remove_item);
                    callRemove.enqueue(new Callback<RemoveWishListItem>() {
                        @Override
                        public void onResponse(Call<RemoveWishListItem> call, Response<RemoveWishListItem> response) {
                            RemoveWishListItem resource = response.body();
                            if (resource.status.equals("success")) {
                                Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                                addtoWishListPrdDetail.setText("Add to Wishlist");
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
        });
    }

    public void display(int number) {
        tv_productCount.setText("" + number);
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

        MenuItem cart_menuItem = menu.findItem(R.id.cart_menu_item);
        FrameLayout rootView = (FrameLayout) cart_menuItem.getActionView();
        mtextCartItemCount = (TextView) rootView.findViewById(R.id.cart_badge);

        Integer cnt = session.getCartCount();
        mtextCartItemCount.setText(String.valueOf(cnt));

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent DeliveryIntent = new Intent(getBaseContext(), cart.class);
                DeliveryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(DeliveryIntent);

            }
        });
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

    public boolean getWishlistStatus() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
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