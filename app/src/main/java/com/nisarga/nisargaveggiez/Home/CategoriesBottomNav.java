package com.nisarga.nisargaveggiez.Home;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nisarga.nisargaveggiez.ContactUs;
import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.MyOrder.MyOrders;
import com.nisarga.nisargaveggiez.PrivacyPolicy;
import com.nisarga.nisargaveggiez.ProfileSection.EditProfile_act;
import com.nisarga.nisargaveggiez.ProfileSection.GoogleFeedback_act;
import com.nisarga.nisargaveggiez.ProfileSection.Login_act;
import com.nisarga.nisargaveggiez.ProfileSection.MyProfileModel;
import com.nisarga.nisargaveggiez.ProfileSection.MyProfile_act;
import com.nisarga.nisargaveggiez.ProfileSection.NavEditImage;
import com.nisarga.nisargaveggiez.ProfileSection.RateUs_act;
import com.nisarga.nisargaveggiez.ProfileSection.RefersAndEarn_act;
import com.nisarga.nisargaveggiez.ProfileSection.SignUpImageResponse;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.TermsConditions;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.Wishlist.WishListHolder;
import com.nisarga.nisargaveggiez.cart.cart;
import com.nisarga.nisargaveggiez.fcm.fcmConfig;
import com.nisarga.nisargaveggiez.notifications.MyNotifications;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.RateModel;
import com.nisarga.nisargaveggiez.retrofit.ShopByCategModel;
import com.nisarga.nisargaveggiez.search;
import com.nisarga.nisargaveggiez.wallet.MyWalletActivity;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesBottomNav extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    APIInterface apiInterface;
    SessionManager session;
    ProgressDialog progressdialog;
    public static TextView headerCartCount;
    private File temp_path;
    private final int COMPRESS = 100;

    public static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_layout);

        progressdialog = new ProgressDialog(CategoriesBottomNav.this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());
        apiInterface = APIClient.getClient().create(APIInterface.class);

        init();
        initApiCall();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(fcmConfig.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(fcmConfig.TOPIC_GLOBAL);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(fcmConfig.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };

        displayFirebaseRegId();
    }

    Toolbar mToolbarShopBy;
    ProgressBar pbarLoading;
    LinearLayout llProfileIcon;
    CircleImageView ivToolbarProfile;
    ExpandablePlaceHolderView phviewCategList;
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayoutShopBy;

    NavigationView navigationView;
    View headerView;
    TextView tvName, tvEmail, tvMobileNo;
    LinearLayout llProfileDesc, llEditProfile, llLeftMenuLogOut;
    CircleImageView ivProfilePic;

    EditText searchEditText;
    String strSearchKey;

    private String imagepath = null;
    String strProfilePic = "null";

    String value;

    public void init() {
        mToolbarShopBy = (Toolbar) findViewById(R.id.toolbarShopBy);
        setSupportActionBar(mToolbarShopBy);
        getSupportActionBar().setTitle(null);

        pbarLoading = findViewById(R.id.pBarloading);
        pbarLoading.setVisibility(View.VISIBLE);
        llProfileIcon = findViewById(R.id.llProfileIcon);
        ivToolbarProfile = findViewById(R.id.ivToolbarProfile);

        phviewCategList = findViewById(R.id.phviewCategList);
        bottomNavigationView = findViewById(R.id.navigationShopBy);
        drawerLayoutShopBy = findViewById(R.id.drawerLayoutShopBy);
        AndroidNetworking.initialize(getApplicationContext());

        //  -----------Navigation Menu--------
        navigationView = (NavigationView) findViewById(R.id.nav_viewShopByCateg);
        headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        setNavMenuItemThemeColors();
        tvName = headerView.findViewById(R.id.tvName);
        tvEmail = headerView.findViewById(R.id.tvEmail);
        tvMobileNo = headerView.findViewById(R.id.tvMobileNo);
        llProfileDesc = headerView.findViewById(R.id.llProfileDesc);
        llEditProfile = headerView.findViewById(R.id.llEditProfile);
        ivProfilePic = headerView.findViewById(R.id.ivProfilePic);
        llLeftMenuLogOut = findViewById(R.id.llleftMenuLogOut);
        //--------------------------------------------------------------------------------
        llProfileDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditProfile = new Intent(getBaseContext(), MyProfile_act.class);
                startActivity(intentEditProfile);
            }
        });

        llEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditProfile = new Intent(getBaseContext(), EditProfile_act.class);
                startActivity(intentEditProfile);
            }
        });

        llLeftMenuLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                finish();
                Intent intent = new Intent(CategoriesBottomNav.this, Login_act.class);
                startActivity(intent);
            }
        });


        llProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutShopBy.openDrawer(Gravity.LEFT);
            }
        });

        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFile();
            }
        });

        bottomNavigationView.getMenu().findItem(R.id.navigation_categories).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        finish();
                        Intent intentHome = new Intent(CategoriesBottomNav.this, HomePage.class);
                        startActivity(intentHome);
                        break;

                    case R.id.navigation_categories:
                        Intent intentCateg = new Intent(CategoriesBottomNav.this, CategoriesBottomNav.class);
                        startActivity(intentCateg);
                        break;

                    case R.id.navigation_wishlist:
                        finish();
                        Intent intentWishlist = new Intent(CategoriesBottomNav.this, WishListHolder.class);
                        startActivity(intentWishlist);
                        break;

                    case R.id.navigation_wallet:
                        finish();
                        Intent intentWallet = new Intent(CategoriesBottomNav.this, MyWalletActivity.class);
                        startActivity(intentWallet);
                        break;
                }
                return true;
            }
        });

        bottomNavigationView.setItemIconSize(40);
    }

    private void initApiCall() {
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            Call<ShopByCategModel> call = apiInterface.getShopByCateg();
            call.enqueue(new Callback<ShopByCategModel>() {
                @Override
                public void onResponse(Call<ShopByCategModel> call, Response<ShopByCategModel> response) {
                    ShopByCategModel resource = response.body();
                    if (resource.status.equals("success")) {
                        List<ShopByCategModel.ResultDatum> datumList = resource.result;
                        for (ShopByCategModel.ResultDatum datum : datumList) {
                            phviewCategList.addView(new ShopByCategItems(getApplicationContext(), datum.category_id,
                                    datum.category_image, datum.category_name));
                            List<ShopByCategModel.PrdDataDatum> data = datum.product_data;
                            for (ShopByCategModel.PrdDataDatum datum_1 : data) {
                                phviewCategList.addView(new ShopByFeedItems(getApplicationContext(), datum_1.category_id,
                                        datum_1.product_id, datum_1.product_name));
                            }
                        }
                    }
                    pbarLoading.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<ShopByCategModel> call, Throwable t) {
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            //------------------------------------- My profile view section------------------------------------------------
            final MyProfileModel myProfileModel = new MyProfileModel(session.getCustomerId());
            Call<MyProfileModel> call = apiInterface.showMyProfile(myProfileModel);
            call.enqueue(new Callback<MyProfileModel>() {
                @Override
                public void onResponse(Call<MyProfileModel> call, Response<MyProfileModel> response) {
                    MyProfileModel resourceMyProfile = response.body();
                    if (resourceMyProfile.status.equals("success")) {
                        List<MyProfileModel.Datum> mpmDatum = resourceMyProfile.resultdata;
                        for (MyProfileModel.Datum mpmResult : mpmDatum) {

                            Glide.with(CategoriesBottomNav.this).load(mpmResult.image).fitCenter().dontAnimate()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true).into(ivProfilePic);

                            Glide.with(CategoriesBottomNav.this).load(mpmResult.image).fitCenter().dontAnimate()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true).into(ivToolbarProfile);

                            tvName.setText(mpmResult.firstname + " " + mpmResult.lastname);
                            tvEmail.setText(mpmResult.email);
                            tvMobileNo.setText(mpmResult.telephone);
                        }
                    }
                }

                @Override
                public void onFailure(Call<MyProfileModel> call, Throwable t) {
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuShopBy = getMenuInflater();
        menuShopBy.inflate(R.menu.toolbar_search, menu);

        MenuItem cart_menuItem = menu.findItem(R.id.cartmenu);
        FrameLayout rootView = (FrameLayout) cart_menuItem.getActionView();
        headerCartCount = (TextView) rootView.findViewById(R.id.cart_badge);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Utils.CheckInternetConnection(getApplicationContext())) {
                    //------------------------------------- My profile view section------------------------------------------------
                    Call<CartCount> call = apiInterface.getCartCount("api/cart/cartcount", session.getToken());
                    call.enqueue(new Callback<CartCount>() {
                        @Override
                        public void onResponse(Call<CartCount> call, Response<CartCount> response) {
                            CartCount cartCount = response.body();
                            if (cartCount.status.equals("success")) {
                                headerCartCount.setText(cartCount.data);
                                session.cartcount(Integer.parseInt(cartCount.data));
                            }
                        }

                        @Override
                        public void onFailure(Call<CartCount> call, Throwable t) {
                            call.cancel();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }, 500);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent DeliveryIntent = new Intent(getBaseContext(), cart.class);
                DeliveryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(DeliveryIntent);

            }
        });

        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchViews = (SearchView) searchViewItem.getActionView();
        //searchViews.setQueryHint("Search...");
        searchViews.setBackgroundColor(getResources().getColor(R.color.white));

        int actionBarHeight = mToolbarShopBy.getLayoutParams().height;
        int actionBarwidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        Log.d("ccccc", String.valueOf(actionBarwidth));
        Log.d("aaaaaa", String.valueOf(actionBarHeight));
        LinearLayout.LayoutParams tvLay = new LinearLayout.LayoutParams((int) (actionBarwidth / 1.65),
                (int) (actionBarHeight / 1.7));
        searchViews.setBackground(ContextCompat.getDrawable(this, R.drawable.search_border));
        searchViews.setLayoutParams(tvLay);

        searchViews.setIconifiedByDefault(false);//make default request focus disable
        searchViews.setFocusable(false);
        searchViews.setIconified(false);

        final ImageView searchMagIcon = (ImageView) searchViews.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchMagIcon.setImageResource(R.drawable.ic_search_black_24dp);
        searchMagIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        searchMagIcon.setPadding(0, 0, 0, 0);
        searchViews.setPadding(-16, 0, 0, 0);//removing extraa space and align icon to leftmost of searchview
        searchViews.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        searchEditText = (EditText) searchViews.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setPadding(0, 2, 2, 2);
        searchEditText.setHint(null);//removing search hint from search layout
        strSearchKey = searchEditText.getText().toString();
        searchEditText.setTextColor(Color.parseColor("#824A4A4A"));

        searchEditText.clearFocus();


        searchEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent prdIntent = new Intent(getBaseContext(), ProductSearch.class);
                    startActivity(prdIntent);
                    return true;
                }
                return false;
            }
        });

        searchViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prdIntent = new Intent(getBaseContext(), ProductSearch.class);
                prdIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(prdIntent);

            }
        });

        //here we will get the search query
        searchViews.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent accountIntent = new Intent(getBaseContext(), search.class);
                startActivity(accountIntent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("seaerchesssssssssssssss", "onQueryTextSubmit: ");
//                RemoteData remoteData = new RemoteData(HomePage.this);
//                remoteData.getStoreData(newText);
                return false;
            }
        });

