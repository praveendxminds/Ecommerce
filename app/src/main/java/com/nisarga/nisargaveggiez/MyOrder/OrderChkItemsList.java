package com.nisarga.nisargaveggiez.MyOrder;

import android.content.Context;
import android.widget.TextView;

import com.nisarga.nisargaveggiez.R;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.View;

@NonReusable
@Layout(R.layout.chk_items_list)
public class OrderChkItemsList {

    @View(R.id.tvPrdTitle)
    public TextView tvPrdTitle;

    @View(R.id.tvPrdWeight)
    public TextView tvPrdWeight;

    @View(R.id.tvPrdQty)
    public TextView tvPrdQty;

    @View(R.id.tvPrdPrice)
    public TextView tvPrdPrice;

    @View(R.id.tvUpdatedPrice)
    public TextView tvUpdatedPrice;

    public Context mContext;
    public String mOrdId,mOrdPrdId,mPrdTitle,mPrdWeight,mPrdQty,mPrdPrice,mPrdUpdatedPrice;
    public OrderChkItemsList(Context contxt)
    {
        this.mContext = contxt;
    }
    public OrderChkItemsList(Context contxt,String ordId,String ordPrdId,String prd_title,String prd_weight,
                             String prd_qty,String prd_price,String updated_price)
    {
        this.mContext = contxt;
        this.mOrdId = ordId;
        this.mOrdPrdId = ordPrdId;
        this.mPrdTitle = prd_title;
        this.mPrdWeight = prd_weight;
        this.mPrdQty = prd_qty;
        this.mPrdPrice = prd_price;
        this.mPrdUpdatedPrice = updated_price;
    }

}
