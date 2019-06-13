package com.app.ecommerce.cart;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

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

    @View(R.id.imageView)
    public ImageView imageView;

    public Drawable mDrawable;

    public cartItem(Drawable drawable) {
        mDrawable = drawable;
    }

    @Resolve
    public void onResolved() {
        imageView.setImageDrawable(mDrawable);
    }
}
