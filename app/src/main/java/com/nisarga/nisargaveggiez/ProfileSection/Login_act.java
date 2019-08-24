package com.nisarga.nisargaveggiez.ProfileSection;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.Home.HomePage;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.cart.cartItem;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.CartListModel;
import com.nisarga.nisargaveggiez.retrofit.CustomerDetails;
import com.nisarga.nisargaveggiez.retrofit.MyOrderList;

import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_act extends AppCompatActivity {

    ProgressDialog progressdialog;
    APIInterface apiInterface;
    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);

        progressdialog = new ProgressDialog(Login_act.this);
        progressdialog.setMessage("Please Wait....");

        apiInterface = APIClient.getClient().create(APIInterface.class);
        sessionManager = new SessionManager(Login_act.this);

        init();
    }

    EditText etEmail, etPassword;
    CheckBox cbRememberMe;
    TextView tvForgetPassword;
    ImageView ivSubmit;
    Button btnSignUp;

    String sEmail, sPassword;

    private void init() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);
        ivSubmit = findViewById(R.id.ivSubmit);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(Login_act.this, SignUp_act.class);
                startActivity(intent);
            }
        });

        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentForgotPassword = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intentForgotPassword);
            }
        });

        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (etPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off_pass, 0);
                            etPassword.setSelection(etPassword.getText().length());
                        } else {
                            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_pass, 0);
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        ivSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sEmail = etEmail.getText().toString();
                sPassword = etPassword.getText().toString();
                if (validateLogin(sEmail, sPassword)) {
                    if (Utils.CheckInternetConnection(getApplicationContext())) {
                        saveLoginData(sEmail, sPassword);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid User Id and Password", Toast.LENGTH_SHORT).show();
                }
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
        Call<UserLogin> calledu = apiInterface.login(user);
        calledu.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> calledu, Response<UserLogin> response) {
                final UserLogin resource = response.body();
                if (resource.status.equals("success")) {
                    Toast.makeText(Login_act.this, resource.message, Toast.LENGTH_LONG).show();
                    Intent intentHomePage = new Intent(Login_act.this, HomePage.class);

                    List<UserLogin.Datum> datumList = resource.resultdata;
                    for (UserLogin.Datum datum : datumList)
                    {
                        sessionManager.createLoginSession(datum.customer_id, datum.customer_group_id,
                                datum.firstname, datum.lastname, datum.email, datum.cart, datum.wishlist,datum.address_id,
                                datum.date_added, datum.image,datum.telephone,datum.company, datum.address_1,
                                datum.address_2, datum.city, datum.country_id, datum.zone_id,datum.postcode, datum.floor,
                                datum.door, datum.block,datum.apartment_name,datum.api_token);

                       // addcustomerseession(datum.api_token,datum.customer_id,datum.firstname,datum.lastname,datum.address_1,datum.city,datum.country_id,datum.zone_id,datum.company,datum.address_2,datum.postcode,datum.telephone,datum.email,datum.customer_group_id);

                        startActivity(intentHomePage);


                    }

                } else if (resource.status.equals("error")) {
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

    private boolean validateLogin(String loginid, String passwd) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (loginid == null || loginid.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Invalid User Id", Toast.LENGTH_SHORT).show();
            return false;

        } else {
            if (loginid.matches("[0-9]+")) {
                if (loginid.length() < 10 && loginid.length() > 10) {
                    etEmail.setError("Please Enter valid phone number");
                    etEmail.requestFocus();
                    return false;
                } else {
                    if (passwd == null || passwd.trim().length() == 0) {
                        etPassword.requestFocus();
                        etPassword.setError("Please enter password");
                        return false;
                    } else {
                        if (passwd.trim().length() < 4) {
                            etPassword.requestFocus();
                            etPassword.setError("Password should not be less than 4 digit");
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            } else {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(loginid).matches()) {
                    etEmail.setError("Please Enter valid email");
                    etEmail.requestFocus();
                    return false;
                } else {
                    if (passwd == null || passwd.trim().length() == 0) {
                        etPassword.requestFocus();
                        etPassword.setError("Please enter password");
                        return false;
                    } else {
                        if (passwd.trim().length() < 4) {
                            etPassword.requestFocus();
                            etPassword.setError("Password should not be less than 4 digit");
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }
        }

    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



//    public void addcustomerseession(String token) {
//        if (Utils.CheckInternetConnection(getApplicationContext())) {
//            //final CartListModel cartListModel = new CartListModel("api/cart/products","ea37ddb9108acd601b295e26fa");
//
//            Log.d("getToken", String.valueOf(token));
//
//            final CustomerDetails custdetails = new CustomerDetails("1");
//            Call<CustomerDetails> call = apiInterface.addcustdetails("api/customer/customerindex", token,custdetails);
//            call.enqueue(new Callback<CustomerDetails>() {
//                @Override
//                public void onResponse(Call<CustomerDetails> call, Response<CustomerDetails> response)
//                {
//                    CustomerDetails resource = response.body();
//                    if (resource.status.equals("success"))
//                    {
//
//                        Intent intentHomePage = new Intent(Login_act.this, HomePage.class);
//                        startActivity(intentHomePage);
//
//                    }
//
//                }
//
//
//
//                @Override
//                public void onFailure(Call<CustomerDetails> call, Throwable t) {
//                    call.cancel();
//                }
//            });
//
//
//
//
//        } else {
//            Toast.makeText(getApplicationContext(), "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
//        }
//    }


}
