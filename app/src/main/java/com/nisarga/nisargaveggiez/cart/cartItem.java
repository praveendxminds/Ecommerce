package com.nisarga.nisargaveggiez.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nisarga.nisargaveggiez.Home.ProductDetailHome;
import com.nisarga.nisargaveggiez.Home.UpdateToCartModel;
import com.nisarga.nisargaveggiez.ProfileSection.QuantityList;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.AddToCartModel;
import com.nisarga.nisargaveggiez.retrofit.InsertWishListItems;
import com.nisarga.nisargaveggiez.retrofit.RemoveWishListItem;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import com.nisarga.nisargaveggiez.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.nisarga.nisargaveggiez.Wishlist.WishListHolder.totalWishList;
import static com.nisarga.nisargaveggiez.cart.cart.tvTotalVeggies;
import static com.nisarga.nisargaveggiez.cart.cart.tvtotalAmount;

/**
 * Created by Sushmita on 24/06/2019.
 */


@NonReusable
@Layout(R.layout.cart_items)
public class cartItem {

    @View(R.id.llCartList)
    public LinearLayout llCartList;

    @View(R.id.ivImage)
    public ImageView ivImage;

    @View(R.id.tvProductName)
    public TextView tvProductName;

    @View(R.id.tvQuantity)
    public TextView tvQuantity;

    @View(R.id.tvNewPrice)
    public TextView tvNewPrice;

    @View(R.id.tvOldPrice)
    public TextView tvOldPrice;

    @View(R.id.llcountItemMyCart)
    public LinearLayout llcountItemMyCart;

    @View(R.id.lldecreasePrdCount)
    public LinearLayout lldecreasePrdCount;

    @View(R.id.tvProductCount)
    public TextView tvProductCount;

    @View(R.id.llincreasePrdCount)
    public LinearLayout llincreasePrdCount;

    Context mcontext;
    APIInterface apiInterface;
    SessionManager session;

    String mprdid, murl, mtitle, mdisprice, mqty, mprice, mOption;
    PlaceHolderView mPlaceHolderView;

    int intCount = 0;

    public cartItem(Context context, String prdid, String url, String title, String disprice, String qty,
                    PlaceHolderView placeHolderView, String price, String option_name) {

        mcontext = context;
        mprdid = prdid;
        murl = url;
        mtitle = title;
        mdisprice = disprice;
        mqty = qty;
        mPlaceHolderView = placeHolderView;
        mprice = price;
        mOption = option_name;
    }

    public String getTitle() {
        return mtitle;
    }

    public String getUrl() {
        return murl;
    }

    @Resolve
    public void onResolved() {
        session = new SessionManager(mcontext);
        Glide.with(mcontext).load(murl).into(ivImage);
        tvProductName.setText(mtitle);
        tvNewPrice.setText("₹" + " " + mprice);
        tvQuantity.setText(mOption);
        tvProductCount.setText(mqty);

        if (mdisprice.equals("null")) {
            tvOldPrice.setVisibility(android.view.View.INVISIBLE);
        } else {
            double dbl_Price_1 = Double.parseDouble(mdisprice);//need to convert string to decimal
            String str_priceValue_1 = String.format("%.2f", dbl_Price_1);//display only 2 decimal places of price
            tvOldPrice.setVisibility(android.view.View.VISIBLE);
            tvOldPrice.setText("₹" + " " + str_priceValue_1);
        }

        intCount = Integer.parseInt(mqty);
    }

    @Click(R.id.llCartList)
    public void onCardClick() {
        Intent myIntent = new Intent(mcontext, ProductDetailHome.class);
        myIntent.putExtra("product_id", mprdid);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mcontext.startActivity(myIntent);

    }

