package com.app.ecommerce.cart;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import com.app.ecommerce.R;

/**
 * Created by praveen on 19/12/18.
 */


@NonReusable
@Layout(R.layout.cart_items)
public class cartItem {

    @View(R.id.itemIconMyCart)
    public ImageView itemIconMyCart;

    @View(R.id.prd_nameMyCart)
    public TextView prd_nameMyCart;

    @View(R.id.qntyMyCart)
    public TextView qntyMyCart;

    @View(R.id.priceNewMyCart)
    public TextView priceNewMyCart;

    @View(R.id.btn_deleteMyCart)
    public TextView btn_deleteMyCart;

    public Context mcontext;

    public cartItem(Context context) {
        mcontext=context;
    }

    @Resolve
    public void onResolved() {

    }
}
