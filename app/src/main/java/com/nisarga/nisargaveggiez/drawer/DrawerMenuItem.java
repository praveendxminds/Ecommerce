package com.nisarga.nisargaveggiez.drawer;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nisarga.nisargaveggiez.ContactUs;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;


import com.nisarga.nisargaveggiez.franchiseRequest;
import com.nisarga.nisargaveggiez.preferences;
import com.nisarga.nisargaveggiez.Home3.HomeThree;
import com.nisarga.nisargaveggiez.R;

import static android.Manifest.permission.CALL_PHONE;

/**
 * Created by praveen on 13/11/18.
 */

@Layout(R.layout.drawer_item)
public class DrawerMenuItem {

    public static final int DRAWER_MENU_ITEM_PREFERENCE = 1;
    public static final int DRAWER_MENU_ITEM_ABOUT = 2;
    public static final int DRAWER_MENU_ITEM_FRANCHISE = 3;
    public static final int DRAWER_MENU_ITEM_CALL = 4;
    public static final int DRAWER_MENU_ITEM_EMAIL = 5;
    public static final int DRAWER_MENU_ITEM_CONTACT = 6;
    public static final int DRAWER_MENU_ITEM_RATE = 7;
    public static final int DRAWER_MENU_ITEM_FEEDBACK = 8;
    public static final int DRAWER_MENU_ITEM_SHARE = 9;

    public int mMenuPosition;
    public Context mContext;
    public DrawerCallBack mCallBack;

    @View(R.id.itemNameTxt)
    public TextView itemNameTxt;

    @View(R.id.itemIcon)
    public ImageView itemIcon;

    @View(R.id.mainView)
    public LinearLayout mDrawer;

    public DrawerMenuItem(Context context, int menuPosition) {
        mContext = context;
        mMenuPosition = menuPosition;
    }


    @Resolve
    public void onResolved() {
        switch (mMenuPosition){
            case DRAWER_MENU_ITEM_PREFERENCE:
                itemNameTxt.setText("Preferences");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.prefs));
                break;
            case DRAWER_MENU_ITEM_ABOUT:
                itemNameTxt.setText("About Us");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.about));
                break;
            case DRAWER_MENU_ITEM_FRANCHISE:
                itemNameTxt.setText("Franchise Request");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.franchise));
                break;
            case DRAWER_MENU_ITEM_CALL:
                itemNameTxt.setText("Call Us");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.phone));
                break;
            case DRAWER_MENU_ITEM_EMAIL:
                itemNameTxt.setText("Email Us");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.mail));
                break;
            case DRAWER_MENU_ITEM_CONTACT:
                itemNameTxt.setText("Contact");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.contact));
                break;
            case DRAWER_MENU_ITEM_RATE:
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate));
                itemNameTxt.setText("Rate App");
                break;
            case DRAWER_MENU_ITEM_FEEDBACK:
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.message));
                itemNameTxt.setText("Send App Feedback");
                break;
            case DRAWER_MENU_ITEM_SHARE:
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.share));
                itemNameTxt.setText("Share App");
                break;
        }
    }

    @Click(R.id.mainView)
    public void onMenuItemClick(){
        switch (mMenuPosition){
            case DRAWER_MENU_ITEM_PREFERENCE:
                prefrences();
                setDrawerCallBack(mCallBack);
                if(mCallBack != null)mCallBack.onPreferencesMenuSelected();
                break;
            case DRAWER_MENU_ITEM_ABOUT:
                about();
                if(mCallBack != null)mCallBack.onAboutMenuSelected();
                break;
            case DRAWER_MENU_ITEM_FRANCHISE:
                franchiseRequest();
                if(mCallBack != null)mCallBack.onFranchiseMenuSelected();
                break;
            case DRAWER_MENU_ITEM_CALL:
                call();
                if(mCallBack != null)mCallBack.onCallMenuSelected();
                break;
            case DRAWER_MENU_ITEM_EMAIL:
                sendEmail();
                if(mCallBack != null)mCallBack.onEmailMenuSelected();
                break;
            case DRAWER_MENU_ITEM_CONTACT:
                contact();
                if(mCallBack != null)mCallBack.onContactMenuSelected();
                break;
            case DRAWER_MENU_ITEM_RATE:
                rateapp();
                if(mCallBack != null)mCallBack.onRateMenuSelected();
                break;
            case DRAWER_MENU_ITEM_FEEDBACK:
                feedback();
                if(mCallBack != null)mCallBack.onFeedbackMenuSelected();
                break;
            case DRAWER_MENU_ITEM_SHARE:
                share();
                if(mCallBack != null)mCallBack.onShareMenuSelected();
                break;
        }
    }

    public void setDrawerCallBack(DrawerCallBack callBack) {
        mCallBack = callBack;
    }

    public interface DrawerCallBack{
        void onPreferencesMenuSelected();
        void onAboutMenuSelected();
        void onFranchiseMenuSelected();
        void onCallMenuSelected();
        void onEmailMenuSelected();
        void onContactMenuSelected();
        void onRateMenuSelected();
        void onFeedbackMenuSelected();
        void onShareMenuSelected();
    }

    protected void call() {

        HomeThree.getInstance().closeDrawer();

        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:18001231947"));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ContextCompat.checkSelfPermission(mContext, CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            mContext.startActivity(i);
        } else {
            ActivityCompat.requestPermissions((Activity)mContext,new String[]{CALL_PHONE}, 1);
        }

    }


    protected void feedback() {

        HomeThree.getInstance().closeDrawer();

        String subject ="ss";
        String bodyText ="sss";

        String mailto = "mailto:bob@example.org" +
                "?cc=" + "alice@example.com" +
                "&subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(bodyText);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            mContext.startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            //TODO: Handle case where no email app is available
        }


    }


    protected void prefrences()
    {
        HomeThree.getInstance().closeDrawer();

        Intent myIntent = new Intent(mContext, preferences.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);
    }


    protected void franchiseRequest()
    {
        HomeThree.getInstance().closeDrawer();

        Intent myIntent = new Intent(mContext, franchiseRequest.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);
    }

    protected void about()
    {
        HomeThree.getInstance().closeDrawer();

        Intent myIntent = new Intent(mContext, ContactUs.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);
    }


    protected void contact()
    {
        HomeThree.getInstance().closeDrawer();

        Intent myIntent = new Intent(mContext, ContactUs.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);
    }



    protected void sendEmail() {

        HomeThree.getInstance().closeDrawer();

        String subject ="ss";
        String bodyText ="sss";

        String mailto = "mailto:bob@example.org" +
                "?cc=" + "alice@example.com" +
                "&subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(bodyText);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailto));
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            mContext.startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            //TODO: Handle case where no email app is available
        }


    }

    protected void share()
    {

        HomeThree.getInstance().closeDrawer();


        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out my app at: https://play.google.com/store/apps/details?id=" + mContext.getPackageName());
        sendIntent.setType("text/plain");
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(sendIntent);



    }

    protected void rateapp() {

        HomeThree.getInstance().closeDrawer();

        Uri uri = Uri.parse("market://details?id=" + mContext.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            mContext.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + mContext.getPackageName())));
        }


    }

}
