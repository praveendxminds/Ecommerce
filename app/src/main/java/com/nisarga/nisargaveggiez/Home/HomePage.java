package com.nisarga.nisargaveggiez.Home;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
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

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nisarga.nisargaveggiez.ContactUs;
import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.MyOrder.MyOrders;
import com.nisarga.nisargaveggiez.PrivacyPolicy;
import com.nisarga.nisargaveggiez.ProfileSection.EditProfile_act;
import com.nisarga.nisargaveggiez.ProfileSection.Login_act;
import com.nisarga.nisargaveggiez.ProfileSection.MyProfileModel;
import com.nisarga.nisargaveggiez.ProfileSection.MyProfile_act;
import com.nisarga.nisargaveggiez.ProfileSection.NavEditImage;
import com.nisarga.nisargaveggiez.ProfileSection.RefersAndEarn_act;
import com.nisarga.nisargaveggiez.ProfileSection.SessionRemember;
import com.nisarga.nisargaveggiez.ProfileSection.SignUpImageResponse;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.TermsConditions;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.Wishlist.WishListHolder;
import com.nisarga.nisargaveggiez.cart.cart;
import com.nisarga.nisargaveggiez.fcm.NotificationUtils;
import com.nisarga.nisargaveggiez.fcm.TokenFCM;
import com.nisarga.nisargaveggiez.fcm.fcmConfig;
import com.nisarga.nisargaveggiez.notifications.MyNotifications;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.EarnReferal;
import com.nisarga.nisargaveggiez.retrofit.ProductslHomePage;
import com.nisarga.nisargaveggiez.retrofit.RateModel;
import com.nisarga.nisargaveggiez.search;
import com.nisarga.nisargaveggiez.wallet.MyWalletActivity;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mindorks.placeholderview.PlaceHolderView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Boolean.TRUE;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    APIInterface apiInterface;
    SessionManager session;
    ProgressDialog progressdialog;
    InstallReferrerClient mReferrerClient;
    public static TextView hometotalCartItemCount;

    public static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        progressdialog = new ProgressDialog(HomePage.this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());


        init();
        initApiCall();

        reffaralcheck();

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
                    String title = intent.getStringExtra("title");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    /* local notification */
