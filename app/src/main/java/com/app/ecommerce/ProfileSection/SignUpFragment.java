package com.app.ecommerce.ProfileSection;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecommerce.R;
import com.app.ecommerce.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SignUpFragment extends Fragment {

    ProgressDialog progressdialog;
    ApiInterface apiInterface;

    private String strFirstName, strLastName, strMobile, strEmail, strPasswd, strApartment, strBlock, strFloor, strDoor;
    private String strArea, strCity, strAddress, strLandmark, strPincode;
    private ImageView ivProfileSignUp;
    private EditText etFirstName, etLastName, etMobileNo, etEmail, etPassword, etWing;
    private EditText etFloorNumber, etDoorNumber, etArea, etCity, etAddress, etLandMark, etPincode;
    private ImageView ibtnSave;
    private Spinner spnrApartmentName;
    private CheckBox cbNearByApartment;
    private TextView tvNearByApartment;
    private String getApartment;
    String selectedApartment;
    List<String> listAdd;
    LinearLayout vscroll_height,signup_frg;
    int height;


    String[] plants = new String[]{
            "Black alder",
            "Speckled alder",
            "Striped alder"
    };


    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        signup_frg = (LinearLayout) view.findViewById(R.id.signup_frg);




//        view.post(new Runnable() {
//            @Override
//            public void run() {
//                // for instance
//                 height = signup_frg.getMeasuredHeight();
//                 Log.d("ddddd", String.valueOf(height));
//
//            }
//        });






        progressdialog = new ProgressDialog(view.getContext());
        progressdialog.setMessage("Please Wait....");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        ivProfileSignUp = view.findViewById(R.id.ivProfileSignUp);
        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etMobileNo = view.findViewById(R.id.etMobileNo);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        spnrApartmentName = view.findViewById(R.id.spnrApartmentName);
        etWing = view.findViewById(R.id.etWing);
        etFloorNumber = view.findViewById(R.id.etFloorNumber);
        etDoorNumber = view.findViewById(R.id.etDoorNumber);
        etArea = view.findViewById(R.id.etArea);
        etCity = view.findViewById(R.id.etCity);
        etAddress = view.findViewById(R.id.etAddress);
        etLandMark = view.findViewById(R.id.etLandMark);
        etPincode = view.findViewById(R.id.etPincode);
        ibtnSave = view.findViewById(R.id.ibtnSave);
        cbNearByApartment = view.findViewById(R.id.cbNearByApartment);
        tvNearByApartment = view.findViewById(R.id.tvNearByApartment);


        final List<String> aprtcategories = new ArrayList<String>();
        aprtcategories.add("Select");


        listAdd = new ArrayList<String>(); //for storing apartment list data

        if (Utils.CheckInternetConnection(getApplicationContext())) {

            Call<ApartmentList> callApartmentList = apiInterface.getApartmentList();
            callApartmentList.enqueue(new Callback<ApartmentList>() {
                @Override
                public void onResponse(Call<ApartmentList> call, Response<ApartmentList> response)
                {


                    ApartmentList apartmentListDatum = response.body();
                    List<ApartmentList.ApartmentListDatum> apartmentList = apartmentListDatum.data;


                    for (ApartmentList.ApartmentListDatum listDatum : apartmentList) {
                        getApartment = listDatum.name + " " + "," + listDatum.address + " " + "," + listDatum.pincode;
                        aprtcategories.add(getApartment);
                    }


                }
                @Override
                public void onFailure(Call<ApartmentList> call, Throwable t) {

                }
            });








            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,
                    aprtcategories);

            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnrApartmentName.setAdapter(spinnerArrayAdapter);
            spnrApartmentName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {

                    Log.d("tttt", String.valueOf(position));

                    if (position == 0) {
                        getApartment = "";
                        return;
                    }
                    spinnerArrayAdapter.notifyDataSetChanged();



                   // selectedApartment = parent.getItemAtPosition(position).toString();
//                    if(cbNearByApartment.isChecked() == false)
//                    {
//                        getApartment = listAdd.get(position);
//                        etAddress.setText(getApartment);
//                    }else
//                    {
//                        getApartment = listAdd.get(position);
//                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Another interface callback
                }
            });




