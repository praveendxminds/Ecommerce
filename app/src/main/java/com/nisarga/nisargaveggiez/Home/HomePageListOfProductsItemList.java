package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
import com.nisarga.nisargaveggiez.retrofit.AddCartNullSpinner;
import com.nisarga.nisargaveggiez.retrofit.AddToCartModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.nisarga.nisargaveggiez.Home.HomePage.hometotalCartItemCount;


@NonReusable
@Layout(R.layout.home_page_listproducts_item_list)
public class HomePageListOfProductsItemList {

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

    String sProductId, sProductImage, sProductName, sadd_product_quantity_in_cart,dsc_price,cut_price;

    String quantity_name, option_id, option_value_id, scartcount, price, sDiscount;
    Object spnrqty;
    ArrayList<String> cntvariable = new ArrayList<>();
    ArrayList<String> putcntlst = new ArrayList<>();
    ArrayList<String> getlists = new ArrayList<>();
    TinyDB tinydb;

    public HomePageListOfProductsItemList(Context context, String prod_id, String prod_image, String prod_name,
                                          Object spnrqty, String add_product_quantity_in_cart,String dsc_price,String cut_price) {
        mContext = context;
        sProductId = prod_id;
        sProductImage = prod_image;
        sProductName = prod_name;
        this.spnrqty = spnrqty;
        this.sadd_product_quantity_in_cart = add_product_quantity_in_cart;
        this.dsc_price = dsc_price;
        this.cut_price = cut_price;

    }

