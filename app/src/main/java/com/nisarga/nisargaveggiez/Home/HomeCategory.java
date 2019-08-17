package com.nisarga.nisargaveggiez.Home;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;;
import android.support.v7.widget.GridLayoutManager;
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
import com.nisarga.nisargaveggiez.ContactUs;
import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.MyOrder.MyOrders;
import com.nisarga.nisargaveggiez.PrivacyPolicy;
import com.nisarga.nisargaveggiez.ProfileSection.EditProfile_act;
import com.nisarga.nisargaveggiez.ProfileSection.GoogleFeedback_act;
import com.nisarga.nisargaveggiez.ProfileSection.LoginSignup_act;
import com.nisarga.nisargaveggiez.ProfileSection.MyProfileModel;
import com.nisarga.nisargaveggiez.ProfileSection.MyProfile_act;
import com.nisarga.nisargaveggiez.ProfileSection.Offers_act;
import com.nisarga.nisargaveggiez.ProfileSection.RateUs_act;
import com.nisarga.nisargaveggiez.ProfileSection.RefersAndEarn_act;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.TermsConditions;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.Wishlist.WishListHolder;
import com.nisarga.nisargaveggiez.cart.cart;
import com.nisarga.nisargaveggiez.notifications.MyNotifications;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.ProductListModel;
import com.nisarga.nisargaveggiez.wallet.MyWalletActivity;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sushmita on 11/06/2019
 */


