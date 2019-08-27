package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.nisarga.nisargaveggiez.ProfileSection.QuantityList;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.cart.DeliverydateAdapter;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.nisarga.nisargaveggiez.retrofit.AddToCartModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.nisarga.nisargaveggiez.Home.HomePage.hometotalCartItemCount;


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

    @View(R.id.llQuantityList)
    public LinearLayout llQuantityList;

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

    String sProductId, sProductImage, sProductName, sProductDis, sAddCart;
    int cartcount = 0;

    String sQuantitySpinner, option_id, option_value_id, price;
    String productPrice;
    Object spnrqty;

    public HomePageDealOfDayItemList(Context context, String product_id, String image_url, String prod_name,
                                     String prod_discount, String addCart, Object spnrqty) {
        this.mContext = context;
        this.sProductId = product_id;
        this.sProductImage = image_url;
        this.sProductName = prod_name;
        this.sProductDis = prod_discount;
        this.sAddCart = addCart;
        this.spnrqty = spnrqty;
    }

    @Resolve
    public void onResolved() {
        session = new SessionManager(mContext);
        Glide.with(mContext).load(sProductImage).into(ivProductImage);
        tvItemName.setText(sProductName);


        if (sProductDis.equals("null")) {
            tvOldPrice.setVisibility(android.view.View.INVISIBLE);
        } else {
            double dbl_Discount = Double.parseDouble(sProductDis);//need to convert string to decimal
            String str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
            tvOldPrice.setVisibility(android.view.View.VISIBLE);
            tvOldPrice.setText("₹" + " " + str_disValue);
        }

        if (sAddCart.equals("0")) {
            btnAddCart.setVisibility(android.view.View.VISIBLE);
            llAddCart.setVisibility(android.view.View.GONE);
        } else {
            btnAddCart.setVisibility(android.view.View.GONE);
            llAddCart.setVisibility(android.view.View.VISIBLE);
            tvNoOfCount.setText(sAddCart);
        }

        final ArrayList<String> product_qty_list = new ArrayList<>();
        final ArrayList<String> product_option_id = new ArrayList<>();
        final ArrayList<String> product_option_value_id = new ArrayList<>();
        final ArrayList<String> product_price = new ArrayList<>();

        final QuantityList quantityList = new QuantityList(sProductId);

        int i = 0;



        if (!spnrqty.equals("null")) {
            JsonArray jsonElements = (JsonArray) new Gson().toJsonTree(spnrqty);
          //  product_option_id = new String[jsonElements.size()];
          //  product_option_value_id = new String[jsonElements.size()];
          //  product_price = new String[jsonElements.size()];

            for (int j = 0; j < jsonElements.size(); j++) {
                Log.d("qqqqqqqqqqqqqqq", String.valueOf(jsonElements.get(j).getAsJsonObject().get("name")));
                product_qty_list.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("name")).replace("\"", ""));

                product_option_id.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("product_option_id")).replace("\"", ""));
                product_option_value_id.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("product_option_value_id")).replace("\"", ""));
                product_price.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("price")));


                sQuantitySpinner = product_qty_list.get(j);

                double dbl_Price = Double.parseDouble(String.valueOf(product_price.get(0)));//need to convert string to decimal
                productPrice = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
                tvItemPrice.setText("₹" + " " + productPrice);
            }
        }
        else
            {

        }


       // ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, product_option_id);
       // spQuantity.setAdapter(itemsAdapter);

        spQuantity.setAdapter(new QtyspinnerAdapter(getApplicationContext(), product_option_value_id, product_option_id,product_qty_list,product_price));

        spQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                sQuantitySpinner = product_qty_list.get(position);
                option_id = product_option_id.get(position);
                option_value_id = product_option_value_id.get(position);
                price = product_price.get(position);

                Log.d("product_price", String.valueOf(price));



                double dbl_Price = Double.parseDouble(price);//need to convert string to decimal
                productPrice = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
                tvItemPrice.setText("₹" + " " + productPrice);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*
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

                if (!spnrqty.equals("null")) {
                    JsonArray jsonElements = (JsonArray) new Gson().toJsonTree(spnrqty);
                    for (int j = 0; j < jsonElements.size(); j++) {
                        Log.d("qqqqqqqqqqqqqqq", String.valueOf(jsonElements.get(j).getAsJsonObject().get("name")));
                        product_qty_list.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("name")).replace("\"", ""));
                        product_option_id[i] = String.valueOf(jsonElements.get(j).getAsJsonObject().get("product_option_id")).replace("\"", "");
                        product_option_value_id[i] = String.valueOf(jsonElements.get(j).getAsJsonObject().get("product_option_value_id")).replace("\"", "");
                        product_price[i] = String.valueOf(jsonElements.get(j).getAsJsonObject().get("price")).replace("\"", "");

                        sQuantitySpinner = product_qty_list.get(j);
                        option_id = product_option_id[i];
                        option_value_id = product_option_value_id[i];
                        price = product_price[i];

                        double dbl_Price = Double.parseDouble(price);//need to convert string to decimal
                        productPrice = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
                        tvItemPrice.setText("₹" + " " + productPrice);
                    }
                } else {

                }

                ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, product_qty_list);
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
                        tvItemPrice.setText("₹" + " " + productPrice);
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

        */

        cartcount = Integer.parseInt(sAddCart);
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
        Integer total_crtcnt = session.getCartCount();
        total_crtcnt = total_crtcnt + 1;
        session.cartcount(total_crtcnt);
        hometotalCartItemCount.setText(String.valueOf(total_crtcnt));

        cartcount = cartcount + 1;//display number in place of add to cart
        display(cartcount);
        tvNoOfCount.setText(String.valueOf(cartcount));
        btnAddCart.setVisibility(android.view.View.GONE);
        llAddCart.setVisibility(android.view.View.VISIBLE);

        final AddToCartModel ref = new AddToCartModel(sProductId, String.valueOf(cartcount), option_id, option_value_id);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AddToCartModel> callAdd = apiInterface.callAddToCart("api/cart/add", session.getToken(), ref);
        callAdd.enqueue(new Callback<AddToCartModel>() {
            @Override
            public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                AddToCartModel resource = response.body();
                if (resource.status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Added in Cart", Toast.LENGTH_LONG).show();
                    session.addCartId(resource.cart_id);
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
        if (cartcount <= 1) {
            Integer total_crtcnt = session.getCartCount();
            total_crtcnt = total_crtcnt - 1;
            session.cartcount(total_crtcnt);
            hometotalCartItemCount.setText(String.valueOf(total_crtcnt));

            cartcount = cartcount - 1;
            display(cartcount);
            tvNoOfCount.setText(String.valueOf(cartcount));
            btnAddCart.setVisibility(android.view.View.VISIBLE);
            llAddCart.setVisibility(android.view.View.GONE);

            final UpdateToCartModel ref = new UpdateToCartModel(sProductId, String.valueOf(cartcount));
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
            cartcount = cartcount - 1;
            display(cartcount);
            tvNoOfCount.setText(String.valueOf(cartcount));

            final UpdateToCartModel ref = new UpdateToCartModel(sProductId, String.valueOf(cartcount));
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
        cartcount = cartcount + 1;//display number in place of add to cart
        display(cartcount);
        tvNoOfCount.setText(String.valueOf(cartcount));

        final UpdateToCartModel ref = new UpdateToCartModel(sProductId, String.valueOf(cartcount));

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
        tvNoOfCount.setText("" + number);
    }
}
