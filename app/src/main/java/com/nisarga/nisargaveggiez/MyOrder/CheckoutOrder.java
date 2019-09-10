package com.nisarga.nisargaveggiez.MyOrder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.payment.PayMentGateWay;
import com.nisarga.nisargaveggiez.payment.PaymentDetails;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.ProductslHomePage;
import com.nisarga.nisargaveggiez.retrofit.ReorderItemsModel;
import com.nisarga.nisargaveggiez.retrofit.WalletBlncModel;
import com.nisarga.nisargaveggiez.wallet.Usewallet;
import com.nisarga.nisargaveggiez.wallet.Walletpayment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutOrder extends AppCompatActivity {
    Toolbar toolbar;
    private Button btnItems;
    private LinearLayout llPayNow;
    String str_custid;
    SessionManager session;
    APIInterface apiInterface;
    private String order_id;
    private TextView tvOrdId, tvNameChkoutOrder, tvPhnChkoutOrder, tvAprtNameChkoutOrder, tvAprtDetailsChkoutOrder,tvDelivDayChkoutOrder;
    private TextView tvChkoutDelvInstruct, tvInvoiceNo, tvOrdNo, tvOrdItems, tvSubTotal, tvDeliveryCharges, tvPrice, tvFinalTotal;
    private TextView tvWalletAmnt;
    String strTotalAmnt, strPrice, strSubTotal, strDelvCharge;
    private String getFirstName, getPhone, getEmail, getAmount, getOrdId,strWallet="0";
    private String shipAddress, shipCity, shipZone,deliveryDay;
    CheckBox checkbox;
    ProgressDialog progressdialog;
    ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_order);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        order_id = getIntent().getExtras().getString("order_id", null);
        session = new SessionManager(getApplicationContext());
        str_custid = session.getCustomerId();

        progressdialog = new ProgressDialog(CheckoutOrder.this);
        progressdialog.setMessage("Please wait...");

        progressBar = findViewById(R.id.pbLoading);
        progressBar.setVisibility(View.VISIBLE);


        tvOrdId = findViewById(R.id.tvOrdId);
        tvNameChkoutOrder = findViewById(R.id.tvNameChkoutOrder);
        tvPhnChkoutOrder = findViewById(R.id.tvPhnChkoutOrder);
        tvAprtNameChkoutOrder = findViewById(R.id.tvAprtNameChkoutOrder);
        tvAprtDetailsChkoutOrder = findViewById(R.id.tvAprtDetailsChkoutOrder);
        tvDelivDayChkoutOrder = findViewById(R.id.tvDelivDayChkoutOrder);
        tvChkoutDelvInstruct = findViewById(R.id.tvChkoutDelvInstruct);
        tvInvoiceNo = findViewById(R.id.tvInvoiceNo);
        tvOrdNo = findViewById(R.id.tvOrdNo);
        tvOrdItems = findViewById(R.id.tvOrdItems);
        tvSubTotal = findViewById(R.id.tvSubTotal);
        tvDeliveryCharges = findViewById(R.id.tvDeliveryCharges);
        tvPrice = findViewById(R.id.tvPrice);
        tvFinalTotal = findViewById(R.id.tvFinalTotal);
        tvWalletAmnt = findViewById(R.id.tvWalletAmnt);
        checkbox = findViewById(R.id.checkbox);

        btnItems = findViewById(R.id.btnItems);
        llPayNow = findViewById(R.id.llPayNow);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        getFirstName = session.getFirstName();
        getPhone = session.getTelephone();
        getEmail = session.getEmail();

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    strWallet = "1";
                }
            }
        });

        //addPaymentOption(4);
        btnItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOrdId = tvOrdId.getText().toString();
                Intent intentReorder = new Intent(CheckoutOrder.this, OrderCheckItems.class);
                intentReorder.putExtra("order_id", getOrdId);
                startActivity(intentReorder);
            }
        });


        llPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkbox.isChecked())
                {
                    int walletBlnc = Integer.parseInt(getAmount);
                    if(walletBlnc == 0)
                    {
                        checkbox.setClickable(false);
                        Toast.makeText(CheckoutOrder.this,"There's not enough funds in your wallet",Toast.LENGTH_SHORT).show();
                    }
                    if (Utils.CheckInternetConnection(getApplicationContext())) {

                        try {
                            progressdialog.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//-------------------------------------Pay now procedure----------------------------------------------------------------------

                        final Usewallet wallet = new Usewallet(session.getCustomerId(),tvOrdId.getText().toString(),strWallet,"failure");
                        Call<Usewallet> call = apiInterface.esewallet(wallet);
                        call.enqueue(new Callback<Usewallet>() {
                            @Override
                            public void onResponse(Call<Usewallet> call, Response<Usewallet> response) {
                                Usewallet resource = response.body();
                                if (resource.status.equals("success"))
                                {

                                    progressdialog.dismiss();
                                    if(resource.total_to_be_paid>0)
                                    {
                                        Toast.makeText(getApplicationContext(), "Pay full amount using online payment method", Toast.LENGTH_SHORT).show();

                                        Log.d("finaltotol", String.valueOf(tvFinalTotal.getText().toString().replaceAll("Rs. ", "")));
                                        Intent intent = new Intent(getApplicationContext(), ReorderPayMentGateWay.class);
                                        intent.putExtra("FIRST_NAME", getFirstName);
                                        intent.putExtra("PHONE_NUMBER", getPhone);
                                        intent.putExtra("PHONE_NUMBER", getPhone);
                                        intent.putExtra("EMAIL_ADDRESS", getEmail);
                                        intent.putExtra("WALLET_STATUS", strWallet);
                                        intent.putExtra("ORDER_ID", tvOrdId.getText().toString());
                                        double dbl_Price_1 = Double.parseDouble(tvFinalTotal.getText().toString().replaceAll("Rs. ", ""));
                                        String strTotalAmntpay = String.format("%.2f", dbl_Price_1);

                                        intent.putExtra("RECHARGE_AMT", String.valueOf(resource.total_to_be_paid));

                                        startActivity(intent);

                                    }
                                    else
                                    {
                                        if(resource.total_to_be_paid == 0) {
                                            Intent intentSuccess = new Intent(getApplicationContext(), OrderPaymentSuccess.class);
                                            startActivity(intentSuccess);
                                        }
                                    }
                                }
                                else
                                {
                                    progressdialog.dismiss();
                                    Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), ReorderPayMentGateWay.class);
                                    intent.putExtra("FIRST_NAME", getFirstName);
                                    intent.putExtra("PHONE_NUMBER", getPhone);
                                    intent.putExtra("PHONE_NUMBER", getPhone);
                                    intent.putExtra("EMAIL_ADDRESS", getEmail);
                                    intent.putExtra("WALLET_STATUS", strWallet);
                                    intent.putExtra("ORDER_ID", tvOrdId.getText().toString());
                                    double dbl_Price_1 = Double.parseDouble(tvFinalTotal.getText().toString().replaceAll("Rs. ", ""));
                                    String strTotalAmntpay = String.format("%.2f", dbl_Price_1);

                                    intent.putExtra("RECHARGE_AMT", String.valueOf(strTotalAmntpay));

                                    startActivity(intent);


                                }
                            }

                            @Override
                            public void onFailure(Call<Usewallet> call, Throwable t) {
                                call.cancel();
                            }
                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                    Log.d("finaltotol","dsdssd");

                }
                else
                {


                    Log.d("finaltotol", String.valueOf(tvFinalTotal.getText().toString().replaceAll("Rs. ", "")));
                    Intent intent = new Intent(getApplicationContext(), ReorderPayMentGateWay.class);
                    intent.putExtra("FIRST_NAME", getFirstName);
                    intent.putExtra("PHONE_NUMBER", getPhone);
                    intent.putExtra("EMAIL_ADDRESS", getEmail);
                    intent.putExtra("ORDER_ID", tvOrdId.getText().toString());
                    intent.putExtra("WALLET_STATUS", strWallet);
                    double dbl_Price_1 = Double.parseDouble(tvFinalTotal.getText().toString().replaceAll("Rs. ", ""));
                    String strTotalAmntpay = String.format("%.2f", dbl_Price_1);

                    intent.putExtra("RECHARGE_AMT", strTotalAmntpay);

                    startActivity(intent);

                }


            }
        });
        showWalletBlnc();
        showListView();

    }

    // public String[] strPayArr = {"Wallet", "G-pay", "Paytm", "Phonepe"};

    /*  public void addPaymentOption(int number)
      {
          for(int i=0;i<4 ; i++)
          {
              RadioGroup ll = new RadioGroup(this);
              ll.setOrientation(LinearLayout.VERTICAL);
                  RadioButton radioBtn = new RadioButton(this);
                  radioBtn.setId(View.generateViewId());
                  radioBtn.setText(strPayArr[i]+radioBtn.getId());
                  ll.addView(radioBtn);
          }
          ((ViewGroup) findViewById(R.id.radiogroup)).addView(ll);
      }*/
    public void showWalletBlnc() {
        if (Utils.CheckInternetConnection(getApplicationContext())) {
//-------------------------------------image slider view----------------------------------------------------------------------
            final WalletBlncModel get_wallet_amnt = new WalletBlncModel(session.getCustomerId());
            Call<WalletBlncModel> call = apiInterface.getWalletBlnc(get_wallet_amnt);
            call.enqueue(new Callback<WalletBlncModel>() {
                @Override
                public void onResponse(Call<WalletBlncModel> call, Response<WalletBlncModel> response) {

                    WalletBlncModel resource = response.body();
                    if ((resource.data).equals("null")) {
                        tvWalletAmnt.setText("Rs." + " " + "0");
                    } else {
                        tvWalletAmnt.setText("Rs." + " " + resource.data);
                        getAmount = resource.data;
                    }
                }

                @Override
                public void onFailure(Call<WalletBlncModel> call, Throwable t) {
                    call.cancel();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void showListView() {
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            ReorderItemsModel reorderModel = new ReorderItemsModel(session.getCustomerId(), order_id);
            Call<ReorderItemsModel> callReorderItems = apiInterface.showReorderItems(reorderModel);
            callReorderItems.enqueue(new Callback<ReorderItemsModel>() {
                @Override
                public void onResponse(Call<ReorderItemsModel> call, Response<ReorderItemsModel> response) {
                    ReorderItemsModel resourcesReorder = response.body();
                    if (resourcesReorder.status.equals("success")) {

                        progressBar.setVisibility(View.INVISIBLE);
                        // Toast.makeText(getApplicationContext(), resourcesReorder.message, Toast.LENGTH_LONG).show();
                        List<ReorderItemsModel.ReorderResult> result = resourcesReorder.result;
                        for (ReorderItemsModel.ReorderResult reorderData : result) {

                            if ((reorderData.order_id) != null && !(reorderData.order_id).isEmpty()
                                    && !(reorderData.order_id).equals("null")) {

                                tvOrdId.setText(reorderData.order_id);
                            } else {
                                tvOrdId.setText("XXX");
                            }
                            if ((reorderData.quantity) != null && !(reorderData.quantity).isEmpty()
                                    && !(reorderData.quantity).equals("null")) {

                                tvOrdItems.setText(reorderData.quantity + " " + "Items");

                            } else {
                                tvOrdItems.setText("0" + " " + "Item");
                            }

                        }
                        //----------delivery day--------------------------

                        if((resourcesReorder.delivery_date)!= null && !(resourcesReorder.delivery_date).isEmpty()
                                && !(resourcesReorder.delivery_date).equals("null"))
                        {
                            Date localTime = null;
                            try {
                                localTime = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(resourcesReorder.delivery_date);
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }
                            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                            String delivDate = sdf.format(new Date(localTime.getTime()));
                            tvDelivDayChkoutOrder.setText(delivDate);
                        }
                        else {
                            tvDelivDayChkoutOrder.setText(deliveryDay);
                        }
                        //-------------------total amount------------------
                        if ((resourcesReorder.totalMoney) != null && !(resourcesReorder.totalMoney).isEmpty()
                                && !(resourcesReorder.totalMoney).equals("null")) {

                            double dbl_Price_1 = Double.parseDouble(resourcesReorder.totalMoney);
                            strTotalAmnt = String.format("%.2f", dbl_Price_1);
                            tvFinalTotal.setText("Rs." + " " + strTotalAmnt);
                        } else {
                            tvFinalTotal.setText("Rs." + " " + "00.00");
                        }
                        //---------------------variable price----------------
                        if (resourcesReorder.variable_price != 0) {

                            tvPrice.setText("Rs." + " " + resourcesReorder.variable_price);
                        } else {
                            tvPrice.setText("Rs." + " " + "00.00");
                        }
                        //----------------------sub-total--------------------
                        if ((resourcesReorder.sub_total) != null && !(resourcesReorder.sub_total).isEmpty()
                                && !(resourcesReorder.sub_total).equals("null")) {

                            double dbl_subTotal = Double.parseDouble(resourcesReorder.sub_total);
                            strSubTotal = String.format("%.2f", dbl_subTotal);
                            tvSubTotal.setText("Rs." + " " + strSubTotal);
                        } else {
                            tvSubTotal.setText("Rs." + " " + "00.00");
                        }
                        //-----------delivery charges------------
                        if ((resourcesReorder.delivery_charges) != null && !(resourcesReorder.delivery_charges).isEmpty()
                                && !(resourcesReorder.delivery_charges).equals("null")) {

                            double dbl_delivery = Double.parseDouble(resourcesReorder.delivery_charges);
                            strDelvCharge = String.format("%.2f", dbl_delivery);
                            tvDeliveryCharges.setText("Rs." + " " + strDelvCharge);
                        } else {
                            tvDeliveryCharges.setText("Rs." + " " + "00.00");
                        }


                        List<ReorderItemsModel.ReorderCustDetails> resultCustDetails = resourcesReorder.customer_details;
                        for (ReorderItemsModel.ReorderCustDetails custDetails : resultCustDetails) {
                            tvNameChkoutOrder.setText(custDetails.firstname + " " + custDetails.lastname);
                            tvPhnChkoutOrder.setText("+91" + " " + custDetails.telephone);

                            if ((custDetails.apartment) != null && !(custDetails.apartment).isEmpty()
                                    && !(custDetails.apartment).equals("null")) {

                                tvAprtNameChkoutOrder.setText(" "+custDetails.apartment);
                            } else {
                                tvAprtNameChkoutOrder.setText("");
                            }

                            if ((custDetails.shipping_address_1) != null && !(custDetails.shipping_address_1).isEmpty()
                                    && !(custDetails.shipping_address_1).equals("null")) {
                                shipAddress = custDetails.shipping_address_1 + ", ";
                            } else {
                                shipAddress = "";
                            }
                            if ((custDetails.shipping_city) != null && !(custDetails.shipping_city).isEmpty()
                                    && !(custDetails.shipping_city).equals("null")) {
                                shipCity = custDetails.shipping_city + ", ";
                            } else {
                                shipCity = "";
                            }
                            if ((custDetails.shipping_zone) != null && !(custDetails.shipping_zone).isEmpty()
                                    && !(custDetails.shipping_zone).equals("null")) {
                                shipZone = custDetails.shipping_zone;
                            } else {
                                shipZone = "";

                            }
                            tvAprtDetailsChkoutOrder.setText(shipAddress + shipCity + shipZone);

                            if ((custDetails.delivery_instruction) != null && !(custDetails.delivery_instruction).isEmpty()
                                    && !(custDetails.delivery_instruction).equals("null")) {

                                tvChkoutDelvInstruct.setText(custDetails.delivery_instruction);

                            } else if (custDetails.delivery_instruction.equals("[]")) {

                                tvChkoutDelvInstruct.setText("No Instructions");

                            } else {

                                tvChkoutDelvInstruct.setText("No Instructions");

                            }
                            //----------------invoice number-------------
                            if ((custDetails.invoice_prefix) != null && !(custDetails.invoice_prefix).isEmpty()
                                    && !(custDetails.invoice_prefix).equals("null")) {

                                tvInvoiceNo.setText(custDetails.invoice_prefix);
                            }
                            else
                            {
                                tvInvoiceNo.setText("");
                            }
                            //--------order number-----------
                            if ((custDetails.order_id) != null && !(custDetails.order_id).isEmpty()
                                    && !(custDetails.order_id).equals("null")) {

                                tvOrdNo.setText(custDetails.order_id);
                            }
                            else
                            {
                                tvOrdNo.setText("XXX");
                            }


                        }
                    } else if (resourcesReorder.status.equals("failure")) {
                        Toast.makeText(getApplicationContext(), resourcesReorder.message, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ReorderItemsModel> call, Throwable t) {

                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.instruction_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.help_menu_item:
                Intent intentHelp = new Intent(getBaseContext(), DeliveryInformation.class);
                startActivity(intentHelp);
                break;
        }
        return true;
    }



}