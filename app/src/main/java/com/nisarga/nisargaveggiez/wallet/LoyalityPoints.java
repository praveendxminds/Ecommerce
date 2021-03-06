package com.nisarga.nisargaveggiez.wallet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.mindorks.placeholderview.PlaceHolderView;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.LoyalityPointsModel;
import com.nisarga.nisargaveggiez.retrofit.ReedemLoyalityPoints;
import com.nisarga.nisargaveggiez.retrofit.WalletBlncModel;

import java.net.ServerSocket;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoyalityPoints extends AppCompatActivity {

    APIInterface apiInterface;
    Toolbar toolbar;
    private TextView tvPoints;
    PlaceHolderView phvLoyalityPoints;
    Button btnProceed;
    private EditText etReedem;
    String strRedeemPoint;
    SessionManager sessionManager;
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loyality_points_details);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        sessionManager = new SessionManager(LoyalityPoints.this);

        progressdialog = new ProgressDialog(LoyalityPoints.this);
        progressdialog.setMessage("Please Wait....");


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        etReedem = findViewById(R.id.etReedem);
        tvPoints = findViewById(R.id.tvPoints);
        phvLoyalityPoints = findViewById(R.id.phvLoyalityPoints);
        btnProceed = findViewById(R.id.btnProceed);

        getLoyalityPoints();
    }

    public void redeemLoyalityPoints(int pointBlnc) {
        strRedeemPoint = etReedem.getText().toString();
        strRedeemPoint = strRedeemPoint.trim();
        final int intReedemPoint = !strRedeemPoint.equals("") ? Integer.parseInt(strRedeemPoint) : 0; //checking blank space replace with zero
        if (intReedemPoint >= 10 && intReedemPoint <= pointBlnc) {

            try {
                progressdialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            final ReedemLoyalityPoints get_loyltyPoints = new ReedemLoyalityPoints(sessionManager.getCustomerId(), strRedeemPoint);
            Call<ReedemLoyalityPoints> call = apiInterface.redeemPoints(get_loyltyPoints);
            call.enqueue(new Callback<ReedemLoyalityPoints>() {
                @Override
                public void onResponse(Call<ReedemLoyalityPoints> call, Response<ReedemLoyalityPoints> response) {

                    ReedemLoyalityPoints resource = response.body();
                    progressdialog.dismiss();
                    Toast.makeText(LoyalityPoints.this, resource.message, Toast.LENGTH_SHORT).show();
                    Intent intentRedeemptionSuccess = new Intent(LoyalityPoints.this, LoyalityPointSuccessAck.class);
                    startActivity(intentRedeemptionSuccess);
                }

                @Override
                public void onFailure(Call<ReedemLoyalityPoints> call, Throwable t) {
                    call.cancel();
                }
            });
        } else if(intReedemPoint < 10){
           etReedem.requestFocus();
           etReedem.setError("Minimum redemption point is 10!");
        }
        else
        {
            Toast.makeText(LoyalityPoints.this, "Insufficient Points", Toast.LENGTH_SHORT).show();
        }
    }

    public void getLoyalityPoints() {

        if (Utils.CheckInternetConnection(getApplicationContext())) {
//-------------------------------------image slider view----------------------------------------------------------------------
            final LoyalityPointsModel get_loyltyPoints = new LoyalityPointsModel(sessionManager.getCustomerId());
            Call<LoyalityPointsModel> call = apiInterface.getLoyalityPoints(get_loyltyPoints);
            call.enqueue(new Callback<LoyalityPointsModel>() {
                @Override
                public void onResponse(Call<LoyalityPointsModel> call, Response<LoyalityPointsModel> response) {

                    final LoyalityPointsModel resource = response.body();


                    Log.d("lo", String.valueOf(resource.data));

                    if ((resource.data).equals("null")) {

                        tvPoints.setText("0" + " " + "Points");
                        btnProceed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(LoyalityPoints.this, "No points available for redemption", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        tvPoints.setText(resource.data + " " + "Points");
                        btnProceed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int number = Integer.parseInt(resource.data);
                                redeemLoyalityPoints(number);
                            }
                        });
                    }

                    Log.d("resource----", String.valueOf(resource.note));

                    List<LoyalityPointsModel.DatumLP> datumList = resource.note;
                    for (LoyalityPointsModel.DatumLP notes : datumList) {
                        if (response.isSuccessful()) {
                            phvLoyalityPoints.addView(new LoyalityPointNotes(LoyalityPoints.this, notes.desciption));
                        }
                    }

                }

                @Override
                public void onFailure(Call<LoyalityPointsModel> call, Throwable t) {
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
                break;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