    @Resolve
    public void onResolved() {
        session = new SessionManager(mContext);
        Glide.with(mContext).load(sProductImage).into(ivProductImage);
        tvItemName.setText(sProductName);

        final ArrayList<String> cart_count = new ArrayList<>();
        final ArrayList<String> product_option_id = new ArrayList<>();
        final ArrayList<String> product_option_value_id = new ArrayList<>();
        final ArrayList<String> name = new ArrayList<>();
        final ArrayList<String> product_price = new ArrayList<>();
        final ArrayList<String> discount_price = new ArrayList<>();
        tinydb = new TinyDB(getApplicationContext());

        if (!spnrqty.equals("null")) {

            spQuantity.setVisibility(android.view.View.VISIBLE);
            llQuantityList.setVisibility(android.view.View.GONE);


            JsonArray jsonElements = (JsonArray) new Gson().toJsonTree(spnrqty);

            for (int j = 0; j < jsonElements.size(); j++) {
                cart_count.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("cart_count")).replace("\"", ""));
                product_option_id.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("product_option_id")).replace("\"", ""));
                product_option_value_id.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("product_option_value_id")).replace("\"", ""));
                name.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("name")).replace("\"", ""));
                product_price.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("price")));
                discount_price.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("discount_price")));

                putcntlst.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("cart_count")).replace("\"", ""));

                quantity_name = name.get(j);

                double dbl_Price = Double.parseDouble(String.valueOf(product_price.get(0)));//need to convert string to decimal
                String productPrice = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
                tvItemPrice.setText("₹" + " " + productPrice);

                if (discount_price.equals("0")) {
                    tvOldPrice.setVisibility(android.view.View.INVISIBLE);
                } else {
                    double dbl_Discount = Double.parseDouble(String.valueOf(discount_price.get(0)));//need to convert string to decimal
                    String str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
                    tvOldPrice.setVisibility(android.view.View.VISIBLE);
                    tvOldPrice.setText("₹" + " " + str_disValue);
                }
            }
        } else if (spnrqty.equals("null"))
        {

            double dbl_Price = Double.parseDouble(cut_price);//need to convert string to decimal
            String productPrice = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
            tvItemPrice.setText("₹" + " " + productPrice);

            if (dsc_price.equals("0")) {
                tvOldPrice.setVisibility(android.view.View.INVISIBLE);
            } else {
                double dbl_Discount = Double.parseDouble(dsc_price);//need to convert string to decimal
                String str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
                tvOldPrice.setVisibility(android.view.View.VISIBLE);
                tvOldPrice.setText("₹" + " " + str_disValue);
            }

            tvNoOfCount.setText(sadd_product_quantity_in_cart);
            spQuantity.setVisibility(android.view.View.GONE);
            llQuantityList.setVisibility(android.view.View.VISIBLE);
        }
        tinydb.putListString(sProductId, putcntlst);

        getlists = tinydb.getListString(sProductId);

        if (putcntlst.size() > 0) {
            tvNoOfCount.setText(String.valueOf(putcntlst.get(0)));
        }


        Log.d("tvNoOfCount", tvNoOfCount.getText().toString());
        if (tvNoOfCount.getText().toString().equals("0")) {

            btnAddCart.setVisibility(android.view.View.VISIBLE);
            llAddCart.setVisibility(android.view.View.GONE);
        } else {
            btnAddCart.setVisibility(android.view.View.GONE);
            llAddCart.setVisibility(android.view.View.VISIBLE);
            if (putcntlst.size() > 0) {
                tvNoOfCount.setText(String.valueOf(putcntlst.get(0)));
            }
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

                tvNoOfCount.setText(String.valueOf(putcntlst.get(position)));

                double dbl_Price = Double.parseDouble(price);//need to convert string to decimal
                String productPrice = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
                tvItemPrice.setText("₹" + " " + productPrice);

                if (sDiscount.equals("0")) {
                    tvOldPrice.setVisibility(android.view.View.INVISIBLE);
                } else {
                    double dbl_Discount = Double.parseDouble(sDiscount);//need to convert string to decimal
                    String str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
                    tvOldPrice.setVisibility(android.view.View.VISIBLE);
                    tvOldPrice.setText("₹" + " " + str_disValue);
                }

                if (tvNoOfCount.getText().toString().equals("0")) {
                    btnAddCart.setVisibility(android.view.View.VISIBLE);
                    llAddCart.setVisibility(android.view.View.GONE);
                } else {
                    btnAddCart.setVisibility(android.view.View.GONE);
                    llAddCart.setVisibility(android.view.View.VISIBLE);
                    tvNoOfCount.setText(String.valueOf(putcntlst.get(position)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

        if (!spnrqty.equals("null")) {
            int i = Integer.parseInt(String.valueOf(putcntlst.get(spQuantity.getSelectedItemPosition())));
            i = i + 1;
            tvNoOfCount.setText(String.valueOf(i));
            putcntlst.set(spQuantity.getSelectedItemPosition(), String.valueOf(i));
            tinydb.putListString(sProductId, putcntlst);
            btnAddCart.setVisibility(android.view.View.GONE);
            llAddCart.setVisibility(android.view.View.VISIBLE);

            final AddToCartModel ref = new AddToCartModel(sProductId, String.valueOf(tvNoOfCount.getText()), option_id,
                    option_value_id);
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<AddToCartModel> callAdd = apiInterface.callAddToCart("api/cart/add", session.getToken(), ref);
            callAdd.enqueue(new Callback<AddToCartModel>() {
                @Override
                public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                    AddToCartModel resource = response.body();
                    if (resource.status.equals("success")) {
                        //  Toast.makeText(getApplicationContext(), "Added in Cart", Toast.LENGTH_LONG).show();
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
            tvNoOfCount.setText(sadd_product_quantity_in_cart);

            btnAddCart.setVisibility(android.view.View.GONE);
            llAddCart.setVisibility(android.view.View.VISIBLE);

            final AddCartNullSpinner nullValue = new AddCartNullSpinner(sProductId, String.valueOf(tvNoOfCount.getText()));

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
        }
    }

    @Click(R.id.lldecreasePrdCount)
    public void onDecreaseClick() {
        if (!spnrqty.equals("null")) {
            if (Integer.parseInt(tvNoOfCount.getText().toString()) <= 1) {
                Integer total_crtcnt = session.getCartCount();
                total_crtcnt = total_crtcnt - 1;
                session.cartcount(total_crtcnt);
                hometotalCartItemCount.setText(String.valueOf(total_crtcnt));

                int i = Integer.parseInt(String.valueOf(putcntlst.get(spQuantity.getSelectedItemPosition())));
                i = i - 1;
                tvNoOfCount.setText(String.valueOf(i));
                putcntlst.set(spQuantity.getSelectedItemPosition(), String.valueOf(i));
                tinydb.putListString(sProductId, putcntlst);

                btnAddCart.setVisibility(android.view.View.VISIBLE);
                llAddCart.setVisibility(android.view.View.GONE);

                final UpdateToCartOptionsModel ref = new UpdateToCartOptionsModel(sProductId,option_id,option_value_id, String.valueOf(tvNoOfCount.getText()));

                apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<UpdateToCartOptionsModel> callAdd = apiInterface.updateAddToCartwithoptions("api/cart/edit_new", session.getToken(), ref);
                callAdd.enqueue(new Callback<UpdateToCartOptionsModel>() {
                    @Override
                    public void onResponse(Call<UpdateToCartOptionsModel> call, Response<UpdateToCartOptionsModel> response) {
                        UpdateToCartOptionsModel resource = response.body();
                        if (resource.status.equals("success")) {
                            // Toast.makeText(getApplicationContext(), "Remove from Cart", Toast.LENGTH_LONG).show();
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
                tvNoOfCount.setText(String.valueOf(i));
                putcntlst.set(spQuantity.getSelectedItemPosition(), String.valueOf(i));
                tinydb.putListString(sProductId, putcntlst);

                final UpdateToCartOptionsModel ref = new UpdateToCartOptionsModel(sProductId,option_id,option_value_id, String.valueOf(tvNoOfCount.getText()));

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
            if (Integer.parseInt(tvNoOfCount.getText().toString()) <= 1) {
                Integer total_crtcnt = session.getCartCount();
                total_crtcnt = total_crtcnt - 1;
                session.cartcount(total_crtcnt);
                hometotalCartItemCount.setText(String.valueOf(total_crtcnt));

                int i = Integer.parseInt(sadd_product_quantity_in_cart);
                i = i - 1;
                sadd_product_quantity_in_cart = String.valueOf(i);
                tvNoOfCount.setText(sadd_product_quantity_in_cart);

                btnAddCart.setVisibility(android.view.View.VISIBLE);
                llAddCart.setVisibility(android.view.View.GONE);

                final UpdateToCartModel ref = new UpdateToCartModel(sProductId, String.valueOf(tvNoOfCount.getText()));

                apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<UpdateToCartModel> callAdd = apiInterface.updateAddToCart("api/cart/edit_new", session.getToken(), ref);
                callAdd.enqueue(new Callback<UpdateToCartModel>() {
                    @Override
                    public void onResponse(Call<UpdateToCartModel> call, Response<UpdateToCartModel> response) {
                        UpdateToCartModel resource = response.body();
                        if (resource.status.equals("success")) {
                            // Toast.makeText(getApplicationContext(), "Remove from Cart", Toast.LENGTH_LONG).show();
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
                tvNoOfCount.setText(sadd_product_quantity_in_cart);

                final UpdateToCartModel ref = new UpdateToCartModel(sProductId, String.valueOf(tvNoOfCount.getText()));

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

    @Click(R.id.llincreasePrdCount)
    public void onIncreaseClick() {
        if (!spnrqty.equals("null")) {
            int i = Integer.parseInt(String.valueOf(putcntlst.get(spQuantity.getSelectedItemPosition())));
            i = i + 1;
            tvNoOfCount.setText(String.valueOf(i));
            putcntlst.set(spQuantity.getSelectedItemPosition(), String.valueOf(i));
            tinydb.putListString(sProductId, putcntlst);

            final UpdateToCartOptionsModel ref = new UpdateToCartOptionsModel(sProductId,option_id,option_value_id, String.valueOf(tvNoOfCount.getText()));

            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<UpdateToCartOptionsModel> callAdd = apiInterface.updateAddToCartwithoptions("api/cart/edit_new", session.getToken(), ref);
            callAdd.enqueue(new Callback<UpdateToCartOptionsModel>() {
                @Override
                public void onResponse(Call<UpdateToCartOptionsModel> call, Response<UpdateToCartOptionsModel> response) {
                    UpdateToCartOptionsModel resource = response.body();
                    if (resource.status.equals("success")) {
                        //  Toast.makeText(getApplicationContext(), "Added in Cart", Toast.LENGTH_LONG).show();
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
            tvNoOfCount.setText(sadd_product_quantity_in_cart);

            final UpdateToCartModel ref = new UpdateToCartModel(sProductId, String.valueOf(tvNoOfCount.getText()));

            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<UpdateToCartModel> callAdd = apiInterface.updateAddToCart("api/cart/edit_new", session.getToken(), ref);
            callAdd.enqueue(new Callback<UpdateToCartModel>() {
                @Override
                public void onResponse(Call<UpdateToCartModel> call, Response<UpdateToCartModel> response) {
                    UpdateToCartModel resource = response.body();
                    if (resource.status.equals("success")) {
                        //  Toast.makeText(getApplicationContext(), "Added in Cart", Toast.LENGTH_LONG).show();
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