public class HomeCategory extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    APIInterface apiInterface;
    public Context mContext;

    Toolbar toolbar;
    private PlaceHolderView mCartView;
    private ImageButton btn_listViewHomeCateg, btn_filterHomeCateg;
    public static BottomNavigationView bottomNavigationView;
    View view_count;
    private LinearLayout llProfileIconCateg, llProfileDesc;
    private ImageButton imgBtnProfileCateg;
    AutoCompleteTextView searchEditText;
    private TextView tv_totalPrds;
    private ProgressBar progressBarHomeCateg;
    private LinearLayout llLeftMenuLogOut;
    private ListView lvMenuListCateg;
    private ImageView ivEditProfileCateg;
    private ImageView ivEditProfile;
    private CircularImageView ivProfilePic;
    private TextView tvName, tvEmail, tvMobileNo;
    private Button btnEditProfilePic;
    NavigationView navigationView;
    View headerView;
    String custId;
    String strSearchKey;


    DrawerLayout drawerLayout;
    public boolean chngView = true;
    public boolean state = true;
    Integer name_session;
    SessionManager session;
    public static TextView textCartItemCount;
    UseSharedPreferences useSharedPreferences;
    Integer[] icon = {R.drawable.round_home_24px, R.drawable.my_order_yellow, R.drawable.location_yellow,
            R.drawable.walletyellow, R.drawable.offers_yellow, R.drawable.refer_earn_yellow, R.drawable.rateus_yellow,
            R.drawable.abt_contact_yellow, R.drawable.faqs_yellow, R.drawable.terms_yellow, R.drawable.google_feedback_yellow,
            R.drawable.privacy_policy_yellow, R.drawable.logout_yellow};

    String[] menu_list = new String[]{"Home", "My Orders", "My Address", "My Wallet", "Offers",
            "Refer & Earn", "Rate Us", "About & Contact Us", "FAQs", "Terms & Conditions",
            "Google Feedback", "Policy", "Logout"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_category);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        mContext = this.getApplicationContext();

        session = new SessionManager(getApplicationContext());
        custId = session.getCustomerId();

        Integer cnt = session.getCartCount();
        Log.d("cartcnt", String.valueOf(cnt));
        useSharedPreferences = new UseSharedPreferences(getApplication());
        AndroidNetworking.initialize(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbarHomeCateg);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        progressBarHomeCateg = (ProgressBar) findViewById(R.id.loadingHomeCateg);
        llProfileIconCateg = findViewById(R.id.llProfileIconCateg);
        imgBtnProfileCateg = findViewById(R.id.imgBtnProfileCateg);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerHomeCateg);
        mCartView = (PlaceHolderView) findViewById(R.id.recycler_order);
        btn_filterHomeCateg = findViewById(R.id.btn_filterHomeCateg);
        btn_listViewHomeCateg = findViewById(R.id.btn_listViewHomeCateg);
        tv_totalPrds = findViewById(R.id.tv_totalPrds);
        ActionBar mActionBar = getActionBar();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        navigationView = (NavigationView) findViewById(R.id.nav_viewHomeCateg);
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
        imgBtnProfileCateg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        showGridView();
        btn_listViewHomeCateg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chngView == false) {
                    showGridView();
                    btn_listViewHomeCateg.setBackgroundResource(R.drawable.listview);
                    chngView = true;
                } else {
                    showListView();
                    btn_listViewHomeCateg.setBackgroundResource(R.drawable.gridview);
                    chngView = false;
                }
            }
        });

        btn_filterHomeCateg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFilter = new Intent(getBaseContext(), CategoryFilter.class);
                startActivity(intentFilter);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent intentHome = new Intent(getBaseContext(), HomePage.class);
                                startActivity(intentHome);
                                break;

                            case R.id.navigation_categories:
                                Intent intentCateg = new Intent(getBaseContext(), CategoriesBottomNav.class);
                                startActivity(intentCateg);
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

    public void showListView() {
        mCartView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        if (Utils.CheckInternetConnection(getApplicationContext())) {

            final ProductListModel prdListModel = new ProductListModel("1");
            Call<ProductListModel> call = apiInterface.getProductsList(prdListModel);
            call.enqueue(new Callback<ProductListModel>() {
                @Override
                public void onResponse(Call<ProductListModel> call, Response<ProductListModel> response) {
                    ProductListModel resource = response.body();
                    List<ProductListModel.ProductListDatum> datumList = resource.result;

                    for (ProductListModel.ProductListDatum imgs : datumList) {
                        if (response.isSuccessful()) {
                            progressBarHomeCateg.setVisibility(View.INVISIBLE);
                            mCartView.addView(new HomeCategoryItem(mContext, textCartItemCount, imgs.prd_id, imgs.image,
                                    imgs.name, imgs.price, imgs.qty));
                        }
                    }

                    mCartView.sort(new Comparator<Object>() {
                        @Override
                        public int compare(Object item1, Object item2) {
                            if (item1 instanceof HomeCategoryItem && item2 instanceof HomeCategoryItem) {
                                HomeCategoryItem view1 = (HomeCategoryItem) item1;
                                HomeCategoryItem view2 = (HomeCategoryItem) item2;
                                return view1.getTitle().compareTo(view2.getTitle());
                            }
                            return 0;
                        }
                    });

                    mCartView.refresh();
                }

                @Override
                public void onFailure(Call<ProductListModel> call, Throwable t) {
                    call.cancel();
                }
            });


        } else {
            Toast.makeText(mContext, "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void showGridView() {
        mCartView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final ProductListModel prdListModel1 = new ProductListModel("1");
            Call<ProductListModel> call = apiInterface.getProductsList(prdListModel1);
            call.enqueue(new Callback<ProductListModel>() {
                @Override
                public void onResponse(Call<ProductListModel> call, Response<ProductListModel> response) {
                    ProductListModel resource = response.body();
                    List<ProductListModel.ProductListDatum> datumList = resource.result;

                    for (ProductListModel.ProductListDatum imgs : datumList) {
                        if (response.isSuccessful()) {
                            progressBarHomeCateg.setVisibility(View.INVISIBLE);
                            mCartView.addView(new HomeCategoryItemGridView(mContext, textCartItemCount, imgs.prd_id, imgs.image,
                                    imgs.name, imgs.price, imgs.qty));
                        }
                    }

                    mCartView.sort(new Comparator<Object>() {
                        @Override
                        public int compare(Object item1, Object item2) {
                            if (item1 instanceof HomeCategoryItem && item2 instanceof HomeCategoryItem) {
                                HomeCategoryItem view1 = (HomeCategoryItem) item1;
                                HomeCategoryItem view2 = (HomeCategoryItem) item2;
                                return view1.getTitle().compareTo(view2.getTitle());
                            }
                            return 0;
                        }
                    });

                    mCartView.refresh();
                }

                @Override
                public void onFailure(Call<ProductListModel> call, Throwable t) {
                    call.cancel();
                }
            });


        } else {
            Toast.makeText(mContext, "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    //------------------------------------Left Profile Navigation Menu---------------------------------------------------
    public void ViewMyProfile() {
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            //------------------------------------- My profile view section-----------------------------------------------
            final MyProfileModel myProfileModel = new MyProfileModel(custId);
            Call<MyProfileModel> call = apiInterface.showMyProfile(myProfileModel);
            call.enqueue(new Callback<MyProfileModel>() {
                @Override
                public void onResponse(Call<MyProfileModel> call, Response<MyProfileModel> response) {
                    MyProfileModel resourceMyProfile = response.body();
                    if (resourceMyProfile.status.equals("success")) {
                        Toast.makeText(getApplicationContext(), resourceMyProfile.message, Toast.LENGTH_SHORT).show();
                        List<MyProfileModel.Datum> mpmDatum = resourceMyProfile.resultdata;
                        for (MyProfileModel.Datum mpmResult : mpmDatum) {
                            tvName.setText(mpmResult.firstname + " " + mpmResult.lastname);
                            tvEmail.setText(mpmResult.email);
                            tvMobileNo.setText(mpmResult.telephone);
                        }

                    } else if (resourceMyProfile.status.equals("error")) {
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

    //---------------------------------------for scanner popup--------------------------------
    public void showResultDialogue(final String result) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Scan Result")
                .setMessage("Scanned result is " + result)
                .setPositiveButton("Copy result", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Scan Result", result);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(HomeCategory.this, "Result copied to clipboard", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        name_session = useSharedPreferences.getCountValue();

        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        view_count = bottomNavigationMenuView.getChildAt(4);
      /*  new QBadgeView(mContext).bindTarget(view_count).setBadgeTextColor(mContext.getResources().getColor(R.color.white))
                .setGravityOffset(15, -2, true).setBadgeNumber(name_session).setBadgeBackgroundColor
                (mContext.getResources().getColor(R.color.colorPrimaryDark));*/
    }

    /* @Override
     public boolean onSupportNavigateUp() {
         onBackPressed();
         return true;
     }
 */
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
        final android.support.v7.widget.SearchView searchViews = (android.support.v7.widget.SearchView) searchViewItem.getActionView();
        //searchViews.setQueryHint("Search...");
        searchViews.setBackgroundColor(getResources().getColor(R.color.white));

        int actionBarHeight = toolbar.getLayoutParams().height;
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
        //searchViews.setPadding(-16, 0, 0, 0);//removing extraa space and align icon to leftmost of searchview
        searchViews.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchEditText = (AutoCompleteTextView) searchViews.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setPadding(2, 2, 2, 2);
        searchEditText.setHint(null);//removing search hint from search layout
        strSearchKey = searchEditText.getText().toString();
        searchEditText.setThreshold(1);//will start working from first character
        searchEditText.setTextColor(Color.parseColor("#824A4A4A"));
        searchEditText.setBackgroundColor(Color.TRANSPARENT);
        searchEditText.setOnItemClickListener(onItemClickListener);
        searchMagIcon.setImageDrawable(null);//remove search icon
        searchEditText.clearFocus();

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
                Intent notificationIntent = new Intent(getBaseContext(), MyNotifications.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(notificationIntent);
                break;

            case R.id.cart:
                Intent DeliveryIntent = new Intent(getBaseContext(), cart.class);
                DeliveryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(DeliveryIntent);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.menuleft_home) {
            menuItem.setEnabled(true);
            Intent intentHome = new Intent(HomeCategory.this, HomePage.class);
            startActivity(intentHome);
        } else if (id == R.id.menuleft_myorders) {
            Intent intentMyOrder = new Intent(HomeCategory.this, MyOrders.class);
            startActivity(intentMyOrder);
        }  else if (id == R.id.menuleft_mywallet) {
            Intent intentMyWallet = new Intent(HomeCategory.this, MyWalletActivity.class);
            startActivity(intentMyWallet);
        } else if (id == R.id.menuleft_offers) {
            Intent intentMyOffers = new Intent(HomeCategory.this, Offers_act.class);
            startActivity(intentMyOffers);
        } else if (id == R.id.menuleft_referearn) {
            Intent intentMyReferEarn = new Intent(HomeCategory.this, RefersAndEarn_act.class);
            startActivity(intentMyReferEarn);
        } else if (id == R.id.menuleft_rateus) {
            Intent intentMyRateUs = new Intent(HomeCategory.this, RateUs_act.class);
            startActivity(intentMyRateUs);
        } else if (id == R.id.menuleft_aboutcontact) {
            Intent intentAbtContact = new Intent(HomeCategory.this, ContactUs.class);
            startActivity(intentAbtContact);
        } else if (id == R.id.menuleft_faqs) {
            Intent intentFaqs = new Intent(HomeCategory.this, DeliveryInformation.class);
            startActivity(intentFaqs);
        } else if (id == R.id.menuleft_terms) {
            Intent intentTerms = new Intent(HomeCategory.this, TermsConditions.class);
            startActivity(intentTerms);
        } else if (id == R.id.menuleft_gfeedback) {
            Intent intentFeedback = new Intent(HomeCategory.this, GoogleFeedback_act.class);
            startActivity(intentFeedback);
        } else if (id == R.id.menuleft_policy) {
            Intent intentPolicy = new Intent(HomeCategory.this, PrivacyPolicy.class);
            startActivity(intentPolicy);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drwLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}