package com.app.ecommerce.cart;

import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import com.app.ecommerce.R;

/**
 * Created by praveen on 19/12/18.
 */


@NonReusable
@Layout(R.layout.cart_item_footer)
public class cartItem_footer {

    @View(R.id.prc)
    public TextView imageView;


    public cartItem_footer() {

    }

    @Resolve
    public void onResolved() {

    }
}
