package com.nisarga.nisargaveggiez.Home;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
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
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.nisarga.nisargaveggiez.ContactUs;
import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.MyOrder.MyOrders;
import com.nisarga.nisargaveggiez.PrivacyPolicy;
import com.nisarga.nisargaveggiez.ProfileSection.EditProfile_act;
import com.nisarga.nisargaveggiez.ProfileSection.FilterCategoryModel;
import com.nisarga.nisargaveggiez.ProfileSection.Login_act;
import com.nisarga.nisargaveggiez.ProfileSection.MyProfileModel;
import com.nisarga.nisargaveggiez.ProfileSection.MyProfile_act;
import com.nisarga.nisargaveggiez.ProfileSection.NavEditImage;
import com.nisarga.nisargaveggiez.ProfileSection.RefersAndEarn_act;
import com.nisarga.nisargaveggiez.ProfileSection.SignUpImageResponse;
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
import com.nisarga.nisargaveggiez.retrofit.RateModel;
import com.nisarga.nisargaveggiez.search;
import com.nisarga.nisargaveggiez.wallet.MyWalletActivity;
import com.mindorks.placeholderview.PlaceHolderView;

import java.io.File;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sushmita on 11/06/2019
 */

public class HomeCategory extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    APIInterface apiInterface;
    SessionManager session;
    ProgressDialog progressdialog;

    public static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_WRITE_PERMISSION = 786;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_category);

        progressdialog = new ProgressDialog(HomeCategory.this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());

        init();
        initApi();
    }

    DrawerLayout drawerHomeCategory;
    Toolbar toolbar;
    TextView tvTotalProduct;
    ImageButton ivbtnListView, ivbtnFilter;
    PlaceHolderView phvCategoryList;
    ProgressBar pbLoading;
    BottomNavigationView bottom_navigation;
    NavigationView navLeftMenu;
    View headerView;
    LinearLayout llLeftMenuLogout;

    //----Toolbar----
    CircleImageView ivToolbarProfile;
    EditText searchEditText;
    LinearLayout llProfileIcon;

    LinearLayout llProfileDesc, llEditProfile;
    CircleImageView ivProfilePic;
    TextView tvName, tvEmail, tvMobileNo;

    String strSearchKey;
    public boolean chngView = true;
    private String imagepath = null;
    String strProfilePic = "null";

    String value;
    String viewAll;

    public static TextView cartItemCount;

    //--------Filter----------
    NavigationView navFilter;
    View header;
    TextView tvSortByText, tvSeekBarMin, tvSeekBarMax;
    LinearLayout llSortByOption,llprds_holder;
    CheckBox cbPopularity, cbLowToHigh, cbHighToLow, cbNewestFirst;
    CrystalRangeSeekbar rangeSeekbar;
    Button btnClearFilter, btnApplyFilter;
    ImageView ivDropDown, ivDropUp;
    ImageButton ivbtnFilterListView, ivbtnFilterGridView;

    String sPopularity, sLowToHigh, sHighToLow, sNewestFirst, minPrice, maxPrice;
    String sFilterPopularity = "0";
    String sFilterLowToHigh = "0";
    String sFilterHighToLow = "0";
    String sFilterNewestFirst = "0";

    private void init() {
        viewAll = getIntent().getExtras().getString("ViewAll", "defaultKey");

        drawerHomeCategory = (DrawerLayout) findViewById(R.id.drawerHomeCategory);
        toolbar = (Toolbar) findViewById(R.id.toolbarHomeCategory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        tvTotalProduct = findViewById(R.id.tvTotalProduct);
        ivbtnListView = findViewById(R.id.ivbtnListView);
        ivbtnFilter = findViewById(R.id.ivbtnFilter);
        phvCategoryList = findViewById(R.id.phvCategoryList);

        pbLoading = findViewById(R.id.pbLoading);
        pbLoading.setVisibility(View.VISIBLE);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        llLeftMenuLogout = findViewById(R.id.llLeftMenuLogout);

        ivToolbarProfile = findViewById(R.id.ivToolbarProfile);
        llProfileIcon = findViewById(R.id.llProfileIcon);
        llprds_holder = findViewById(R.id.llprds_holder);

        navLeftMenu = findViewById(R.id.navLeftMenu);
        headerView = navLeftMenu.getHeaderView(0);
        navLeftMenu.setNavigationItemSelectedListener(this);
        setNavMenuItemThemeColors();
        tvName = headerView.findViewById(R.id.tvName);
        tvEmail = headerView.findViewById(R.id.tvEmail);
        tvMobileNo = headerView.findViewById(R.id.tvMobileNo);
        llProfileDesc = headerView.findViewById(R.id.llProfileDesc);
        ivProfilePic = headerView.findViewById(R.id.ivProfilePic);
        llEditProfile = headerView.findViewById(R.id.llEditProfile);





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

        llLeftMenuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                finish();
                Intent intent = new Intent(getBaseContext(), Login_act.class);
                startActivity(intent);
            }
        });

        llProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerHomeCategory.openDrawer(Gravity.LEFT);
            }
        });

        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFile();
            }
        });

        showGridView();
      //  llprds_holder.setPadding(14,14,14,14);

        ivbtnListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chngView == false) {
                    showGridView();
                 //   llprds_holder.setPadding(14,14,14,14);
                    ivbtnListView.setBackgroundResource(R.drawable.listview);
                    chngView = true;
                } else {
                    showListView();
                   // llprds_holder.setPadding(0,0,0,0);
                    ivbtnListView.setBackgroundResource(R.drawable.gridview);
                    chngView = false;
                }
            }
        });

        ivbtnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerHomeCategory.openDrawer(Gravity.RIGHT);
            }
        });

        bottom_navigation.getMenu().findItem(R.id.navigation_home).setChecked(true);
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        finish();
                        Intent intentHome = new Intent(HomeCategory.this, HomePage.class);
                        startActivity(intentHome);
                        break;

                    case R.id.navigation_categories:
                        finish();
                        Intent intentCateg = new Intent(HomeCategory.this, CategoriesBottomNav.class);
                        startActivity(intentCateg);
                        break;

                    case R.id.navigation_wishlist:
                        finish();
                        Intent intentWishlist = new Intent(HomeCategory.this, WishListHolder.class);
                        startActivity(intentWishlist);
                        break;

                    case R.id.navigation_wallet:
                        finish();
                        Intent intentWallet = new Intent(HomeCategory.this, MyWalletActivity.class);
                        startActivity(intentWallet);
                        break;

                }
                return true;
            }
        });
        bottom_navigation.setItemIconSize(40);

        //------Filter------
        navFilter = findViewById(R.id.navFilter);
        header = navFilter.getHeaderView(0);
        navFilter.setNavigationItemSelectedListener(this);
        tvSortByText = header.findViewById(R.id.tvSortByText);
        tvSeekBarMin = header.findViewById(R.id.tvSeekBarMin);
        tvSeekBarMax = header.findViewById(R.id.tvSeekBarMax);

        ivDropDown = header.findViewById(R.id.ivDropDown);
        ivDropUp = header.findViewById(R.id.ivDropUp);
        llSortByOption = header.findViewById(R.id.llSortByOption);

        cbPopularity = header.findViewById(R.id.cbPopularity);
        cbLowToHigh = header.findViewById(R.id.cbLowToHigh);
        cbHighToLow = header.findViewById(R.id.cbHighToLow);
        cbNewestFirst = header.findViewById(R.id.cbNewestFirst);

        rangeSeekbar = header.findViewById(R.id.rangeSeekbar);

        btnClearFilter = findViewById(R.id.btnClearFilter);
        btnApplyFilter = findViewById(R.id.btnApplyFilter);

        ivbtnFilterListView = findViewById(R.id.ivbtnFilterListView);
        ivbtnFilterGridView = findViewById(R.id.ivbtnFilterGridView);

        ivbtnFilterListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterListView();
                ivbtnListView.setVisibility(View.GONE);
                ivbtnFilterGridView.setVisibility(View.VISIBLE);
                ivbtnFilterListView.setVisibility(View.GONE);
            }
        });

        ivbtnFilterGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterGridView();
                ivbtnListView.setVisibility(View.GONE);
                ivbtnFilterGridView.setVisibility(View.GONE);
                ivbtnFilterListView.setVisibility(View.VISIBLE);
            }
        });

        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                minPrice = String.valueOf(minValue);
                maxPrice = String.valueOf(maxValue);
                tvSeekBarMin.setText(minPrice);
                tvSeekBarMax.setText(maxPrice);
            }
        });

        ivDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSortByOption.setVisibility(View.VISIBLE);
                ivDropDown.setVisibility(View.GONE);
                ivDropUp.setVisibility(View.VISIBLE);
            }
        });

        ivDropUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSortByOption.setVisibility(View.GONE);
                ivDropUp.setVisibility(View.GONE);
                ivDropDown.setVisibility(View.VISIBLE);
            }
        });

        cbPopularity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    sPopularity = cbPopularity.getText().toString();
                    sFilterPopularity = "1";
                }
            }
        });

        cbLowToHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    sLowToHigh = cbLowToHigh.getText().toString();
                    sFilterLowToHigh = "1";
                }
            }
        });

        cbHighToLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    sHighToLow = cbHighToLow.getText().toString();
                    sFilterHighToLow = "1";
                }
            }
        });

        cbNewestFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    sNewestFirst = cbNewestFirst.getText().toString();
                    sFilterNewestFirst = "1";
                }
            }
        });

        btnClearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phvCategoryList.removeAllViews();
                showGridView();
                ivbtnListView.setVisibility(View.VISIBLE);
                ivbtnFilterGridView.setVisibility(View.GONE);
                ivbtnFilterListView.setVisibility(View.GONE);
                cbPopularity.setChecked(false);
                cbHighToLow.setChecked(false);
                cbLowToHigh.setChecked(false);
                cbNewestFirst.setChecked(false);
                cbNewestFirst.setChecked(false);
                tvSeekBarMin.setText("1");
                tvSeekBarMax.setText("500");
                rangeSeekbar.setMinValue(1).apply();
                rangeSeekbar.setMaxValue(500).apply();
                drawerHomeCategory.closeDrawer(Gravity.RIGHT);
            }
        });

        btnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerHomeCategory.closeDrawer(Gravity.RIGHT);
                if (Utils.CheckInternetConnection(getApplicationContext())) {
                    filterGridView();
                    ivbtnListView.setVisibility(View.GONE);
                    ivbtnFilterGridView.setVisibility(View.GONE);
                    ivbtnFilterListView.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void filterListView() {
        phvCategoryList.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

        final FilterCategoryModel model = new FilterCategoryModel(session.getCustomerId(), sFilterPopularity,
                sFilterLowToHigh, sFilterHighToLow, sFilterNewestFirst, minPrice, maxPrice);

        Call<FilterCategoryModel> callEditProfile = apiInterface.filter_products(model);
        callEditProfile.enqueue(new Callback<FilterCategoryModel>() {
            @Override
            public void onResponse(Call<FilterCategoryModel> call, Response<FilterCategoryModel> response) {
                FilterCategoryModel model = response.body();
                if (model.status.equals("success")) {
                    // phvCategoryList.removeAllViews();
                    tvTotalProduct.setText(model.count + " Products found");
                    List<FilterCategoryModel.Datum> datumList = model.resultdata;
                    for (FilterCategoryModel.Datum imgs : datumList) {
                        Object qtyspinner = "null";

                        if ((imgs.options.equals("null")) && (!imgs.weight_classes.equals("null"))) {
                            qtyspinner = imgs.weight_classes;
                            phvCategoryList.addView(new HomeCategoryItem(HomeCategory.this, imgs.product_id,
                                    imgs.image, imgs.name, imgs.wishlist_status, qtyspinner,
                                    imgs.add_product_quantity_in_cart, imgs.discount_price, imgs.price));

                        } else if ((!imgs.options.equals("null")) && (imgs.weight_classes.equals("null"))) {
                            qtyspinner = imgs.options;
                            phvCategoryList.addView(new HomeCategoryItem(HomeCategory.this, imgs.product_id,
                                    imgs.image, imgs.name, imgs.wishlist_status, qtyspinner,
                                    imgs.add_product_quantity_in_cart, imgs.discount_price, imgs.price));

                        } else if ((imgs.options.equals("null")) && (imgs.weight_classes.equals("null"))) {
                            phvCategoryList.addView(new HomeCategoryItem(HomeCategory.this, imgs.product_id,
                                    imgs.image, imgs.name, imgs.wishlist_status, qtyspinner,
                                    imgs.add_product_quantity_in_cart, imgs.discount_price, imgs.price));
                        }

                    }
                }

                pbLoading.setVisibility(View.INVISIBLE);

                phvCategoryList.sort(new Comparator<Object>() {
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

                phvCategoryList.refresh();
            }

            @Override
            public void onFailure(Call<FilterCategoryModel> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void filterGridView() {


        phvCategoryList.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));





        final FilterCategoryModel model = new FilterCategoryModel(session.getCustomerId(), sFilterPopularity,
                sFilterLowToHigh, sFilterHighToLow, sFilterNewestFirst, minPrice, maxPrice);

        Call<FilterCategoryModel> callEditProfile = apiInterface.filter_products(model);
        callEditProfile.enqueue(new Callback<FilterCategoryModel>() {
            @Override
            public void onResponse(Call<FilterCategoryModel> call, Response<FilterCategoryModel> response) {
                FilterCategoryModel model = response.body();
                if (model.status.equals("success")) {
                    phvCategoryList.removeAllViews();
                    tvTotalProduct.setText(model.count + " Products found");
                    List<FilterCategoryModel.Datum> datumList = model.resultdata;
                    for (FilterCategoryModel.Datum imgs : datumList) {
                        Object qtyspinner = "null";

                        if ((imgs.options.equals("null")) && (!imgs.weight_classes.equals("null"))) {
                            qtyspinner = imgs.weight_classes;
                            phvCategoryList.addView(new HomeCategoryItemGridView(HomeCategory.this, imgs.product_id,
                                    imgs.image, imgs.name, imgs.wishlist_status, qtyspinner,
                                    imgs.add_product_quantity_in_cart, imgs.discount_price, imgs.price));

                        } else if ((!imgs.options.equals("null")) && (imgs.weight_classes.equals("null"))) {
                            qtyspinner = imgs.options;
                            phvCategoryList.addView(new HomeCategoryItemGridView(HomeCategory.this, imgs.product_id,
                                    imgs.image, imgs.name, imgs.wishlist_status, qtyspinner,
                                    imgs.add_product_quantity_in_cart, imgs.discount_price, imgs.price));

                        } else if ((imgs.options.equals("null")) && (imgs.weight_classes.equals("null"))) {
                            phvCategoryList.addView(new HomeCategoryItemGridView(HomeCategory.this, imgs.product_id,
                                    imgs.image, imgs.name, imgs.wishlist_status, qtyspinner,
                                    imgs.add_product_quantity_in_cart, imgs.discount_price, imgs.price));
                        }

                    }
                }

                pbLoading.setVisibility(View.INVISIBLE);

                phvCategoryList.sort(new Comparator<Object>() {
                    @Override
                    public int compare(Object item1, Object item2) {
                        if (item1 instanceof HomeCategoryItemGridView && item2 instanceof HomeCategoryItemGridView) {
                            HomeCategoryItemGridView view1 = (HomeCategoryItemGridView) item1;
                            HomeCategoryItemGridView view2 = (HomeCategoryItemGridView) item2;
                            return view1.getTitle().compareTo(view2.getTitle());
                        }
                        return 0;
                    }
                });

                phvCategoryList.refresh();
            }

            @Override
            public void onFailure(Call<FilterCategoryModel> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void initApi() {
        //------------------- My profile view section----------------
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final MyProfileModel myProfileModel = new MyProfileModel(session.getCustomerId());
            Call<MyProfileModel> call = apiInterface.showMyProfile(myProfileModel);
            call.enqueue(new Callback<MyProfileModel>() {
                @Override
                public void onResponse(Call<MyProfileModel> call, Response<MyProfileModel> response) {
                    MyProfileModel resourceMyProfile = response.body();
                    if (resourceMyProfile.status.equals("success")) {
                        List<MyProfileModel.Datum> mpmDatum = resourceMyProfile.resultdata;
                        for (MyProfileModel.Datum mpmResult : mpmDatum) {

                            Glide.with(HomeCategory.this).load(mpmResult.image).fitCenter().dontAnimate()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true).into(ivProfilePic);

                            Glide.with(HomeCategory.this).load(mpmResult.image).fitCenter().dontAnimate()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true).into(ivToolbarProfile);

                            tvName.setText(mpmResult.firstname + " " + mpmResult.lastname);
                            tvEmail.setText(mpmResult.email);
                            tvMobileNo.setText(mpmResult.telephone);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), resourceMyProfile.message, Toast.LENGTH_SHORT).show();
                    }
                    pbLoading.setVisibility(View.INVISIBLE);
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

    public void showListView() {
        phvCategoryList.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final ProductListModel prdListModel = new ProductListModel(session.getCustomerId(), viewAll);
            Call<ProductListModel> call = apiInterface.getProductsList(prdListModel);
            call.enqueue(new Callback<ProductListModel>() {
                @Override
                public void onResponse(Call<ProductListModel> call, Response<ProductListModel> response) {
                    ProductListModel resource = response.body();
                    if (resource.status.equals("success")) {
                        tvTotalProduct.setText(resource.total_product_count + " Products found");
                        List<ProductListModel.ProductListDatum> datumList = resource.result;
                        for (ProductListModel.ProductListDatum imgs : datumList) {
                            Object qtyspinner = "null";

                            if ((imgs.options.equals("null")) && (!imgs.weight_classes.equals("null"))) {
                                qtyspinner = imgs.weight_classes;
                                phvCategoryList.addView(new HomeCategoryItem(HomeCategory.this, imgs.prd_id,
                                        imgs.image, imgs.name, imgs.wishlist_status, qtyspinner,
                                        imgs.add_product_quantity_in_cart, imgs.discount_price, imgs.price));

                            } else if ((!imgs.options.equals("null")) && (imgs.weight_classes.equals("null"))) {
                                qtyspinner = imgs.options;
                                phvCategoryList.addView(new HomeCategoryItem(HomeCategory.this, imgs.prd_id,
                                        imgs.image, imgs.name, imgs.wishlist_status, qtyspinner,
                                        imgs.add_product_quantity_in_cart, imgs.discount_price, imgs.price));

                            } else if ((imgs.options.equals("null")) && (imgs.weight_classes.equals("null"))) {
                                phvCategoryList.addView(new HomeCategoryItem(HomeCategory.this, imgs.prd_id,
                                        imgs.image, imgs.name, imgs.wishlist_status, qtyspinner,
                                        imgs.add_product_quantity_in_cart, imgs.discount_price, imgs.price));
                            }
                        }
                    }

                    pbLoading.setVisibility(View.INVISIBLE);

                    phvCategoryList.sort(new Comparator<Object>() {
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

                    phvCategoryList.refresh();
                }

                @Override
                public void onFailure(Call<ProductListModel> call, Throwable t) {
                    call.cancel();
                }
            });


        } else {
            Toast.makeText(HomeCategory.this, "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void showGridView() {
        phvCategoryList.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));





        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final ProductListModel prdListModel1 = new ProductListModel(session.getCustomerId(), viewAll);
            Call<ProductListModel> call = apiInterface.getProductsList(prdListModel1);
            call.enqueue(new Callback<ProductListModel>() {
                @Override
                public void onResponse(Call<ProductListModel> call, Response<ProductListModel> response) {
                    ProductListModel resource = response.body();
                    if (resource.status.equals("success")) {
                        tvTotalProduct.setText(resource.total_product_count + " Products found");
                        List<ProductListModel.ProductListDatum> datumList = resource.result;
                        for (ProductListModel.ProductListDatum imgs : datumList) {
                            Object qtyspinner = "null";

                            if ((imgs.options.equals("null")) && (!imgs.weight_classes.equals("null"))) {
                                qtyspinner = imgs.weight_classes;
                                phvCategoryList.addView(new HomeCategoryItemGridView(HomeCategory.this, imgs.prd_id,
                                        imgs.image, imgs.name, imgs.wishlist_status, qtyspinner,
                                        imgs.add_product_quantity_in_cart, imgs.discount_price, imgs.price));

                            } else if ((!imgs.options.equals("null")) && (imgs.weight_classes.equals("null"))) {
                                qtyspinner = imgs.options;
                                phvCategoryList.addView(new HomeCategoryItemGridView(HomeCategory.this, imgs.prd_id,
                                        imgs.image, imgs.name, imgs.wishlist_status, qtyspinner,
                                        imgs.add_product_quantity_in_cart, imgs.discount_price, imgs.price));

                            } else if ((imgs.options.equals("null")) && (imgs.weight_classes.equals("null"))) {
                                phvCategoryList.addView(new HomeCategoryItemGridView(HomeCategory.this, imgs.prd_id,
                                        imgs.image, imgs.name, imgs.wishlist_status, qtyspinner,
                                        imgs.add_product_quantity_in_cart, imgs.discount_price, imgs.price));
                            }
                        }
                    }

                    pbLoading.setVisibility(View.INVISIBLE);

                    phvCategoryList.sort(new Comparator<Object>() {
                        @Override
                        public int compare(Object item1, Object item2) {
                            if (item1 instanceof HomeCategoryItemGridView && item2 instanceof HomeCategoryItemGridView) {
                                HomeCategoryItemGridView view1 = (HomeCategoryItemGridView) item1;
                                HomeCategoryItemGridView view2 = (HomeCategoryItemGridView) item2;
                                return view1.getTitle().compareTo(view2.getTitle());
                            }
                            return 0;
                        }
                    });

                    phvCategoryList.refresh();
                }

                @Override
                public void onFailure(Call<ProductListModel> call, Throwable t) {
                    call.cancel();
                }
            });


        } else {
            Toast.makeText(HomeCategory.this, "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_search, menu);

        MenuItem cart_menuItem = menu.findItem(R.id.cartmenu);
        FrameLayout rootView = (FrameLayout) cart_menuItem.getActionView();
        cartItemCount = (TextView) rootView.findViewById(R.id.cart_badge);

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
                                cartItemCount.setText(cartCount.data);
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

    public void setNavMenuItemThemeColors() {
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

        navLeftMenu.setItemTextColor(navMenuTextList);
        navLeftMenu.setItemIconTintList(navMenuIconList);
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
        } else if (id == R.id.menuleft_mywallet) {
            Intent intentMyWallet = new Intent(HomeCategory.this, MyWalletActivity.class);
            startActivity(intentMyWallet);
        } else if (id == R.id.menuleft_referearn) {
            Intent intentMyReferEarn = new Intent(HomeCategory.this, RefersAndEarn_act.class);
            startActivity(intentMyReferEarn);
        } else if (id == R.id.menuleft_rateus) {
            Intent intentRateUs = new Intent(HomeCategory.this, RateUsReviews.class);
            intentRateUs.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentRateUs);
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
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        } else if (id == R.id.menuleft_policy) {
            Intent intentPolicy = new Intent(HomeCategory.this, PrivacyPolicy.class);
            startActivity(intentPolicy);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerHomeCategory);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

            Glide.with(HomeCategory.this).load(filePath).fitCenter().dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).into(ivProfilePic);

            Glide.with(HomeCategory.this).load(filePath).fitCenter().dontAnimate()
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

    public static int dpToPx(final float dp) {
        return Math.round(dp * (Resources.getSystem().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


}