    @Click(R.id.lldecreasePrdCount)
    public void onDecreaseClick() {
        if (intCount <= 1) {
            intCount = intCount - 1;
            tvProductCount.setText(String.valueOf(intCount));
            llcountItemMyCart.setVisibility(android.view.View.GONE);

            final UpdateToCartModel ref = new UpdateToCartModel(mprdid, String.valueOf(intCount));

            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<UpdateToCartModel> callAdd = apiInterface.updateAddToCart("api/cart/edit_new", session.getToken(), ref);
            callAdd.enqueue(new Callback<UpdateToCartModel>() {
                @Override
                public void onResponse(Call<UpdateToCartModel> call, Response<UpdateToCartModel> response) {
                    UpdateToCartModel resource = response.body();
                    if (resource.status.equals("success")) {
                        if (tvTotalVeggies.getText().toString().equals("1 Item")) {
                            int cnt = Integer.parseInt(tvTotalVeggies.getText().toString().replace(" Item", ""));
                            cnt = cnt - 1;

                            tvTotalVeggies.setText(cnt + " " + "Item");
                        } else {
                            int cnt = Integer.parseInt(tvTotalVeggies.getText().toString().replace(" Items", ""));
                            cnt = cnt - 1;

                            tvTotalVeggies.setText(cnt + " " + "Items");
                        }

                        //Toast.makeText(getApplicationContext(), "Remove from Cart", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateToCartModel> call, Throwable t) {
                    call.cancel();
                }
            });

            if (tvTotalVeggies.getText().toString().equals("0 Item")) {
                tvtotalAmount.setText("Total" + " " + "\u20B9 " + "0");
            } else {
                double price_total = Double.parseDouble(tvtotalAmount.getText().toString().replace("Total \u20B9 ", ""));
                double price_new = Double.parseDouble(tvNewPrice.getText().toString().replace("₹ ", ""));
                price_total = price_total - price_new;
                tvtotalAmount.setText("Total" + " " + "\u20B9 " + String.valueOf(price_total));
            }

            mPlaceHolderView.removeView(this);

        } else {
            intCount = intCount - 1;
            tvProductCount.setText(String.valueOf(intCount));

            final UpdateToCartModel ref = new UpdateToCartModel(mprdid, String.valueOf(intCount));

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

            if (tvTotalVeggies.getText().toString().equals("0 Item")) {
                tvtotalAmount.setText("Total" + " " + "\u20B9 " + "0");

            } else {
                double price_total = Double.parseDouble(tvtotalAmount.getText().toString().replace("Total \u20B9 ", ""));
                double price_new = Double.parseDouble(tvNewPrice.getText().toString().replace("₹ ", ""));
                price_total = price_total - price_new;
                tvtotalAmount.setText("Total" + " " + "\u20B9 " + String.valueOf(price_total));
            }
        }
    }

    @Click(R.id.llincreasePrdCount)
    public void onIncreaseClick() {
        intCount = intCount + 1;//display number in place of add to cart
        tvProductCount.setText(String.valueOf(intCount));

        final UpdateToCartModel ref = new UpdateToCartModel(mprdid, String.valueOf(intCount));

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<UpdateToCartModel> callAdd = apiInterface.updateAddToCart("api/cart/edit_new", session.getToken(), ref);
        callAdd.enqueue(new Callback<UpdateToCartModel>() {
            @Override
            public void onResponse(Call<UpdateToCartModel> call, Response<UpdateToCartModel> response) {
                UpdateToCartModel resource = response.body();
                if (resource.status.equals("success")) {
                    //   Toast.makeText(getApplicationContext(), "Added in Cart", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateToCartModel> call, Throwable t) {
                call.cancel();
            }
        });

        if (tvTotalVeggies.getText().toString().equals("0 Item")) {
            tvtotalAmount.setText("Total" + " " + "\u20B9 " + "0");
        } else {
            double price_total = Double.parseDouble(tvtotalAmount.getText().toString().replace("Total \u20B9 ", ""));
            double price_new = Double.parseDouble(tvNewPrice.getText().toString().replace("₹ ", ""));
            price_total = price_total + price_new;
            tvtotalAmount.setText("Total" + " " + "\u20B9 " + String.valueOf(price_total));
        }
    }
}
