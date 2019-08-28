package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.ProfileSection.QuantityList;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.AddToCartModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.nisarga.nisargaveggiez.Home.ProductDetailHome.cartcount;

@NonReusable
@Layout(R.layout.similar_products_list)
public class SimilarProductsListItem {

    @View(R.id.llSimilarProduct)
    public LinearLayout llSimilarProduct;

    @View(R.id.tvItemName)
    public TextView tvItemName;

    @View(R.id.tvNewPrice)
    public TextView tvNewPrice;

    @View(R.id.tvOldPrice)
    public TextView tvOldPrice;

    @View(R.id.ivItemImage)
    public ImageView ivItemImage;

    @View(R.id.spQuantity)
    public Spinner spQuantity;

    @View(R.id.btnAddCart)
    public Button btnAddCart;

    @View(R.id.llCountProduct)
    public LinearLayout llCountProduct;

    @View(R.id.llDecreaseCount)
    public LinearLayout llDecreaseCount;

    @View(R.id.llIncreaseCount)
    public LinearLayout llIncreaseCount;

    @View(R.id.tvTotalCount)
    public TextView tvTotalCount;

    APIInterface apiInterface;
    SessionManager session;
    Context mContext;

    String mrelated_id, mPrd_id, mPrdImgUrl, mHeading, mPrice, mQty, mdiscount, wishlist, sCart;

    int minteger = 0;

    String product_option_id[], product_option_value_id[], product_price[];
    String sQuantitySpinner, option_id, option_value_id, price;

    public SimilarProductsListItem(Context context, String related_id, String prd_id, String url, String heading,
                                   String price, String qty, String discount, String wishlist_status,
                                   String addCart) {
        mContext = context;
        mrelated_id = related_id;
        mPrd_id = prd_id;
        mPrdImgUrl = url;
        mHeading = heading;
        mPrice = price;
        mQty = qty;
        mdiscount = discount;
        wishlist = wishlist_status;
        sCart = addCart;
    }

    @Resolve
    public void onResolved() {
        session = new SessionManager(mContext);
        tvItemName.setText(mHeading);
        Glide.with(mContext).load(mPrdImgUrl).into(ivItemImage);

        if (mdiscount.equals("null")) {
            tvOldPrice.setVisibility(android.view.View.INVISIBLE);
        } else {
            double dbl_Discount = Double.parseDouble(mdiscount);//need to convert string to decimal
            String str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
            tvOldPrice.setVisibility(android.view.View.VISIBLE);
            tvOldPrice.setText("₹" + " " + str_disValue);
        }

        if (sCart.equals("0")) {
            btnAddCart.setVisibility(android.view.View.VISIBLE);
            llCountProduct.setVisibility(android.view.View.GONE);
        } else {
            btnAddCart.setVisibility(android.view.View.GONE);
            llCountProduct.setVisibility(android.view.View.VISIBLE);
            tvTotalCount.setText(sCart);
        }

        final ArrayList<String> product_qty_list = new ArrayList<>();

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final QuantityList quantityList = new QuantityList(mPrd_id);

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

                    ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item,
                            product_qty_list);
                    spQuantity.setAdapter(itemsAdapter);
                    spQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                            sQuantitySpinner = product_qty_list.get(position);
                            option_id = product_option_id[position];
                            option_value_id = product_option_value_id[position];
                            price = product_price[position];

                            double dbl_Price = Double.parseDouble(price);//need to convert string to decimal
                            String productPrice = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
                            tvNewPrice.setText("₹" + " " + productPrice);
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

        minteger = Integer.parseInt(sCart);
    }

    @Click(R.id.llSimilarProduct)
    public void onLongClick() {
        Intent intent = new Intent(mContext, ProductDetailHome.class);
        intent.putExtra("product_id", mrelated_id);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @Click(R.id.btnAddCart)
    public void AddToCartClick() {
        Integer total_crtcnt = session.getCartCount();
        total_crtcnt = total_crtcnt + 1;
        session.cartcount(total_crtcnt);
        cartcount.setText(String.valueOf(total_crtcnt));

        minteger = minteger + 1;//display number in place of add to cart
        display(minteger);
        tvTotalCount.setText(String.valueOf(minteger));
        btnAddCart.setVisibility(android.view.View.GONE);
        llCountProduct.setVisibility(android.view.View.VISIBLE);

        final AddToCartModel ref = new AddToCartModel(mPrd_id, String.valueOf(minteger), option_id, option_value_id);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AddToCartModel> callAdd = apiInterface.callAddToCart("api/cart/add", session.getToken(), ref);
        callAdd.enqueue(new Callback<AddToCartModel>() {
            @Override
            public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                AddToCartModel resource = response.body();
                if (resource.status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Added in Cart", Toast.LENGTH_LONG).show();
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

    @Click(R.id.llDecreaseCount)
    public void onDecreaseClick() {
        if (minteger <= 1) {
            Integer total_crtcnt = session.getCartCount();
            total_crtcnt = total_crtcnt - 1;
            session.cartcount(total_crtcnt);
            cartcount.setText(String.valueOf(total_crtcnt));

            minteger = minteger - 1;
            display(minteger);
            tvTotalCount.setText(minteger);
            btnAddCart.setVisibility(android.view.View.VISIBLE);
            llCountProduct.setVisibility(android.view.View.GONE);

            final UpdateToCartModel ref = new UpdateToCartModel(mPrd_id, String.valueOf(minteger));
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<UpdateToCartModel> callAdd = apiInterface.updateAddToCart("api/cart/edit_new", session.getToken(), ref);
            callAdd.enqueue(new Callback<UpdateToCartModel>() {
                @Override
                public void onResponse(Call<UpdateToCartModel> call, Response<UpdateToCartModel> response) {
                    UpdateToCartModel resource = response.body();
                    if (resource.status.equals("success")) {
                        Toast.makeText(getApplicationContext(), "Remove from Cart", Toast.LENGTH_LONG).show();
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
            minteger = minteger - 1;
            display(minteger);
            tvTotalCount.setText(minteger);

            final UpdateToCartModel ref = new UpdateToCartModel(mPrd_id, String.valueOf(minteger));
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<UpdateToCartModel> callAdd = apiInterface.updateAddToCart("api/cart/edit_new", session.getToken(), ref);
            callAdd.enqueue(new Callback<UpdateToCartModel>() {
                @Override
                public void onResponse(Call<UpdateToCartModel> call, Response<UpdateToCartModel> response) {
                    UpdateToCartModel resource = response.body();
                    if (resource.status.equals("success")) {
                        Toast.makeText(getApplicationContext(), "Remove from Cart", Toast.LENGTH_LONG).show();
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

    @Click(R.id.llIncreaseCount)
    public void onIncreaseClick() {
        minteger = minteger + 1;//display number in place of add to cart
        display(minteger);
        tvTotalCount.setText(minteger);

        final UpdateToCartModel ref = new UpdateToCartModel(mPrd_id, String.valueOf(minteger));

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<UpdateToCartModel> callAdd = apiInterface.updateAddToCart("api/cart/edit_new", session.getToken(), ref);
        callAdd.enqueue(new Callback<UpdateToCartModel>() {
            @Override
            public void onResponse(Call<UpdateToCartModel> call, Response<UpdateToCartModel> response) {
                UpdateToCartModel resource = response.body();
                if (resource.status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Added in Cart", Toast.LENGTH_LONG).show();
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

    public void display(int number) {
        tvTotalCount.setText("" + number);
    }
}
