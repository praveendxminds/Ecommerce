package com.nisarga.nisargaveggiez.Home;

import android.content.Intent;
import android.os.Bundle;
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
import com.mindorks.placeholderview.PlaceHolderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.nisarga.nisargaveggiez.cart.cart;
import com.nisarga.nisargaveggiez.retrofit.SimilarProductsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sushmita 25/06/2019
 */

public class ProductDetailHome extends AppCompatActivity {

    SessionManager session;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_product_detail_home);

        session = new SessionManager(getApplicationContext());
        apiInterface = APIClient.getClient().create(APIInterface.class);

        init();
    }

    Toolbar toolbarSingleProduct;
    PlaceHolderView phvSingleProductImage, phvSimilarProduct;
    TextView tvPrdName, tvNewPrice, tvOldPrice, tvProductCount;
    Spinner spQuantity;
    ImageButton ivbtnAddItem, ivBtnDecrease, ivBtnIncrease;
    LinearLayout llAddQuantity, llProductCount, llSimilarProduct;
    WebView webViewDesc;
    Button btnAddWishlist, btnAddCart;

    String sPrdId, sPrdName, sPrdPrice, sPrdDisPrice, sPrdQty, sDesc, sWishlistStatus, sAddQtyStatus;
    int countVal = 0;
    private String str_priceValue, str_priceValue_1;

    String product_option_id[], product_option_value_id[];
    String sQuantitySpinner, option_id, option_value_id;

    private void init() {
        toolbarSingleProduct = (Toolbar) findViewById(R.id.toolbarSingleProduct);
        setSupportActionBar(toolbarSingleProduct);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        phvSingleProductImage = findViewById(R.id.phvSingleProductImage);
        tvPrdName = findViewById(R.id.tvPrdName);
        spQuantity = findViewById(R.id.spQuantity);
        tvNewPrice = findViewById(R.id.tvNewPrice);
        tvOldPrice = findViewById(R.id.tvOldPrice);
        ivbtnAddItem = findViewById(R.id.ivbtnAddItem);

        llAddQuantity = findViewById(R.id.llAddQuantity);
        llProductCount = findViewById(R.id.llProductCount);
        ivBtnDecrease = findViewById(R.id.ivBtnDecrease);
        tvProductCount = findViewById(R.id.tvProductCount);
        ivBtnIncrease = findViewById(R.id.ivBtnIncrease);

        llSimilarProduct = findViewById(R.id.llSimilarProduct);
        phvSimilarProduct = findViewById(R.id.phvSimilarProduct);
        webViewDesc = findViewById(R.id.webViewDesc);

        btnAddWishlist = findViewById(R.id.btnAddWishlist);
        btnAddCart = findViewById(R.id.btnAddCart);

        sPrdId = getIntent().getExtras().getString("product_id", "defaultKey");

        phvSimilarProduct.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(ProductDetailHome.this,
                        LinearLayoutManager.HORIZONTAL, false));

        final ArrayList<String> product_qty_list = new ArrayList<>();

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final QuantityList quantityList = new QuantityList(sPrdId);

            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<QuantityList> callheight = apiInterface.quantity_list(quantityList);
            callheight.enqueue(new Callback<QuantityList>() {
                @Override
                public void onResponse(Call<QuantityList> callheight, Response<QuantityList> response) {
                    QuantityList eduresource = response.body();
                    List<QuantityList.Datum> datumList = eduresource.data;
                    product_option_id = new String[datumList.size()];
                    product_option_value_id = new String[datumList.size()];
                    int i = 0;
                    for (QuantityList.Datum datum : datumList) {
                        product_qty_list.add(datum.name);
                        product_option_id[i] = datum.product_option_id;
                        product_option_value_id[i] = datum.product_option_value_id;
                        i++;
                    }

                    ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(ProductDetailHome.this, R.layout.spinner_item,
                            product_qty_list);
                    spQuantity.setAdapter(itemsAdapter);
                    spQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                            sQuantitySpinner = product_qty_list.get(position);
                            option_id = product_option_id[position];
                            option_value_id = product_option_value_id[position];
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

        final ArrayList<String> imageArr = new ArrayList<>();

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            //-----------------------------------------------------------for product details ---------------------------------
            final ProductDetailsModel productDetail = new ProductDetailsModel(session.getCustomerId(), sPrdId);
            Call<ProductDetailsModel> call = apiInterface.getProductDetails(productDetail);
            call.enqueue(new Callback<ProductDetailsModel>() {
                @Override
                public void onResponse(Call<ProductDetailsModel> call, Response<ProductDetailsModel> response) {
                    ProductDetailsModel productResponse = response.body();
                    if (productResponse.status.equals("success")) {
                        List<ProductDetailsModel.Datum> datumList = productResponse.result;
                        for (ProductDetailsModel.Datum imgs : datumList) {
                            String PrdId = imgs.product_id;
                            sPrdName = imgs.name;
                            tvPrdName.setText(sPrdName);
                            toolbarSingleProduct.setTitle(sPrdName);

                            sPrdPrice = imgs.price;
                            tvNewPrice.setText(sPrdPrice);

                            sPrdDisPrice = imgs.discount_price;
                            tvOldPrice.setText(sPrdDisPrice);

                            sPrdQty = imgs.quantity;
                            sDesc = imgs.description;
                            if (sDesc.equals("null")) {
                                webViewDesc.setVisibility(View.GONE);
                            } else {
                                String res = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                    res = Html.fromHtml(sDesc, Html.FROM_HTML_MODE_COMPACT).toString();
                                } else {
                                    res = Html.fromHtml(sDesc).toString();
                                }
                                webViewDesc.loadDataWithBaseURL(null, res, "text/html", "utf-8",
                                        null);
                            }

                            sWishlistStatus = imgs.wishlist_status;
                            if (sWishlistStatus.equals("1")) {
                                btnAddWishlist.setText("Added To WishList");
                                btnAddWishlist.setEnabled(false);
                                btnAddWishlist.setBackgroundColor(ProductDetailHome.this.getResources().getColor(R.color.green));
                            }

                            sAddQtyStatus = imgs.add_prd_qty;
                            if (sAddQtyStatus.equals("1")) {
                                btnAddCart.setText("Added To Cart");
                                btnAddCart.setEnabled(false);
                                btnAddCart.setBackgroundColor(ProductDetailHome.this.getResources().getColor(R.color.green));
                            }

                            List<ProductDetailsModel.Datum.ImageArr> imageList = imgs.image;
                            for (ProductDetailsModel.Datum.ImageArr imgSlide : imageList) {
                                if (response.isSuccessful()) {
                                    imageArr.add(imgSlide.image_1);
                                }
                            }
                            phvSingleProductImage.addView(new ProductDetailsImageSlider(ProductDetailHome.this,
                                    imageArr, sWishlistStatus, sPrdId, btnAddWishlist));

                            double dbl_Price = Double.parseDouble(imgs.price);//need to convert string to decimal
                            str_priceValue = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
                            tvNewPrice.setText("₹" + " " + str_priceValue);

                            double dbl_Price1 = Double.parseDouble(imgs.discount_price);//need to convert string to decimal
                            str_priceValue_1 = String.format("%.2f", dbl_Price1);//display only 2 decimal places of price
                            tvOldPrice.setText("₹" + " " + str_priceValue_1);
                        }

                       /* if (productResponse.similarProduct.equals("null")) {
                            llSimilarProduct.setVisibility(View.GONE);
                        } else {
                            List<ProductDetailsModel.SimilarProductDatum> similarProductData = productResponse.similarProduct;
                            for (ProductDetailsModel.SimilarProductDatum data : similarProductData) {
                                if (response.isSuccessful()) {
                                    phvSimilarProduct.addView(new SimilarProductsListItem(ProductDetailHome.this,
                                            data.related_id, data.product_id, data.image, data.wishlist_status,
                                            data.add_product_quantity_in_cart, data.price, data.quantity,
                                            data.discount_price, data.name));
                                }
                            }
                        }*/
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

        //--------------------------------------similar product response---------------------------------------
        final SimilarProductsModel productslSimilarPrd = new SimilarProductsModel(sPrdId, session.getCustomerId());
        Call<SimilarProductsModel> callSimilarProducts = apiInterface.getSimilarProducts(productslSimilarPrd);
        callSimilarProducts.enqueue(new Callback<SimilarProductsModel>() {
            @Override
            public void onResponse(Call<SimilarProductsModel> call, Response<SimilarProductsModel> response) {
                SimilarProductsModel resource = response.body();
                if (resource.status.equals("success")) {
                    llSimilarProduct.setVisibility(View.VISIBLE);
                    List<SimilarProductsModel.SimilarPrdDatum> datumList = resource.result;
                    for (SimilarProductsModel.SimilarPrdDatum imgs : datumList) {
                        if (response.isSuccessful()) {
                            phvSimilarProduct.addView(new SimilarProductsListItem(getApplicationContext(),
                                    imgs.related_id, imgs.product_id, imgs.image, imgs.price, imgs.quantity,
                                    imgs.discount_price, imgs.name));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SimilarProductsModel> call, Throwable t) {
                call.cancel();
            }
        });

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        btnAddWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToWishList();
            }
        });

        ivbtnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countVal = countVal + 1;//display number in place of add to cart
                session.cartcount(countVal);
                display(countVal);
                tvProductCount.setText(countVal);
                llAddQuantity.setVisibility(View.GONE);
                llProductCount.setVisibility(View.VISIBLE);
            }
        });

        ivBtnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countVal = countVal + 1;//display number in place of add to carticon
                session.cartcount(countVal);
                display(countVal);
                tvProductCount.setText(countVal);
            }
        });

        ivBtnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countVal <= 1) {
                    countVal = countVal - 1;
                    session.cartcount(countVal);
                    display(countVal);
                    tvProductCount.setText(countVal);
                    llAddQuantity.setVisibility(android.view.View.VISIBLE);
                    llProductCount.setVisibility(android.view.View.GONE);
                } else {
                    countVal = countVal - 1;
                    session.cartcount(countVal);
                    display(countVal);
                    tvProductCount.setText(countVal);
                }
            }
        });
    }

    private void addToCart() {
        final AddToCartModel ref = new AddToCartModel(sPrdId, sQuantitySpinner, option_id, option_value_id);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AddToCartModel> callAdd = apiInterface.callAddToCart("api/cart/add", session.getToken(), ref);
        callAdd.enqueue(new Callback<AddToCartModel>() {
            @Override
            public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                AddToCartModel resource = response.body();
                if (resource.status.equals("success")) {
                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                } else if (resource.status.equals("failure")) {
                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddToCartModel> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void addToWishList() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        final InsertWishListItems add_item = new InsertWishListItems(session.getCustomerId(), sPrdId);
        Call<InsertWishListItems> callAdd = apiInterface.addtoWishList(add_item);
        callAdd.enqueue(new Callback<InsertWishListItems>() {
            @Override
            public void onResponse(Call<InsertWishListItems> call, Response<InsertWishListItems> response) {
                InsertWishListItems resource = response.body();
                if (resource.status.equals("success")) {
                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                    btnAddWishlist.setText("Added To WishList");
                    btnAddWishlist.setEnabled(false);
                    btnAddWishlist.setBackgroundColor(ProductDetailHome.this.getResources().getColor(R.color.green));
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

    public void display(int number) {
        tvProductCount.setText("" + number);
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
        final TextView cart_badge = (TextView) rootView.findViewById(R.id.cart_badge);

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            Call<CartCount> call = apiInterface.getCartCount("api/cart/cartcount", session.getToken());
            call.enqueue(new Callback<CartCount>() {
                @Override
                public void onResponse(Call<CartCount> call, Response<CartCount> response) {
                    CartCount cartCount = response.body();
                    if (cartCount.status.equals("success")) {
                        cart_badge.setText(cartCount.data);
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
                Intent myctIntent = new Intent(getApplicationContext(), cart.class);
                startActivity(myctIntent);
                break;
        }
        return true;
    }
}