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

    String prdId, image, prdName, prdWhishStatus;
    String sPrice, sDisPrice;

    int cartcount = 0;

    String sQuantitySpinner, option_id, option_value_id, scartcount, price, sDiscount;
    String productPrice;
    Object spnrqty;

    public boolean state = false;

    public HomeCategoryItemGridView(Context context, String productId, String image_url, String productName,
                                    String productDisPrice, String addCart, String whishlistStatus,
                                    Object spnrqty) {
        this.mContext = context;
        this.prdId = productId;
        this.image = image_url;
        this.prdName = productName;
        this.prdWhishStatus = whishlistStatus;
        this.spnrqty = spnrqty;
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

        final ArrayList<String> product_qty_list = new ArrayList<>();
        final ArrayList<String> product_option_id = new ArrayList<>();
        final ArrayList<String> product_option_value_id = new ArrayList<>();
        final ArrayList<String> cart_count = new ArrayList<>();
        final ArrayList<String> product_price = new ArrayList<>();
        final ArrayList<String> discount_price = new ArrayList<>();

        if (!spnrqty.equals("null")) {
            JsonArray jsonElements = (JsonArray) new Gson().toJsonTree(spnrqty);

            for (int j = 0; j < jsonElements.size(); j++) {
                Log.d("qqqqqqqqqqqqqqq", String.valueOf(jsonElements.get(j).getAsJsonObject().get("name")));
                product_qty_list.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("name")).replace("\"", ""));
                product_option_id.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("product_option_id")).replace("\"", ""));
                product_option_value_id.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("product_option_value_id")).replace("\"", ""));
                cart_count.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("cart_count")).replace("\"", ""));
                product_price.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("price")));
                discount_price.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("discount_price")));

                sQuantitySpinner = product_qty_list.get(j);

                double dbl_Price = Double.parseDouble(String.valueOf(product_price.get(0)));//need to convert string to decimal
                productPrice = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
                tvProdPrice.setText("₹" + " " + productPrice);

                if (discount_price.equals("null")) {
                    tvProdOldPrice.setVisibility(android.view.View.INVISIBLE);
                } else {
                    double dbl_Discount = Double.parseDouble(String.valueOf(discount_price.get(0)));//need to convert string to decimal
                    String str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
                    tvProdOldPrice.setVisibility(android.view.View.VISIBLE);
                    tvProdOldPrice.setText("₹" + " " + str_disValue);
                }

                if (cart_count.equals("0")) {
                    btnAddCart.setVisibility(android.view.View.VISIBLE);
                    llProductCount.setVisibility(android.view.View.GONE);
                } else {
                    btnAddCart.setVisibility(android.view.View.GONE);
                    llProductCount.setVisibility(android.view.View.VISIBLE);
                    tvProductCount.setText(String.valueOf(cart_count.get(0)));
                }
            }
        } else if (spnrqty.equals("null")) {
            spQuantity.setVisibility(android.view.View.GONE);
            llQuantityList.setVisibility(android.view.View.VISIBLE);
        }

        spQuantity.setAdapter(new QtyspinnerAdapter(getApplicationContext(), product_qty_list, product_option_id,
                product_option_value_id, cart_count, product_price, discount_price));
        spQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                sQuantitySpinner = product_qty_list.get(position);
                option_id = product_option_id.get(position);
                option_value_id = product_option_value_id.get(position);
                scartcount = cart_count.get(position);
                price = product_price.get(position);
                sDiscount = discount_price.get(position);

                double dbl_Price = Double.parseDouble(price);//need to convert string to decimal
                productPrice = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
                tvProdPrice.setText("₹" + " " + productPrice);

                if (sDiscount.equals("null")) {
                    tvProdOldPrice.setVisibility(android.view.View.INVISIBLE);
                } else {
                    double dbl_Discount = Double.parseDouble(sDiscount);//need to convert string to decimal
                    String str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
                    tvProdOldPrice.setVisibility(android.view.View.VISIBLE);
                    tvProdOldPrice.setText("₹" + " " + str_disValue);
                }

                btnAddCart.setVisibility(android.view.View.VISIBLE);
                llProductCount.setVisibility(android.view.View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cartcount = Integer.parseInt(String.valueOf(cart_count.size()));
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

        cartcount = cartcount + 1;//display number in place of add to cart
        display(cartcount);
        tvProductCount.setText(String.valueOf(cartcount));
        btnAddCart.setVisibility(android.view.View.GONE);
        llProductCount.setVisibility(android.view.View.VISIBLE);

        final AddToCartModel ref = new AddToCartModel(prdId, String.valueOf(cartcount), option_id, option_value_id);

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

    @Click(R.id.llProductDecrese)
    public void removeCount() {
        if (cartcount <= 1) {
            Integer total_crtcnt = session.getCartCount();
            total_crtcnt = total_crtcnt - 1;
            session.cartcount(total_crtcnt);
            cartItemCount.setText(String.valueOf(total_crtcnt));

            cartcount = cartcount - 1;
            display(cartcount);
            tvProductCount.setText(String.valueOf(cartcount));
            btnAddCart.setVisibility(android.view.View.VISIBLE);
            llProductCount.setVisibility(android.view.View.GONE);

            final UpdateToCartModel ref = new UpdateToCartModel(prdId, String.valueOf(cartcount));

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
            tvProductCount.setText(String.valueOf(cartcount));

            final UpdateToCartModel ref = new UpdateToCartModel(prdId, String.valueOf(cartcount));

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

    @Click(R.id.llProductIncrease)
    public void addCount() {
        cartcount = cartcount + 1;//display number in place of add to cart
        display(cartcount);
        tvProductCount.setText(String.valueOf(cartcount));

        final UpdateToCartModel ref = new UpdateToCartModel(prdId, String.valueOf(cartcount));

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
