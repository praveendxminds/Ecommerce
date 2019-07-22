package com.app.ecommerce.Home;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.app.ecommerce.MyOrder.MyOrders;
import com.app.ecommerce.PrivacyPolicy;
import com.app.ecommerce.ProfileSection.EditProfile_act;
import com.app.ecommerce.ProfileSection.Faqs_act;
import com.app.ecommerce.ProfileSection.GoogleFeedback_act;
import com.app.ecommerce.ProfileSection.Login_act;
import com.app.ecommerce.ProfileSection.MyListAdapter;
import com.app.ecommerce.ProfileSection.MyProfile_act;
import com.app.ecommerce.ProfileSection.Offers_act;
import com.app.ecommerce.ProfileSection.RateUs_act;
import com.app.ecommerce.ProfileSection.RefersAndEarn_act;
import com.app.ecommerce.R;
import com.app.ecommerce.SessionManager;
import com.app.ecommerce.TermsConditions;
import com.app.ecommerce.Wishlist.WishListHolder;
import com.app.ecommerce.adapter.RemoteData;
import com.app.ecommerce.cart.cart;
import com.app.ecommerce.fcm.fcmConfig;
import com.app.ecommerce.notifications.MyNotifications;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;

import java.util.ArrayList;

public class CategoriesBottomNav extends AppCompatActivity {

    private ExpandablePlaceHolderView phviewCategList;
    private ProgressBar pbarLoading;
    public static Integer[] murl = {R.drawable.greenleaves, R.drawable.sprouts, R.drawable.englishitem, R.drawable.otherveggiez};
    public static String[] mheading = {"Green Leaves", "Sprouts", "English Item", "Other Veggiez"};
    private AutoCompleteTextView searchEditText;
    public static TextView textCartItemCount;
    public static BottomNavigationView bottomNavigationView;
    SessionManager session;
    private Toolbar mToolbarShopBy;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private DrawerLayout drawerLayoutShopBy;
    private ImageView ivProfilePic, ivEditProfile;
    private TextView tvName, tvEmail, tvMobileNo;
    private ListView lvMenuList;
    private Button btnEditProfilePic;
    private ImageButton imgBtnProfile;
    private LinearLayout llProfileDesc;

    Integer[] icon = {R.drawable.round_home_24px, R.drawable.my_order_yellow, R.drawable.location_yellow,
            R.drawable.walletyellow, R.drawable.offers_yellow, R.drawable.refer_earn_yellow, R.drawable.rateus_yellow,
            R.drawable.abt_contact_yellow, R.drawable.faqs_yellow, R.drawable.terms_yellow, R.drawable.google_feedback_yellow,
            R.drawable.privacy_policy_yellow, R.drawable.logout_yellow};

    String[] menu_list = new String[]{"Home", "My Orders", "My Address", "My Wallet", "Offers",
            "Refer & Earn", "Rate Us", "About & Contact Us", "FAQs", "Terms & Conditions",
            "Google Feedback", "Policy", "Logout"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_layout);

        drawerLayoutShopBy = findViewById(R.id.drawerLayoutShopBy);
        phviewCategList = findViewById(R.id.phviewCategList);
        pbarLoading = findViewById(R.id.pBarloading);

        mToolbarShopBy = findViewById(R.id.toolbarShopBy);
        setSupportActionBar(mToolbarShopBy);
        getSupportActionBar().setTitle(null);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationShopBy);
        AndroidNetworking.initialize(getApplicationContext());

        final ArrayList<Integer> urlArr = new ArrayList<Integer>();
        final ArrayList<String> headingArr = new ArrayList<String>();
        for (int i = 0; i < mheading.length; i++) {
            pbarLoading.setVisibility(View.INVISIBLE);
            urlArr.add(murl[i]);
            headingArr.add(mheading[i]);
            phviewCategList.addView(new ShopByCategItems(getApplication(), urlArr.get(i), headingArr.get(i)));
            phviewCategList.addView(new ShopByListItems(getApplicationContext()));
        }

        ivProfilePic = findViewById(R.id.ivProfilePic);
        ivEditProfile = findViewById(R.id.ivEditProfile);
        llProfileDesc = (LinearLayout) findViewById(R.id.llProfileDesc);
        imgBtnProfile = findViewById(R.id.imgBtnProfile);

        btnEditProfilePic = findViewById(R.id.btnEditProfilePic);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvMobileNo = findViewById(R.id.tvMobileNo);
        lvMenuList = findViewById(R.id.lvMenuList);
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
        imgBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutShopBy.openDrawer(Gravity.LEFT);
            }
        });
        MyListAdapter adapter = new MyListAdapter(this, icon, menu_list);
        lvMenuList.setAdapter(adapter);
        lvMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(CategoriesBottomNav.this, HomePage.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(CategoriesBottomNav.this, MyOrders.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(CategoriesBottomNav.this, Login_act.class);
                    startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent(CategoriesBottomNav.this, EditProfile_act.class);
                    startActivity(intent);
                } else if (position == 4) {
                    Intent intent = new Intent(CategoriesBottomNav.this, Offers_act.class);
                    startActivity(intent);
                } else if (position == 5) {
                    Intent intent = new Intent(CategoriesBottomNav.this, RefersAndEarn_act.class);
                    startActivity(intent);
                } else if (position == 6) {
                    Intent intent = new Intent(CategoriesBottomNav.this, RateUs_act.class);
                    startActivity(intent);
                } else if (position == 7) {
                    Intent intent = new Intent(CategoriesBottomNav.this, ContactUs.class);
                    startActivity(intent);
                } else if (position == 8) {
                    Intent intent = new Intent(CategoriesBottomNav.this, Faqs_act.class);
                    startActivity(intent);
                } else if (position == 9) {
                    Intent intent = new Intent(CategoriesBottomNav.this, TermsConditions.class);
                    startActivity(intent);
                } else if (position == 10) {
                    Intent intent = new Intent(CategoriesBottomNav.this, GoogleFeedback_act.class);
                    startActivity(intent);
                } else if (position == 11) {
                    Intent intent = new Intent(CategoriesBottomNav.this, PrivacyPolicy.class);
                    startActivity(intent);
                } else if (position == 12) {
                    Intent intent = new Intent(CategoriesBottomNav.this, PrivacyPolicy.class);
                    startActivity(intent);
                }
            }
        });
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
        initBottomNavigation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuShopBy = getMenuInflater();
        menuShopBy.inflate(R.menu.toolbar_search, menu);
        MenuItem cart_menuItem = menu.findItem(R.id.cartmenu);
        FrameLayout rootView = (FrameLayout) cart_menuItem.getActionView();

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

//        Log.d( "Firebase reg id: ", regId);

        //if (!TextUtils.isEmpty(regId))
        //  txtRegId.setText("Firebase Reg Id: " + regId);
        // else
        //   txtRegId.setText("Firebase Reg Id is not received yet!");
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
}
