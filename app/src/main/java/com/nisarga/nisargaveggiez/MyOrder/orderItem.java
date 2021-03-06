package com.nisarga.nisargaveggiez.MyOrder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.nisarga.nisargaveggiez.R;
import com.mindorks.placeholderview.annotations.View;
import com.nisarga.nisargaveggiez.retrofit.CancelOrderModel;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @View(R.id.llPayNow)
    public LinearLayout llPayNow;

    @View(R.id.btnPayNow)
    public Button btnPayNow;

    // public Activity mActivity;
    APIInterface apiInterface;
    SessionManager session;
    public Context mContext;
    String chkCancel, chkStatus, mstatus, morderId, mdeliveryDate, mcancel, mPayStatus;

    /*public orderItem(Context context) {
        mContext = context;
    }*/


    AlertDialog.Builder builder;


    public orderItem(Context context, String orderId, String deliveryDate, String status, String cancel, String pay_status) {
        this.mContext = context;
        this.mstatus = status;
        this.morderId = orderId;
        this.mdeliveryDate = deliveryDate;
        this.mcancel = cancel;
        this.mPayStatus = pay_status;
    }

    @Resolve
    public void onResolved() {
        session = new SessionManager(mContext);
        apiInterface = APIClient.getClient().create(APIInterface.class);


        builder = new AlertDialog.Builder(mContext);

        if (morderId != null && !morderId.isEmpty() && !morderId.equals("null")) {
            orderIdOrder.setText("Order Id :" + " " + morderId);
        } else {
            orderIdOrder.setText("Order Id :" + " " + "XXX");
        }


        Date localTime = null;
        try {
            localTime = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(mdeliveryDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localDay = new SimpleDateFormat("dd");
        String dayFormat = localDay.format(new Date(localTime.getTime()));
        int getDay = Integer.parseInt(dayFormat);
        String dayNumberSuffix = getDayNumberSuffix(getDay);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMMM d'" + dayNumberSuffix + "'''yy");

        String delivDate = sdf.format(new Date(localTime.getTime()));
        Log.e("-----date-------------", delivDate);
        deliveryDateOrder.setText("Delivered on" + " " + delivDate);


        if (mstatus.equals("Canceled")) {
            canceledOrder.setVisibility(android.view.View.VISIBLE);
            pendingOrder.setVisibility(android.view.View.GONE);
            deliveredOrder.setVisibility(android.view.View.GONE);
            llPayNow.setVisibility(android.view.View.GONE);
            deliveryDateOrder.setText("Order Canceled");
            canceledOrder.setText(mstatus);
        } else if (mstatus.equals("Complete")) {
            canceledOrder.setVisibility(android.view.View.GONE);
            pendingOrder.setVisibility(android.view.View.GONE);
            deliveredOrder.setVisibility(android.view.View.VISIBLE);
            llPayNow.setVisibility(android.view.View.GONE);
            deliveryDateOrder.setText("Order Delivered");
            deliveredOrder.setText("Delivered");
        } else {
            canceledOrder.setVisibility(android.view.View.GONE);
            pendingOrder.setVisibility(android.view.View.VISIBLE);
            deliveredOrder.setVisibility(android.view.View.GONE);
            deliveryDateOrder.setText("Delivered on" + " " + delivDate);
            pendingOrder.setText("Pending");
        }

        if (mcancel.equals("0")) {
            btnCancelOrder.setVisibility(android.view.View.INVISIBLE);
        } else {
            btnCancelOrder.setVisibility(android.view.View.VISIBLE);
            btnCancelOrder.setText("Cancel");


            /* time date comparison 4 pm starts */
            String tddate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());


            SimpleDateFormat del_dt = new SimpleDateFormat("dd/MM/yyyy");
            String deliver_Date = del_dt.format(new Date(localTime.getTime()));


            SimpleDateFormat sdfss = new SimpleDateFormat("dd/MM/yyyy");
            Date strDate = null;
            try {
                strDate = sdfss.parse(deliver_Date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.d("my_date", String.valueOf(deliver_Date));

            if (tddate.equals(deliver_Date)) {
                btnPayNow.setEnabled(true);
                btnPayNow.setClickable(true);
                btnPayNow.setBackgroundResource(R.drawable.paynow_buttons); //rounded corner yellow button
                GradientDrawable drawable = (GradientDrawable) btnPayNow.getBackground();
                drawable.setColor(Color.parseColor("#FBD249"));

                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                if (hour > 16) {
                    btnCancelOrder.setEnabled(false);
                    btnCancelOrder.setClickable(false);
                    btnCancelOrder.setBackgroundColor(Color.parseColor("#33FA5400"));
                    Log.d("my_date", "YES");
                }

            }

            if (new Date().after(strDate)) {
                btnCancelOrder.setEnabled(false);
                btnCancelOrder.setClickable(false);
                btnCancelOrder.setBackgroundColor(Color.parseColor("#33FA5400"));
            } else {
                btnCancelOrder.setClickable(true);
                btnCancelOrder.setEnabled(true);
            }
            if (mPayStatus != null && !mPayStatus.isEmpty() && !mPayStatus.equals("null")) {
                if (mPayStatus.equals("0")) {
                    llPayNow.setVisibility(android.view.View.VISIBLE);
                } else {
                    llPayNow.setVisibility(android.view.View.INVISIBLE);
                }
            } else {
                llPayNow.setVisibility(android.view.View.VISIBLE);
            }
            /* time date comparison 4 pm ends */
        }
    }

    @Click(R.id.btnCancelOrder)
    public void onCancelClick() {

        LayoutInflater li = LayoutInflater.from(mContext);
        android.view.View promptsView = li.inflate(R.layout.cancel_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, R.style.AlertDialogStyle);
        alertDialogBuilder.setView(promptsView);

        // set the custom dialog components - text, image and button
        TextView tvReorder = (TextView) promptsView.findViewById(R.id.tvReorder);
        TextView tvClose = (TextView) promptsView.findViewById(R.id.tvClose);
        TextView tvdesc = (TextView) promptsView.findViewById(R.id.tvDesc);
        Button btnCancel = (Button) promptsView.findViewById(R.id.btnCancel);
        Button btnConfirm = (Button) promptsView.findViewById(R.id.btnConfirm);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        btnCancel.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                alertDialog.cancel();
            }
        });
        btnConfirm.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                //api-call
                if (Utils.CheckInternetConnection(mContext)) {
                    final CancelOrderModel get_order_list = new CancelOrderModel(morderId, session.getCustomerId());
                    Call<CancelOrderModel> call = apiInterface.cancelOrder(get_order_list);
                    call.enqueue(new Callback<CancelOrderModel>() {
                        @Override
                        public void onResponse(Call<CancelOrderModel> call, Response<CancelOrderModel> response) {
                            CancelOrderModel resource = response.body();
                            Toast.makeText(mContext, "Your order has been cancelled", Toast.LENGTH_SHORT).show();
                            btnCancelOrder.setVisibility(android.view.View.INVISIBLE);
                            canceledOrder.setVisibility(android.view.View.VISIBLE);
                            pendingOrder.setVisibility(android.view.View.GONE);
                            deliveredOrder.setVisibility(android.view.View.GONE);
                            llPayNow.setVisibility(android.view.View.INVISIBLE);
                            canceledOrder.setText("Canceled");
                            deliveryDateOrder.setText("Order Canceled");
                            alertDialog.dismiss();

                        }

                        @Override
                        public void onFailure(Call<CancelOrderModel> call, Throwable t) {
                            call.cancel();
                        }
                    });

                } else {
                    Toast.makeText(mContext, "No Internet. Please check internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvClose.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                alertDialog.dismiss();
            }
        });
    }


    @Click(R.id.btnReOrder)
    public void onReorderClick() {
        LayoutInflater li = LayoutInflater.from(mContext);
        android.view.View promptsView = li.inflate(R.layout.reorder_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, R.style.AlertDialogStyle);
        alertDialogBuilder.setView(promptsView);

        // set the custom dialog components - text, image and button
        TextView tvReorder = (TextView) promptsView.findViewById(R.id.tvReorder);
        TextView tvClose = (TextView) promptsView.findViewById(R.id.tvClose);
        TextView tvdesc = (TextView) promptsView.findViewById(R.id.tvDesc);
        Button btnCancel = (Button) promptsView.findViewById(R.id.btnCancel);
        Button btnChkItems = (Button) promptsView.findViewById(R.id.btnChkItems);

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
                myIntent.putExtra("order_id", morderId);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(myIntent);
                alertDialog.dismiss();
            }
        });
        tvClose.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                alertDialog.dismiss();
            }
        });


    }

    @Click(R.id.btnDetails)
    public void onDetailsOrder() {
        Intent myIntent = new Intent(mContext, OrderDetailsHolder.class);
        myIntent.putExtra("order_id", morderId);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(myIntent);
    }

    @Click(R.id.btnPayNow)
    public void onPayNow() {
        Intent myIntent = new Intent(mContext, CheckoutOrder.class);
        myIntent.putExtra("order_id", morderId);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(myIntent);
    }

    private String getDayNumberSuffix(int day) {//day format
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

}

