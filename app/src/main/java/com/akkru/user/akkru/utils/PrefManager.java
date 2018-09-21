package com.akkru.user.akkru.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jati on 25/05/18
 */

public class PrefManager {

    public static final String PREF_INTRO = "intro";
    public static final String PREF_USERNAME = "email";
    public static final String PREF_TOKEN = "jwt";
    public static final String PREF_LOGIN = "login";


    private SharedPreferences sharedPreferences;

    public PrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setBoolean(String key, Boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public void setString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
}
