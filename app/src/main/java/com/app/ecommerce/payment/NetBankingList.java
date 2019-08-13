package com.app.ecommerce.payment;

import android.content.Context;
import android.widget.TextView;

import com.app.ecommerce.R;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.View;

@NonReusable
@Layout(R.layout.net_banking_list)
public class NetBankingList {

    @View(R.id.tvBankName)
    public TextView tvBankName;

    public Context mContext;
    public String bankName;
    public NetBankingList(Context contxt)
    {
        this.mContext = contxt;
    }
    public NetBankingList(Context contxt,String bank_name)
    {
        this.mContext = contxt;
        this.bankName= bank_name;
    }
}
