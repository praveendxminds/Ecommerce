package com.app.ecommerce;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecommerce.drawer.DrawerMenuItem;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@NonReusable
@Layout(R.layout.menu_item_list)
public class NavMenuItems {

    public static final int DRAWER_MENU_ITEM_HOME = 1;
    public static final int DRAWER_MENU_ITEM_MY_ORDER = 2;
    public static final int DRAWER_MENU_ITEM_MY_ADDRESS = 3;
    public static final int DRAWER_MENU_ITEM_MY_WALLET = 4;
    public static final int DRAWER_MENU_ITEM_OFFERS = 5;
    public static final int DRAWER_MENU_ITEM_REFER_EARN = 6;
    public static final int DRAWER_MENU_ITEM_RATE_US = 7;
    public static final int DRAWER_MENU_ITEM_ABT_CONTACT = 8;
    public static final int DRAWER_MENU_ITEM_FAQ = 9;
    public static final int DRAWER_MENU_ITEM_TERMS = 10;
    public static final int DRAWER_MENU_ITEM_GFEEDBACK = 11;
    public static final int DRAWER_MENU_ITEM_POLICY = 12;

    public Context contxt;
    public int mMenuPosition;
    public NavMenuItems.DrawerCallBack mCallBack;

    @View(R.id.icon)
    public ImageView icon;

    @View(R.id.title)
    public TextView title;


    public NavMenuItems(Context mcontext, int menuPosition) {
        contxt = mcontext;
        mMenuPosition = menuPosition;
    }

    @Resolve
    public void onResolved() {

        switch (mMenuPosition) {
            case DRAWER_MENU_ITEM_HOME:
                title.setText("Home");
                icon.setImageDrawable(contxt.getResources().getDrawable(R.drawable.home));
                break;

            case DRAWER_MENU_ITEM_MY_ORDER:
                title.setText("My Order");
                icon.setImageDrawable(contxt.getResources().getDrawable(R.drawable.home));
                break;

            case DRAWER_MENU_ITEM_MY_ADDRESS:
                title.setText("My Address");
                icon.setImageDrawable(contxt.getResources().getDrawable(R.drawable.home));
break;

            case DRAWER_MENU_ITEM_MY_WALLET:
                title.setText("My Wallet");
                icon.setImageDrawable(contxt.getResources().getDrawable(R.drawable.home));
                break;

            case DRAWER_MENU_ITEM_OFFERS:
                title.setText("Offers");
                icon.setImageDrawable(contxt.getResources().getDrawable(R.drawable.home));
                break;

            case DRAWER_MENU_ITEM_REFER_EARN:
                title.setText("Refer and Earn");
                icon.setImageDrawable(contxt.getResources().getDrawable(R.drawable.home));
                break;

            case DRAWER_MENU_ITEM_RATE_US:
                title.setText("Rate Us");
                icon.setImageDrawable(contxt.getResources().getDrawable(R.drawable.home));
                break;

            case DRAWER_MENU_ITEM_ABT_CONTACT:
                title.setText("About & Contact Us ");
                icon.setImageDrawable(contxt.getResources().getDrawable(R.drawable.home));
                break;

            case DRAWER_MENU_ITEM_FAQ:
                title.setText("FAQs");
                icon.setImageDrawable(contxt.getResources().getDrawable(R.drawable.home));
                break;

            case DRAWER_MENU_ITEM_TERMS:
                title.setText("Terms & Conditions");
                icon.setImageDrawable(contxt.getResources().getDrawable(R.drawable.home));
                break;

            case DRAWER_MENU_ITEM_GFEEDBACK:
                title.setText("Google feedback");
                icon.setImageDrawable(contxt.getResources().getDrawable(R.drawable.home));
                break;

            case DRAWER_MENU_ITEM_POLICY:
                title.setText("Policy");
                icon.setImageDrawable(contxt.getResources().getDrawable(R.drawable.home));
                break;


        }
    }

    @Click(R.id.mainView)
    public void onMenuItemClick() {
        switch (mMenuPosition) {
            case DRAWER_MENU_ITEM_HOME:
                Toast.makeText(contxt, "Home", Toast.LENGTH_SHORT).show();
                if (mCallBack != null) mCallBack.onHomeMenuSelected();
                break;

            case DRAWER_MENU_ITEM_MY_ORDER:
                Toast.makeText(contxt, "My Order", Toast.LENGTH_SHORT).show();
                if (mCallBack != null) mCallBack.onMyOrderMenuSelected();
                break;

            case DRAWER_MENU_ITEM_MY_ADDRESS:
                Toast.makeText(contxt, "Address", Toast.LENGTH_SHORT).show();
                if (mCallBack != null) mCallBack.onMyAddressMenuSelected();
                break;

            case DRAWER_MENU_ITEM_MY_WALLET:
                Toast.makeText(contxt, "My Wallet", Toast.LENGTH_SHORT).show();
                if (mCallBack != null) mCallBack.onMyWalletMenuSelected();
                break;

            case DRAWER_MENU_ITEM_OFFERS:
                Toast.makeText(contxt, "Offers", Toast.LENGTH_SHORT).show();
                if (mCallBack != null) mCallBack.onOffersMenuSelected();
                break;

            case DRAWER_MENU_ITEM_REFER_EARN:
                Toast.makeText(contxt, "Refer & Earn", Toast.LENGTH_SHORT).show();
                if (mCallBack != null) mCallBack.onReferEarnMenuSelected();
                break;

            case DRAWER_MENU_ITEM_RATE_US:
                Toast.makeText(contxt, "Rate Us", Toast.LENGTH_SHORT).show();
                if (mCallBack != null) mCallBack.onRateUsMenuSelected();
                break;

            case DRAWER_MENU_ITEM_ABT_CONTACT:
                Toast.makeText(contxt, "About & Contact Us", Toast.LENGTH_SHORT).show();
                if (mCallBack != null) mCallBack.onAboutContactMenuSelected();
                break;

            case DRAWER_MENU_ITEM_FAQ:
                Toast.makeText(contxt, "FAQs", Toast.LENGTH_SHORT).show();
                if (mCallBack != null) mCallBack.onFaqsMenuSelected();
                break;

            case DRAWER_MENU_ITEM_TERMS:
                Toast.makeText(contxt, "Terms & Conditions", Toast.LENGTH_SHORT).show();
                if (mCallBack != null) mCallBack.onTermsConditionsMenuSelected();
                break;

            case DRAWER_MENU_ITEM_GFEEDBACK:
                Toast.makeText(contxt, "Google feedback", Toast.LENGTH_SHORT).show();
                if (mCallBack != null) mCallBack.onGoogleFeedbackMenuSelected();
                break;

            case DRAWER_MENU_ITEM_POLICY:
                Toast.makeText(contxt, "Policy", Toast.LENGTH_SHORT).show();
                if (mCallBack != null) mCallBack.onPolicyMenuSelected();
                break;

        }
    }

    public void setDrawerCallBack(NavMenuItems.DrawerCallBack callBack) {
        mCallBack = callBack;
    }
    public interface DrawerCallBack{
        void onHomeMenuSelected();
        void onMyOrderMenuSelected();
        void onMyAddressMenuSelected();
        void onMyWalletMenuSelected();
        void onOffersMenuSelected();
        void onReferEarnMenuSelected();
        void onRateUsMenuSelected();
        void onAboutContactMenuSelected();
        void onFaqsMenuSelected();
        void onTermsConditionsMenuSelected();
        void onGoogleFeedbackMenuSelected();
        void onPolicyMenuSelected();
    }
}
