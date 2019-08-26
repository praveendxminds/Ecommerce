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
import com.nisarga.nisargaveggiez.ProfileSection.QuantityList;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
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

    public TextView mtextCartItemCount;
    public String mcustid, mprdid;
    public String murl, mtitle, mprice, mdisprice, mqty, str_priceValue, str_priceValue_1;


    PlaceHolderView mPlaceHolderView;
    String product_option_id[], product_option_value_id[], product_price[];
    String sQuantitySpinner, option_id, option_value_id, price;
    String productPrice;

    int intCount = 0;

    public cartItem(Context context, TextView textCartItemCount, String custid, String prdid, String url,
                    String title, String price, String disprice, String qty, PlaceHolderView placeHolderView) {

        mcontext = context;
        mcustid = custid;
        mprdid = prdid;
        murl = url;
        mtitle = title;
        mprice = price;
        mdisprice = disprice;
        mqty = qty;
        mtextCartItemCount = textCartItemCount;
        mPlaceHolderView = placeHolderView;
    }

    public cartItem(Context contxt) {
        mcontext = contxt;
    }

    public String getTitle() {
        return mtitle;
    }

    public String getPrice() {
        return mprice;
    }

    public String getUrl() {
        return murl;
    }

    @Resolve
    public void onResolved() {
        tvProductName.setText(mtitle);
        Glide.with(mcontext).load(murl).into(ivImage);

        /*if (mdisprice.equals("null")) {
            tvpriceOldMyCart.setVisibility(android.view.View.INVISIBLE);
        } else {
            double dbl_Price_1 = Double.parseDouble(mdisprice);//need to convert string to decimal
            str_priceValue_1 = String.format("%.2f", dbl_Price_1);//display only 2 decimal places of price
            tvpriceOldMyCart.setVisibility(android.view.View.VISIBLE);
            tvpriceOldMyCart.setText("₹" + " " + str_priceValue_1);
        }*/

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

    }

    @Click(R.id.llCartList)
    public void onCardClick() {
        Intent myIntent = new Intent(mcontext, ProductDetailHome.class);
        myIntent.putExtra("product_id", mprdid);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mcontext.startActivity(myIntent);

    }

   /* @Click(R.id.btnAddCart)
    public void AddToCartClick() {
        if (statusCount == true) {
            intCount = intCount + 1;//display number in place of add to cart
            display(intCount);
            btnAddCart.setVisibility(android.view.View.GONE);
            llcountItemMyCart.setVisibility(android.view.View.VISIBLE);
            statusCount = false;
        }
    }

    @Click(R.id.lldecreasePrdCount)
    public void onDecreaseClick() {
        if (intCount <= 1) {
            intCount = intCount - 1;
            display(intCount);
            mPlaceHolderView.removeView(this);
            statusCount = true;

        } else {
            intCount = intCount - 1;
            display(intCount);
        }
    }*/

    @Click(R.id.llincreasePrdCount)
    public void onIncreaseClick() {
        intCount = intCount + 1;//display number in place of add to cart
        display(intCount);
    }

    public void display(int number) {
        tvProductCount.setText("" + number);
    }

}
