package com.nisarga.nisargaveggiez.ProfileSection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.ProfileSection.ApartmentList;
import com.nisarga.nisargaveggiez.ProfileSection.SignUpImageResponse;
import com.nisarga.nisargaveggiez.ProfileSection.UserSignUp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SignUp_act extends AppCompatActivity {

    ProgressDialog progressdialog;
    APIInterface apiInterface;

    public static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_WRITE_PERMISSION = 786;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_act);

        progressdialog = new ProgressDialog(SignUp_act.this);
        progressdialog.setMessage("Please Wait....");

        apiInterface = APIClient.getClient().create(APIInterface.class);
        init();
    }


    EditText etFirstName, etLastName, etMobileNo, etEmail, etPassword, etWing, etFloorNumber, etDoorNumber,
            etArea, etCity, etAddress, etLandMark, etPincode;
    ImageView ivProfile, ivSubmit;
    TextView tvApartment;
    ListView lvApartmentList;
    DrawerLayout mDrawer;
    CheckBox cbNearByApartment;
    Button btnLogIn;

    private String imagepath = null;
    String strFirstName, strLastName, strMobile, strEmail, strPasswd, strApartment, strBlock, strFloor, strDoor,
            strArea, strCity, strAddress, strLandmark, strPincode;
    String sProfilePic = "null";

    private void init() {
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMobileNo = findViewById(R.id.etMobileNo);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvApartment = findViewById(R.id.tvApartment);
        etWing = findViewById(R.id.etWing);
        etFloorNumber = findViewById(R.id.etFloorNumber);
        etDoorNumber = findViewById(R.id.etDoorNumber);
        etArea = findViewById(R.id.etArea);
        etCity = findViewById(R.id.etCity);
        etAddress = findViewById(R.id.etAddress);
        etLandMark = findViewById(R.id.etLandMark);
        etPincode = findViewById(R.id.etPincode);

        lvApartmentList = findViewById(R.id.lvApartmentList);
        mDrawer = findViewById(R.id.mDrawer);

        cbNearByApartment = findViewById(R.id.cbNearByApartment);

        ivProfile = findViewById(R.id.ivProfile);
        ivSubmit = findViewById(R.id.ivSubmit);
        btnLogIn = findViewById(R.id.btnLogIn);

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

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(SignUp_act.this, Login_act.class);
                startActivity(intent);
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFile();
            }
        });

        tvApartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(Gravity.RIGHT);

                final ArrayList<String> apartment_list = new ArrayList<>();
                apartment_list.add("Select Apartment");



                if (Utils.CheckInternetConnection(getApplicationContext())) {
                    Call<ApartmentList> callheight = apiInterface.getApartmentList();
                    callheight.enqueue(new Callback<ApartmentList>() {
                        @Override
                        public void onResponse(Call<ApartmentList> callheight, Response<ApartmentList> response) {
                            ApartmentList eduresource = response.body();


                            List<ApartmentList.ApartmentListDatum> datumList = eduresource.data;
                            for (ApartmentList.ApartmentListDatum datum : datumList) {
                                apartment_list.add(datum.name + " , " + datum.address);
                            }


                            ArrayAdapter<String> itemsAdapter =
                                    new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, apartment_list);

                            lvApartmentList.setAdapter(itemsAdapter);
                            lvApartmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //    Toast.makeText(SignUp_act.this, apartment_list.get(position)+"", Toast.LENGTH_SHORT).show();

                                    String textValue = (String) lvApartmentList.getItemAtPosition(position);
                                    tvApartment.setText(textValue);
                                    strApartment = textValue;
                                    mDrawer.closeDrawer(Gravity.RIGHT);

                                }
                            });



                        }

                        @Override
                        public void onFailure(Call<ApartmentList> callheight, Throwable t) {
                            callheight.cancel();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                }


            }
        });

        /*if (Utils.CheckInternetConnection(getApplicationContext())) {
            Call<ApartmentListModel> callcntry = apiInterface.getApartmentList();
            callcntry.enqueue(new Callback<ApartmentListModel>() {
                @Override
                public void onResponse(Call<ApartmentListModel> call_cntry, Response<ApartmentListModel> response) {
                    ApartmentListModel resource = response.body();
                    List<ApartmentListModel.ApartmentListDatum> datumList = resource.data;
                    for (ApartmentListModel.ApartmentListDatum datum : datumList) {
                        apartment_list.add(datum.name + " , " + datum.address);
                    }
                }

                @Override
                public void onFailure(Call<ApartmentListModel> callcntry, Throwable t) {
                    callcntry.cancel();
                }
            });


            final ArrayAdapter<String> apartment_adapter = new ArrayAdapter<>(SignUp_act.this, R.layout.apartment_spinner_item,
                    R.id.tvSpinnerItem, apartment_list);
            spnrApartmentName.setAdapter(apartment_adapter);
            spnrApartmentName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (strApartment != null) {
                        int spinnerPosition = apartment_adapter.getPosition(strApartment);
                        spnrApartmentName.setSelection(spinnerPosition);
                    }
                   *//* if (cbNearByApartment.isChecked() == false) {
                        strApartment = apartment_list.get(position);
                        etAddress.setText(strApartment);
                    } else {
                        strApartment = apartment_list.get(position);
                    }*//*
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
        }*/

        ivSubmit.setOnClickListener(new View.OnClickListener() {
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
                if (validateRegister(strFirstName, strLastName, strMobile, strEmail, strPasswd, strApartment, strBlock,
                        strFloor, strDoor, strArea, strAddress, strLandmark, strPincode, strCity)) {

                    if (Utils.CheckInternetConnection(getApplicationContext())) {
                        saveSignUpData(strFirstName, strLastName, strMobile, strEmail, strPasswd, strApartment, strBlock,
                                strFloor, strDoor, strArea, strAddress, strLandmark, strPincode, strCity);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter all the Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                    Toast.makeText(SignUp_act.this, resource.message, Toast.LENGTH_LONG).show();
                } else if (resource.status.equals("error")) {
                    Toast.makeText(SignUp_act.this, resource.message, Toast.LENGTH_LONG).show();
                }
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserSignUp> calledu, Throwable t) {
                calledu.cancel();
            }
        });
    }

    public void pickFile() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_PERMISSION);
            return;
        }
        openGallery();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            imagepath = getPath(filePath);
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            ivProfile.setImageBitmap(bitmap);
            imagePath(imagepath);
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void imagePath(String imagepath) {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        File file0 = new File(imagepath);
        RequestBody requestFile0 = RequestBody.create(MediaType.parse("image"), file0);

        MultipartBody.Part img1 = MultipartBody.Part.createFormData("uploaded_file[]", file0.getName(),
                requestFile0);

        Call<SignUpImageResponse> call = apiInterface.signupImageUpload(img1);
        call.enqueue(new Callback<SignUpImageResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpImageResponse> call, @NonNull Response<SignUpImageResponse> response) {
                SignUpImageResponse responseModel = response.body();
                if (responseModel.status.equals("success")) {
                    List<SignUpImageResponse.Datum> datumList = responseModel.resultdata;
                    for (SignUpImageResponse.Datum datum : datumList) {
                        sProfilePic = datum.image0;
                    }
                }
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<SignUpImageResponse> call, @NonNull Throwable t) {
                Log.d("val", "Exception");
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
