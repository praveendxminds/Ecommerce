package com.nisarga.nisargaveggiez.cart;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.nisarga.nisargaveggiez.Home.ProductDetailHome;
import com.nisarga.nisargaveggiez.Home.UpdateToCartModel;
import com.nisarga.nisargaveggiez.Home.UpdateToCartOptionsModel;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import com.nisarga.nisargaveggiez.R;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.nisarga.nisargaveggiez.cart.cart.llEmptyCart;
import static com.nisarga.nisargaveggiez.cart.cart.llShowCart;
import static com.nisarga.nisargaveggiez.cart.cart.mCartView;
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

    String mprdid, murl, mtitle, mdisprice, mqty, mprice, mOption, mRealprice, mRealdiscount;
    PlaceHolderView mPlaceHolderView;
    Object mOption_val;
    int mPos;
    JsonArray jsonElements;

    int intCount = 0;

    public cartItem(Context context, String prdid, String url, String title, String disprice, String qty,
                    PlaceHolderView placeHolderView, String price, String option_name,String realprice,String realdiscount,int pos,Object option_val) {

        mcontext = context;
        mprdid = prdid;
        murl = url;
        mtitle = title;
        mdisprice = disprice;
        mqty = qty;
        mPlaceHolderView = placeHolderView;
        mprice = price;
        mOption = option_name;
        mRealprice = realprice;
        mRealdiscount=realdiscount;
        mPos = pos;
        mOption_val = option_val;
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




        if (mOption.equals("null")) {
            tvQuantity.setText("1 Piece");
        } else {
            tvQuantity.setText(mOption);
        }

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
        if (intCount <= 1)
        {




            intCount = intCount - 1;
            if(intCount>=0) {
                tvProductCount.setText(String.valueOf(intCount));
            }




            if(mOption_val.equals("null")) {

                final UpdateToCartModel ref = new UpdateToCartModel(mprdid, String.valueOf(tvProductCount.getText()));

                apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<UpdateToCartModel> callAdd = apiInterface.updateAddToCart("api/cart/edit_new", session.getToken(), ref);
                callAdd.enqueue(new Callback<UpdateToCartModel>() {
                    @Override
                    public void onResponse(Call<UpdateToCartModel> call, Response<UpdateToCartModel> response) {
                        UpdateToCartModel resource = response.body();
                        if (resource.status.equals("success")) {


                            double price_total = Double.parseDouble(tvtotalAmount.getText().toString().replace("Total \u20B9 ", ""));
                            double price_new = Double.parseDouble(tvNewPrice.getText().toString().replace("₹ ", ""));
                            double price_old = Double.parseDouble(tvOldPrice.getText().toString().replace("₹ ", ""));
                            double price_new_single = Double.parseDouble(mRealdiscount.toString());
                            double price_old_single = Double.parseDouble(mRealprice.toString());
                            price_total = price_total - price_new_single;
                            tvtotalAmount.setText("Total" + " " + "\u20B9 " + String.valueOf(price_total));


                            tvNewPrice.setText("₹" + " " + String.valueOf(price_new - price_new_single));

                            if (!mdisprice.equals("null")) {
                                tvOldPrice.setText("₹" + " " + String.valueOf(price_old - price_old_single));
                            }


                            if (Integer.parseInt(tvProductCount.getText().toString()) <= 0)
                            {
                                mCartView.removeView(mPos);
                            }




                            if (tvTotalVeggies.getText().toString().equals("1 Item")) {
                                int cnt = Integer.parseInt(tvTotalVeggies.getText().toString().replace(" Item", ""));
                                cnt = cnt - 1;
                                tvTotalVeggies.setText(cnt + " " + "Item");



                            } else {
                                int cnt = Integer.parseInt(tvTotalVeggies.getText().toString().replace(" Items", ""));
                                cnt = cnt - 1;

                                tvTotalVeggies.setText(cnt + " " + "Items");
                            }


                            Log.d("tvTotalVeggies1", tvTotalVeggies.getText().toString());

                            if(tvTotalVeggies.getText().toString().equals("0 Item"))
                            {
                                tvTotalVeggies.setText("No Items");
                                llEmptyCart.setVisibility(android.view.View.VISIBLE);
                                llShowCart.setVisibility(android.view.View.GONE);

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

            }
            else
            {

                jsonElements = (JsonArray) new Gson().toJsonTree(mOption_val);

                String product_option_id = String.valueOf(jsonElements.get(0).getAsJsonObject().get("product_option_id")).replace("\"", "");
                String product_option_value_id = String.valueOf(jsonElements.get(0).getAsJsonObject().get("product_option_value_id")).replace("\"", "");


                final UpdateToCartOptionsModel ref = new UpdateToCartOptionsModel(mprdid,product_option_id,product_option_value_id, String.valueOf(tvProductCount.getText()));

                apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<UpdateToCartOptionsModel> callAdd = apiInterface.updateAddToCartwithoptions("api/cart/edit_new", session.getToken(), ref);
                callAdd.enqueue(new Callback<UpdateToCartOptionsModel>() {
                    @Override
                    public void onResponse(Call<UpdateToCartOptionsModel> call, Response<UpdateToCartOptionsModel> response) {
                        UpdateToCartOptionsModel resource = response.body();
                        if (resource.status.equals("success")) {


                            double price_total = Double.parseDouble(tvtotalAmount.getText().toString().replace("Total \u20B9 ", ""));
                            double price_new = Double.parseDouble(tvNewPrice.getText().toString().replace("₹ ", ""));
                            double price_old = Double.parseDouble(tvOldPrice.getText().toString().replace("₹ ", ""));
                            double price_new_single = Double.parseDouble(mRealdiscount.toString());
                            double price_old_single = Double.parseDouble(mRealprice.toString());
                            price_total = price_total - price_new_single;
                            tvtotalAmount.setText("Total" + " " + "\u20B9 " + String.valueOf(price_total));


                            tvNewPrice.setText("₹" + " " + String.valueOf(price_new - price_new_single));

                            if (!mdisprice.equals("null")) {
                                tvOldPrice.setText("₹" + " " + String.valueOf(price_old - price_old_single));
                            }


                            if (Integer.parseInt(tvProductCount.getText().toString()) <= 0)
                            {
                                mCartView.removeView(mPos);
                            }





                            if (tvTotalVeggies.getText().toString().equals("1 Item")) {
                                int cnt = Integer.parseInt(tvTotalVeggies.getText().toString().replace(" Item", ""));
                                cnt = cnt - 1;
                                tvTotalVeggies.setText(cnt + " " + "Item");

                            } else {
                                int cnt = Integer.parseInt(tvTotalVeggies.getText().toString().replace(" Items", ""));
                                cnt = cnt - 1;

                                tvTotalVeggies.setText(cnt + " " + "Items");
                            }

                            Log.d("tvTotalVeggies1", tvTotalVeggies.getText().toString());

                            if(tvTotalVeggies.getText().toString().equals("0 Item"))
                            {
                                tvTotalVeggies.setText("No Items");
                                llEmptyCart.setVisibility(android.view.View.VISIBLE);
                                llShowCart.setVisibility(android.view.View.GONE);

                            }




                            //Toast.makeText(getApplicationContext(), "Remove from Cart", Toast.LENGTH_LONG).show();
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




            if(mOption_val.equals("null"))
            {

                final UpdateToCartModel ref = new UpdateToCartModel(mprdid, String.valueOf(tvProductCount.getText()));

                apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<UpdateToCartModel> callAdd = apiInterface.updateAddToCart("api/cart/edit_new", session.getToken(), ref);
                callAdd.enqueue(new Callback<UpdateToCartModel>() {
                    @Override
                    public void onResponse(Call<UpdateToCartModel> call, Response<UpdateToCartModel> response) {
                        UpdateToCartModel resource = response.body();
                        if (resource.status.equals("success"))
                        {

                            double price_total = Double.parseDouble(tvtotalAmount.getText().toString().replace("Total \u20B9 ", ""));
                            double price_new = Double.parseDouble(tvNewPrice.getText().toString().replace("₹ ", ""));
                            double price_old = Double.parseDouble(tvOldPrice.getText().toString().replace("₹ ", ""));
                            double price_new_single = Double.parseDouble(mRealdiscount.toString());
                            double price_old_single = Double.parseDouble(mRealprice.toString());
                            price_total = price_total - price_new_single;
                            tvtotalAmount.setText("Total" + " " + "\u20B9 " + String.valueOf(price_total));


                            intCount = intCount - 1;
                            tvProductCount.setText(String.valueOf(intCount));


                            Log.d("price_new_real", String.valueOf(mRealprice));

                            tvNewPrice.setText("₹" + " " + String.valueOf(price_new-price_new_single));

                            if (!mdisprice.equals("null"))
                            {
                                tvOldPrice.setText("₹" + " " + String.valueOf(price_old-price_old_single));
                            }


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
            else
            {

                jsonElements = (JsonArray) new Gson().toJsonTree(mOption_val);

                String product_option_id = String.valueOf(jsonElements.get(0).getAsJsonObject().get("product_option_id")).replace("\"", "");
                String product_option_value_id = String.valueOf(jsonElements.get(0).getAsJsonObject().get("product_option_value_id")).replace("\"", "");


                final UpdateToCartOptionsModel ref = new UpdateToCartOptionsModel(mprdid,product_option_id,product_option_value_id, String.valueOf(tvProductCount.getText()));

                apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<UpdateToCartOptionsModel> callAdd = apiInterface.updateAddToCartwithoptions("api/cart/edit_new", session.getToken(), ref);
                callAdd.enqueue(new Callback<UpdateToCartOptionsModel>() {
                    @Override
                    public void onResponse(Call<UpdateToCartOptionsModel> call, Response<UpdateToCartOptionsModel> response) {
                        UpdateToCartOptionsModel resource = response.body();
                        if (resource.status.equals("success"))
                        {

                            double price_total = Double.parseDouble(tvtotalAmount.getText().toString().replace("Total \u20B9 ", ""));
                            double price_new = Double.parseDouble(tvNewPrice.getText().toString().replace("₹ ", ""));
                            double price_old = Double.parseDouble(tvOldPrice.getText().toString().replace("₹ ", ""));
                            double price_new_single = Double.parseDouble(mRealdiscount.toString());
                            double price_old_single = Double.parseDouble(mRealprice.toString());
                            price_total = price_total - price_new_single;
                            tvtotalAmount.setText("Total" + " " + "\u20B9 " + String.valueOf(price_total));


                            intCount = intCount - 1;
                            tvProductCount.setText(String.valueOf(intCount));


                            Log.d("price_new_real", String.valueOf(mRealprice));

                            tvNewPrice.setText("₹" + " " + String.valueOf(price_new-price_new_single));

                            if (!mdisprice.equals("null"))
                            {
                                tvOldPrice.setText("₹" + " " + String.valueOf(price_old-price_old_single));
                            }


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



        }
    }

    @Click(R.id.llincreasePrdCount)
    public void onIncreaseClick() {
        intCount = intCount + 1;//display number in place of add to cart
        tvProductCount.setText(String.valueOf(intCount));



        if(mOption_val.equals("null"))
        {

            final UpdateToCartModel ref = new UpdateToCartModel(mprdid, String.valueOf(intCount));

            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<UpdateToCartModel> callAdd = apiInterface.updateAddToCart("api/cart/edit_new", session.getToken(), ref);
            callAdd.enqueue(new Callback<UpdateToCartModel>() {
                @Override
                public void onResponse(Call<UpdateToCartModel> call, Response<UpdateToCartModel> response) {
                    UpdateToCartModel resource = response.body();
                    if (resource.status.equals("success")) {

                        double price_total = Double.parseDouble(tvtotalAmount.getText().toString().replace("Total \u20B9 ", ""));
                        double price_new = Double.parseDouble(tvNewPrice.getText().toString().replace("₹ ", ""));
                        double price_old = Double.parseDouble(tvOldPrice.getText().toString().replace("₹ ", ""));
                        double price_new_single = Double.parseDouble(mRealdiscount.toString());
                        double price_old_single = Double.parseDouble(mRealprice.toString());
                        price_total = price_total + price_new_single;
                        tvtotalAmount.setText("Total" + " " + "\u20B9 " + String.valueOf(price_total));


                        Log.d("price_new_real", String.valueOf(mRealprice));

                        tvNewPrice.setText("₹" + " " + String.valueOf(price_new + price_new_single));

                        if (!mdisprice.equals("null")) {
                            tvOldPrice.setText("₹" + " " + String.valueOf(price_old + price_old_single));
                        }


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



        }
        else {

            jsonElements = (JsonArray) new Gson().toJsonTree(mOption_val);

            String product_option_id = String.valueOf(jsonElements.get(0).getAsJsonObject().get("product_option_id")).replace("\"", "");
            String product_option_value_id = String.valueOf(jsonElements.get(0).getAsJsonObject().get("product_option_value_id")).replace("\"", "");


            final UpdateToCartOptionsModel ref = new UpdateToCartOptionsModel(mprdid,product_option_id,product_option_value_id, String.valueOf(intCount));

            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<UpdateToCartOptionsModel> callAdd = apiInterface.updateAddToCartwithoptions("api/cart/edit_new", session.getToken(), ref);
            callAdd.enqueue(new Callback<UpdateToCartOptionsModel>() {
                @Override
                public void onResponse(Call<UpdateToCartOptionsModel> call, Response<UpdateToCartOptionsModel> response) {
                    UpdateToCartOptionsModel resource = response.body();
                    if (resource.status.equals("success")) {

                        double price_total = Double.parseDouble(tvtotalAmount.getText().toString().replace("Total \u20B9 ", ""));
                        double price_new = Double.parseDouble(tvNewPrice.getText().toString().replace("₹ ", ""));
                        double price_old = Double.parseDouble(tvOldPrice.getText().toString().replace("₹ ", ""));
                        double price_new_single = Double.parseDouble(mRealdiscount.toString());
                        double price_old_single = Double.parseDouble(mRealprice.toString());
                        price_total = price_total + price_new_single;
                        tvtotalAmount.setText("Total" + " " + "\u20B9 " + String.valueOf(price_total));


                        Log.d("price_new_real", String.valueOf(mRealprice));

                        tvNewPrice.setText("₹" + " " + String.valueOf(price_new + price_new_single));

                        if (!mdisprice.equals("null")) {
                            tvOldPrice.setText("₹" + " " + String.valueOf(price_old + price_old_single));
                        }


                        //   Toast.makeText(getApplicationContext(), "Added in Cart", Toast.LENGTH_LONG).show();
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


    }
}
