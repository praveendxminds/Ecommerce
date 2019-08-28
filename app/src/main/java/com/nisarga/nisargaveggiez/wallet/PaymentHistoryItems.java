package com.nisarga.nisargaveggiez.wallet;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Resolve;
import com.nisarga.nisargaveggiez.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.View;
import com.nisarga.nisargaveggiez.SessionManager;

import static android.view.View.GONE;

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

    SessionManager sessionManager;

    Context mContext;
    public String mDate, mtxnType, mDesc, mAmnt, mBlnc, mType, mStatus, paymentId;

    public PaymentHistoryItems(Context contxt) {
        this.mContext = contxt;
    }

    public PaymentHistoryItems(Context contxt, String date, String txn_Type, String desc,
                               String amnt, String blnc, String type,String status) {
        this.mContext = contxt;
        this.mDate = date;
        this.mtxnType = txn_Type;
        this.mDesc = desc;
        this.mAmnt = amnt;
        this.mBlnc = blnc;
        this.mType = type;
        this.mStatus = status;
    }

    @Resolve
    public void onResolved() {

        sessionManager = new SessionManager(mContext);

        if(mStatus.equals("failure"))
        {
            btnCredit.setVisibility(GONE);
            btnDebit.setVisibility(GONE);
        }
        else {
            if (mType.equals("wallet")) {
                if (mtxnType.equals("credit")) {
                    btnCredit.setVisibility(android.view.View.VISIBLE);
                    btnDebit.setVisibility(GONE);
                    Glide.with(mContext).load(R.drawable.addmoneyinwallate).into(iconPayHistory);
                } else {
                    btnCredit.setVisibility(GONE);
                    btnDebit.setVisibility(android.view.View.VISIBLE);
                    Glide.with(mContext).load(R.drawable.moneyspentorder).into(iconPayHistory);
                }
            } else {
                if (mtxnType.equals("credit")) {
                    btnCredit.setVisibility(android.view.View.VISIBLE);
                    btnDebit.setVisibility(GONE);
                    Glide.with(mContext).load(R.drawable.reedempoints).into(iconPayHistory);
                } else {
                    btnCredit.setVisibility(GONE);
                    btnDebit.setVisibility(android.view.View.VISIBLE);
                    Glide.with(mContext).load(R.drawable.loyalitypoints).into(iconPayHistory);
                }
            }
        }

        if (mStatus != null && !mStatus.isEmpty() && !mStatus.equals("null")) {
            tvStatusPayHistory.setText(mStatus);
        }
        else {
            tvStatusPayHistory.setVisibility(android.view.View.INVISIBLE);
        }

        if (mDesc != null && !mDesc.isEmpty() && !mDesc.equals("null")) {
            tvTitlePayHistory.setText(mDesc);
        }
        else
        {
            tvTitlePayHistory.setText("Payment Description");
        }
            tvTxnId.setText("");

        if (mAmnt != null && !mAmnt.isEmpty() && !mAmnt.equals("null")) {
            tvAmountPayHistory.setText("\u20B9" + " " + mAmnt);
        }
        else
        {
            tvAmountPayHistory.setText("\u20B9" + " " + "00.00");
        }
    }
}