//            final ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(), R.layout.apartment_spinner_item,
//                    listAdd);
//            adp.setDropDownViewResource(R.layout.apartment_spinner_item);
//            spnrApartmentName.setAdapter(adp);
//
//            spnrApartmentName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                    Log.d("tttt", "onItemSelected: 122");
//
//                    if (position == 0) {
//                        getApartment = "";
//                        return;
//                    }
//                    adp.notifyDataSetChanged();
//
//                   // selectedApartment = parent.getItemAtPosition(position).toString();
//                    if(cbNearByApartment.isChecked() == false)
//                    {
//                        getApartment = listAdd.get(position);
//                        etAddress.setText(getApartment);
//                    }else
//                    {
//                        getApartment = listAdd.get(position);
//                    }
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });





        } else {
            Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
        }



        ibtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strFirstName = etFirstName.getText().toString();
                strLastName = etLastName.getText().toString();
                strMobile = etMobileNo.getText().toString();
                strEmail = etEmail.getText().toString();
                strPasswd = etPassword.getText().toString();
                strBlock = etWing.getText().toString();//
                strFloor = etFloorNumber.getText().toString();//
                strDoor = etDoorNumber.getText().toString();
                strArea = etArea.getText().toString();//
                strCity = etCity.getText().toString();//
                strAddress = etAddress.getText().toString();
                strLandmark = etLandMark.getText().toString();
                strPincode = etPincode.getText().toString();
                if (validateRegister(strFirstName, strLastName, strMobile, strEmail, strPasswd, getApartment, strBlock,
                        strFloor, strDoor, strArea, strAddress, strLandmark, strPincode, strCity)) {

                    saveSignUpData(strFirstName, strLastName, strMobile, strEmail, strPasswd, getApartment, strBlock,
                            strFloor, strDoor, strArea, strAddress, strLandmark, strPincode, strCity);
                }
            }
        });

        return view;
    }



    private boolean validateRegister(String firstname, String lastname, String mobile,
                                     String emailid, String password, String apartment, String block,
                                     String floorno, String doorno, String area,
                                     String address, String landmark, String pincode, String city) {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (firstname == null || firstname.trim().length() == 0) {
            etFirstName.requestFocus();
            etFirstName.setError("First name is Required");
            return false;

        }
        if (lastname == null || lastname.trim().length() == 0) {
            etLastName.requestFocus();
            etLastName.setError("Last Name is Required");
            return false;

        }
        if (mobile == null || mobile.trim().length() == 0) {
            etMobileNo.requestFocus();
            etMobileNo.setError("Please enter valid mobile number");
            return false;

        }
        if (mobile.matches("[0-9]+")) {
            if (mobile.trim().length() != 10) {
                etMobileNo.requestFocus();
                etMobileNo.setError("Enter 10 digit Mobile Number");
                return false;
            } else {
                return true;
            }
        }
        if (!(emailid.matches(emailPattern) && emailid.length() > 0)) {
            etEmail.requestFocus();
            etEmail.setError("Invalid email address");
            return false;
        } /*else {
            Toast.makeText(getApplicationContext(), "Please enter valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        if (password == null || password.trim().length() == 0) {
            etPassword.requestFocus();
            etPassword.setError("Please enter a valid Password");
            return false;

        } else {
            if (password.trim().length() < 4) {
                etPassword.requestFocus();
                etPassword.setError("Password should not be less than 4 digit");
                return false;
            }
        }
        if (block == null || block.trim().length() == 0) {
            etWing.requestFocus();
            etWing.setError("Block/Wing is required");
            return false;

        }
        if (floorno == null || floorno.trim().length() == 0) {
            etFloorNumber.requestFocus();
            etFloorNumber.setError("Floor Number is required");
            return false;

        }
        if (doorno == null || doorno.trim().length() == 0) {
            etDoorNumber.requestFocus();
            etDoorNumber.setError("Door Number is required");
            return false;

        }
        if (area == null || area.trim().length() == 0) {
            etArea.requestFocus();
            etArea.setError("Area is required");
            return false;

        }
        if (address == null || address.trim().length() == 0) {
            etAddress.requestFocus();
            etAddress.setError("Address is required");
            return false;

        }
        if (landmark == null || landmark.trim().length() == 0) {
            etLandMark.requestFocus();
            etLandMark.setError("LandMark is required");
            return false;

        }
        if (pincode == null || pincode.trim().length() == 0) {
            etPincode.requestFocus();
            etPincode.setError("Pincode is required");
            return false;

        }
        if (pincode.trim().length() != 6) {
            etPincode.requestFocus();
            etPincode.setError("Pincode should be in 6 digit");
            return false;

        }
        if (city == null || city.trim().length() == 0) {
            etCity.requestFocus();
            etCity.setError("City is required");
            return false;
        }
        return true;
    }

    private void saveSignUpData(String firstname, String lastname, String mobile, String emailid, String password,
                                String apartment, String block, String floorno, String doorno, String area,
                                String address, String landmark, String pincode, String city) {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final UserSignUp userSignUp = new UserSignUp(firstname, lastname, mobile, emailid, password,
                apartment, block, floorno, doorno, area, address, landmark, pincode, city);

        Call<UserSignUp> calledu = apiInterface.postRegisterUser(userSignUp);
        calledu.enqueue(new Callback<UserSignUp>() {
            @Override
            public void onResponse(Call<UserSignUp> calledu, Response<UserSignUp> response) {
                final UserSignUp resource = response.body();
                if (resource.status.equals("success")) {
                    Toast.makeText(getContext(), resource.message, Toast.LENGTH_LONG).show();
                    ((LoginSignup_act) getActivity()).navigateFragment(0); //for intent to login after successful signup
                } else if (resource.status.equals("error")) {
                    Toast.makeText(getContext(), resource.message, Toast.LENGTH_LONG).show();
                }
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserSignUp> calledu, Throwable t) {
                calledu.cancel();
            }
        });
    }

    public void signupheight()
    {


        signup_frg.post(new Runnable() {
            @Override
            public void run() {
                // for instance

            }
        });



    }


}
