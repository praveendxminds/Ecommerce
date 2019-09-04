package com.nisarga.nisargaveggiez.Home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.ProfileSection.QuantityList;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.AddCartNullSpinner;
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

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.nisarga.nisargaveggiez.Home.ProductDetailsImageSlider.btn_addtoWishlistPrdDetail;

/**
 * Created by sushmita 25/06/2019
 */

public class ProductDetailHome extends AppCompatActivity {

    APIInterface apiInterface;
    SessionManager session;

    Toolbar toolbar;
    PlaceHolderView phvSingleProductImage, phvSimilarProduct;
    TextView tvTitle, tvPrdName, tvNewPrice, tvOldPrice, tvProductCount, tvQuantityList;
    Spinner spQuantity;
    LinearLayout llAddQuantity, llProductCount, llSimilarProduct, lldecreasePrdCount, llincreasePrdCount, llspinnerlayout;
    ImageButton ivbtnAddItem;
    WebView webViewDesc;
    Button btnAddCart;

    String sadd_product_quantity_in_cart;

    public static Button btnAddWishlist;

    String sPrd_id, sname, sDisPrice, sDesc, sWishlistStatus, sAddQtyStatus;

    int countVal = 0;

    //String product_option_id[], product_option_value_id[], product_price[], product_price_dis[],disqty_ar[];
    //  String sQuantitySpinner, option_id, option_value_id, price, disPrice,disqty;

    String quantity_name, option_id, option_value_id, scartcount, price, sDiscount;

    public static TextView cartcount;

