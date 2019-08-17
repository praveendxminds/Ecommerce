package com.nisarga.nisargaveggiez.wallet;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nisarga.nisargaveggiez.R;
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
    public EditText etAmount;

    public AddMoneyItems(Context contxt, String amnt, EditText amount) {
        this.mContext = contxt;
        this.tvAmount = amnt;
        this.etAmount = amount;
    }

    @Resolve
    public void onResolved() {
        tvAddtoAmount.setText(tvAmount);
    }

    @Click(R.id.llAddtoMoneyItem)
    public void getAmount() {
        etAmount.setText(tvAmount);
    }

}
