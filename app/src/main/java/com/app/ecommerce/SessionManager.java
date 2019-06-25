package com.app.ecommerce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.app.ecommerce.ProfileSection.Login_act;

public class SessionManager {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "SessionData";

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_MOBILE_NO = "MobileNo";

    public static final String KEY_USER_ID = "UserId";

    public static final String KEY_NAME = "Name";

    public static final String KEY_EMAIL_ID = "Email";

    public static final String KEY_TOKEN_ID = "fcm_id";

    public static final String KEY_TOKEN_SAVED = "fcm_saved";

    public static final String KEY_SPLASH = "firstrun";

    public static final String KEY_Cart_COUNT = "cartcount";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void cartcount(Integer cnt)
    {
        editor.putInt(KEY_Cart_COUNT,cnt);
        editor.commit();
    }

    public Integer getCartCount() {
        return pref.getInt(KEY_Cart_COUNT, 0);
    }

    public boolean isFirstrun()
    {
        return pref.getBoolean(KEY_SPLASH, true);
    }


    public void splashFirstrun()
    {
        editor.putBoolean(KEY_SPLASH, false);
        editor.commit();
    }


    public void createTokenSession(String tkn)
    {
        editor.putString(KEY_TOKEN_ID, tkn);
        editor.commit();
    }

    public String getKeyTokenId() {
        return pref.getString(KEY_TOKEN_ID, null);
    }

    public void createTokenStatus()
    {
        editor.putBoolean(KEY_TOKEN_SAVED, true);
        editor.commit();
    }

    public boolean getTokenStatus() {
        return pref.getBoolean(KEY_TOKEN_SAVED, false);
    }


    public void createLoginSession(String mob_no, String userId, String name, String email) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_MOBILE_NO, mob_no);
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL_ID, email);
        editor.commit();
    }

    public String getKeyUserId() {
        return pref.getString(KEY_USER_ID, null);
    }

    public String getName() {
        return pref.getString(KEY_NAME, null);
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL_ID, null);
    }

    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent i = new Intent(_context, Login_act.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, Login_act.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }


}
