package com.nisarga.nisargaveggiez.Home;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.bumptech.glide.Glide;
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
import com.nisarga.nisargaveggiez.ProfileSection.Offers_act;
import com.nisarga.nisargaveggiez.ProfileSection.RateUs_act;
import com.nisarga.nisargaveggiez.ProfileSection.RefersAndEarn_act;
import com.nisarga.nisargaveggiez.ProfileSection.SignUpImageResponse;
import com.nisarga.nisargaveggiez.ProfileSection.SignUp_act;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.TermsConditions;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.Wishlist.WishListHolder;
import com.nisarga.nisargaveggiez.cart.cart;
import com.nisarga.nisargaveggiez.fcm.NotificationUtils;
import com.nisarga.nisargaveggiez.fcm.fcmConfig;
import com.nisarga.nisargaveggiez.notifications.MyNotifications;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.ProductslHomePage;
import com.nisarga.nisargaveggiez.search;
import com.nisarga.nisargaveggiez.wallet.MyWalletActivity;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.PlaceHolderView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    APIInterface apiInterface;
    SessionManager session;
    ProgressDialog progressdialog;

    public static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_WRITE_PERMISSION = 786;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private LinearLayout llLeftMenuLogOut;
    public static TextView textCartItemCount;
    public static BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        progressdialog = new ProgressDialog(HomePage.this);
        progressdialog.setMessage("Please Wait....");

        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());

        init();
        initApiCall();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(fcmConfig.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(fcmConfig.TOPIC_GLOBAL);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(fcmConfig.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };

        displayFirebaseRegId();
    }

    Toolbar mToolbarHomePage;
    PlaceHolderView list_items_homePage;
    ProgressBar progressBarHomePage;
    LinearLayout llProfileIcon, llProfileDesc, llEditProfile;
    EditText searchEditText;
    DrawerLayout drwLayout;
    CircularImageView ivToolbarProfile, ivProfilePic;
    TextView tvName, tvEmail, tvMobileNo;
    NavigationView navigationView;
    View headerView;
    String strSearchKey;

    private String imagepath = null;
    String strProfilePic = "null";

    private void init() {
        mToolbarHomePage = (Toolbar) findViewById(R.id.toolbarHomePage);
        setSupportActionBar(mToolbarHomePage);
        getSupportActionBar().setTitle(null);

        progressBarHomePage = (ProgressBar) findViewById(R.id.loadingHomePage);
        llProfileIcon = (LinearLayout) findViewById(R.id.llProfileIcon);
        ivToolbarProfile = findViewById(R.id.ivToolbarProfile);

        list_items_homePage = (PlaceHolderView) findViewById(R.id.list_items_homePage);
        list_items_homePage.setPadding(0, 0, 0, 0);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationHomePage);
        drwLayout = findViewById(R.id.drwLayout);

        //-----------------------------------------------------------------------------------
        navigationView = (NavigationView) findViewById(R.id.nav_viewHomePage);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        setNavMenuItemThemeColors(R.color.light_black_2, R.color.green);
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
                Intent intent = new Intent(HomePage.this, Login_act.class);
                startActivity(intent);
            }
        });

        llProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drwLayout.openDrawer(Gravity.LEFT);
            }
        });

        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFile();
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent intentHomePage = new Intent(HomePage.this, HomePage.class);
                                intentHomePage.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intentHomePage);
                                break;

                            case R.id.navigation_categories:
                                Intent intentCategories = new Intent(HomePage.this, CategoriesBottomNav.class);
                                intentCategories.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intentCategories);
                                break;

                            case R.id.navigation_wishlist:
                                Intent intentWishlist = new Intent(HomePage.this, WishListHolder.class);
                                intentWishlist.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intentWishlist);
                                break;

                            case R.id.navigation_wallet:
                                Intent intentWallet = new Intent(HomePage.this, MyWalletActivity.class);
                                intentWallet.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intentWallet);
                                break;
                        }
                        return true;
                    }
                });
        bottomNavigationView.setItemIconSize(40);
    }

    private void initApiCall() {
        final ArrayList<String> imageArray = new ArrayList<String>();
        final ArrayList<String> headArray = new ArrayList<String>();

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            //-------------------------------------image slider view------------------------------------------------
            final ProductslHomePage productslHomePage = new ProductslHomePage(session.getCustomerId());
            Call<ProductslHomePage> call = apiInterface.getHomePageProducts(productslHomePage);
            call.enqueue(new Callback<ProductslHomePage>() {
                @Override
                public void onResponse(Call<ProductslHomePage> call, Response<ProductslHomePage> response) {
                    ProductslHomePage resource = response.body();
                    if (String.valueOf(resource.profile_pic) == "null") {
                        ivToolbarProfile.setImageResource(R.drawable.camera);
                    } else {
                        Glide.with(HomePage.this).load(resource.profile_pic).fitCenter().dontAnimate()
                                .into(ivToolbarProfile);
                    }
                    List<ProductslHomePage.BannerList> datumList = resource.banner;
                    for (ProductslHomePage.BannerList imageslider1 : datumList) {
                        progressBarHomePage.setVisibility(View.INVISIBLE);
                        imageArray.add(imageslider1.image);
                        headArray.add(imageslider1.title);
                    }
                    list_items_homePage.addView(new HomePageImageSlider(HomePage.this, headArray, imageArray));
                    //-----------------------------------------deal of day ------------------------------------------

                    List<ProductslHomePage.DealOfDayList> imageListDeal = resource.dealoftheday;
                    List<ProductslHomePage.DealOfDayList> newImageListDeal = new ArrayList<>();
                    for (int i = 0; i < (imageListDeal.size() > 10 ? 10 : imageListDeal.size()); i++) {
                        newImageListDeal.add(imageListDeal.get(i));
                    }
                    list_items_homePage.addView(new HomePageDealofDayList(HomePage.this, textCartItemCount, newImageListDeal));
                    //--------------------------------------------Products-------------------------------------------
                    List<ProductslHomePage.Products> imageListProducts = resource.products;
                    List<ProductslHomePage.Products> newImageListPrd = new ArrayList<>();
                    for (int i = 0; i < (imageListProducts.size() > 10 ? 10 : imageListProducts.size()); i++) {
                        newImageListPrd.add(imageListProducts.get(i));
                    }
                    list_items_homePage.addView(new HomePageListofProducts(HomePage.this, textCartItemCount, newImageListPrd));
                    //-----------------------------------------Recommended List-------------------------------------

                    List<ProductslHomePage.RecommendedList> imageRecomendProducts = resource.recommended;
                    List<ProductslHomePage.RecommendedList> newImageRecommendProducts = new ArrayList<>();
                    for (int i = 0; i < (imageRecomendProducts.size() > 10 ? 10 : imageRecomendProducts.size()); i++) {
                        newImageRecommendProducts.add(imageRecomendProducts.get(i));
                    }
                    list_items_homePage.addView(new HomePageRecommended(HomePage.this, textCartItemCount, newImageRecommendProducts));
                }

                @Override
                public void onFailure(Call<ProductslHomePage> call, Throwable t) {
                    call.cancel();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
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
                            if (String.valueOf(mpmResult.image) == "null") {
                                ivProfilePic.setImageResource(R.drawable.camera);
                            } else {
                                Glide.with(HomePage.this).load(mpmResult.image).fitCenter().dontAnimate()
                                        .into(ivProfilePic);
                            }
                            tvName.setText(mpmResult.firstname + " " + mpmResult.lastname);
                            tvEmail.setText(mpmResult.email);
                            tvMobileNo.setText(mpmResult.telephone);
                        }

                    } else if (resourceMyProfile.status.equals("failure")) {
                        Toast.makeText(getApplicationContext(), resourceMyProfile.message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MyProfileModel> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_search, menu);

        MenuItem cart_menuItem = menu.findItem(R.id.cartmenu);
        FrameLayout rootView = (FrameLayout) cart_menuItem.getActionView();
        textCartItemCount = (TextView) rootView.findViewById(R.id.cart_badge);

        Integer cnt = session.getCartCount();
        textCartItemCount.setText(String.valueOf(cnt));

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

        int actionBarHeight = mToolbarHomePage.getLayoutParams().height;
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
        //  searchViews.setMaxWidth(600);
        //searchViews.setMaxWidth(Integer.MAX_VALUE);

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
                    prdIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

        return true;
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent prdIntent = new Intent(getBaseContext(), ProductDetailHome.class);
            prdIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(prdIntent);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.notification:
                Intent notificationIntent = new Intent(getBaseContext(), MyNotifications.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(notificationIntent);
                break;

            case R.id.cartmenu:
                Intent DeliveryIntent = new Intent(getBaseContext(), cart.class);
                DeliveryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(DeliveryIntent);
                break;
        }
        return true;
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(fcmConfig.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
//        Log.d( "Firebase reg id: ", regId);
        //if (!TextUtils.isEmpty(regId))
        //  txtRegId.setText("Firebase Reg Id: " + regId);
        // else
        //   txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(fcmConfig.REGISTRATION_COMPLETE));
        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(fcmConfig.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        drwLayout.closeDrawers();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.menuleft_home) {
            menuItem.setEnabled(true);
            Intent intentHome = new Intent(HomePage.this, HomePage.class);
            startActivity(intentHome);
        } else if (id == R.id.menuleft_myorders) {
            Intent intentMyOrder = new Intent(HomePage.this, MyOrders.class);
            startActivity(intentMyOrder);
        } else if (id == R.id.menuleft_mywallet) {
            Intent intentMyWallet = new Intent(HomePage.this, MyWalletActivity.class);
            startActivity(intentMyWallet);
        }else if (id == R.id.menuleft_referearn) {
            Intent intentMyReferEarn = new Intent(HomePage.this, RefersAndEarn_act.class);
            startActivity(intentMyReferEarn);
        } else if (id == R.id.menuleft_rateus) {
            Intent intentMyRateUs = new Intent(HomePage.this, RateUs_act.class);
            startActivity(intentMyRateUs);
        } else if (id == R.id.menuleft_aboutcontact) {
            Intent intentAbtContact = new Intent(HomePage.this, ContactUs.class);
            startActivity(intentAbtContact);
        } else if (id == R.id.menuleft_faqs) {
            Intent intentFaqs = new Intent(HomePage.this, DeliveryInformation.class);
            startActivity(intentFaqs);
        } else if (id == R.id.menuleft_terms) {
            Intent intentTerms = new Intent(HomePage.this, TermsConditions.class);
            startActivity(intentTerms);
        } else if (id == R.id.menuleft_gfeedback) {
            Intent intentFeedback = new Intent(HomePage.this, GoogleFeedback_act.class);
            startActivity(intentFeedback);
        } else if (id == R.id.menuleft_policy) {
            Intent intentPolicy = new Intent(HomePage.this, PrivacyPolicy.class);
            startActivity(intentPolicy);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drwLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setNavMenuItemThemeColors(int color, int icolor) {
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
            Uri filePath = data.getData();
            imagepath = getPath(filePath);
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            ivProfilePic.setImageBitmap(bitmap);
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
                } else if (responsedata.status.equals("failure")) {
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
}
