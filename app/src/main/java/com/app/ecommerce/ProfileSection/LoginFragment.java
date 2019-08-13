package com.app.ecommerce.ProfileSection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecommerce.Home.HomePage;
import com.app.ecommerce.R;
import com.app.ecommerce.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class LoginFragment extends Fragment {

    ProgressDialog progressdialog;
    ApiInterface apiInterface;
    SessionManager sessionManager;

    ImageView ivHidePass, ivShowPasss;
    EditText etEmail, etPassword;
    CheckBox cbRememberMe;
    TextView tvForgetPassword;
    ImageView ibtnSave;

    String sEmail, sPassword;
    LinearLayout vscroll_height;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        vscroll_height = (LinearLayout) ((LoginSignup_act)getActivity()).findViewById(R.id.vscroll_height);


        int height = 900;

//        ViewGroup.LayoutParams params = vscroll_height.getLayoutParams();
//        params.height = height+200;
//        vscroll_height.setLayoutParams(params);



//        view.post(new Runnable() {
//            @Override
//            public void run() {
//                // for instance
//            }
//        });



        progressdialog = new ProgressDialog(view.getContext());
        progressdialog.setMessage("Please Wait....");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(getContext());

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        cbRememberMe = view.findViewById(R.id.cbRememberMe);
        tvForgetPassword = view.findViewById(R.id.tvForgetPassword);
        ibtnSave = view.findViewById(R.id.ibtnSave);
        ivHidePass = view.findViewById(R.id.ivHidePass);
        ivShowPasss = view.findViewById(R.id.ivShowPass);
        //-------------------------show hide password-------------
        ivHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHidePass.setVisibility(View.GONE);
                ivShowPasss.setVisibility(View.VISIBLE);
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });

        ivShowPasss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivShowPasss.setVisibility(View.GONE);
                ivHidePass.setVisibility(View.VISIBLE);
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        //-----------------------save------------------------
        ibtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sEmail = etEmail.getText().toString();
                sPassword = etPassword.getText().toString();
                if (validateLogin(sEmail, sPassword)) {
                saveLoginData(sEmail, sPassword);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid User Id and Password", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
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
                    Toast.makeText(getContext(), resource.message, Toast.LENGTH_LONG).show();
                    List<UserLogin.Datum> datumList = resource.resultdata;
                    for (UserLogin.Datum datum : datumList) {
                        sessionManager.createLoginSession(datum.customer_id, datum.customer_group_id,
                                datum.firstname, datum.lastname, datum.email, datum.cart, datum.wishlist,
                                datum.address_id, datum.date_added, datum.api_token);
                        Intent intentHomePage = new Intent(getActivity(), HomePage.class);
                        startActivity(intentHomePage);
                    }

                } else if (resource.status.equals("error")) {
                    Toast.makeText(getContext(), resource.message, Toast.LENGTH_LONG).show();
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
}
