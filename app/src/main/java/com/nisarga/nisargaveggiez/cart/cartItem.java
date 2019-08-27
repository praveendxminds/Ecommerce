package com.nisarga.nisargaveggiez.cart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
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

    @View(R.id.spQuantity)
    public Spinner spQuantity;

    @View(R.id.tvNewPrice)
    public TextView tvNewPrice;

    @View(R.id.tvOldPrice)
    public TextView tvOldPrice;

    @View(R.id.btnAddCart)
    public ImageButton btnAddCart;

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

    String mprdid;
    String murl, mtitle, mdisprice, mqty;

    PlaceHolderView mPlaceHolderView;
    String product_option_id[], product_option_value_id[], product_price[];
    String sQuantitySpinner, option_id, option_value_id, price;
    String productPrice;

    int intCount = 0;

    public cartItem(Context context, String prdid, String url, String title, String disprice, String qty,
                    PlaceHolderView placeHolderView) {

        mcontext = context;
        mprdid = prdid;
        murl = url;
        mtitle = title;
        mdisprice = disprice;
        mqty = qty;
        mPlaceHolderView = placeHolderView;
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
        tvProductName.setText(mtitle);
        Glide.with(mcontext).load(murl).into(ivImage);

        if (mdisprice.equals("null")) {
            tvOldPrice.setVisibility(android.view.View.INVISIBLE);
        } else {
            double dbl_Price_1 = Double.parseDouble(mdisprice);//need to convert string to decimal
            String str_priceValue_1 = String.format("%.2f", dbl_Price_1);//display only 2 decimal places of price
            tvOldPrice.setVisibility(android.view.View.VISIBLE);
            tvOldPrice.setText("₹" + " " + str_priceValue_1);
        }

        if (mqty.equals("0")) {
            btnAddCart.setVisibility(android.view.View.VISIBLE);
            llcountItemMyCart.setVisibility(android.view.View.GONE);
        } else {
            btnAddCart.setVisibility(android.view.View.GONE);
            llcountItemMyCart.setVisibility(android.view.View.VISIBLE);
            tvProductCount.setText(mqty);
        }


        final ArrayList<String> product_qty_list = new ArrayList<>();

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final QuantityList quantityList = new QuantityList(mprdid);

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

                    ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(mcontext, R.layout.spinner_item,
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
                            productPrice = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
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

        intCount = Integer.parseInt(mqty);
    }

    @Click(R.id.llCartList)
    public void onCardClick() {
        Intent myIntent = new Intent(mcontext, ProductDetailHome.class);
        myIntent.putExtra("product_id", mprdid);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mcontext.startActivity(myIntent);

    }

    @Click(R.id.btnAddCart)
    public void AddToCartClick() {
        intCount = intCount + 1;//display number in place of add to cart
        display(intCount);
        tvProductCount.setText(String.valueOf(intCount));
        btnAddCart.setVisibility(android.view.View.GONE);
        llcountItemMyCart.setVisibility(android.view.View.VISIBLE);

        final AddToCartModel ref = new AddToCartModel(mprdid, String.valueOf(intCount), option_id, option_value_id);

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

    @Click(R.id.lldecreasePrdCount)
    public void onDecreaseClick() {
        if (intCount <= 1) {
            intCount = intCount - 1;
            display(intCount);
            tvProductCount.setText(String.valueOf(intCount));
            // btnAddCart.setVisibility(android.view.View.VISIBLE);
            llcountItemMyCart.setVisibility(android.view.View.GONE);

            final UpdateToCartModel ref = new UpdateToCartModel(mprdid, String.valueOf(intCount));

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

            mPlaceHolderView.removeView(this);

        } else {
            intCount = intCount - 1;
            display(intCount);
            tvProductCount.setText(String.valueOf(intCount));

            final UpdateToCartModel ref = new UpdateToCartModel(mprdid, String.valueOf(intCount));

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

    @Click(R.id.llincreasePrdCount)
    public void onIncreaseClick() {
        intCount = intCount + 1;//display number in place of add to cart
        display(intCount);
        tvProductCount.setText(String.valueOf(intCount));

        final UpdateToCartModel ref = new UpdateToCartModel(mprdid, String.valueOf(intCount));

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
        tvProductCount.setText("" + number);
    }

}
