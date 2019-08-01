package com.app.ecommerce.Home;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.app.ecommerce.ContactUs;
import com.app.ecommerce.DeliveryInformation;
import com.app.ecommerce.MyOrder.MyOrders;
import com.app.ecommerce.PrivacyPolicy;
import com.app.ecommerce.ProfileSection.EditProfile_act;
import com.app.ecommerce.ProfileSection.Faqs_act;
import com.app.ecommerce.ProfileSection.GoogleFeedback_act;
import com.app.ecommerce.ProfileSection.LoginSignup_act;
import com.app.ecommerce.ProfileSection.MyListAdapter;
import com.app.ecommerce.ProfileSection.MyProfileModel;
import com.app.ecommerce.ProfileSection.MyProfile_act;
import com.app.ecommerce.ProfileSection.Offers_act;
import com.app.ecommerce.ProfileSection.RateUs_act;
import com.app.ecommerce.ProfileSection.RefersAndEarn_act;
import com.app.ecommerce.R;
import com.app.ecommerce.SessionManager;
import com.app.ecommerce.TermsConditions;
import com.app.ecommerce.Utils;
import com.app.ecommerce.Wishlist.WishListHolder;
import com.app.ecommerce.adapter.RemoteData;
import com.app.ecommerce.cart.cart;
import com.app.ecommerce.fcm.NotificationUtils;
import com.app.ecommerce.fcm.fcmConfig;
import com.app.ecommerce.notifications.MyNotifications;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.ProductslHomePage;
import com.app.ecommerce.search;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.PlaceHolderView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    APIInterface apiInterface;
    SessionManager session;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private static HomePage instance;
    private Toolbar mToolbarHomePage;
    private PlaceHolderView list_items_homePage;

    public static TextView textCartItemCount;
    public static BottomNavigationView bottomNavigationView;
    public Context mContext;

    private ProgressBar progressBarHomePage;
    private LinearLayout llProfileIcon, llProfileDesc;
    private AutoCompleteTextView searchEditText;
    private DrawerLayout drwLayout;
    private CircularImageView ivProfilePic;
    private ImageView ivEditProfile;
    private TextView tvName, tvEmail, tvMobileNo;
    private Button btnEditProfilePic;
    private ImageButton imgBtnProfile;
    NavigationView navigationView;
    private LinearLayout llLeftMenuLogOut;
    View headerView;
    String custId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());
        mContext = this.getApplicationContext();
        instance = this;



        AndroidNetworking.initialize(getApplicationContext());
        init();
        initBottomNavigation();
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

    private void init() {
        mToolbarHomePage = (Toolbar) findViewById(R.id.toolbarHomePage);
        setSupportActionBar(mToolbarHomePage);
        getSupportActionBar().setTitle(null);

        progressBarHomePage = (ProgressBar) findViewById(R.id.loadingHomePage);
        llProfileIcon = (LinearLayout) findViewById(R.id.llProfileIcon);
        imgBtnProfile = findViewById(R.id.imgBtnProfile);

        list_items_homePage = (PlaceHolderView) findViewById(R.id.list_items_homePage);
        list_items_homePage.setPadding(0, 0, 0, 0);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationHomePage);
        drwLayout = findViewById(R.id.drwLayout);



        //-----------------------------------------------------------------------------------
        navigationView = (NavigationView) findViewById(R.id.nav_viewHomePage);
        headerView = navigationView.getHeaderView(0);
        btnEditProfilePic = headerView.findViewById(R.id.btnEditProfilePic);
        tvName = headerView.findViewById(R.id.tvName);
        tvEmail = headerView.findViewById(R.id.tvEmail);
        tvMobileNo = headerView.findViewById(R.id.tvMobileNo);
        llProfileDesc = (LinearLayout) headerView.findViewById(R.id.llProfileDesc);
        ivProfilePic = headerView.findViewById(R.id.ivProfilePic);
        ivEditProfile = headerView.findViewById(R.id.ivEditProfile);
        navigationView.setNavigationItemSelectedListener(this);
        setNavMenuItemThemeColors(R.color.light_black_2, R.color.green);
        llLeftMenuLogOut = findViewById(R.id.llleftMenuLogOut);
        //--------------------------------------------------------------------------------
        llProfileDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditProfile = new Intent(getBaseContext(), MyProfile_act.class);
                startActivity(intentEditProfile);
            }
        });

        ivEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditProfile = new Intent(getBaseContext(), EditProfile_act.class);
                startActivity(intentEditProfile);
            }
        });
        llLeftMenuLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.checkLogin();
                session.logoutUser();
            }
        });
        imgBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drwLayout.openDrawer(Gravity.LEFT);
            }
        });

    }

    private void initBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent intentHomePage = new Intent(getBaseContext(), HomePage.class);
                                intentHomePage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentHomePage);
                                break;

                            case R.id.navigation_categories:
                                Intent intentCategories = new Intent(getBaseContext(), CategoriesBottomNav.class);
                                intentCategories.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentCategories);
                                break;

                            case R.id.navigation_wishlist:
                                Intent intentWishlist = new Intent(getBaseContext(), WishListHolder.class);
                                intentWishlist.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentWishlist);
                                break;

                            case R.id.navigation_wallet:
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
            final ProductslHomePage productslHomePage = new ProductslHomePage("7");
            Call<ProductslHomePage> call = apiInterface.getHomePageProducts(productslHomePage);
            call.enqueue(new Callback<ProductslHomePage>() {
                @Override
                public void onResponse(Call<ProductslHomePage> call, Response<ProductslHomePage> response) {
                    ProductslHomePage resource = response.body();
                    List<ProductslHomePage.BannerList> datumList = resource.banner;
                    for (ProductslHomePage.BannerList imageslider1 : datumList) {
                        progressBarHomePage.setVisibility(View.INVISIBLE);
                        imageArray.add(imageslider1.image);
                        headArray.add(imageslider1.title);
                    }
                    list_items_homePage.addView(new HomePageImageSlider(mContext, headArray, imageArray));
                    //-----------------------------------------deal of day ------------------------------------------

                    List<ProductslHomePage.DealOfDayList> imageListDeal = resource.dealoftheday;
                    List<ProductslHomePage.DealOfDayList> newImageListDeal = new ArrayList<>();
                    for (int i = 0; i < (imageListDeal.size() > 10 ? 10 : imageListDeal.size()); i++) {
                        newImageListDeal.add(imageListDeal.get(i));
                    }
                    list_items_homePage.addView(new HomePageDealofDayList(getApplicationContext(), textCartItemCount, newImageListDeal));
                    //--------------------------------------------Products-------------------------------------------
                    List<ProductslHomePage.RecommendedList> imageListProducts = resource.recommended;
                    List<ProductslHomePage.RecommendedList> newImageListPrd = new ArrayList<>();
                    for (int i = 0; i < (imageListProducts.size() > 10 ? 10 : imageListProducts.size()); i++) {
                        newImageListPrd.add(imageListProducts.get(i));
                    }
                    list_items_homePage.addView(new HomePageListofProducts(getApplicationContext(), textCartItemCount, newImageListPrd));
                    //-----------------------------------------Recommended List-------------------------------------

                    List<ProductslHomePage.RecommendedList> imageRecomendProducts = resource.recommended;
                    List<ProductslHomePage.RecommendedList> newImageRecommendProducts = new ArrayList<>();
                    for (int i = 0; i < (imageRecomendProducts.size() > 10 ? 10 : imageRecomendProducts.size()); i++) {
                        newImageRecommendProducts.add(imageRecomendProducts.get(i));
                    }
                    list_items_homePage.addView(new HomePageRecommended(getApplicationContext(), textCartItemCount, newImageRecommendProducts));
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
            custId = session.getCustomerId();
            final MyProfileModel myProfileModel = new MyProfileModel(custId);
            Call<MyProfileModel> call = apiInterface.showMyProfile(myProfileModel);
            call.enqueue(new Callback<MyProfileModel>() {
                @Override
                public void onResponse(Call<MyProfileModel> call, Response<MyProfileModel> response) {
                    MyProfileModel resourceMyProfile = response.body();
                    if(resourceMyProfile.status.equals("success"))
                    {
                       // Toast.makeText(getApplicationContext(),resourceMyProfile.message,Toast.LENGTH_SHORT).show();
                        List<MyProfileModel.Datum> mpmDatum = resourceMyProfile.resultdata;
                        for(MyProfileModel.Datum mpmResult : mpmDatum)
                        {
                            tvName.setText(mpmResult.firstname+" "+mpmResult.lastname);
                            tvEmail.setText(mpmResult.email);
                            tvMobileNo.setText(mpmResult.telephone);
                        }

                    }
                    else if(resourceMyProfile.status.equals("failure"))
                    {
                        Toast.makeText(getApplicationContext(),resourceMyProfile.message,Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MyProfileModel> call, Throwable t) {

                }
            });
            }
        else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public static HomePage getInstance() {
        return instance;
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

        searchEditText = (AutoCompleteTextView) searchViews.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setPadding(0, 2, 2, 2);
        searchEditText.setHint(null);//removing search hint from search layout
        searchEditText.setThreshold(1);//will start working from first character
        searchEditText.setTextColor(Color.parseColor("#824A4A4A"));
        searchEditText.setOnItemClickListener(onItemClickListener);
        searchEditText.clearFocus();

        RemoteData remoteData = new RemoteData(this);
        remoteData.getStoreData();
/*
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
                return false;
            }
        });*/

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

    /*
        @Override
        public void onBackPressed() {
            super.onBackPressed();
        }*/
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
            Intent intentMyWallet = new Intent(HomePage.this, LoginSignup_act.class);
            startActivity(intentMyWallet);
        } else if (id == R.id.menuleft_offers) {
            Intent intentMyOffers = new Intent(HomePage.this, Offers_act.class);
            startActivity(intentMyOffers);
        } else if (id == R.id.menuleft_referearn) {
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

}
