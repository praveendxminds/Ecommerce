package com.nisarga.nisargaveggiez.ProfileSection;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.Home.HomePage;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {
    Toolbar toolbar;
    EditText etEmailorMobile;
    Button btnSubmit, btnResendOTP;
    String strInput;
    ProgressDialog progressdialog;
    APIInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        progressdialog = new ProgressDialog(ForgotPassword.this);
        progressdialog.setMessage("Please Wait....");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        etEmailorMobile = findViewById(R.id.etEmailorMobile);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnResendOTP = findViewById(R.id.btnResendOTP);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strInput = etEmailorMobile.getText().toString();

                if (TextUtils.isEmpty(strInput)) {
                    new AlertDialog.Builder(ForgotPassword.this)
                            .setMessage("Please Enter Register Email or Mobile No !")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }

                if (Utils.CheckInternetConnection(ForgotPassword.this)) {
                    forgetPassword(strInput);
                } else {
                    Toast.makeText(ForgotPassword.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strInput = etEmailorMobile.getText().toString();

                if (TextUtils.isEmpty(strInput)) {
                    new AlertDialog.Builder(ForgotPassword.this)
                            .setMessage("Please Enter Register Email or Mobile No !")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }

                if (Utils.CheckInternetConnection(ForgotPassword.this)) {
                    forgetPassword(strInput);
                } else {
                    Toast.makeText(ForgotPassword.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void forgetPassword(String strInput) {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final ForgetPasswordModel forgetPasswordModel = new ForgetPasswordModel(strInput);
        Call<ForgetPasswordModel> calledu = apiInterface.forget_password(forgetPasswordModel);
        calledu.enqueue(new Callback<ForgetPasswordModel>() {
            @Override
            public void onResponse(Call<ForgetPasswordModel> calledu, Response<ForgetPasswordModel> response) {
                final ForgetPasswordModel resource = response.body();
                if (resource.status.equals("success")) {
                    List<ForgetPasswordModel.Datum> datumList = resource.resultdata;
                    for (ForgetPasswordModel.Datum datum : datumList) {
                        Intent intentOtpForgotPassword = new Intent(ForgotPassword.this, ForgotPasswdOtp.class);
                        intentOtpForgotPassword.putExtra("Telephone", datum.telephone);
                        startActivity(intentOtpForgotPassword);
                    }

                } else if (resource.status.equals("failure")) {
                    Toast.makeText(ForgotPassword.this, resource.message, Toast.LENGTH_LONG).show();
                }
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(Call<ForgetPasswordModel> calledu, Throwable t) {
                calledu.cancel();
            }
        });
    }

    public boolean validateInput(String strVal) {
        if (strVal == null || strVal.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Invalid Email Id and Mobile No", Toast.LENGTH_SHORT).show();
            return false;

        } else {
            if (strVal.matches("[0-9]+")) {
                if (strVal.length() < 10 && strVal.length() > 10) {
                    etEmailorMobile.setError("Please Enter valid phone number");
                    etEmailorMobile.requestFocus();
                    return false;
                }
            } else {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(strVal).matches()) {
                    etEmailorMobile.setError("Please Enter valid email");
                    etEmailorMobile.requestFocus();
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}