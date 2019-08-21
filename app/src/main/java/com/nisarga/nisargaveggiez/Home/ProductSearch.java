package com.nisarga.nisargaveggiez.Home;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.nisarga.nisargaveggiez.ContactUs;
import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.MyOrder.MyOrders;
import com.nisarga.nisargaveggiez.PrivacyPolicy;
import com.nisarga.nisargaveggiez.ProfileSection.EditProfile_act;
import com.nisarga.nisargaveggiez.ProfileSection.GoogleFeedback_act;
import com.nisarga.nisargaveggiez.ProfileSection.MyProfile_act;
import com.nisarga.nisargaveggiez.ProfileSection.Offers_act;
import com.nisarga.nisargaveggiez.ProfileSection.RateUs_act;
import com.nisarga.nisargaveggiez.ProfileSection.RefersAndEarn_act;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.TermsConditions;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.Wishlist.WishListHolder;
import com.nisarga.nisargaveggiez.adapter.AutoCompleteAdapter;
import com.nisarga.nisargaveggiez.adapter.RemoteData;
import com.nisarga.nisargaveggiez.cart.cart;
import com.nisarga.nisargaveggiez.fcm.NotificationUtils;
import com.nisarga.nisargaveggiez.fcm.fcmConfig;
import com.nisarga.nisargaveggiez.notifications.MyNotifications;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.CancelOrderModel;
import com.nisarga.nisargaveggiez.retrofit.EarnReferal;
import com.nisarga.nisargaveggiez.retrofit.RateModel;
import com.nisarga.nisargaveggiez.wallet.MyWalletActivity;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mikhaellopez.circularimageview.CircularImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductSearch extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    APIInterface apiInterface;
    SessionManager session;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private static ProductSearch instance;
    private Toolbar mToolbarHomePage;

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
    String strSearchKey;
    private AutoCompleteTextView storeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_search);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());
        mContext = this.getApplicationContext();
        instance = this;

        RemoteData remoteData = new RemoteData(this);
        remoteData.getStoreData();

        storeTV = (AutoCompleteTextView) findViewById(R.id.store);
        storeTV.requestFocus();
        //storeTV.setOnItemClickListener(onItemClickListener);


        AndroidNetworking.initialize(getApplicationContext());
        init();
        initBottomNavigation();

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

        llProfileIcon = (LinearLayout) findViewById(R.id.llProfileIcon);
        imgBtnProfile = findViewById(R.id.imgBtnProfile);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationHomePage);
        drwLayout = findViewById(R.id.drwLayout);


        //-----------------------------------------------------------------------------------
        navigationView = (NavigationView) findViewById(R.id.nav_viewHomePage);
        headerView = navigationView.getHeaderView(0);
        //btnEditProfilePic = headerView.findViewById(R.id.btnEditProfilePic);
        tvName = headerView.findViewById(R.id.tvName);
        tvEmail = headerView.findViewById(R.id.tvEmail);
        tvMobileNo = headerView.findViewById(R.id.tvMobileNo);
        llProfileDesc = (LinearLayout) headerView.findViewById(R.id.llProfileDesc);
       // ivProfilePic = headerView.findViewById(R.id.ivProfilePic);
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

