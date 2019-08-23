package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.internal.BottomNavigationMenuView;
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
import com.mindorks.placeholderview.annotations.expand.ParentPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nisarga.nisargaveggiez.Home2.HomeTwoCategory.bottomNavigationView;
import static com.facebook.FacebookSdk.getApplicationContext;

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

    String prdId, image, prdName, prdPrice, prdDisPrice, prdQuantity;
    String sPrice, sDisPrice;

    int cartcount = 0;
    String product_option_id[], product_option_value_id[];
    String sQuantitySpinner, option_id, option_value_id;

    public boolean state = false;


    public HomeCategoryItemGridView(Context context, String productId, String image_url, String productName,
                                    String productPrice, String productDisPrice, String quantity) {
        mContext = context;
        prdId = productId;
        image = image_url;
        prdName = productName;
        prdPrice = productPrice;
        prdDisPrice = productDisPrice;
        prdQuantity = quantity;
    }

    public String getTitle() {
        return prdName;
    }

    @Resolve
    public void onResolved() {
        session = new SessionManager(mContext);
        tvProductName.setText(prdName);
        Glide.with(mContext).load(image).into(ivImage);

        double dbl_Price = Double.parseDouble(prdPrice);//need to convert string to decimal
        sPrice = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
        tvProdPrice.setText("₹" + " " + sPrice);

        double dbl_Dis_Price = Double.parseDouble(prdDisPrice);//need to convert string to decimal
        sDisPrice = String.format("%.2f", dbl_Dis_Price);//display only 2 decimal places of price
        tvProdOldPrice.setText("₹" + " " + sDisPrice);

        final ArrayList<String> product_qty_list = new ArrayList<>();

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final QuantityList quantityList = new QuantityList(prdId);

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

    @Click(R.id.llProductGridView)
    public void onCardClick() {
        Intent myIntent = new Intent(mContext, ProductDetailHome.class);
        myIntent.putExtra("prd_id", prdId);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);
    }

    @Click(R.id.btnAddCart)
    public void AddToCartClick() {
        cartcount = cartcount + 1;//display number in place of add to cart
        session.cartcount(cartcount);
        display(cartcount);
        tvProductCount.setText(String.valueOf(cartcount));
        btnAddCart.setVisibility(android.view.View.GONE);
        llProductCount.setVisibility(android.view.View.VISIBLE);

        final AddToCartModel ref = new AddToCartModel(prdId, sQuantitySpinner, option_id, option_value_id);

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

    @Click(R.id.llProductDecrese)
    public void addCount() {
        cartcount = cartcount + 1;//display number in place of add to cart
        session.cartcount(cartcount);
        display(cartcount);
        tvProductCount.setText(String.valueOf(cartcount));
    }

    @Click(R.id.imgBtn_decre)
    public void removeCount() {
        if (cartcount <= 1) {
            cartcount = cartcount - 1;
            session.cartcount(cartcount);
            display(cartcount);
            tvProductCount.setText(String.valueOf(cartcount));
            btnAddCart.setVisibility(android.view.View.VISIBLE);
            llProductCount.setVisibility(android.view.View.GONE);
        } else {
            cartcount = cartcount - 1;
            session.cartcount(cartcount);
            display(cartcount);
            tvProductCount.setText(String.valueOf(cartcount));
        }
    }

    @Click(R.id.llAddWishlist)
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
                        Toast.makeText(mContext, resource.message, Toast.LENGTH_LONG).show();
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
                        Toast.makeText(mContext, resource.message, Toast.LENGTH_LONG).show();
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
