package com.nisarga.nisargaveggiez.MyOrder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.payment.PaymentDetails;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.ReorderItemsModel;
import com.nisarga.nisargaveggiez.retrofit.WalletBlncModel;

import java.util.List;

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
    private TextView tvOrdId, tvNameChkoutOrder, tvPhnChkoutOrder, tvAprtNameChkoutOrder, tvAprtDetailsChkoutOrder;
    private TextView tvChkoutDelvInstruct, tvInvoiceNo, tvOrdNo, tvOrdItems, tvSubTotal, tvDeliveryCharges, tvPrice, tvFinalTotal;
    private TextView tvWalletAmnt;
    String strTotalAmnt, strPrice, strSubTotal, strDelvCharge;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_order);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        order_id = getIntent().getExtras().getString("order_id", "1");
        session = new SessionManager(getApplicationContext());
        str_custid = session.getCustomerId();

        tvOrdId = findViewById(R.id.tvOrdId);
        tvNameChkoutOrder = findViewById(R.id.tvNameChkoutOrder);
        tvPhnChkoutOrder = findViewById(R.id.tvPhnChkoutOrder);
        tvAprtNameChkoutOrder = findViewById(R.id.tvAprtNameChkoutOrder);
        tvAprtDetailsChkoutOrder = findViewById(R.id.tvAprtDetailsChkoutOrder);
        tvChkoutDelvInstruct = findViewById(R.id.tvChkoutDelvInstruct);
        tvInvoiceNo = findViewById(R.id.tvInvoiceNo);
        tvOrdNo = findViewById(R.id.tvOrdNo);
        tvOrdItems = findViewById(R.id.tvOrdItems);
        tvSubTotal = findViewById(R.id.tvSubTotal);
        tvDeliveryCharges = findViewById(R.id.tvDeliveryCharges);
        tvPrice = findViewById(R.id.tvPrice);
        tvFinalTotal = findViewById(R.id.tvFinalTotal);
        tvWalletAmnt = findViewById(R.id.tvWalletAmnt);

        btnItems = findViewById(R.id.btnItems);
        llPayNow = findViewById(R.id.llPayNow);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //addPaymentOption(4);
        btnItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReorder = new Intent(CheckoutOrder.this, ReorderHolder.class);
                startActivity(intentReorder);
            }
        });
        llPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        showListView();
        showWalletBlnc();
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
                    }
                    else
                    {
                        tvWalletAmnt.setText("Rs." + " " + resource.data);

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
            ReorderItemsModel reorderModel = new ReorderItemsModel("1", "1");
            Call<ReorderItemsModel> callReorderItems = apiInterface.showReorderItems(reorderModel);
            callReorderItems.enqueue(new Callback<ReorderItemsModel>() {
                @Override
                public void onResponse(Call<ReorderItemsModel> call, Response<ReorderItemsModel> response) {
                    ReorderItemsModel resourcesReorder = response.body();
                    if (resourcesReorder.status.equals("success")) {
                        Toast.makeText(getApplicationContext(), resourcesReorder.message, Toast.LENGTH_LONG).show();
                        List<ReorderItemsModel.ReorderResult> result = resourcesReorder.result;
                        for (ReorderItemsModel.ReorderResult reorderData : result) {
                            tvOrdId.setText(reorderData.order_id);
                            tvOrdItems.setText(reorderData.quantity + " " + "Items");
                            double dbl_Price = Double.parseDouble(reorderData.price);
                            strPrice = String.format("%.2f", dbl_Price);
                            tvPrice.setText("Rs." + " " + strPrice);


                        }

                        double dbl_Price_1 = Double.parseDouble(resourcesReorder.totalMoney);
                        strTotalAmnt = String.format("%.2f", dbl_Price_1);
                        tvFinalTotal.setText("Rs." + " " + strTotalAmnt);

                        List<ReorderItemsModel.ReorderCustDetails> resultCustDetails = resourcesReorder.customer_details;
                        for (ReorderItemsModel.ReorderCustDetails custDetails : resultCustDetails) {
                            tvNameChkoutOrder.setText(custDetails.firstname + " " + custDetails.lastname);
                            tvPhnChkoutOrder.setText("+91" + " " + custDetails.telephone);
                            tvAprtNameChkoutOrder.setText(custDetails.apartment);
                            tvAprtDetailsChkoutOrder.setText(custDetails.shipping_address_1 + ", " + custDetails.shipping_city + ", " + custDetails.shipping_zone);
                            if (custDetails.delivery_instruction.equals("[]")) {
                                tvChkoutDelvInstruct.setText("No Instructions");
                            } else {
                                tvChkoutDelvInstruct.setText(custDetails.delivery_instruction);
                            }
                            tvInvoiceNo.setText(custDetails.invoice_no);
                            tvOrdNo.setText(custDetails.order_id);

                            double dbl_subTotal = Double.parseDouble(resourcesReorder.totalMoney);
                            strSubTotal = String.format("%.2f", dbl_subTotal);
                            tvSubTotal.setText("Rs." + " " + strSubTotal);


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
