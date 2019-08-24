package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.ProfileSection.QuantityList;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Toggle;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.AddToCartModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

@NonReusable
@Layout(R.layout.similar_products_list)
public class SimilarProductsListItem {

    @View(R.id.imageCategorySimilarPrd)
    public ImageView imageCategorySimilarPrd;

    @View(R.id.titleCategorySimilarPrd)
    public TextView titleCategorySimilarPrd;

    @View(R.id.llSimilarProduct)
    public LinearLayout llSimilarProduct;

    @View(R.id.newPriceCategorySimilarPrd)
    public TextView newPriceCategorySimilarPrd;

    @View(R.id.oldPriceCategorySimilarPrd)
    public TextView oldPriceCategorySimilarPrd;

    @View(R.id.qtyCategorySimilarPrd)
    public Spinner qtyCategorySimilarPrd;

    @View(R.id.llCountPrd)
    public LinearLayout llCountPrd;

    @View(R.id.imgBtn_decreSimPrd)
    public ImageButton imgBtn_decreSimPrd;

    @View(R.id.imgBtn_increSimPrd)
    public ImageButton imgBtn_increSimPrd;

    @View(R.id.tv_countSimPrd)
    public TextView tv_countSimPrd;

    @View(R.id.btnAddCategorySimilarPrd)
    public Button btnAddCategorySimilarPrd;

    APIInterface apiInterface;
    SessionManager session;
    Context mContext;

    public String mUlr;
    public PlaceHolderView mPlaceHolderView;
    public TextView mtextCartItemCount;
    public String mrelated_id;
    public String mPrd_id;
    public String mHeading;
    public String mPrdImgUrl, mPrice, mQty;
    public String mdiscount;
    public Boolean status = true;
    int minteger = 0;
    public String str_priceValue, str_disValue;

    String product_option_id[], product_option_value_id[], product_price[];
    String sQuantitySpinner, option_id, option_value_id, price;
    String productPrice;

    public SimilarProductsListItem(Context context, TextView textCartItemCount, PlaceHolderView placeHolderView,
                                   String related_id, String prd_id, String url, String heading, String price,
                                   String qty, String discount) {
        mContext = context;
        mtextCartItemCount = textCartItemCount;
        mPlaceHolderView = placeHolderView;
        mrelated_id = related_id;
        mPrd_id = prd_id;
        mPrdImgUrl = url;
        mHeading = heading;
        mPrice = price;
        mQty = qty;
        mdiscount = discount;
    }

    @Resolve
    public void onResolved() {
        session = new SessionManager(mContext);
        Glide.with(mContext).load(mPrdImgUrl).into(imageCategorySimilarPrd);
        titleCategorySimilarPrd.setText(mHeading);

        if (mdiscount.equals("null")) {
            oldPriceCategorySimilarPrd.setVisibility(android.view.View.INVISIBLE);
        } else {
            double dbl_Discount = Double.parseDouble(mdiscount);//need to convert string to decimal
            str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
            oldPriceCategorySimilarPrd.setVisibility(android.view.View.VISIBLE);
            oldPriceCategorySimilarPrd.setText("₹" + " " + str_disValue);
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
                    qtyCategorySimilarPrd.setAdapter(itemsAdapter);
                    qtyCategorySimilarPrd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                            sQuantitySpinner = product_qty_list.get(position);
                            option_id = product_option_id[position];
                            option_value_id = product_option_value_id[position];
                            price = product_price[position];

                            double dbl_Price = Double.parseDouble(price);//need to convert string to decimal
                            productPrice = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
                            titleCategorySimilarPrd.setText("₹" + " " + productPrice);
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

    @Click(R.id.llSimilarProduct)
    public void onLongClick() {
        Intent intent = new Intent(mContext, ProductDetailHome.class);
        intent.putExtra("product_id", mPrd_id);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @Click(R.id.btnAddCategorySimilarPrd)
    public void AddToCartClick() {
        minteger = minteger + 1;//display number in place of add to cart
        session.cartcount(minteger);
        display(minteger);
        tv_countSimPrd.setText(minteger);
        btnAddCategorySimilarPrd.setVisibility(android.view.View.GONE);
        llCountPrd.setVisibility(android.view.View.VISIBLE);

        final AddToCartModel ref = new AddToCartModel(mPrd_id, sQuantitySpinner, option_id, option_value_id);

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

    @Click(R.id.imgBtn_increSimPrd)
    public void onIncreaseClick() {
        minteger = minteger + 1;//display number in place of add to cart
        session.cartcount(minteger);
        display(minteger);
        tv_countSimPrd.setText(minteger);
    }

    @Click(R.id.imgBtn_decreSimPrd)
    public void onDecreaseClick() {
        if (minteger <= 1) {
            minteger = minteger - 1;
            session.cartcount(minteger);
            display(minteger);
            tv_countSimPrd.setText(minteger);
            btnAddCategorySimilarPrd.setVisibility(android.view.View.VISIBLE);
            llCountPrd.setVisibility(android.view.View.GONE);
        } else {
            minteger = minteger - 1;
            session.cartcount(minteger);
            display(minteger);
            tv_countSimPrd.setText(minteger);
        }
    }

    public void display(int number) {
        tv_countSimPrd.setText("" + number);
    }
}
