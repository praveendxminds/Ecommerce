package com.app.ecommerce.Home;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ecommerce.R;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.SingleTop;

@Parent
@SingleTop
@Layout(R.layout.shopbycateg_items)
public class ShopByCategItems {

    @View(R.id.itemIconShopBy)
    public ImageView itemIconShopBy;

    @View(R.id.headingTxtShopBy)
    public TextView headingTxtShopBy;

    @View(R.id.toggleIconShopBy)
    public ImageView toggleIconShopBy;

    public Context context;
    public String mheading;
    public Integer murl;

    public ShopByCategItems(Context contxt, Integer imgUrl, String heading)
    {
        context = contxt;
        murl = imgUrl;
        mheading = heading;

    }
    @Resolve
    public void onResolved()
    {
        Glide.with(context).load(murl).into(itemIconShopBy);
        headingTxtShopBy.setText(mheading);
        toggleIconShopBy.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
    }
    @Expand
    public void onExpand(){
        toggleIconShopBy.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
    }
    @Collapse
    public void onCollapse(){
        toggleIconShopBy.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
    }
}
