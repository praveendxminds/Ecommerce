package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.mindorks.placeholderview.annotations.NonReusable;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.InsertWishListItems;
import com.nisarga.nisargaveggiez.retrofit.RemoveWishListItem;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.SingleTop;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by sushmita 26/06/2019
 */

@NonReusable
@Layout(R.layout.image_slider_product_details)
public class ProductDetailsImageSlider {

    @View(R.id.pager_PrdDetails)
    public ViewPager pager_PrdDetails;

    @View(R.id.indicator_PrdDetails)
    public TabLayout indicator_PrdDetails;

    @View(R.id.btnWishlist)
    public ImageButton btnWishlist;

    ArrayList<String> XMENArray = new ArrayList<String>();
    APIInterface apiInterface;
    SessionManager sessionManager;
    Context mContext;
    Button addWishlist;

    List<String> imageList;
    String wishlistNo, PrdId;

    public ProductDetailsImageSlider(Context context, List<String> CatImgUrl, String addWishlist,
                                     String callPrdId, Button wishlist) {
        this.mContext = context;
        this.imageList = CatImgUrl;
        this.wishlistNo = addWishlist;
        this.PrdId = callPrdId;
        this.addWishlist = wishlist;

        for (int i = 0; i < imageList.size(); i++) {
            XMENArray.add(imageList.get(i));
            Log.e("---images----", String.valueOf(imageList.get(i)));
        }
    }

    @Resolve
    public void onResolved() {
        sessionManager = new SessionManager(mContext);
        pager_PrdDetails.setAdapter(new ProductDetailImageSliderAdapter(mContext, XMENArray));
        indicator_PrdDetails.setupWithViewPager(pager_PrdDetails);
        if (wishlistNo.equals("0")) {
            onClick();
        } else {
            btnWishlist.setEnabled(false);
            btnWishlist.setBackgroundResource(R.drawable.wishlist_red);
        }
    }

    @Click(R.id.btnWishlist)
    public void onClick() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        final InsertWishListItems add_item = new InsertWishListItems(sessionManager.getCustomerId(), PrdId);
        Call<InsertWishListItems> callAdd = apiInterface.addtoWishList(add_item);
        callAdd.enqueue(new Callback<InsertWishListItems>() {
            @Override
            public void onResponse(Call<InsertWishListItems> call, Response<InsertWishListItems> response) {
                InsertWishListItems resource = response.body();
                if (resource.status.equals("success")) {
                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                    btnWishlist.setBackgroundResource(R.drawable.wishlist_red);
                    addWishlist.setText("Added To WishList");

                } else {
                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<InsertWishListItems> call, Throwable t) {
                call.cancel();
            }
        });
    }
}




