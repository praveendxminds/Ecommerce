package com.app.ecommerce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.app.ecommerce.ProfileSection.LoginSignup_act;

public class SessionManager {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "SessionData";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_CUST_ID = "customer_id";
    private static final String KEY_DATE_ADDED = "date_added";
    private static final String KEY_TOKEN_LOGIN = "api_token";
    private static final String KEY_CUST_GRP_ID = "customer_group_id";
    private static final String KEY_CART = "cart";
    private static final String KEY_WISHLIST = "wishlist";
    private static final String KEY_ADDRESS_ID = "address_id";
    public static final String KEY_EMAIL_ID = "email";
    public static final String KEY_LNAME = "lastname";
    public static final String KEY_FNAME = "firstname";
    public static final String KEY_TOKEN_ID = "fcm_id";
    public static final String KEY_TOKEN_SAVED = "fcm_saved";
    public static final String KEY_Cart_COUNT = "cartcount";
    public static final String KEY_Cancel = "cancel";
    public static final String KEY_Status = "status";
    public static final String KEY_PrdId = "product_id";


    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void cartcount(Integer cnt) {
        editor.putInt(KEY_Cart_COUNT, cnt);
        editor.commit();
    }

    public Integer getCartCount() {
        return pref.getInt(KEY_Cart_COUNT, 0);
    }

    public void createTokenSession(String tkn) {
        editor.putString(KEY_TOKEN_ID, tkn);
        editor.commit();
    }
    public String getKeyTokenId() {
        return pref.getString(KEY_TOKEN_ID, null);
    }
    public void storeCancelId(String cancel_id)
    {
        editor.putString(KEY_Cancel,cancel_id);
        editor.commit();
    }
    public String getCancelId()
    {
        return pref.getString(KEY_Cancel,null);
    }
    public void storeStatusOrder(String status)
    {
        editor.putString(KEY_Status,status);
        editor.commit();
    }
    public String getStatusOrder()
    {
        return pref.getString(KEY_Status,null);
    }
    public void storePrdId(String status)
    {
        editor.putString(KEY_PrdId,status);
        editor.commit();
    }
    public String getPrdId()
    {
        return pref.getString(KEY_PrdId,null);
    }



    public void createTokenStatus() {
        editor.putBoolean(KEY_TOKEN_SAVED, true);
        editor.commit();
    }

    public boolean getTokenStatus() {
        return pref.getBoolean(KEY_TOKEN_SAVED, false);
    }


    public void createLoginSession(String customer_id, String customer_group_id, String firstname, String lastname,
                                   String email, String cart, String wishlist, String address_id,
                                   String date_added, String api_token) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_CUST_ID, customer_id);
        editor.putString(KEY_CUST_GRP_ID, customer_group_id);
        editor.putString(KEY_FNAME, firstname);
        editor.putString(KEY_LNAME, lastname);
        editor.putString(KEY_CART, cart);
        editor.putString(KEY_WISHLIST, wishlist);
        editor.putString(KEY_EMAIL_ID, email);
        editor.putString(KEY_ADDRESS_ID, address_id);
        editor.putString(KEY_DATE_ADDED, date_added);
        editor.putString(KEY_TOKEN_LOGIN, api_token);
        editor.commit();
    }

    public String getCustomerId() {
        return pref.getString(KEY_CUST_ID, null);
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN_LOGIN, null);
    }

    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent i = new Intent(_context, LoginSignup_act.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, LoginSignup_act.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }



    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

}
