package com.app.ecommerce.ProfileSection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecommerce.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_act extends AppCompatActivity {

    ProgressDialog progressdialog;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);

        progressdialog = new ProgressDialog(Login_act.this);
        progressdialog.setMessage("Please Wait....");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        init();
    }

    Button btnSignUp;
    EditText etEmail, etPassword;
    CheckBox cbRememberMe;
    TextView tvForgetPassword;
    ImageButton ibtnSave;

    String sEmail, sPassword;

    private void init() {
        btnSignUp = findViewById(R.id.btnSignUp);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        cbRememberMe = findViewById(R.id.cbRememberMe);

        tvForgetPassword = findViewById(R.id.tvForgetPassword);

        ibtnSave = findViewById(R.id.ibtnSave);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_act.this, SignUp_act.class);
                startActivity(intent);
            }
        });

        ibtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sEmail = etEmail.getText().toString();
                sPassword = etPassword.getText().toString();

                saveLoginData(sEmail, sPassword);
            }
        });
    }

    private void saveLoginData(String sEmail, String sPassword) {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final UserLogin user = new UserLogin(sEmail, sPassword);
        Call<UserLogin> calledu = (Call<UserLogin>) apiInterface.login(user);
        calledu.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> calledu, Response<UserLogin> response) {
                final UserLogin resource = response.body();
                if (resource.status.equals("success")) {
                    Toast.makeText(Login_act.this, resource.message, Toast.LENGTH_LONG).show();
                    List<UserLogin.Datum> datumList = resource.resultdata;
                    for (UserLogin.Datum datum : datumList) {

                    }

                } else if (resource.status.equals("failure")) {
                    Toast.makeText(Login_act.this, resource.message, Toast.LENGTH_LONG).show();
                }
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserLogin> calledu, Throwable t) {
                calledu.cancel();
            }
        });
    }
}