//        RemoteData remoteData = new RemoteData(this);
//        remoteData.getStoreData(strSearchKey);
        return true;
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent prdIntent = new Intent(getBaseContext(), ProductDetailHome.class);
            prdIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(prdIntent);
            Log.d("Firebase reg i: ", String.valueOf(i));
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification:
                Intent intentNotification = new Intent(getBaseContext(), MyNotifications.class);
                startActivity(intentNotification);
                break;
            case R.id.cartmenu:
                Intent intentCart = new Intent(getBaseContext(), cart.class);
                startActivity(intentCart);
                break;
        }
        return true;
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(fcmConfig.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.menuleft_home) {
            menuItem.setEnabled(true);
            Intent intentHome = new Intent(CategoriesBottomNav.this, HomePage.class);
            startActivity(intentHome);
        } else if (id == R.id.menuleft_myorders) {
            Intent intentMyOrder = new Intent(CategoriesBottomNav.this, MyOrders.class);
            startActivity(intentMyOrder);
        } else if (id == R.id.menuleft_mywallet) {
            Intent intentMyWallet = new Intent(CategoriesBottomNav.this, MyWalletActivity.class);
            startActivity(intentMyWallet);
        } else if (id == R.id.menuleft_referearn) {
            Intent intentMyReferEarn = new Intent(CategoriesBottomNav.this, RefersAndEarn_act.class);
            startActivity(intentMyReferEarn);
        } else if (id == R.id.menuleft_rateus) {
            Intent intentRateUs = new Intent(CategoriesBottomNav.this, RateUsReviews.class);
            intentRateUs.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentRateUs);
        } else if (id == R.id.menuleft_aboutcontact) {
            Intent intentAbtContact = new Intent(CategoriesBottomNav.this, ContactUs.class);
            startActivity(intentAbtContact);
        } else if (id == R.id.menuleft_faqs) {
            Intent intentFaqs = new Intent(CategoriesBottomNav.this, DeliveryInformation.class);
            startActivity(intentFaqs);
        } else if (id == R.id.menuleft_terms) {
            Intent intentTerms = new Intent(CategoriesBottomNav.this, TermsConditions.class);
            startActivity(intentTerms);
        } else if (id == R.id.menuleft_gfeedback) {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        } else if (id == R.id.menuleft_policy) {
            Intent intentPolicy = new Intent(CategoriesBottomNav.this, PrivacyPolicy.class);
            startActivity(intentPolicy);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayoutShopBy);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setNavMenuItemThemeColors() {
        //Setting default colors for menu item Text and Icon
        int navDefaultTextColor = Color.parseColor("#AB4A4A4A");
        int navDefaultIconColor = Color.parseColor("#FFFBD249");
        int navActiveIconColor = Color.parseColor("#FF34773C");

        //Defining ColorStateList for menu item Text
        ColorStateList navMenuTextList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{-android.R.attr.state_checked}

                },
                new int[]{
                        navDefaultTextColor,
                        navDefaultTextColor
                }
        );

        //Defining ColorStateList for menu item Icon
        ColorStateList navMenuIconList = new ColorStateList(
                new int[][]{

                        new int[]{android.R.attr.state_checked},
                        new int[]{-android.R.attr.state_checked}
                },
                new int[]{
                        navActiveIconColor,
                        navDefaultIconColor
                }
        );

        navigationView.setItemTextColor(navMenuTextList);
        navigationView.setItemIconTintList(navMenuIconList);
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

            InputStream imInputStream = null;
            try {
                imInputStream = getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(imInputStream);

            Bitmap bp_resized = resize(bitmap,200,200);

            String smallImagePath = saveGalaryImageOnLitkat(bp_resized);

            Log.e("----imagepath---", "" + smallImagePath);


            ivProfilePic.setImageBitmap(BitmapFactory.decodeFile(smallImagePath));
            ivToolbarProfile.setImageBitmap(BitmapFactory.decodeFile(smallImagePath));

            imagePath(smallImagePath);


//            Uri filePath = data.getData();
//            imagepath = getPath(filePath);
//
//            Glide.with(CategoriesBottomNav.this).load(filePath).fitCenter().dontAnimate()
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true).into(ivProfilePic);
//
//            Glide.with(CategoriesBottomNav.this).load(filePath).fitCenter().dontAnimate()
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true).into(ivToolbarProfile);
//
//            if (requestCode == PICK_IMAGE_REQUEST) {
//                new Thread(new Runnable() {
//                    public void run() {
//                        imagePath(imagepath);
//                        Log.e("----imagepath---", "" + imagepath);
//
//                    }
//                }).start();
//            }



        }
    }

    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }



    private String saveGalaryImageOnLitkat(Bitmap bitmap) {
        try {
            File cacheDir;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                cacheDir = new File(Environment.getExternalStorageDirectory(), getResources().getString(R.string.app_name));
            else
                cacheDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (!cacheDir.exists())
                cacheDir.mkdirs();
            String filename = System.currentTimeMillis() + ".jpg";
            File file = new File(cacheDir, filename);
            temp_path = file.getAbsoluteFile();
            // if(!file.exists())
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS, out);
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

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
                    if (Utils.CheckInternetConnection(getApplicationContext())) {
                        navImageUpload(strProfilePic);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUpImageResponse> call, @NonNull Throwable t) {
                Log.d("val", "Exception");
            }
        });
    }

    private void navImageUpload(String strProfilePic) {
        final NavEditImage navEditImage = new NavEditImage(session.getCustomerId(), strProfilePic);

        Call<NavEditImage> call = apiInterface.nav_edit_image(navEditImage);
        call.enqueue(new Callback<NavEditImage>() {
            @Override
            public void onResponse(Call<NavEditImage> call, Response<NavEditImage> response) {
                NavEditImage responsedata = response.body();
                if (responsedata.status.equals("success")) {
                    Toast.makeText(getApplicationContext(), responsedata.message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), responsedata.message, Toast.LENGTH_SHORT).show();
                }
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(Call<NavEditImage> call, Throwable t) {
                call.cancel();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CategoriesBottomNav.this, HomePage.class);
        startActivity(intent);
    }
}
