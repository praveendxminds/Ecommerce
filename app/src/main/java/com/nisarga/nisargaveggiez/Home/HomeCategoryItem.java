package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.nisarga.nisargaveggiez.ProfileSection.QuantityList;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.AddToCartModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.nisarga.nisargaveggiez.Home.HomeCategory.cartItemCount;

/**
 * Created by sushmita
 */

@NonReusable
@Layout(R.layout.home_category_item)
public class HomeCategoryItem {

    @View(R.id.ivProductImage)
    public ImageView ivProductImage;

    @View(R.id.tvProductName)
    public TextView tvProductName;

    @View(R.id.spQuantity)
    public Spinner spQuantity;

    @View(R.id.llQuantityPiece)
    public LinearLayout llQuantityPiece;

    @View(R.id.tvNewPrice)
    public TextView tvNewPrice;

    @View(R.id.tvOldPrice)
    public TextView tvOldPrice;

    @View(R.id.btnAddItem)
    public ImageButton btnAddItem;

    @View(R.id.llAccountItem)
    public LinearLayout llAccountItem;

    @View(R.id.llDecreaseCount)
    public LinearLayout llDecreaseCount;

    @View(R.id.tvProductCount)
    public TextView tvProductCount;

    @View(R.id.llIncreaseCount)
    public LinearLayout llIncreaseCount;

    Context mContext;
    SessionManager session;
    APIInterface apiInterface;

    String productId, image, productName, productDisPrice, sAddCart, sWhislistStatus;
    String sPrice, sDisPrice;

    String product_option_id[], product_option_value_id[], product_price[];
    String sQuantitySpinner, option_id, option_value_id, price;
    String productPrice;

    int cartcount = 0;

    public HomeCategoryItem(Context context, String prdId, String imageUrl, String prdName,
                            String prdDisPrice, String addCart, String whislistStatus) {

        mContext = context;
        productId = prdId;
        image = imageUrl;
        productName = prdName;
        productDisPrice = prdDisPrice;
        sAddCart = addCart;
        sWhislistStatus = whislistStatus;
    }

    public String getTitle() {
        return productName;
    }

    @Resolve
    public void onResolved() {
        session = new SessionManager(mContext);
        tvProductName.setText(productName);
        Glide.with(mContext).load(image).into(ivProductImage);

        if (productDisPrice.equals("null")) {
            tvOldPrice.setVisibility(android.view.View.INVISIBLE);
        } else {
            double dbl_Discount = Double.parseDouble(productDisPrice);//need to convert string to decimal
            String str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
            tvOldPrice.setVisibility(android.view.View.VISIBLE);
            tvOldPrice.setText("₹" + " " + str_disValue);
        }

        if (sAddCart.equals("0")) {
            btnAddItem.setVisibility(android.view.View.VISIBLE);
            llAccountItem.setVisibility(android.view.View.GONE);
        } else {
            btnAddItem.setVisibility(android.view.View.GONE);
            llAccountItem.setVisibility(android.view.View.VISIBLE);
            tvProductCount.setText(sAddCart);
        }

        final ArrayList<String> product_qty_list = new ArrayList<>();

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final QuantityList quantityList = new QuantityList(productId);

            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<QuantityList> callheight = apiInterface.quantity_list(quantityList);
            callheight.enqueue(new Callback<QuantityList>() {
                @Override
                public void onResponse(Call<QuantityList> callheight, Response<QuantityList> response) {
                    QuantityList eduresource = response.body();
                    if (eduresource.data.equals("null")) {
                        llQuantityPiece.setVisibility(android.view.View.VISIBLE);
                    } else {
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

        cartcount = Integer.parseInt(sAddCart);
    }

    @Click(R.id.llProductsListView)
    public void onCardClick() {
        Intent myIntent = new Intent(mContext, ProductDetailHome.class);
        myIntent.putExtra("product_id", productId);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);
    }

    @Click(R.id.btnAddItem)
    public void AddToCartClick() {
        Integer total_crtcnt = session.getCartCount();
        total_crtcnt = total_crtcnt + 1;
        session.cartcount(total_crtcnt);
        cartItemCount.setText(String.valueOf(total_crtcnt));

        cartcount = cartcount + 1;//display number in place of add to cart
        display(cartcount);
        tvProductCount.setText(String.valueOf(cartcount));
        btnAddItem.setVisibility(android.view.View.GONE);
        llAccountItem.setVisibility(android.view.View.VISIBLE);

        final AddToCartModel ref = new AddToCartModel(productId, String.valueOf(cartcount), option_id, option_value_id);

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

    @Click(R.id.llDecreaseCount)
    public void removeItem() {
        if (cartcount <= 1) {
            Integer total_crtcnt = session.getCartCount();
            total_crtcnt = total_crtcnt - 1;
            session.cartcount(total_crtcnt);
            cartItemCount.setText(String.valueOf(total_crtcnt));

            cartcount = cartcount - 1;
            display(cartcount);
            tvProductCount.setText(String.valueOf(cartcount));
            btnAddItem.setVisibility(android.view.View.VISIBLE);
            llAccountItem.setVisibility(android.view.View.GONE);

            final UpdateToCartModel ref = new UpdateToCartModel(productId, String.valueOf(cartcount));

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

            final UpdateToCartModel ref = new UpdateToCartModel(productId, String.valueOf(cartcount));

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

    @Click(R.id.llIncreaseCount)
    public void AddItem() {
        cartcount = cartcount + 1;//display number in place of add to cart
        display(cartcount);
        tvProductCount.setText(String.valueOf(cartcount));

        final UpdateToCartModel ref = new UpdateToCartModel(productId, String.valueOf(cartcount));

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
