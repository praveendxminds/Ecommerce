package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.ProfileSection.QuantityList;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.AddToCartModel;
import com.nisarga.nisargaveggiez.retrofit.InsertWishListItems;
import com.nisarga.nisargaveggiez.retrofit.ProductDetailsModel;
import com.nisarga.nisargaveggiez.retrofit.SimilarProductsModel;
import com.mindorks.placeholderview.PlaceHolderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.nisarga.nisargaveggiez.cart.cart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sushmita 25/06/2019
 */


public class ProductDetailHome extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tv_title, tv_original_price, tv_productCount, tvDisPricePrdDetail;
    private Spinner qtyPrdDetail;
    APIInterface apiInterface;
    private PlaceHolderView mPlaceHolderView, img_list_PrdDetails;
    private ImageButton imgBtn_decre, imgBtn_incre, btnAddItem;
    private LinearLayout llAddCartItem, llCountItem, ll_similar_prd;
    private Button addtoCartPrdDetail;
    public Button addtoWishListPrdDetail;
    private WebView webViewDesc;
    String sprd_id, sname, sprice, sqty, sDisPrice, sDesc, sWishlistStatus, sAddQtyStatus;
    SessionManager session;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public String callPrdId, sCustId;
    int countVal = 0;
    private String str_priceValue, str_priceValue_1;
    public ImageButton btn_addtoWishlistPrdDetail;
    public boolean status_wish;

    String product_option_id[], product_option_value_id[], product_price[];
    String sQuantitySpinner, option_id, option_value_id, price;
    String productPrice;

    TextView cartcount;

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
        ll_similar_prd = findViewById(R.id.ll_similar_prd);

        final ArrayList<String> product_qty_list = new ArrayList<>();

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final QuantityList quantityList = new QuantityList(callPrdId);

            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<QuantityList> callheight = apiInterface.quantity_list(quantityList);
            callheight.enqueue(new Callback<QuantityList>() {
                @Override
                public void onResponse(Call<QuantityList> callheight, Response<QuantityList> response) {
                    QuantityList eduresource = response.body();
                    List<QuantityList.Datum> datumList = eduresource.data;
                    product_option_id = new String[datumList.size()];
                    product_option_value_id = new String[datumList.size()];
                    product_price = new String[datumList.size()];
                    int i = 0;
                    for (QuantityList.Datum datum : datumList) {
                        product_qty_list.add(datum.name);
                        product_option_id[i] = datum.product_option_id;
                        product_option_value_id[i] = datum.product_option_value_id;
                        product_price[i] = datum.price;
                        i++;
                    }

                    ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(ProductDetailHome.this, R.layout.spinner_item,
                            product_qty_list);
                    qtyPrdDetail.setAdapter(itemsAdapter);
                    qtyPrdDetail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                            sQuantitySpinner = product_qty_list.get(position);
                            option_id = product_option_id[position];
                            option_value_id = product_option_value_id[position];
                            price = product_price[position];

                            double dbl_Price = Double.parseDouble(price);//need to convert string to decimal
                            productPrice = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
                            tv_original_price.setText("₹" + " " + productPrice);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                @Override
                public void onFailure(Call<QuantityList> callheight, Throwable t) {
                    callheight.cancel();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
        }


        mPlaceHolderView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.HORIZONTAL, false));

        apiInterface = APIClient.getClient().create(APIInterface.class);
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            //-----------------------------------------------------------for product details ---------------------------------

            final ProductDetailsModel productDetail = new ProductDetailsModel(session.getCustomerId(), callPrdId);
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
                                } catch (NumberFormatException nfe) {
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
                            img_list_PrdDetails.addView(new ProductDetailsImageSlider(ProductDetailHome.this,
                                    imageArr, addtoWishListPrdDetail, callPrdId, session.getCustomerId()));

                            toolbar.setTitle(sname);
                            tv_title.setText(sname);

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
                    final AddToCartModel ref = new AddToCartModel(callPrdId, sQuantitySpinner, option_id, option_value_id);

                    apiInterface = APIClient.getClient().create(APIInterface.class);
                    Call<AddToCartModel> callAdd = apiInterface.callAddToCart("api/cart/add", session.getToken(), ref);
                    callAdd.enqueue(new Callback<AddToCartModel>() {
                        @Override
                        public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                            AddToCartModel resource = response.body();
                            if (resource.status.equals("success")) {
                                Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddToCartModel> call, Throwable t) {
                            call.cancel();
                        }
                    });
                }
            });

            btnAddItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countVal = countVal + 1;//display number in place of add to cart
                    session.cartcount(countVal);
                    display(countVal);
                    llAddCartItem.setVisibility(android.view.View.GONE);
                    llCountItem.setVisibility(android.view.View.VISIBLE);

                }
            });

            imgBtn_incre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countVal = countVal + 1;//display number in place of add to cart
                    session.cartcount(countVal);
                    display(countVal);
                    tv_productCount.setText(countVal);
                }
            });

            imgBtn_decre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (countVal <= 1) {
                        countVal = countVal - 1;
                        session.cartcount(countVal);
                        display(countVal);
                        tv_productCount.setText(countVal);
                        llAddCartItem.setVisibility(android.view.View.VISIBLE);
                        llCountItem.setVisibility(android.view.View.GONE);

                    } else {
                        countVal = countVal - 1;
                        session.cartcount(countVal);
                        display(countVal);
                        tv_productCount.setText(countVal);
                    }
                }
            });

            //----------------------------------------------------------similar product response---------------------------------------
            final SimilarProductsModel productslSimilarPrd = new SimilarProductsModel(callPrdId, session.getCustomerId());
            Call<SimilarProductsModel> callSimilarProducts = apiInterface.getSimilarProducts(productslSimilarPrd);
            callSimilarProducts.enqueue(new Callback<SimilarProductsModel>() {
                @Override
                public void onResponse(Call<SimilarProductsModel> call, Response<SimilarProductsModel> response) {
                    SimilarProductsModel resource = response.body();
                    if (resource.status.equals("success")) {
                        ll_similar_prd.setVisibility(View.VISIBLE);
                        List<SimilarProductsModel.SimilarPrdDatum> datumList = resource.result;
                        for (SimilarProductsModel.SimilarPrdDatum imgs : datumList) {
                            if (response.isSuccessful()) {
                                mPlaceHolderView.addView(new SimilarProductsListItem(getApplicationContext(), cartcount,
                                        mPlaceHolderView, imgs.related_id, imgs.product_id, imgs.image, imgs.name,
                                        imgs.price, imgs.quantity, imgs.discount_price));
                            }
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
                //------------------------------------------for adding to wishlist-----------------------------
                final InsertWishListItems add_item = new InsertWishListItems(session.getCustomerId(), callPrdId);
                Call<InsertWishListItems> callAdd = apiInterface.addtoWishList(add_item);
                callAdd.enqueue(new Callback<InsertWishListItems>() {
                    @Override
                    public void onResponse(Call<InsertWishListItems> call, Response<InsertWishListItems> response) {
                        InsertWishListItems resource = response.body();
                        if (resource.status.equals("success")) {
                            Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                            addtoWishListPrdDetail.setText("Added in Wishlist");

                            btn_addtoWishlistPrdDetail = findViewById(R.id.btn_addtoWishlistPrdDetail);
                            status_wish = getWishlistStatus();
                            status_wish = true;
                            btn_addtoWishlistPrdDetail.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    btn_addtoWishlistPrdDetail.setBackgroundResource(R.drawable.wishlist_red);
                                    // status_wish = true;
                                    // Do something after 1000 ms
                                }
                            }, 1000);


                        } else {
                            Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<InsertWishListItems> call, Throwable t) {
                        call.cancel();
                    }
                });
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
        cartcount = (TextView) rootView.findViewById(R.id.cart_badge);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Utils.CheckInternetConnection(getApplicationContext())) {
                    //------------------------------------- My profile view section------------------------------------------------
                    Call<CartCount> call = apiInterface.getCartCount("api/cart/cartcount", session.getToken());
                    call.enqueue(new Callback<CartCount>() {
                        @Override
                        public void onResponse(Call<CartCount> call, Response<CartCount> response) {
                            CartCount cartCount = response.body();
                            if (cartCount.status.equals("success")) {
                                cartcount.setText(cartCount.data);
                            }
                        }

                        @Override
                        public void onFailure(Call<CartCount> call, Throwable t) {
                            call.cancel();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }, 500);

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
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.help_menu_item:
                Intent i = new Intent(getApplicationContext(), DeliveryInformation.class);
                startActivity(i);
                break;

            case R.id.cart_menu_item:
                Intent myctIntent = new Intent(getBaseContext(), cart.class);
                startActivity(myctIntent);
                break;
        }
        return true;
    }
}