//        ivEditProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentEditProfile = new Intent(getBaseContext(), EditProfile_act.class);
//                startActivity(intentEditProfile);
//            }
//        });
        llLeftMenuLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //session.checkLogin();
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
                                Intent intentHomePage = new Intent(ProductSearch.this, ProductSearch.class);
                                intentHomePage.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intentHomePage);
                                break;

                            case R.id.navigation_categories:
                                Intent intentCategories = new Intent(ProductSearch.this, CategoriesBottomNav.class);
                                intentCategories.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intentCategories);
                                break;

                            case R.id.navigation_wishlist:
                                Intent intentWishlist = new Intent(ProductSearch.this, WishListHolder.class);
                                intentWishlist.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intentWishlist);
                                break;

                            case R.id.navigation_wallet:
                                Intent intentWallet = new Intent(ProductSearch.this, MyWalletActivity.class);
                                intentWallet.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intentWallet);
                                break;
                        }
                        return true;
                    }
                });
        bottomNavigationView.setItemIconSize(40);
    }


    public static ProductSearch getInstance() {
        return instance;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

//    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
//        {
//
//           // Log.d("Firebase reg i: ", String.valueOf(customer.getTitle()));
//        }
//    };

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
            Intent intentHome = new Intent(ProductSearch.this, ProductSearch.class);
            startActivity(intentHome);
        } else if (id == R.id.menuleft_myorders) {
            Intent intentMyOrder = new Intent(ProductSearch.this, MyOrders.class);
            startActivity(intentMyOrder);
        } else if (id == R.id.menuleft_mywallet) {
            Intent intentMyWallet = new Intent(ProductSearch.this, MyWalletActivity.class);
            startActivity(intentMyWallet);
        } else if (id == R.id.menuleft_referearn) {
            Intent intentMyReferEarn = new Intent(ProductSearch.this, RefersAndEarn_act.class);
            startActivity(intentMyReferEarn);

        } else if (id == R.id.menuleft_rateus) {

            LayoutInflater li = LayoutInflater.from(ProductSearch.this);
            android.view.View promptsView = li.inflate(R.layout.rate_us_act, null);
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ProductSearch.this,
                    R.style.AlertDialogStyle);
            alertDialogBuilder.setView(promptsView);

            // set the custom dialog components - text, image and button
            ImageView ivClose = (ImageView) promptsView.findViewById(R.id.ivClose);
            ImageView ivUnlike = (ImageView) promptsView.findViewById(R.id.ivUnlike);
            ImageView ivLike = (ImageView) promptsView.findViewById(R.id.ivLike);
            Button btnSubmit = (Button) promptsView.findViewById(R.id.btnSubmit);

            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            ivLike.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view)
                {


                    final RateModel ref = new RateModel(session.getCustomerId(),"1");
                    Call<RateModel> calledu = apiInterface.setrate(ref);
                    calledu.enqueue(new Callback<RateModel>() {
                        @Override
                        public void onResponse(Call<RateModel> calledu, Response<RateModel> response) {
                            final RateModel resource = response.body();
                            if (resource.status.equals("success"))
                            {
                                Log.d("ivsuccess", "onResponse: ");
                            }
                            else if (resource.status.equals("error"))
                            {

                            }
                        }

                        @Override
                        public void onFailure(Call<RateModel> calledu, Throwable t) {
                            calledu.cancel();
                        }
                    });


                }
            });

            ivUnlike.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view)
                {

                    final RateModel ref = new RateModel(session.getCustomerId(),"0");
                    Call<RateModel> calledu = apiInterface.setrate(ref);
                    calledu.enqueue(new Callback<RateModel>() {
                        @Override
                        public void onResponse(Call<RateModel> calledu, Response<RateModel> response) {
                            final RateModel resource = response.body();
                            if (resource.status.equals("success"))
                            {
                                Log.d("ivfail", "onResponse: ");
                            }
                            else if (resource.status.equals("error"))
                            {

                            }
                        }

                        @Override
                        public void onFailure(Call<RateModel> calledu, Throwable t) {
                            calledu.cancel();
                        }
                    });


                }
            });

            ivClose.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view)
                {
                    alertDialog.cancel();
                }
            });
            btnSubmit.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v)
                {
                    alertDialog.cancel();
                }
            });

        } else if (id == R.id.menuleft_aboutcontact) {
            Intent intentAbtContact = new Intent(ProductSearch.this, ContactUs.class);
            startActivity(intentAbtContact);
        } else if (id == R.id.menuleft_faqs) {
            Intent intentFaqs = new Intent(ProductSearch.this, DeliveryInformation.class);
            startActivity(intentFaqs);
        } else if (id == R.id.menuleft_terms) {
            Intent intentTerms = new Intent(ProductSearch.this, TermsConditions.class);
            startActivity(intentTerms);
        } else if (id == R.id.menuleft_gfeedback) {

            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }


        } else if (id == R.id.menuleft_policy) {
            Intent intentPolicy = new Intent(ProductSearch.this, PrivacyPolicy.class);
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

    public void notifcations(View v) {
        Intent notificationIntent = new Intent(getBaseContext(), MyNotifications.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(notificationIntent);
    }


    public void cart(View v) {
        Intent DeliveryIntent = new Intent(getBaseContext(), cart.class);
        DeliveryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(DeliveryIntent);
    }

}
