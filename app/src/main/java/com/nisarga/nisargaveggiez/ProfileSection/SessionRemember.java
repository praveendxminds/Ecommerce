package com.nisarga.nisargaveggiez.ProfileSection;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionRemember {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "SessionRemember";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_EMAIL_ID = "email";
    public static final String KEY_REMEMBER = "remember";

    public SessionRemember(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createRemember(String email, String sRemember) {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL_ID, email);
        editor.putString(KEY_REMEMBER, sRemember);
        editor.commit();
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL_ID, null);
    }

    public String getRemember() {
        return pref.getString(KEY_REMEMBER, null);
    }
}
