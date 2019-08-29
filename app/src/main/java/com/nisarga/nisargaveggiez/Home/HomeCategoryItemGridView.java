package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
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
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.AddCartNullSpinner;
import com.nisarga.nisargaveggiez.retrofit.AddToCartModel;
import com.nisarga.nisargaveggiez.retrofit.InsertWishListItems;
import com.nisarga.nisargaveggiez.retrofit.RemoveWishListItem;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.nisarga.nisargaveggiez.Home.HomeCategory.cartItemCount;

/**
 * Created by sushmita on 17th June 2019
 */

@NonReusable
@Layout(R.layout.home_category_grid_item)
public class HomeCategoryItemGridView {

    @View(R.id.llProductGridView)
    public LinearLayout llProductGridView;

    @View(R.id.tvProductName)
    public TextView tvProductName;

    @View(R.id.ivbtnAddWishlist)
    public ImageButton ivbtnAddWishlist;

    @View(R.id.ivImage)
    public ImageView ivImage;

    @View(R.id.spQuantity)
    public Spinner spQuantity;

    @View(R.id.llQuantityList)
    public LinearLayout llQuantityList;

    @View(R.id.tvProdPrice)
    public TextView tvProdPrice;

    @View(R.id.tvProdOldPrice)
    public TextView tvProdOldPrice;

    @View(R.id.llProductCount)
    public LinearLayout llProductCount;

    @View(R.id.llProductDecrese)
    public LinearLayout llProductDecrese;

    @View(R.id.tvProductCount)
    public TextView tvProductCount;

    @View(R.id.llProductIncrease)
    public LinearLayout llProductIncrease;

    @View(R.id.btnAddCart)
    public Button btnAddCart;

    SessionManager session;
    APIInterface apiInterface;
    Context mContext;

    String prdId, image, prdName, prdWhishStatus, sadd_product_quantity_in_cart;

    String quantity_name, option_id, option_value_id, scartcount, price, sDiscount;
    Object spnrqty;
    ArrayList<String> cntvariable = new ArrayList<>();
    ArrayList<String> putcntlst = new ArrayList<>();
    ArrayList<String> getlists = new ArrayList<>();
    TinyDB tinydb;

    public boolean state = false;

    public HomeCategoryItemGridView(Context context, String productId, String image_url, String productName,
                                    String whishlistStatus, Object spnrqty, String add_product_quantity_in_cart) {
        this.mContext = context;
        this.prdId = productId;
        this.image = image_url;
        this.prdName = productName;
        this.prdWhishStatus = whishlistStatus;
        this.spnrqty = spnrqty;
        this.sadd_product_quantity_in_cart = add_product_quantity_in_cart;
    }

    public String getTitle() {
        return prdName;
    }

    @Resolve
    public void onResolved() {
        session = new SessionManager(mContext);
        tvProductName.setText(prdName);
        Glide.with(mContext).load(image).into(ivImage);

        if (prdWhishStatus.equals("0")) {
            ivbtnAddWishlist.setBackgroundResource(R.drawable.wishlistgrey);
        } else {
            ivbtnAddWishlist.setBackgroundResource(R.drawable.wishlist_red);
        }

        final ArrayList<String> cart_count = new ArrayList<>();
        final ArrayList<String> product_option_id = new ArrayList<>();
        final ArrayList<String> product_option_value_id = new ArrayList<>();
        final ArrayList<String> name = new ArrayList<>();
        final ArrayList<String> product_price = new ArrayList<>();
        final ArrayList<String> discount_price = new ArrayList<>();
        tinydb = new TinyDB(getApplicationContext());

        if (!spnrqty.equals("null")) {
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
                tvProdPrice.setText("₹" + " " + productPrice);

                if (discount_price.equals("0")) {
                    tvProdOldPrice.setVisibility(android.view.View.INVISIBLE);
                } else {
                    double dbl_Discount = Double.parseDouble(String.valueOf(discount_price.get(0)));//need to convert string to decimal
                    String str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
                    tvProdOldPrice.setVisibility(android.view.View.VISIBLE);
                    tvProdOldPrice.setText("₹" + " " + str_disValue);
                }
            }
        } else if (spnrqty.equals("null")) {
            tvProductCount.setText(sadd_product_quantity_in_cart);
            spQuantity.setVisibility(android.view.View.GONE);
            llQuantityList.setVisibility(android.view.View.VISIBLE);
        }

        tinydb.putListString(prdId, putcntlst);

        getlists = tinydb.getListString(prdId);

        if (putcntlst.size() > 0) {
            tvProductCount.setText(String.valueOf(putcntlst.get(0)));
        }