//                    Intent p_intent = new Intent(context, HomePage.class);
//                    PendingIntent contentIntent = PendingIntent.getActivity(context, 0, p_intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                    NotificationCompat.Builder b = new NotificationCompat.Builder(context);
//
//                    b.setAutoCancel(true)
//                            .setDefaults(Notification.DEFAULT_ALL)
//                            .setWhen(System.currentTimeMillis())
//                            .setSmallIcon(R.drawable.ic_launcher)
//                            .setContentTitle(title)
//                            .setContentText(message)
//                            .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
//                            .setContentIntent(contentIntent)
//                            .setContentInfo("Info");
//
//
//                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                    notificationManager.notify(1, b.build());

                    /* local notification */

                }
            }
        };

        displayFirebaseRegId();
    }

    LinearLayout llLeftMenuLogOut, llProfileIcon, llProfileDesc, llEditProfile;
    BottomNavigationView bottomNavigationView;
    Toolbar mToolbarHomePage;
    PlaceHolderView list_items_homePage;
    ProgressBar pbLoading;
    EditText searchEditText;
    DrawerLayout drwLayout;
    CircleImageView ivToolbarProfile, ivProfilePic;
    TextView tvName, tvEmail, tvMobileNo;
    NavigationView navigationView;
    View headerView;
    String strSearchKey;

    private String imagepath = null;
    String strProfilePic = "null";
    String value;

    private void init() {
        mToolbarHomePage = (Toolbar) findViewById(R.id.toolbarHomePage);
        setSupportActionBar(mToolbarHomePage);
        getSupportActionBar().setTitle(null);

        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
        pbLoading.setVisibility(View.VISIBLE);
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

        bottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent intentHome = new Intent(HomePage.this, HomePage.class);
                                startActivity(intentHome);
                                break;

                            case R.id.navigation_categories:
                                finish();
                                Intent intentCateg = new Intent(HomePage.this, CategoriesBottomNav.class);
                                startActivity(intentCateg);
                                break;

                            case R.id.navigation_wishlist:
                                finish();
                                Intent intentWishlist = new Intent(HomePage.this, WishListHolder.class);
                                startActivity(intentWishlist);
                                break;

                            case R.id.navigation_wallet:
                                finish();
                                Intent intentWallet = new Intent(HomePage.this, MyWalletActivity.class);
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
                    if (resource.status.equals("success")) {
                        List<ProductslHomePage.BannerList> datumList = resource.banner;
                        for (ProductslHomePage.BannerList imageslider1 : datumList) {
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
                        list_items_homePage.addView(new HomePageDealofDayList(HomePage.this, newImageListDeal));
                        //--------------------------------------------Products-------------------------------------------
                        List<ProductslHomePage.Products> imageListProducts = resource.products;
                        List<ProductslHomePage.Products> newImageListPrd = new ArrayList<>();
                        for (int i = 0; i < (imageListProducts.size() > 10 ? 10 : imageListProducts.size()); i++) {
                            newImageListPrd.add(imageListProducts.get(i));
                        }
                        list_items_homePage.addView(new HomePageListofProducts(HomePage.this, newImageListPrd));
                        //-----------------------------------------Recommended List-------------------------------------

                        List<ProductslHomePage.RecommendedList> imageRecomendProducts = resource.recommended;
                        List<ProductslHomePage.RecommendedList> newImageRecommendProducts = new ArrayList<>();
                        for (int i = 0; i < (imageRecomendProducts.size() > 10 ? 10 : imageRecomendProducts.size()); i++) {
                            newImageRecommendProducts.add(imageRecomendProducts.get(i));
                        }
                        list_items_homePage.addView(new HomePageRecommended(HomePage.this, newImageRecommendProducts));

                        if (!resource.layout1.equals("null")) {
                            list_items_homePage.addView(new HomePageLayoutOne(HomePage.this, resource.layout1));

                        }
                        if (!resource.layout2.equals("null")) {
                            list_items_homePage.addView(new HomePageLayoutTwo(HomePage.this, resource.layout2, resource.layout2_title));
                        }


                    }
                    pbLoading.setVisibility(View.INVISIBLE);
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

                            Glide.with(HomePage.this).load(mpmResult.image).fitCenter().dontAnimate()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true).into(ivProfilePic);

                            Glide.with(HomePage.this).load(mpmResult.image).fitCenter().dontAnimate()
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
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_search, menu);

        MenuItem cart_menuItem = menu.findItem(R.id.cartmenu);
        FrameLayout rootView = (FrameLayout) cart_menuItem.getActionView();
        hometotalCartItemCount = (TextView) rootView.findViewById(R.id.cart_badge);

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
                                hometotalCartItemCount.setText(cartCount.data);
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
        String emp_user_id = session.getCustomerId();
        String emp_token_id = session.getKeyTokenId();

       //  Log.d("emp_token_id", emp_token_id);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        if (!session.getTokenStatus()) {
            final TokenFCM tkn = new TokenFCM(emp_user_id, emp_token_id);
            Call<TokenFCM> calledu = apiInterface.fcmtoken(tkn);
            calledu.enqueue(new Callback<TokenFCM>() {
                @Override
                public void onResponse(Call<TokenFCM> calledu, Response<TokenFCM> response) {
                    final TokenFCM resource = response.body();
                    if (resource.status.equals("success")) {
                        session.createTokenStatus();
                    }
                }

                @Override
                public void onFailure(Call<TokenFCM> calledu, Throwable t) {
                    calledu.cancel();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // setNavMenuItemThemeColors();

        Menu menu = navigationView.getMenu();
        MenuItem item = menu.getItem(0);
        item.setChecked(true);
        Log.d("item", String.valueOf(item));


        Log.d("resumed", "resumed: ");
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
            Intent intentHome = new Intent(HomePage.this, HomePage.class);
            startActivity(intentHome);
        } else if (id == R.id.menuleft_myorders) {
            Intent intentMyOrder = new Intent(HomePage.this, MyOrders.class);
            startActivity(intentMyOrder);
        } else if (id == R.id.menuleft_mywallet) {
            Intent intentMyWallet = new Intent(HomePage.this, MyWalletActivity.class);
            startActivity(intentMyWallet);
        } else if (id == R.id.menuleft_referearn) {
            Intent intentMyReferEarn = new Intent(HomePage.this, RefersAndEarn_act.class);
            startActivity(intentMyReferEarn);
        } else if (id == R.id.menuleft_rateus) {
            Intent intentRateUs = new Intent(HomePage.this, RateUsReviews.class);
            intentRateUs.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentRateUs);
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
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        } else if (id == R.id.menuleft_policy) {
            Intent intentPolicy = new Intent(HomePage.this, PrivacyPolicy.class);
            startActivity(intentPolicy);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drwLayout);
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
            Uri filePath = data.getData();
            imagepath = getPath(filePath);

            Glide.with(HomePage.this).load(filePath).fitCenter().dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).into(ivProfilePic);

            Glide.with(HomePage.this).load(filePath).fitCenter().dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).into(ivToolbarProfile);

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

    private void reffaralcheck() {
        if (session.getReferalStatus() == TRUE) {

            mReferrerClient = InstallReferrerClient.newBuilder(this).build();

            mReferrerClient.startConnection(new InstallReferrerStateListener() {
                @Override
                public void onInstallReferrerSetupFinished(int responseCode) {
                    switch (responseCode) {
                        case InstallReferrerClient.InstallReferrerResponse.OK:
                            // Connection established
                            try {
                                ReferrerDetails response = mReferrerClient.getInstallReferrer();

                                // if (!response.getInstallReferrer().contains("utm_source"))

                                final EarnReferal ref = new EarnReferal(session.getCustomerId(), response.getInstallReferrer());
                                Call<EarnReferal> calledu = apiInterface.earnReferal(ref);
                                calledu.enqueue(new Callback<EarnReferal>() {
                                    @Override
                                    public void onResponse(Call<EarnReferal> calledu, Response<EarnReferal> response) {
                                        final EarnReferal resource = response.body();
                                        if (resource.status.equals("success")) {


                                        } else if (resource.status.equals("error")) {

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<EarnReferal> calledu, Throwable t) {
                                        calledu.cancel();
                                    }
                                });


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            mReferrerClient.endConnection();
                            break;
                        case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                            // API not available on the current Play Store app
                            break;
                        case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                            // Connection could not be established
                            break;
                    }
                }

                @Override
                public void onInstallReferrerServiceDisconnected() {
                    // Try to restart the connection on the next request to
                    // Google Play by calling the startConnection() method.
                }
            });

        }
    }
}
