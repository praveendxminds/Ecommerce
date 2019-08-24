package com.nisarga.nisargaveggiez.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.payment.PayMentGateWay;
import com.mindorks.placeholderview.PlaceHolderView;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.AddMoneytoWalletModel;
import com.nisarga.nisargaveggiez.retrofit.WalletBlncModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMoneyToWallet extends AppCompatActivity {

    APIInterface apiInterface;
    SessionManager session;
    Toolbar toolbar;
    private EditText etAmount;
    PlaceHolderView phvAddtoMoney;
    private Button btnProceed;
    private String strAmount;
    private String getFirstName, getPhone, getEmail, getAmount;
    private TextView tvWalletBlnc;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_money_to_wallet);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        etAmount = findViewById(R.id.etAmount);
        phvAddtoMoney = findViewById(R.id.phvAddtoMoney);
        btnProceed = findViewById(R.id.btnProceed);
        tvWalletBlnc = findViewById(R.id.tvWalletBlnc);
        final String[] strArrAmount = {"100", "200", "500", "1000"};
        phvAddtoMoney.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        for (int i = 0; i < strArrAmount.length; i++) {
            phvAddtoMoney.addView(new AddMoneyItems(getApplicationContext(), strArrAmount[i], etAmount));

        }
        getFirstName = session.getFirstName();
        getPhone = session.getTelephone();
        getEmail = session.getEmail();
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strAmount = etAmount.getText().toString();
                Intent intent = new Intent(getApplicationContext(), PayMentGateWay.class);
                intent.putExtra("FIRST_NAME", getFirstName);
                intent.putExtra("PHONE_NUMBER", getPhone);
                intent.putExtra("EMAIL_ADDRESS", getEmail);
                if(strAmount.equals("")||strAmount.equals("0"))
                {
                    etAmount.requestFocus();
                    etAmount.setError("Insufficient amount!");
                }else {
                    intent.putExtra("RECHARGE_AMT", strAmount);
                    startActivity(intent);
                }

            }
        });
        getWalletAmount();

    }

    public void getWalletAmount() {
        if (Utils.CheckInternetConnection(getApplicationContext())) {
//-------------------------------------image slider view----------------------------------------------------------------------
            final WalletBlncModel get_wallet_amnt = new WalletBlncModel(session.getCustomerId());
            Call<WalletBlncModel> call = apiInterface.getWalletBlnc(get_wallet_amnt);
            call.enqueue(new Callback<WalletBlncModel>() {
                @Override
                public void onResponse(Call<WalletBlncModel> call, Response<WalletBlncModel> response) {

                    WalletBlncModel resource = response.body();
                    if ((resource.data).equals("null")) {
                        tvWalletBlnc.setText("Rs." + " " + "0");
                    }
                    else
                    {
                        tvWalletBlnc.setText("Rs." + " " + resource.data);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuAddtoMoney = getMenuInflater();
        menuAddtoMoney.inflate(R.menu.instruction_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.help_menu_item:
                Intent intentAddtoMoney = new Intent(getBaseContext(), DeliveryInformation.class);
                startActivity(intentAddtoMoney);
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