        Log.d("tvNoOfCount", tvProductCount.getText().toString());
        if (tvProductCount.getText().toString().equals("0")) {
            btnAddCart.setVisibility(android.view.View.VISIBLE);
            llProductCount.setVisibility(android.view.View.GONE);
        } else {
            btnAddCart.setVisibility(android.view.View.GONE);
            llProductCount.setVisibility(android.view.View.VISIBLE);
            if (putcntlst.size() > 0) {
                tvProductCount.setText(String.valueOf(putcntlst.get(0)));
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

                tvProductCount.setText(String.valueOf(putcntlst.get(position)));

                double dbl_Price = Double.parseDouble(price);//need to convert string to decimal
                String productPrice = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
                tvProdPrice.setText("₹" + " " + productPrice);

                if (sDiscount.equals("0")) {
                    tvProdOldPrice.setVisibility(android.view.View.INVISIBLE);
                } else {
                    double dbl_Discount = Double.parseDouble(sDiscount);//need to convert string to decimal
                    String str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
                    tvProdOldPrice.setVisibility(android.view.View.VISIBLE);
                    tvProdOldPrice.setText("₹" + " " + str_disValue);
                }

                if (tvProductCount.getText().toString().equals("0")) {
                    btnAddCart.setVisibility(android.view.View.VISIBLE);
                    llProductCount.setVisibility(android.view.View.GONE);
                } else {
                    btnAddCart.setVisibility(android.view.View.GONE);
                    llProductCount.setVisibility(android.view.View.VISIBLE);
                    tvProductCount.setText(String.valueOf(putcntlst.get(position)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Click(R.id.llProductGridView)
    public void onCardClick() {
        Intent myIntent = new Intent(mContext, ProductDetailHome.class);
        myIntent.putExtra("product_id", prdId);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);
    }

    @Click(R.id.btnAddCart)
    public void AddToCartClick() {
        Integer total_crtcnt = session.getCartCount();
        total_crtcnt = total_crtcnt + 1;
        session.cartcount(total_crtcnt);
        cartItemCount.setText(String.valueOf(total_crtcnt));

        if (!spnrqty.equals("null")) {
            int i = Integer.parseInt(String.valueOf(putcntlst.get(spQuantity.getSelectedItemPosition())));
            i = i + 1;
            tvProductCount.setText(String.valueOf(i));
            putcntlst.set(spQuantity.getSelectedItemPosition(), String.valueOf(i));
            tinydb.putListString(prdId, putcntlst);
            btnAddCart.setVisibility(android.view.View.GONE);
            llProductCount.setVisibility(android.view.View.VISIBLE);

            final AddToCartModel ref = new AddToCartModel(prdId, String.valueOf(tvProductCount.getText()), option_id, option_value_id);

            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<AddToCartModel> callAdd = apiInterface.callAddToCart("api/cart/add", session.getToken(), ref);
            callAdd.enqueue(new Callback<AddToCartModel>() {
                @Override
                public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                    AddToCartModel resource = response.body();
                    if (resource.status.equals("success")) {
                        // Toast.makeText(getApplicationContext(), "Added in Cart", Toast.LENGTH_LONG).show();
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
            tvProductCount.setText(sadd_product_quantity_in_cart);

            btnAddCart.setVisibility(android.view.View.GONE);
            llProductCount.setVisibility(android.view.View.VISIBLE);

            final AddCartNullSpinner nullValue = new AddCartNullSpinner(prdId, String.valueOf(tvProductCount.getText()));

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

    @Click(R.id.llProductDecrese)
    public void removeCount() {
        if (!spnrqty.equals("null")) {
            if (Integer.parseInt(tvProductCount.getText().toString()) <= 1) {
                Integer total_crtcnt = session.getCartCount();
                total_crtcnt = total_crtcnt - 1;
                session.cartcount(total_crtcnt);
                cartItemCount.setText(String.valueOf(total_crtcnt));

                int i = Integer.parseInt(String.valueOf(putcntlst.get(spQuantity.getSelectedItemPosition())));
                i = i - 1;
                tvProductCount.setText(String.valueOf(i));
                putcntlst.set(spQuantity.getSelectedItemPosition(), String.valueOf(i));
                tinydb.putListString(prdId, putcntlst);

                btnAddCart.setVisibility(android.view.View.VISIBLE);
                llProductCount.setVisibility(android.view.View.GONE);

                final UpdateToCartModel ref = new UpdateToCartModel(prdId, String.valueOf(tvProductCount.getText()));

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

            } else {

                int i = Integer.parseInt(String.valueOf(putcntlst.get(spQuantity.getSelectedItemPosition())));
                i = i - 1;
                tvProductCount.setText(String.valueOf(i));
                putcntlst.set(spQuantity.getSelectedItemPosition(), String.valueOf(i));
                tinydb.putListString(prdId, putcntlst);

                final UpdateToCartModel ref = new UpdateToCartModel(prdId, String.valueOf(tvProductCount.getText()));

                apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<UpdateToCartModel> callAdd = apiInterface.updateAddToCart("api/cart/edit_new", session.getToken(), ref);
                callAdd.enqueue(new Callback<UpdateToCartModel>() {
                    @Override
                    public void onResponse(Call<UpdateToCartModel> call, Response<UpdateToCartModel> response) {
                        UpdateToCartModel resource = response.body();
                        if (resource.status.equals("success")) {
                            //   Toast.makeText(getApplicationContext(), "Remove from Cart", Toast.LENGTH_LONG).show();
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
        } else {
            if (Integer.parseInt(tvProductCount.getText().toString()) <= 1) {
                Integer total_crtcnt = session.getCartCount();
                total_crtcnt = total_crtcnt - 1;
                session.cartcount(total_crtcnt);
                cartItemCount.setText(String.valueOf(total_crtcnt));

                int i = Integer.parseInt(sadd_product_quantity_in_cart);
                i = i - 1;
                sadd_product_quantity_in_cart = String.valueOf(i);
                tvProductCount.setText(sadd_product_quantity_in_cart);

                btnAddCart.setVisibility(android.view.View.VISIBLE);
                llProductCount.setVisibility(android.view.View.GONE);

                final UpdateToCartModel ref = new UpdateToCartModel(prdId, String.valueOf(tvProductCount.getText()));

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
                tvProductCount.setText(sadd_product_quantity_in_cart);

                final UpdateToCartModel ref = new UpdateToCartModel(prdId, String.valueOf(tvProductCount.getText()));

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

    @Click(R.id.llProductIncrease)
    public void addCount() {
        if (!spnrqty.equals("null")) {
            int i = Integer.parseInt(String.valueOf(putcntlst.get(spQuantity.getSelectedItemPosition())));
            i = i + 1;
            tvProductCount.setText(String.valueOf(i));
            putcntlst.set(spQuantity.getSelectedItemPosition(), String.valueOf(i));
            tinydb.putListString(prdId, putcntlst);

            final UpdateToCartModel ref = new UpdateToCartModel(prdId, String.valueOf(tvProductCount.getText()));

            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<UpdateToCartModel> callAdd = apiInterface.updateAddToCart("api/cart/edit_new", session.getToken(), ref);
            callAdd.enqueue(new Callback<UpdateToCartModel>() {
                @Override
                public void onResponse(Call<UpdateToCartModel> call, Response<UpdateToCartModel> response) {
                    UpdateToCartModel resource = response.body();
                    if (resource.status.equals("success")) {
                        // Toast.makeText(getApplicationContext(), "Added in Cart", Toast.LENGTH_LONG).show();
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
            i = i + 1;
            sadd_product_quantity_in_cart = String.valueOf(i);
            tvProductCount.setText(sadd_product_quantity_in_cart);

            final UpdateToCartModel ref = new UpdateToCartModel(prdId, String.valueOf(tvProductCount.getText()));

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

    @Click(R.id.ivbtnAddWishlist)
    public void onClick() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        if (state == false) {
            final InsertWishListItems add_item = new InsertWishListItems(session.getCustomerId(), prdId);
            Call<InsertWishListItems> callAdd = apiInterface.addtoWishList(add_item);
            callAdd.enqueue(new Callback<InsertWishListItems>() {
                @Override
                public void onResponse(Call<InsertWishListItems> call, Response<InsertWishListItems> response) {
                    InsertWishListItems resource = response.body();
                    if (resource.status.equals("success")) {
                        Toast.makeText(mContext, "Added In Wishlist", Toast.LENGTH_LONG).show();
                        ivbtnAddWishlist.setBackgroundResource(R.drawable.wishlist_red);
                    } else {
                        Toast.makeText(mContext, resource.message, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<InsertWishListItems> call, Throwable t) {
                    call.cancel();
                }
            });
            state = true;
        } else {
            //---------------------for removing from wishlist---------------------------
            final RemoveWishListItem remove_item = new RemoveWishListItem(session.getCustomerId(), prdId);
            Call<RemoveWishListItem> callRemove = apiInterface.removeWishListItem(remove_item);
            callRemove.enqueue(new Callback<RemoveWishListItem>() {
                @Override
                public void onResponse(Call<RemoveWishListItem> call, Response<RemoveWishListItem> response) {
                    RemoveWishListItem resource = response.body();
                    if (resource.status.equals("success")) {
                        Toast.makeText(mContext, "Remove from Wishlist", Toast.LENGTH_LONG).show();
                        ivbtnAddWishlist.setBackgroundResource(R.drawable.wishlistgrey);
                    } else {
                        Toast.makeText(mContext, resource.message, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RemoveWishListItem> call, Throwable t) {
                    call.cancel();
                }
            });
            state = false;
        }
    }

    public void display(int number) {
        tvProductCount.setText("" + number);
    }
}
