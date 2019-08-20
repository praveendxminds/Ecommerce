package com.nisarga.nisargaveggiez.ProfileSection;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
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

public class ResetPassword extends AppCompatActivity {
    Toolbar toolbar;
    EditText etNewPassword, etConfirmPassword;
    Button btnSubmit;
    String sNewPass, sConfPass, telephone;

    ProgressDialog progressdialog;
    APIInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        progressdialog = new ProgressDialog(ResetPassword.this);
        progressdialog.setMessage("Please Wait....");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSubmit = findViewById(R.id.btnSubmit);

        etNewPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (etNewPassword.getRight() - etNewPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (etNewPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            etNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            etNewPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off_pass, 0);
                            etNewPassword.setSelection(etNewPassword.getText().length());
                        } else {
                            etNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            etNewPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_pass, 0);
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        etConfirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (etConfirmPassword.getRight() - etConfirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (etConfirmPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            etConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off_pass, 0);
                            etConfirmPassword.setSelection(etConfirmPassword.getText().length());
                        } else {
                            etConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            etConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_pass, 0);
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telephone = getIntent().getExtras().getString("Mobile", "defaultKey");
                sNewPass = etNewPassword.getText().toString();
                sConfPass = etConfirmPassword.getText().toString();

                if (TextUtils.isEmpty(sNewPass)) {
                    new AlertDialog.Builder(ResetPassword.this)
                            .setMessage("Please Enter your New Password")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(sConfPass)) {
                    new AlertDialog.Builder(ResetPassword.this)
                            .setMessage("Please Enter your Confirm Password")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }

                if (!(etNewPassword.getText().toString().equals(etConfirmPassword.getText().toString()))) {
                    new AlertDialog.Builder(ResetPassword.this)
                            .setMessage("Password Entered is not Matching")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }

                if (Utils.CheckInternetConnection(ResetPassword.this)) {
                    resetPassword(sNewPass, sConfPass, telephone);
                } else {
                    Toast.makeText(ResetPassword.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void resetPassword(String sNewPass, String sConfPass, String telephone) {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final ResetPasswordModel reset_password = new ResetPasswordModel(sNewPass, sConfPass, telephone);
        Call<ResetPasswordModel> calledu = apiInterface.reset_password(reset_password);
        calledu.enqueue(new Callback<ResetPasswordModel>() {
            @Override
            public void onResponse(Call<ResetPasswordModel> calledu, Response<ResetPasswordModel> response) {
                final ResetPasswordModel resource = response.body();
                if (resource.status.equals("success")) {
                    Toast.makeText(ResetPassword.this, resource.message, Toast.LENGTH_LONG).show();
                    Intent intentResetPassword = new Intent(ResetPassword.this, Login_act.class);
                    startActivity(intentResetPassword);
                } else if (resource.status.equals("failure")) {
                    Toast.makeText(ResetPassword.this, resource.message, Toast.LENGTH_LONG).show();
                }
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResetPasswordModel> calledu, Throwable t) {
                calledu.cancel();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
