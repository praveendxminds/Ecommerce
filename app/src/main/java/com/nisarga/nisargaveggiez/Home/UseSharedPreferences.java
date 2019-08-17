package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.SharedPreferences;

public class UseSharedPreferences {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    public static String MyPREFERENCES = "sessiondata";
    public static  String COUNT = "fcm_id";

    public UseSharedPreferences(Context mcontext) {
       this.context = mcontext;
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public int createCountValue(int num) {
        editor.putInt(COUNT, num);
        editor.commit();
        return num;
    }

    public int getCountValue() {
        Integer number = sharedPreferences.getInt(COUNT, 0);
        number = number + 1;
        return number;
    }
}
