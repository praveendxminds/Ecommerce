package com.app.ecommerce.MyOrder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecommerce.SessionManager;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.app.ecommerce.R;
import com.mindorks.placeholderview.annotations.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Modified by sushmita on 12/07/2019.
 */

@NonReusable
@Layout(R.layout.my_orders_list)
public class orderItem {

    @View(R.id.itemIconOrder)
    public CircularImageView itemIconOrder;

    @View(R.id.statusOrder)
    public TextView statusOrder;

    @View(R.id.pendingOrder)
    public TextView pendingOrder;

    @View(R.id.canceledOrder)
    public TextView canceledOrder;

    @View(R.id.deliveredOrder)
    public TextView deliveredOrder;

    @View(R.id.orderIdOrder)
    public TextView orderIdOrder;

    @View(R.id.deliveryDateOrder)
    public TextView deliveryDateOrder;

    @View(R.id.btnCancelOrder)
    public Button btnCancelOrder;

    @View(R.id.btnReOrder)
    public Button btnReOrder;

    @View(R.id.btnDetails)
    public Button btnDetails;

    @View(R.id.btnPayNow)
    public Button btnPayNow;

    // public Activity mActivity;
    APIInterface apiInterface;
    SessionManager session;
    public Context mContext;
    String chkCancel, chkStatus, mstatus, morderId, mdeliveryDate, mcancel;

    /*public orderItem(Context context) {
        mContext = context;
    }*/


    AlertDialog.Builder builder;



    public orderItem(Context context, String orderId, String deliveryDate, String status, String cancel) {
        this.mContext = context;
        this.mstatus = status;
        this.morderId = orderId;
        this.mdeliveryDate = deliveryDate;
        this.mcancel = cancel;
    }

    @Resolve
    public void onResolved() {
        session = new SessionManager(mContext);
        apiInterface = APIClient.getClient().create(APIInterface.class);


        builder = new AlertDialog.Builder(mContext);


        orderIdOrder.setText("Order Id :" + " " + morderId);
        Date localTime = null;
        try {
            localTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(mdeliveryDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMMM d'th'''yy");
        String delivDate = sdf.format(new Date(localTime.getTime()));
        deliveryDateOrder.setText("Delivered on" + " " + delivDate);

        chkStatus = session.getStatusOrder();

        if (chkStatus.equals("Canceled")) {
            canceledOrder.setVisibility(android.view.View.VISIBLE);
            pendingOrder.setVisibility(android.view.View.GONE);
            deliveredOrder.setVisibility(android.view.View.GONE);
            canceledOrder.setText(mstatus);

        } else if (chkStatus.equals("Pending")) {
            canceledOrder.setVisibility(android.view.View.GONE);
            pendingOrder.setVisibility(android.view.View.VISIBLE);
            deliveredOrder.setVisibility(android.view.View.GONE);
            pendingOrder.setText(mstatus);

        } else {
            canceledOrder.setVisibility(android.view.View.GONE);
            pendingOrder.setVisibility(android.view.View.GONE);
            deliveredOrder.setVisibility(android.view.View.VISIBLE);
            deliveredOrder.setText(mstatus);
        }
        session.storeStatusOrder(null);

        chkCancel = session.getCancelId();
        if (chkCancel.equals("0")) {
            btnCancelOrder.setVisibility(android.view.View.GONE);
        } else {
            btnCancelOrder.setVisibility(android.view.View.VISIBLE);
            btnCancelOrder.setText(mcancel);
        }
        session.storeCancelId(null);

    }

    @Click(R.id.llMyorder1)
    public void onCardClick() {
        Intent myIntent = new Intent(mContext, OrderDetial.class);
        //  myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);

    }

   /* public orderItem(Activity activity) {
        this.mActivity = activity;
    }*/

    @Click(R.id.btnCancelOrder)
    public void onCancelClick() {
      /*  LayoutInflater layoutInflaterCancel = LayoutInflater.from(this);
        final android.view.View cancelDialogView = (android.view.View) layoutInflaterCancel.inflate(R.layout.cancel_dialog,null);
        final AlertDialog dialogCancel = new AlertDialog.Builder(mContext).create();
        dialogCancel.setView(cancelDialogView);
        cancelDialogView.findViewById(R.id.btnCancel).setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                dialogCancel.dismiss();
            }
        });
        cancelDialogView.findViewById(R.id.btnConfirm).setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                dialogCancel.dismiss();
            }
        });
        dialogCancel.show();*/
    }


    @Click(R.id.btnReOrder)
    public void onReorderClick()
    {


        LayoutInflater li = LayoutInflater.from(mContext);
        android.view.View promptsView = li.inflate(R.layout.reorder_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, R.style.AlertDialogStyle);
        alertDialogBuilder.setView(promptsView);

        // set the custom dialog components - text, image and button
        TextView tvReorder = (TextView) promptsView.findViewById(R.id.tvReorder);
        TextView tvClose = (TextView) promptsView.findViewById(R.id.tvClose);
        TextView tvdesc = (TextView) promptsView.findViewById(R.id.tvdesc);
        Button btnCancel = (Button) promptsView.findViewById(R.id.btnCancel);
        Button btnChkItems = (Button) promptsView.findViewById(R.id.btnCheckItems);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        btnCancel.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                alertDialog.cancel();
            }
        });
        btnChkItems.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent myIntent = new Intent(mContext, ReorderHolder.class);
                // myIntent.putExtra("order_id", morderId);
                // myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);
            }
        });



    }

    @Click(R.id.btnDetails)
    public void onDetailsOrder() {
        Intent myIntent = new Intent(mContext, OrderDetailsHolder.class);
        // myIntent.putExtra("order_id", morderId);
        // myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);
    }

    @Click(R.id.btnPayNow)
    public void onPayNow() {
        Intent myIntent = new Intent(mContext, CheckoutOrder.class);
        // myIntent.putExtra("order_id", morderId);
        // myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);
    }






}

