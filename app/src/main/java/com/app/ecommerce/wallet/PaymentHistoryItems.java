package com.app.ecommerce.wallet;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.app.ecommerce.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.View;

@NonReusable
@Layout(R.layout.payment_history_items)
public class PaymentHistoryItems {

    @View(R.id.iconPayHistory)
    public CircularImageView iconPayHistory;

    @View(R.id.tvStatusPayHistory)
    public TextView tvStatusPayHistory;

    @View(R.id.tvTitlePayHistory)
    public TextView tvTitlePayHistory;

    @View(R.id.tvTxnId)
    public TextView tvTxnId;

    @View(R.id.tvAmountPayHistory)
    public TextView tvAmountPayHistory;

    @View(R.id.btnDebit)
    public Button btnDebit;

    @View(R.id.btnCredit)
    public Button btnCredit;


    Context mContext;
    public PaymentHistoryItems(Context contxt)
    {
        this.mContext=contxt;
    }
}
