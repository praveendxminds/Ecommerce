package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
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
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Toggle;
import com.nisarga.nisargaveggiez.retrofit.AddToCartModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


@NonReusable
@Layout(R.layout.home_page_dealofday_item_list)
public class HomePageDealOfDayItemList {

    @View(R.id.llDealOfDay)
    public LinearLayout llDealOfDay;

    @View(R.id.ivProductImage)
    public ImageView ivProductImage;

    @View(R.id.tvItemName)
    public TextView tvItemName;

    @View(R.id.tvItemPrice)
    public TextView tvItemPrice;

    @View(R.id.tvOldPrice)
    public TextView tvOldPrice;

    @View(R.id.spQuantity)
    public Spinner spQuantity;

    @View(R.id.lldecreasePrdCount)
    public LinearLayout lldecreasePrdCount;

    @View(R.id.llincreasePrdCount)
    public LinearLayout llincreasePrdCount;

    @View(R.id.tvNoOfCount)
    public TextView tvNoOfCount;

    @View(R.id.btnAddCart)
    public Button btnAddCart;

    @View(R.id.llAddCart)
    public LinearLayout llAddCart;

    APIInterface apiInterface;
    SessionManager session;
    Context mContext;
    PlaceHolderView mPlaceHolderView;

    String sProductId, sProductImage, sProductName, sProductPrice, sProductDis, sQuantity;
    int cartcount = 0;

    String product_option_id[], product_option_value_id[];
    String sQuantitySpinner, option_id, option_value_id;

    public HomePageDealOfDayItemList(Context context, String product_id, String image_url, String prod_name,
                                     String prod_price, String prod_discount, String quantity) {
        this.mContext = context;
        this.sProductId = product_id;
        this.sProductImage = image_url;
        this.sProductName = prod_name;
        this.sProductPrice = prod_price;
        this.sProductDis = prod_discount;
        this.sQuantity = quantity;
    }

    @Resolve
    public void onResolved() {
        session = new SessionManager(mContext);
        Glide.with(mContext).load(sProductImage).into(ivProductImage);
        tvItemName.setText(sProductName);

        double dbl_Price = Double.parseDouble(sProductPrice);//need to convert string to decimal
        String str_priceValue = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
        tvItemPrice.setText("₹" + " " + str_priceValue);

        if (sProductDis.equals("null")) {
            tvOldPrice.setVisibility(android.view.View.INVISIBLE);
        } else {
            double dbl_Discount = Double.parseDouble(sProductDis);//need to convert string to decimal
            String str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
            tvOldPrice.setVisibility(android.view.View.VISIBLE);
            tvOldPrice.setText("₹" + " " + str_disValue);
        }

        final ArrayList<String> product_qty_list = new ArrayList<>();

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final QuantityList quantityList = new QuantityList(sProductId);

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

                    ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item,
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
    }

    @Click(R.id.llDealOfDay)
    public void onLongClick() {
        Intent intent = new Intent(mContext, ProductDetailHome.class);
        intent.putExtra("product_id", sProductId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @Click(R.id.btnAddCart)
    public void addtocart() {
        cartcount = cartcount + 1;//display number in place of add to cart
        session.cartcount(cartcount);
        display(cartcount);
        tvNoOfCount.setText(String.valueOf(cartcount));
        btnAddCart.setVisibility(android.view.View.GONE);
        llAddCart.setVisibility(android.view.View.VISIBLE);

        final AddToCartModel ref = new AddToCartModel(sProductId, sQuantitySpinner, option_id, option_value_id);

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

    @Click(R.id.lldecreasePrdCount)
    public void onDecreaseClick() {
        if (cartcount <= 1) {
            cartcount = cartcount - 1;
            session.cartcount(cartcount);
            display(cartcount);
            tvNoOfCount.setText(String.valueOf(cartcount));
            btnAddCart.setVisibility(android.view.View.VISIBLE);
            llAddCart.setVisibility(android.view.View.GONE);
        } else {
            cartcount = cartcount - 1;
            session.cartcount(cartcount);
            display(cartcount);
            tvNoOfCount.setText(String.valueOf(cartcount));
        }
    }

    @Click(R.id.llincreasePrdCount)
    public void onIncreaseClick() {
        cartcount = cartcount + 1;//display number in place of add to cart
        session.cartcount(cartcount);
        display(cartcount);
        tvNoOfCount.setText(String.valueOf(cartcount));
    }

    public void display(int number) {
        tvNoOfCount.setText("" + number);
    }
}