    ArrayList<String> putcntlst = new ArrayList<>();
    TinyDB tinydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_home);

        session = new SessionManager(getApplicationContext());

        sPrd_id = getIntent().getExtras().getString("product_id", "defaultKey");

        toolbar = (Toolbar) findViewById(R.id.toolbarSingleProduct);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tvTitle = findViewById(R.id.tvTitle);

        phvSingleProductImage = findViewById(R.id.phvSingleProductImage);
        tvPrdName = (TextView) findViewById(R.id.tvPrdName);
        spQuantity = findViewById(R.id.spQuantity);
        tvNewPrice = findViewById(R.id.tvNewPrice);
        tvOldPrice = findViewById(R.id.tvOldPrice);
        tvQuantityList = findViewById(R.id.tvQuantityList);

        llAddQuantity = findViewById(R.id.llAddQuantity);
        ivbtnAddItem = findViewById(R.id.ivbtnAddItem);

        llProductCount = findViewById(R.id.llProductCount);
        lldecreasePrdCount = findViewById(R.id.lldecreasePrdCount);
        tvProductCount = findViewById(R.id.tvProductCount);
        llincreasePrdCount = findViewById(R.id.llincreasePrdCount);
        llspinnerlayout = findViewById(R.id.llspinnerlayout);

        llSimilarProduct = findViewById(R.id.llSimilarProduct);
        phvSimilarProduct = findViewById(R.id.phvSimilarProduct);

        webViewDesc = findViewById(R.id.webViewDesc);

        btnAddWishlist = findViewById(R.id.btnAddWishlist);
        btnAddCart = findViewById(R.id.btnAddCart);
        btnAddCart.setEnabled(false);

        final ArrayList<String> product_qty_list = new ArrayList<>();
        final ArrayList<String> disqty_arrr = new ArrayList<>();

        final ArrayList<String> cart_count = new ArrayList<>();
        final ArrayList<String> product_option_id = new ArrayList<>();
        final ArrayList<String> product_option_value_id = new ArrayList<>();
        final ArrayList<String> name = new ArrayList<>();
        final ArrayList<String> product_price = new ArrayList<>();
        final ArrayList<String> discount_price = new ArrayList<>();


        phvSimilarProduct.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.HORIZONTAL, false));

        apiInterface = APIClient.getClient().create(APIInterface.class);
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            //-----------------------------------------------------------for product details ---------------------------------
            final ProductDetailsModel productDetail = new ProductDetailsModel(session.getCustomerId(), sPrd_id);
            Call<ProductDetailsModel> call = apiInterface.getProductDetails(productDetail);
            call.enqueue(new Callback<ProductDetailsModel>() {
                @Override
                public void onResponse(Call<ProductDetailsModel> call, Response<ProductDetailsModel> response) {
                    ProductDetailsModel productResponse = response.body();
                    List<String> imageArr = new ArrayList<>();
                    List<ProductDetailsModel.Datum> datumList = productResponse.result;
                    for (ProductDetailsModel.Datum imgs : datumList) {
                        if (response.isSuccessful()) {
                            String product_id = imgs.product_id;
                            sname = imgs.name;
                            sWishlistStatus = imgs.wishlist_status;
                            sAddQtyStatus = imgs.add_prd_cart;
                            sDisPrice = imgs.discount_price;
                            sDesc = imgs.desc;

                            List<ProductDetailsModel.Datum.ImageArr> imageList = imgs.image;
                            for (ProductDetailsModel.Datum.ImageArr imgSlide : imageList) {
                                if (response.isSuccessful()) {
                                    imageArr.add(imgSlide.image_1);
                                }
                            }
                            phvSingleProductImage.addView(new ProductDetailsImageSlider(ProductDetailHome.this,
                                    imageArr, sWishlistStatus, sPrd_id));

                            tvTitle.setText(sname);
                            tvPrdName.setText(sname);

                            Log.d("weight_classes", String.valueOf(imgs.weight_classes));
                            Log.d("weight_classes", String.valueOf(imgs.weight_classes));

                            final ArrayList<String> cart_count = new ArrayList<>();
                            final ArrayList<String> product_option_id = new ArrayList<>();
                            final ArrayList<String> product_option_value_id = new ArrayList<>();
                            final ArrayList<String> name = new ArrayList<>();
                            final ArrayList<String> product_price = new ArrayList<>();
                            final ArrayList<String> discount_price = new ArrayList<>();
                            tinydb = new TinyDB(getApplicationContext());

                            if ((imgs.weight_classes.equals("null")) && (imgs.options.equals("null"))) {
                                llspinnerlayout.setVisibility(View.GONE);
                                tvQuantityList.setVisibility(View.VISIBLE);
                                tvNewPrice.setText("₹" + " " + imgs.price);
                                tvOldPrice.setText("₹" + " " + imgs.discount_price);
                                tvProductCount.setText(imgs.add_prd_cart);
                                sadd_product_quantity_in_cart = imgs.add_prd_cart;

                            } else if ((!imgs.weight_classes.equals("null")) && (imgs.options.equals("null"))) {

                                JsonArray jsonElements = (JsonArray) new Gson().toJsonTree(imgs.weight_classes);

                                for (int j = 0; j < jsonElements.size(); j++) {
                                    cart_count.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("cart_count")).replace("\"", ""));
                                    product_option_id.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("product_option_id")).replace("\"", ""));
                                    product_option_value_id.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("product_option_value_id")).replace("\"", ""));
                                    name.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("name")).replace("\"", ""));
                                    product_price.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("price")));
                                    discount_price.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("discount_price")));

                                    putcntlst.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("cart_count")).replace("\"", ""));


                                    double dbl_Price = Double.parseDouble(String.valueOf(product_price.get(0)));//need to convert string to decimal
                                    String productPrice = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
                                    tvNewPrice.setText("₹" + " " + productPrice);

                                    tvProductCount.setText(String.valueOf(jsonElements.get(0).getAsJsonObject().get("cart_count")).replace("\"", ""));

                                    if (discount_price.equals("0")) {
                                        tvOldPrice.setVisibility(android.view.View.INVISIBLE);
                                    } else {
                                        double dbl_Discount = Double.parseDouble(String.valueOf(discount_price.get(0)));//need to convert string to decimal
                                        String str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
                                        tvOldPrice.setVisibility(android.view.View.VISIBLE);
                                        tvOldPrice.setText("₹" + " " + str_disValue);
                                    }
                                }


                            } else if ((imgs.weight_classes.equals("null")) && (!imgs.options.equals("null"))) {


                                JsonArray jsonElements = (JsonArray) new Gson().toJsonTree(imgs.options);

                                for (int j = 0; j < jsonElements.size(); j++) {
                                    cart_count.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("cart_count")).replace("\"", ""));
                                    product_option_id.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("product_option_id")).replace("\"", ""));
                                    product_option_value_id.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("product_option_value_id")).replace("\"", ""));
                                    name.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("name")).replace("\"", ""));
                                    product_price.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("price")));
                                    discount_price.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("discount_price")));

                                    putcntlst.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("cart_count")).replace("\"", ""));


                                    double dbl_Price = Double.parseDouble(String.valueOf(product_price.get(0)));//need to convert string to decimal
                                    String productPrice = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
                                    tvNewPrice.setText("₹" + " " + productPrice);

                                    tvProductCount.setText(String.valueOf(jsonElements.get(0).getAsJsonObject().get("cart_count")).replace("\"", ""));


                                    if (discount_price.equals("0")) {
                                        tvOldPrice.setVisibility(android.view.View.INVISIBLE);
                                    } else {
                                        double dbl_Discount = Double.parseDouble(String.valueOf(discount_price.get(0)));//need to convert string to decimal
                                        String str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
                                        tvOldPrice.setVisibility(android.view.View.VISIBLE);
                                        tvOldPrice.setText("₹" + " " + str_disValue);
                                    }


                                }


                            } else {

                            }


                            spQuantity.setAdapter(new QtyspinnerAdapter(getApplicationContext(), name, product_option_id,
                                    product_option_value_id, cart_count, product_price, discount_price));
                            spQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                                    scartcount = cart_count.get(position);
                                    option_id = product_option_id.get(position);
                                    option_value_id = product_option_value_id.get(position);
                                    quantity_name = name.get(position);
                                    price = product_price.get(position);
                                    sDiscount = discount_price.get(position);

                                    tvProductCount.setText(String.valueOf(putcntlst.get(position)));

                                    double dbl_Price = Double.parseDouble(price);//need to convert string to decimal
                                    String productPrice = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
                                    tvNewPrice.setText("₹" + " " + productPrice);

                                    if (sDiscount.equals("0")) {
                                        tvOldPrice.setVisibility(android.view.View.INVISIBLE);
                                    } else {
                                        double dbl_Discount = Double.parseDouble(sDiscount);//need to convert string to decimal
                                        String str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
                                        tvOldPrice.setVisibility(android.view.View.VISIBLE);
                                        tvOldPrice.setText("₹" + " " + str_disValue);
                                    }

                                    if (tvProductCount.getText().toString().equals("0")) {
                                        llAddQuantity.setVisibility(View.VISIBLE);
                                        llProductCount.setVisibility(View.GONE);
                                    } else {
                                        llAddQuantity.setVisibility(View.GONE);
                                        llProductCount.setVisibility(View.VISIBLE);
                                    }


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                            if (sWishlistStatus.equals("0")) {
                                btnAddWishlist.setText("Add WishList");
                            } else {
                                btnAddWishlist.setText("Added In Wishlist");
                                btnAddWishlist.setEnabled(false);
                                btnAddWishlist.setBackgroundResource(R.drawable.login_reg_border);
                                btnAddWishlist.setTextColor(getResources().getColor(R.color.black));
                            }

                            if (sAddQtyStatus.equals("0")) {
                                llAddQuantity.setVisibility(View.VISIBLE);
                                llProductCount.setVisibility(View.GONE);
                            } else {
                                llAddQuantity.setVisibility(View.GONE);
                                llProductCount.setVisibility(View.VISIBLE);
                                tvProductCount.setText(sAddQtyStatus);
                                btnAddCart.setText("Already In Cart");
                                btnAddCart.setEnabled(false);
                            }
                            countVal = Integer.parseInt(sAddQtyStatus);

                            if (sDesc.equals("null")) {
                                webViewDesc.setVisibility(View.GONE);
                            } else {
                                String res = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                    res = Html.fromHtml(sDesc, Html.FROM_HTML_MODE_COMPACT).toString();
                                } else {
                                    res = Html.fromHtml(sDesc).toString();
                                }
                                webViewDesc.loadDataWithBaseURL(null, res, "text/html",
                                        "utf-8", null);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProductDetailsModel> call, Throwable t) {
                    call.cancel();
                }
            });

            //----------------------------------------------------------similar product response---------------------------------------
            final SimilarProductsModel productslSimilarPrd = new SimilarProductsModel(sPrd_id, session.getCustomerId());
            Call<SimilarProductsModel> callSimilarProducts = apiInterface.getSimilarProducts(productslSimilarPrd);
            callSimilarProducts.enqueue(new Callback<SimilarProductsModel>() {
                @Override
                public void onResponse(Call<SimilarProductsModel> call, Response<SimilarProductsModel> response) {
                    SimilarProductsModel resource = response.body();
                    if (resource.status.equals("success")) {
                        llSimilarProduct.setVisibility(View.VISIBLE);
                        List<SimilarProductsModel.SimilarPrdDatum> datumList = resource.result;
                        for (SimilarProductsModel.SimilarPrdDatum imgs : datumList) {
                            Object qtyspinner = "null";

                            if ((imgs.options.equals("null")) && (!imgs.weight_classes.equals("null"))) {
                                qtyspinner = imgs.weight_classes;
                                phvSimilarProduct.addView(new SimilarProductsListItem(ProductDetailHome.this,
                                        imgs.related_id, imgs.product_id, imgs.image, imgs.name, qtyspinner,
                                        imgs.add_product_quantity_in_cart,imgs.discount_price,imgs.price));

                            } else if ((!imgs.options.equals("null")) && (imgs.weight_classes.equals("null"))) {
                                qtyspinner = imgs.options;
                                phvSimilarProduct.addView(new SimilarProductsListItem(ProductDetailHome.this,
                                        imgs.related_id, imgs.product_id, imgs.image, imgs.name, qtyspinner,
                                        imgs.add_product_quantity_in_cart,imgs.discount_price,imgs.price));

                            } else if ((imgs.options.equals("null")) && (imgs.weight_classes.equals("null"))) {
                                phvSimilarProduct.addView(new SimilarProductsListItem(ProductDetailHome.this,
                                        imgs.related_id, imgs.product_id, imgs.image, imgs.name, qtyspinner,
                                        imgs.add_product_quantity_in_cart,imgs.discount_price,imgs.price));
                            }
                        }
                    } else {
                        llSimilarProduct.setVisibility(View.GONE);
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

        ivbtnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer total_crtcnt = session.getCartCount();
                total_crtcnt = total_crtcnt + 1;
                session.cartcount(total_crtcnt);
                cartcount.setText(String.valueOf(total_crtcnt));


                Log.d("sgdsgdf", String.valueOf(putcntlst.size()));

                if (putcntlst.size() > 0) {

                    int i = Integer.parseInt(String.valueOf(putcntlst.get(spQuantity.getSelectedItemPosition())));
                    i = i + 1;
                    tvProductCount.setText(String.valueOf(i));
                    putcntlst.set(spQuantity.getSelectedItemPosition(), String.valueOf(i));
                    tinydb.putListString(sPrd_id, putcntlst);


                    llAddQuantity.setVisibility(View.GONE);
                    llProductCount.setVisibility(View.VISIBLE);


                    final AddToCartModel ref = new AddToCartModel(sPrd_id, tvProductCount.getText().toString(), option_id, option_value_id);

                    apiInterface = APIClient.getClient().create(APIInterface.class);
                    Call<AddToCartModel> callAdd = apiInterface.callAddToCart("api/cart/add", session.getToken(), ref);
                    callAdd.enqueue(new Callback<AddToCartModel>() {
                        @Override
                        public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                            AddToCartModel resource = response.body();
                            if (resource.status.equals("success")) {
                                // Toast.makeText(getApplicationContext(), "Added in Cart", Toast.LENGTH_LONG).show();
                                btnAddCart.setText("Added in Cart");
                                btnAddCart.setEnabled(false);
                            } else {
                                Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddToCartModel> call, Throwable t) {
                            call.cancel();
                        }
                    });


                } else {


                    int i = Integer.parseInt(sadd_product_quantity_in_cart);
                    i = i + 1;
                    sadd_product_quantity_in_cart = String.valueOf(i);
                    tvProductCount.setText(sadd_product_quantity_in_cart);

                    llAddQuantity.setVisibility(View.GONE);
                    llProductCount.setVisibility(View.VISIBLE);


                    final AddCartNullSpinner nullValue = new AddCartNullSpinner(sPrd_id, tvProductCount.getText().toString());

                    apiInterface = APIClient.getClient().create(APIInterface.class);
                    Call<AddCartNullSpinner> callAdd = apiInterface.callNullSpinner("api/cart/add", session.getToken(),
                            nullValue);
                    callAdd.enqueue(new Callback<AddCartNullSpinner>() {
                        @Override
                        public void onResponse(Call<AddCartNullSpinner> call, Response<AddCartNullSpinner> response) {
                            AddCartNullSpinner resource = response.body();
                            if (resource.status.equals("success")) {
                                //  Toast.makeText(getApplicationContext(), "Added in Cart", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddCartNullSpinner> call, Throwable t) {
                            call.cancel();
                        }
                    });


                    Log.d("dddd", "onClick: ddddddd");
                }

            }
        });

        lldecreasePrdCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (putcntlst.size() > 0) {

                    if (Integer.parseInt(tvProductCount.getText().toString()) <= 1) {
                        Integer total_crtcnt = session.getCartCount();
                        total_crtcnt = total_crtcnt - 1;
                        session.cartcount(total_crtcnt);
                        cartcount.setText(String.valueOf(total_crtcnt));

                        int i = Integer.parseInt(String.valueOf(putcntlst.get(spQuantity.getSelectedItemPosition())));
                        i = i - 1;
                        tvProductCount.setText(String.valueOf(i));
                        putcntlst.set(spQuantity.getSelectedItemPosition(), String.valueOf(i));
                        tinydb.putListString(sPrd_id, putcntlst);

                        llAddQuantity.setVisibility(View.VISIBLE);
                        llProductCount.setVisibility(View.GONE);

                        final UpdateToCartOptionsModel ref = new UpdateToCartOptionsModel(sPrd_id,option_id,option_value_id, tvProductCount.getText().toString());
                        apiInterface = APIClient.getClient().create(APIInterface.class);
                        Call<UpdateToCartOptionsModel> callAdd = apiInterface.updateAddToCartwithoptions("api/cart/edit_new", session.getToken(), ref);
                        callAdd.enqueue(new Callback<UpdateToCartOptionsModel>() {
                            @Override
                            public void onResponse(Call<UpdateToCartOptionsModel> call, Response<UpdateToCartOptionsModel> response) {
                                UpdateToCartOptionsModel resource = response.body();
                                if (resource.status.equals("success")) {
                                    //  Toast.makeText(getApplicationContext(), "Remove from Cart", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UpdateToCartOptionsModel> call, Throwable t) {
                                call.cancel();
                            }
                        });

                    } else {

                        int i = Integer.parseInt(String.valueOf(putcntlst.get(spQuantity.getSelectedItemPosition())));
                        i = i - 1;
                        tvProductCount.setText(String.valueOf(i));
                        putcntlst.set(spQuantity.getSelectedItemPosition(), String.valueOf(i));
                        tinydb.putListString(sPrd_id, putcntlst);

                        final UpdateToCartOptionsModel ref = new UpdateToCartOptionsModel(sPrd_id,option_id,option_value_id, tvProductCount.getText().toString());
                        apiInterface = APIClient.getClient().create(APIInterface.class);
                        Call<UpdateToCartOptionsModel> callAdd = apiInterface.updateAddToCartwithoptions("api/cart/edit_new", session.getToken(), ref);
                        callAdd.enqueue(new Callback<UpdateToCartOptionsModel>() {
                            @Override
                            public void onResponse(Call<UpdateToCartOptionsModel> call, Response<UpdateToCartOptionsModel> response) {
                                UpdateToCartOptionsModel resource = response.body();
                                if (resource.status.equals("success")) {
                                    //  Toast.makeText(getApplicationContext(), "Remove from Cart", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UpdateToCartOptionsModel> call, Throwable t) {
                                call.cancel();
                            }
                        });


                    }


                } else {

                    if (Integer.parseInt(tvProductCount.getText().toString()) <= 1) {
                        Integer total_crtcnt = session.getCartCount();
                        total_crtcnt = total_crtcnt - 1;
                        session.cartcount(total_crtcnt);
                        cartcount.setText(String.valueOf(total_crtcnt));

                        int i = Integer.parseInt(sadd_product_quantity_in_cart);
                        i = i - 1;
                        sadd_product_quantity_in_cart = String.valueOf(i);
                        tvProductCount.setText(sadd_product_quantity_in_cart);

                        llAddQuantity.setVisibility(View.VISIBLE);
                        llProductCount.setVisibility(View.GONE);

                        final UpdateToCartModel ref = new UpdateToCartModel(sPrd_id, tvProductCount.getText().toString());
                        apiInterface = APIClient.getClient().create(APIInterface.class);
                        Call<UpdateToCartModel> callAdd = apiInterface.updateAddToCart("api/cart/edit_new", session.getToken(), ref);
                        callAdd.enqueue(new Callback<UpdateToCartModel>() {
                            @Override
                            public void onResponse(Call<UpdateToCartModel> call, Response<UpdateToCartModel> response) {
                                UpdateToCartModel resource = response.body();
                                if (resource.status.equals("success")) {
                                    //  Toast.makeText(getApplicationContext(), "Remove from Cart", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UpdateToCartModel> call, Throwable t) {
                                call.cancel();
                            }
                        });

                    } else {

                        int i = Integer.parseInt(sadd_product_quantity_in_cart);
                        i = i - 1;
                        sadd_product_quantity_in_cart = String.valueOf(i);
                        tvProductCount.setText(sadd_product_quantity_in_cart);

                        final UpdateToCartModel ref = new UpdateToCartModel(sPrd_id, tvProductCount.getText().toString());
                        apiInterface = APIClient.getClient().create(APIInterface.class);
                        Call<UpdateToCartModel> callAdd = apiInterface.updateAddToCart("api/cart/edit_new", session.getToken(), ref);
                        callAdd.enqueue(new Callback<UpdateToCartModel>() {
                            @Override
                            public void onResponse(Call<UpdateToCartModel> call, Response<UpdateToCartModel> response) {
                                UpdateToCartModel resource = response.body();
                                if (resource.status.equals("success")) {
                                    //  Toast.makeText(getApplicationContext(), "Remove from Cart", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UpdateToCartModel> call, Throwable t) {
                                call.cancel();
                            }
                        });


                    }


                }

            }
        });

        llincreasePrdCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (putcntlst.size() > 0) {

                    int i = Integer.parseInt(String.valueOf(putcntlst.get(spQuantity.getSelectedItemPosition())));
                    i = i + 1;
                    tvProductCount.setText(String.valueOf(i));
                    putcntlst.set(spQuantity.getSelectedItemPosition(), String.valueOf(i));
                    tinydb.putListString(sPrd_id, putcntlst);


                    final UpdateToCartOptionsModel ref = new UpdateToCartOptionsModel(sPrd_id,option_id,option_value_id,tvProductCount.getText().toString());
                    apiInterface = APIClient.getClient().create(APIInterface.class);
                    Call<UpdateToCartOptionsModel> callAdd = apiInterface.updateAddToCartwithoptions("api/cart/edit_new", session.getToken(), ref);
                    callAdd.enqueue(new Callback<UpdateToCartOptionsModel>() {
                        @Override
                        public void onResponse(Call<UpdateToCartOptionsModel> call, Response<UpdateToCartOptionsModel> response) {
                            UpdateToCartOptionsModel resource = response.body();
                            if (resource.status.equals("success")) {
                                //    Toast.makeText(getApplicationContext(), "Added in Cart", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateToCartOptionsModel> call, Throwable t) {
                            call.cancel();
                        }
                    });


                } else {

                    int i = Integer.parseInt(sadd_product_quantity_in_cart);
                    i = i + 1;
                    sadd_product_quantity_in_cart = String.valueOf(i);
                    tvProductCount.setText(sadd_product_quantity_in_cart);

                    final UpdateToCartModel ref = new UpdateToCartModel(sPrd_id, tvProductCount.getText().toString());
                    apiInterface = APIClient.getClient().create(APIInterface.class);
                    Call<UpdateToCartModel> callAdd = apiInterface.updateAddToCart("api/cart/edit_new", session.getToken(), ref);
                    callAdd.enqueue(new Callback<UpdateToCartModel>() {
                        @Override
                        public void onResponse(Call<UpdateToCartModel> call, Response<UpdateToCartModel> response) {
                            UpdateToCartModel resource = response.body();
                            if (resource.status.equals("success")) {
                                //    Toast.makeText(getApplicationContext(), "Added in Cart", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateToCartModel> call, Throwable t) {
                            call.cancel();
                        }
                    });


                }


            }
        });

        btnAddWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final InsertWishListItems add_item = new InsertWishListItems(session.getCustomerId(), sPrd_id);
                Call<InsertWishListItems> callAdd = apiInterface.addtoWishList(add_item);
                callAdd.enqueue(new Callback<InsertWishListItems>() {
                    @Override
                    public void onResponse(Call<InsertWishListItems> call, Response<InsertWishListItems> response) {
                        InsertWishListItems resource = response.body();
                        if (resource.status.equals("success")) {
                            Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                            btnAddWishlist.setText("Added In Wishlist");
                            btnAddWishlist.setEnabled(false);
                            btnAddWishlist.setBackgroundResource(R.drawable.login_reg_border);
                            btnAddWishlist.setTextColor(getResources().getColor(R.color.black));

                            btn_addtoWishlistPrdDetail.setBackgroundResource(R.drawable.wishlist_red);

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
        tvProductCount.setText("" + number);
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
                                session.cartcount(Integer.parseInt(cartCount.data));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}