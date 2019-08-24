package com.nisarga.nisargaveggiez.Wishlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.Home.ProductDetailHome;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.MoveToCartModel;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.nisarga.nisargaveggiez.retrofit.RemoveWishListItem;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sushmita 14/06/2019
 */

@NonReusable
@Layout(R.layout.wishlist_items)
public class WishListItem {

    @View(R.id.llWishlist)
    public LinearLayout llWishlist;

    @View(R.id.prd_nameWishList)
    public TextView prd_nameWishList;

    @View(R.id.itemIconWishList)
    public ImageView itemIconWishList;

    @View(R.id.qntyWishList)
    public TextView qntyWishList;

    @View(R.id.priceNewWishList)
    public TextView priceNewWishList;

    @View(R.id.priceOldWishList)
    public TextView priceOldWishList;

    @View(R.id.ord_itWishList)
    public CardView ord_itWishList;

    PlaceHolderView mPlaceHolderView;

    @ParentPosition
    public int mParentPosition;

    SessionManager session;
    public Context mContext;
    public String prod_id;
    public String murl;
    public String mprice;
    public String mqty;
    public String mtitle;
    public String mDisPrice;
    public int count;
    APIInterface apiInterface;
    public Integer countItems;

    String status;
   // public WishListHolder mactivity;

    public WishListItem(Context context, String product_id, String url, String title, String price,
                        String qty, String dis_price, PlaceHolderView placeHolderView) {
        this.mContext = context;
        this.prod_id = product_id;
        this.murl = url;
        this.mtitle = title;
        this.mprice = price;
        this.mqty = qty;
        this.mDisPrice = dis_price;
        this.mPlaceHolderView = placeHolderView;
    }

    @Resolve
    public void onResolved() {

        session = new SessionManager(mContext);
        prd_nameWishList.setText(mtitle);
        Glide.with(mContext).load(murl).into(itemIconWishList);

        double dbl_price = Double.parseDouble(mprice);
        String strPrice = String.format("%.2f", dbl_price);
        priceNewWishList.setText("\u20B9" + " " + strPrice);

        double dbl_Discount = Double.parseDouble(mDisPrice);
        String str_disValue = String.format("%.2f", dbl_Discount);
        priceOldWishList.setVisibility(android.view.View.VISIBLE);
        priceOldWishList.setText("\u20B9" + " " + str_disValue);

        qntyWishList.setText(mqty);


    }

    @Click(R.id.llWishlist)
    public void onCardClick() {

        Intent myIntent = new Intent(mContext, ProductDetailHome.class);
        myIntent.putExtra("product_id", prod_id);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);

    }

    @Click(R.id.btn_Remove)
    public void deleteWishListItem() {


        RemoveWishListItem remove_item = new RemoveWishListItem(session.getCustomerId(), prod_id);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<RemoveWishListItem> call = apiInterface.removeWishListItem(remove_item);
        call.enqueue(new Callback<RemoveWishListItem>() {
            @Override
            public void onResponse(Call<RemoveWishListItem> call, Response<RemoveWishListItem> response) {
                RemoveWishListItem resource = response.body();

                if (resource.status.equals("success")) {
                    // mPlaceHolderView.removeView(mContext);

                } else {
                    // Toast.makeText(mContext, resource.message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RemoveWishListItem> call, Throwable t) {
                call.cancel();
            }
        });
        mPlaceHolderView.removeView(this);


    }


    @Click(R.id.btn_moveToCart)
    public void moveToCartClick() {

      /*  if (Utils.CheckInternetConnection(mContext)) {

            MoveToCartModel move_item = new MoveToCartModel(session.getCustomerId(), prod_id);
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<MoveToCartModel> call = apiInterface.moveToCart(move_item);
            call.enqueue(new Callback<MoveToCartModel>() {
                @Override
                public void onResponse(Call<MoveToCartModel> call, Response<MoveToCartModel> response) {
                    MoveToCartModel resource = response.body();
                    if (resource.status.equals("success")) {
                        mPlaceHolderView.removeView(this);
                        Toast.makeText(mContext, resource.message, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext, resource.message, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<MoveToCartModel> call, Throwable t) {
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(mContext, "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }*/
    }


}
