package com.app.ecommerce.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.internal.BottomNavigationMenuView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecommerce.R;
import com.app.ecommerce.ProductDetails_act;
import com.app.ecommerce.SessionManager;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.InsertWishListItems;
import com.app.ecommerce.retrofit.RemoveWishListItem;
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

import static com.app.ecommerce.Home2.HomeTwoCategory.bottomNavigationView;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by sushmita on 17th June 2019
 */


@NonReusable
@Layout(R.layout.home_category_grid_item)
public class HomeCategoryItemGridView {

    @View(R.id.imageCategoryGrid)
    public ImageView imageCategoryGrid;

    @View(R.id.titleCategoryGrid)
    public TextView titleCategoryGrid;

    @View(R.id.newPriceCategoryGrid)
    public TextView newPriceCategoryGrid;

    @View(R.id.qtyCategoryGrid)
    public Spinner qtyCategoryGrid;

    @View(R.id.oldPriceCategoryGrid)
    public TextView oldPriceCategoryGrid;

    @View(R.id.likeCategoryGrid)
    public ImageButton likeCategoryGrid;

    @View(R.id.llCountPrd)
    public LinearLayout llCountPrd;

    @View(R.id.imgBtn_decre)
    public ImageButton imgBtn_decre;

    @View(R.id.imgBtn_incre)
    public ImageButton imgBtn_incre;

    @View(R.id.tv_count)
    public TextView tv_count;

    @View(R.id.btnAddCategoryGrid)
    public Button btnAddCategoryGrid;

    @ParentPosition
    public int mParentPosition;

    SessionManager session;

    public String murl;
    public String mprice;
    public String mqty;
    public String mtitle;
    public String mid;
    public Context mContext;
    public static String MyPREFERENCES = "sessiondata";
    SharedPreferences sharedpreferences;
    public TextView mtextCartItemCount;
    UseSharedPreferences useSharedPreferences;
    public String imgUrl="http://3.213.33.73/Ecommerce/upload/image/";
    String[] qtyArray = {"qty","100gm", "200gm", "300gm", "50gm", "500gm", "1kg"};
    int countVal=0;

    public boolean status = true;
    public boolean state = false;
    APIInterface apiInterface;

    public HomeCategoryItemGridView(Context context,TextView textCartItemCount, String id, String url, String title, String price,String qty) {
        mContext = context;
        mid = id;
        murl = url;
        mtitle = title;
        mprice = price;
        mqty=qty;
        mtextCartItemCount = textCartItemCount;
    }

    public String getTitle() {
        return mtitle;
    }

    public String getPrice() {
        return mprice;
    }

    public String getUrl() {
        return murl;
    }


    @Resolve
    public void onResolved() {
        titleCategoryGrid.setText(mtitle);
        Glide.with(mContext)
                .load(imgUrl+murl)
                .into(imageCategoryGrid);
        newPriceCategoryGrid.setText("\u20B9" + " " + mprice);

        qtyArray[0]=mqty;
        final List<String> qtyList = new ArrayList<>(Arrays.asList(qtyArray));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_product_qtylist_home_two, qtyList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_product_qtylist_home_two);
        qtyCategoryGrid.setAdapter(spinnerArrayAdapter);

        useSharedPreferences = new UseSharedPreferences(mContext);

    }

    @Click(R.id.ord_itCategoryGrid)
    public void onCardClick() {
        Intent myIntent = new Intent(mContext, ProductDetailHome.class);
        myIntent.putExtra("prd_id", mid);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);

    }

    @Click(R.id.btnAddCategoryGrid)
    public void AddToCartClick() {
       /* sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Integer name_session = useSharedPreferences.getCountValue();

        Integer value = useSharedPreferences.createCountValue(name_session);
        Log.d("values of count", String.valueOf(value));
        countCartDisplay(value);*/
       if(status == true)
       {
           btnAddCategoryGrid.setVisibility(android.view.View.GONE);
           llCountPrd.setVisibility(android.view.View.VISIBLE);
           status = false;

       }
    }

    @Click(R.id.imgBtn_incre)
    public void addCount()
    {
        countVal = countVal + 1;//display number in place of add to cart
        session = new SessionManager(mContext);
        Integer cnt = session.getCartCount();
        cnt = cnt +1;//display number in cart icon
        session.cartcount(cnt);
        display(countVal);
        mtextCartItemCount.setText(String.valueOf(cnt));
    }
    @Click(R.id.imgBtn_decre)
    public void removeCount()
    {
        if (countVal == 0) {
            display(0);
        } else {
            countVal = countVal - 1;
            session = new SessionManager(mContext);
            Integer cnt = session.getCartCount();
            cnt = cnt -1;
            session.cartcount(cnt);
            display(countVal);
            mtextCartItemCount.setText(String.valueOf(cnt));
        }
    }
    @Click(R.id.likeCategoryGrid)
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
                        likeCategoryGrid.setBackgroundResource(R.drawable.like);

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
                        likeCategoryGrid.setBackgroundResource(R.drawable.addwishlist);
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

    public void display(int number) {
        tv_count.setText("" + number);
    }
    public void countCartDisplay(int number) {

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        android.view.View v = bottomNavigationMenuView.getChildAt(4);
        Integer name_session = useSharedPreferences.getCountValue();

        new QBadgeView(mContext).bindTarget(v).setBadgeTextColor(mContext.getResources()
                .getColor(R.color.white)).setGravityOffset(15, -2, true)
                .setBadgeNumber(name_session).setBadgeBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));

    }
}
