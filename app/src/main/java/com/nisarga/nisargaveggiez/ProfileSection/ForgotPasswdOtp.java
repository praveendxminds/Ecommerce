package com.nisarga.nisargaveggiez.ProfileSection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswdOtp extends AppCompatActivity implements TextWatcher, View.OnKeyListener, View.OnFocusChangeListener {
    Toolbar toolbar;
    EditText etOtp1, etOtp2, etOtp3, etOtp4;
    Button btnVerify;
    private int whoHasFocus;
    char[] code = new char[4];//Store the digits in charArray.

    String strOtp1, strOtp2, strOtp3, strOtp4;
    String finalOtp;
    String telephone;

    ProgressDialog progressdialog;
    APIInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_passwd_otp);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        progressdialog = new ProgressDialog(ForgotPasswdOtp.this);
        progressdialog.setMessage("Please Wait....");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        etOtp1 = findViewById(R.id.etOtp1);
        etOtp2 = findViewById(R.id.etOtp2);
        etOtp3 = findViewById(R.id.etOtp3);
        etOtp4 = findViewById(R.id.etOtp4);
        etOtp1.requestFocus();//Left digit gets focus after adding of fragment in Container

        btnVerify = findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telephone = getIntent().getExtras().getString("Telephone", "defaultKey");
                strOtp1 = etOtp1.getText().toString();
                strOtp2 = etOtp2.getText().toString();
                strOtp3 = etOtp3.getText().toString();
                strOtp4 = etOtp4.getText().toString();

                finalOtp = strOtp1 + strOtp2 + strOtp3 + strOtp4;

                if (Utils.CheckInternetConnection(ForgotPasswdOtp.this)) {
                    otpVerify(finalOtp, telephone);
                } else {
                    Toast.makeText(ForgotPasswdOtp.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void otpVerify(String finalOtp, final String telephone) {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final VerifyOTP verifyOTP = new VerifyOTP(finalOtp, telephone);
        Call<VerifyOTP> calledu = apiInterface.verify_otp(verifyOTP);
        calledu.enqueue(new Callback<VerifyOTP>() {
            @Override
            public void onResponse(Call<VerifyOTP> calledu, Response<VerifyOTP> response) {
                final VerifyOTP resource = response.body();
                if (resource.status.equals("success")) {
                    Intent intentResetPassword = new Intent(ForgotPasswdOtp.this, ResetPassword.class);
                    intentResetPassword.putExtra("Mobile", telephone);
                    startActivity(intentResetPassword);
                } else if (resource.status.equals("failure")) {
                    Toast.makeText(ForgotPasswdOtp.this, resource.message, Toast.LENGTH_LONG).show();
                }
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(Call<VerifyOTP> calledu, Throwable t) {
                calledu.cancel();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        switch (whoHasFocus) {
            case 1:
                if (!etOtp1.getText().toString().isEmpty()) {
                    code[0] = etOtp1.getText().toString().charAt(0);
                    etOtp2.requestFocus();
                }
                break;

            case 2:
                if (!etOtp2.getText().toString().isEmpty()) {
                    code[1] = etOtp2.getText().toString().charAt(0);
                    etOtp2.requestFocus();
                }
                break;

            case 3:
                if (!etOtp3.getText().toString().isEmpty()) {
                    code[2] = etOtp3.getText().toString().charAt(0);
                    etOtp4.requestFocus();
                }
                break;

            case 4:
                if (!etOtp4.getText().toString().isEmpty()) {
                    code[3] = etOtp4.getText().toString().charAt(0);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.etOtp1:
                whoHasFocus = 1;
                break;

            case R.id.etOtp2:
                whoHasFocus = 2;
                break;

            case R.id.etOtp3:
                whoHasFocus = 3;
                break;

            case R.id.etOtp4:
                whoHasFocus = 4;
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                switch (v.getId()) {
                    case R.id.etOtp2:
                        if (etOtp2.getText().toString().isEmpty())
                            etOtp1.requestFocus();
                        break;

                    case R.id.etOtp3:
                        if (etOtp3.getText().toString().isEmpty())
                            etOtp2.requestFocus();
                        break;

                    case R.id.etOtp4:
                        if (etOtp4.getText().toString().isEmpty())
                            etOtp3.requestFocus();
                        break;

                    default:
                        break;
                }
            }
        }
        return false;
    }
}
