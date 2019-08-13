package com.app.ecommerce.Home;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ecommerce.R;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.ShopByCategModel;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.SingleTop;

import java.util.List;

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

    APIInterface apiInterface;

    public Context mContext;
    public String categoryId;
    public String categoryImage;
    public String categoryName;
    public List<ShopByCategModel.PrdDataDatum> mPrdData;

    public ShopByCategItems(Context context, String categ_id, String categ_image, String categ_name) {
        mContext = context;
        categoryId = categ_id;
        categoryImage = categ_image;
        categoryName = categ_name;
    }
    @Resolve
    public void onResolved()
    {
        Glide.with(mContext).load(categoryImage).into(itemIconShopBy);
        headingTxtShopBy.setText(categoryName);
        toggleIconShopBy.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));

    }
    @Expand
    public void onExpand(){
        toggleIconShopBy.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));

    }
    @Collapse
    public void onCollapse(){
        toggleIconShopBy.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
    }
}
