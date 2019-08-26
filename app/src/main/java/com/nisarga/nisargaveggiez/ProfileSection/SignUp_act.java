package com.nisarga.nisargaveggiez.ProfileSection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        progressdialog.setCancelable(false);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        init();
    }

    EditText etFirstName, etLastName, etMobileNo, etEmail, etPassword, etBlock, etFloorNumber, etDoorNumber,
            etAddress, etCity, etLandMark, etPincode;
    ImageView ivSubmit;
    CircleImageView ivProfile;
    TextView tvApartment;
    ListView lvApartmentList;
    DrawerLayout mDrawer;
    CheckBox cbNearByApartment;
    Button btnLogIn;

    private String imagepath = null;
    String strFirstName, strLastName, strMobile, strEmail, strPassword, strApartmentName, strBlock, strFloor,
            strDoor, strAddress, strPincode, strCity, strNearBy = "0", strApartmentId, strLandmark;
    String strProfilePic = "0";

    String apartment_pincode[], apartment_address[], apartment_id[], apartment_city[], apartment_landmark[];

    private void init() {
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMobileNo = findViewById(R.id.etMobileNo);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etBlock = findViewById(R.id.etBlock);
        etFloorNumber = findViewById(R.id.etFloorNumber);
        etDoorNumber = findViewById(R.id.etDoorNumber);
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etLandMark = findViewById(R.id.etLandMark);
        etPincode = findViewById(R.id.etPincode);

        tvApartment = findViewById(R.id.tvApartment);

        lvApartmentList = findViewById(R.id.lvApartmentList);
        mDrawer = findViewById(R.id.mDrawer);

        cbNearByApartment = findViewById(R.id.cbNearByApartment);

        ivProfile = findViewById(R.id.ivProfile);
        ivSubmit = findViewById(R.id.ivSubmit);
        btnLogIn = findViewById(R.id.btnLogIn);

        cbNearByApartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    strNearBy = "1";
                    etBlock.getText().clear();
                    etFloorNumber.getText().clear();
                    etDoorNumber.getText().clear();
                    etAddress.getText().clear();
                    etLandMark.getText().clear();
                    // etBlock.setHint("Block/Wing/House No");
                } else {
                    etAddress.setText(strAddress);
                    etLandMark.setText(strLandmark);
                }
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
                cbNearByApartment.setChecked(false);
                final ArrayList<String> apartment_list = new ArrayList<>();

                if (Utils.CheckInternetConnection(getApplicationContext())) {
                    Call<ApartmentList> callheight = apiInterface.getApartmentList();
                    callheight.enqueue(new Callback<ApartmentList>() {
                        @Override
                        public void onResponse(Call<ApartmentList> callheight, Response<ApartmentList> response) {
                            ApartmentList eduresource = response.body();
                            List<ApartmentList.ApartmentListDatum> datumList = eduresource.data;
                            apartment_id = new String[datumList.size()];
                            apartment_address = new String[datumList.size()];
                            apartment_pincode = new String[datumList.size()];
                            apartment_city = new String[datumList.size()];
                            apartment_landmark = new String[datumList.size()];
                            int i = 0;
                            for (ApartmentList.ApartmentListDatum datum : datumList) {
                                apartment_list.add(datum.name);
                                apartment_id[i] = datum.apartment_id;
                                apartment_address[i] = datum.address;
                                apartment_pincode[i] = datum.pincode;
                                apartment_city[i] = datum.city;
                                apartment_landmark[i] = datum.landmark;
                                i++;
                            }

                            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(SignUp_act.this,
                                    R.layout.apartment_spinner_item, apartment_list);
                            lvApartmentList.setAdapter(itemsAdapter);
                            lvApartmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String apartmentName = (String) lvApartmentList.getItemAtPosition(position);
                                    String apartmentId = apartment_id[position];
                                    String apartmentAddress = apartment_address[position];
                                    String apartmentPinCode = apartment_pincode[position];
                                    String apartmentCity = apartment_city[position];
                                    String apartmentLandmark = apartment_landmark[position];
                                    strApartmentId = apartmentId;
                                    strApartmentName = apartmentName;
                                    strAddress = apartmentAddress;
                                    strPincode = apartmentPinCode;
                                    strCity = apartmentCity;
                                    strLandmark = apartmentLandmark;

                                    tvApartment.setText(strApartmentName);
                                    etAddress.setText(strAddress);
                                    etCity.setText(strCity);
                                    etLandMark.setText(strLandmark);
                                    etPincode.setText(strPincode);

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

        ivSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strFirstName = etFirstName.getText().toString();
                strLastName = etLastName.getText().toString();
                strMobile = etMobileNo.getText().toString();
                strEmail = etEmail.getText().toString();
                strPassword = etPassword.getText().toString();
                strBlock = etBlock.getText().toString();//
                strFloor = etFloorNumber.getText().toString();//
                strDoor = etDoorNumber.getText().toString();
                strPincode = etPincode.getText().toString();
                strCity = etCity.getText().toString();//
                strLandmark = etLandMark.getText().toString();

                if (validateRegister(strFirstName, strLastName, strMobile, strEmail, strPassword, strDoor,
                        strApartmentName, strAddress, strCity, strPincode)) {

                    if (Utils.CheckInternetConnection(getApplicationContext())) {
                        saveSignUpData(strFirstName, strLastName, strMobile, strEmail, strPassword, strApartmentName,
                                strBlock, strFloor, strDoor, strAddress, strCity, strLandmark, strPincode, strNearBy,
                                strApartmentId, strProfilePic);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean validateRegister(String firstname, String lastname, String mobile, String emailid, String password,
                                     String doorno, String apartmentname, String address, String city, String pincode) {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (firstname == null || firstname.trim().length() == 0) {
            etFirstName.requestFocus();
            etFirstName.setError("First Name is Required");
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
            }
        }

        if (!(emailid.matches(emailPattern) && emailid.length() > 0)) {
            etEmail.requestFocus();
            etEmail.setError("Email is Required");
            return false;
        }

        if (password == null || password.trim().length() == 0) {
            etPassword.requestFocus();
            etPassword.setError("Please enter a Valid Password");
            return false;
        }

        if (password.trim().length() < 4) {
            etPassword.requestFocus();
            etPassword.setError("Password should not be less than 4 digit");
            return false;
        }

        if (apartmentname == null || apartmentname.trim().length() == 0) {
            tvApartment.requestFocus();
            tvApartment.setError("Apartment Name Required");
            return false;
        }

        if (doorno == null || doorno.trim().length() == 0) {
            etDoorNumber.requestFocus();
            etDoorNumber.setError("Door Number is required");
            return false;
        }

        if (address == null || address.trim().length() == 0) {
            etAddress.requestFocus();
            etAddress.setError("Address is required");
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
                                String apartment_name, String block, String floorno, String doorno, String address,
                                String city, String landmark, String pincode, final String near_by,
                                String apartment_id, String profile_pic) {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final UserSignUp userSignUp = new UserSignUp(firstname, lastname, mobile, emailid, password, apartment_name,
                block, floorno, doorno, address, city, landmark, pincode, near_by, apartment_id, profile_pic);

        Call<UserSignUp> calledu = apiInterface.postRegisterUser(userSignUp);
        calledu.enqueue(new Callback<UserSignUp>() {
            @Override
            public void onResponse(Call<UserSignUp> calledu, Response<UserSignUp> response) {
                final UserSignUp resource = response.body();
                if (resource.status.equals("success")) {
                    Toast.makeText(SignUp_act.this, resource.message, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUp_act.this, Login_act.class);
                    startActivity(intent);
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
            Glide.with(getApplicationContext()).load(filePath).into(ivProfile);
//          Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
//          ivProfile.setImageBitmap(bitmap);

            if (requestCode == PICK_IMAGE_REQUEST) {
                new Thread(new Runnable() {
                    public void run() {
                        imagePath(imagepath);
                        Log.e("----imagepath---", "" + imagepath);

                    }
                }).start();
            }
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

        MultipartBody.Part img1 = MultipartBody.Part.createFormData("file", file0.getName(),
                requestFile0);

        Call<SignUpImageResponse> call = apiInterface.signupImageUpload(img1);
        call.enqueue(new Callback<SignUpImageResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpImageResponse> call, @NonNull Response<SignUpImageResponse> response) {
                SignUpImageResponse responseModel = response.body();
                if (responseModel.status.equals("success")) {
                    strProfilePic = responseModel.profile_url;
                }
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<SignUpImageResponse> call, @NonNull Throwable t) {
                Log.d("val", "Exception");
            }
        });
    }
}
