package com.app.ecommerce.wallet;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.ecommerce.R;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@NonReusable
@Layout(R.layout.add_to_money_items)
public class AddMoneyItems {
    @View(R.id.llAddtoMoneyItem)
    public LinearLayout llAddtoMoney;

    @View(R.id.tvAddtoAmount)
    public TextView tvAddtoAmount;

    public Context mContext;
    public String tvAmount;

    public AddMoneyItems(Context contxt,String amnt)
    {
        this.mContext = contxt;
        this.tvAmount = amnt;
    }
    @Resolve
    public void onResolved()
    {
        tvAddtoAmount.setText(tvAmount);
    }
    @Click(R.id.llAddtoMoneyItem)
    public void getAmount()
    {
        Intent intentAmount = new Intent(mContext,AddMoneyToWallet.class);
        intentAmount.putExtra("textAmount",tvAmount);
        //intentAmount.setFlags(intentAmount.FLAG_ACTIVITY_NEW_TASK);
        //mContext.startActivity(intentAmount);

    }

}
