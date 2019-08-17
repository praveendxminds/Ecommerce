package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.R;
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

@Parent
@SingleTop
@Layout(R.layout.image_slider_product_details)
public class ProductDetailsImageSlider {

    @View(R.id.pager_PrdDetails)
    public ViewPager pager_PrdDetails;

    @View(R.id.indicator_PrdDetails)
    public TabLayout indicator_PrdDetails;

    @View(R.id.btn_addtoWishlistPrdDetail)
    public ImageButton btn_addtoWishlistPrdDetail;

    public static int currentPage = 0;
    public static Integer[] XMEN = {R.drawable.flower, R.drawable.deep, R.drawable.flower, R.drawable.deep};
    public ArrayList<String> XMENArray = new ArrayList<String>();


    @ParentPosition
    public int mParentPosition;

    public Context mContext;
    public ArrayList<String> mHeading;
    public List<String> mCatImgUrl;
    public boolean state;
    APIInterface apiInterface;
    ProductDetailHome pdetailHome = new ProductDetailHome();

    public ProductDetailsImageSlider(Context context,List<String> CatImgUrl) {
        mContext = context;
        mCatImgUrl = CatImgUrl;

        for (int i = 0; i < mCatImgUrl.size(); i++) {
            XMENArray.add(mCatImgUrl.get(i));
            Log.e("---images----", String.valueOf(mCatImgUrl.get(i)));
        }
    }

    @Resolve
    public void onResolved() {

        pager_PrdDetails.setAdapter(new ProductDetailImageSliderAdapter(mContext, XMENArray));

        indicator_PrdDetails.setupWithViewPager(pager_PrdDetails);//for tab layout
        state = pdetailHome.getWishlistStatus();

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == mCatImgUrl.size()) {
                    currentPage = 0;
                }
                pager_PrdDetails.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }

    @Click(R.id.btn_addtoWishlistPrdDetail)
    public void onClick()
    {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        //---------------------------------------------------------
        if (state == false) {
            //------------------------------------------for adding to wishlist-----------------------------
            final InsertWishListItems add_item = new InsertWishListItems("1", "46");
            Call<InsertWishListItems> callAdd = apiInterface.addtoWishList(add_item);
            callAdd.enqueue(new Callback<InsertWishListItems>() {
                @Override
                public void onResponse(Call<InsertWishListItems> call, Response<InsertWishListItems> response) {
                    InsertWishListItems resource = response.body();
                    if (resource.status.equals("success")) {
                        Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                        btn_addtoWishlistPrdDetail.setBackgroundResource(R.drawable.wishlist_red);

                    } else {
                        Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
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
            final RemoveWishListItem remove_item = new RemoveWishListItem("1", "46");
            Call<RemoveWishListItem> callRemove = apiInterface.removeWishListItem(remove_item);
            callRemove.enqueue(new Callback<RemoveWishListItem>() {
                @Override
                public void onResponse(Call<RemoveWishListItem> call, Response<RemoveWishListItem> response) {
                    RemoveWishListItem resource = response.body();
                    if (resource.status.equals("success")) {
                        Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                        btn_addtoWishlistPrdDetail.setBackgroundResource(R.drawable.addwishlist);
                    } else {
                        Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
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
}




