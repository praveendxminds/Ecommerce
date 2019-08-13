package com.app.ecommerce.Wishlist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecommerce.Home.ProductDetailHome;
import com.app.ecommerce.R;
import com.app.ecommerce.ProductDetails_act;
import com.app.ecommerce.Utils;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.GetWishList;
import com.app.ecommerce.retrofit.MoveToCartModel;
import com.app.ecommerce.retrofit.RemoveWishListItem;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.places.Place;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;

import java.util.Collections;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by sushmita 14/06/2019
 */

@NonReusable
@Layout(R.layout.wishlist_items)
public class WishListItem {
    @View(R.id.llWishlist)
    public  LinearLayout llWishlist;

    @View(R.id.prd_nameWishList)
    public TextView prd_nameWishList;

    @View(R.id.itemIconWishList)
    public ImageView itemIconWishList;

    @View(R.id.qntyWishList)
    public TextView qntyWishList;

    @View(R.id.priceNewWishList)
    public TextView priceNewWishList;

    @View(R.id.ord_itWishList)
    public CardView ord_itWishList;

     PlaceHolderView mPlaceHolderView;

    @ParentPosition
    public int mParentPosition;

    public Context mContext;
    public String prod_id;
    public String murl;
    public String mprice;
    public String mqty;
    public String mtitle;
    public int count;
    public String imgUrl="http://3.213.33.73/Ecommerce/upload/image/";

    APIInterface apiInterface;

    public WishListItem(Context context, String product_id, String url, String title, String price,
                        String qty, PlaceHolderView placeHolderView) {
        mContext = context;
        prod_id = product_id;
        murl = url;
        mtitle = title;
        mprice = price;
        mqty = qty;
        mPlaceHolderView = placeHolderView;
    }

    public int getTotalItems() {
        return count;
    }

    @Resolve
    public void onResolved() {
        prd_nameWishList.setText(mtitle);
        Glide.with(mContext).load(imgUrl+murl).into(itemIconWishList);
        priceNewWishList.setText("\u20B9" + " " + mprice);
        qntyWishList.setText(mqty);

    }

    @Click(R.id.llWishlist)
    public void onCardClick() {
        Intent myIntent = new Intent(mContext, ProductDetailHome.class);
        myIntent.putExtra("product_id",prod_id);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);

    }

    @Click(R.id.btn_Remove)
    public void deleteWishListItem()
    {

       mPlaceHolderView.removeView(this);
       /* if (Utils.CheckInternetConnection(mContext)) {
            RemoveWishListItem remove_item = new RemoveWishListItem("1", prod_id);
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<RemoveWishListItem> call = apiInterface.removeWishListItem(remove_item);
            call.enqueue(new Callback<RemoveWishListItem>() {
                @Override
                public void onResponse(Call<RemoveWishListItem> call, Response<RemoveWishListItem> response) {
                    RemoveWishListItem resource = response.body();
                    if (resource.status.equals("success")) {
                      //  mPlaceHolderView.removeView(this);
                        Toast.makeText(mContext, resource.message, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext, resource.message, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RemoveWishListItem> call, Throwable t) {
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(mContext, "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }*/




    }


    @Click(R.id.btn_moveToCart)
    public void moveToCartClick() {
         if (Utils.CheckInternetConnection(mContext)) {
            MoveToCartModel move_item = new MoveToCartModel("1", prod_id);
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
        }
    }


}
