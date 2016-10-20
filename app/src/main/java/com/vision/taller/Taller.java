package com.vision.taller;

import android.app.Application;
import android.content.SharedPreferences;

public class Taller extends Application {

    private static final String PREFS_NAME = "prefs";
    private static final String PREFS_ITEM_LOGIN = "usnm";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void saveLoginState(String username) {
        getPreferences().edit().putString(PREFS_ITEM_LOGIN, username).apply();
    }

    public void removeLoginState() {
        getPreferences().edit().remove(PREFS_ITEM_LOGIN).apply();
    }

    public String getUsername() {
        return getPreferences().getString(PREFS_ITEM_LOGIN, null);
    }

    private SharedPreferences getPreferences() {
        return getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }
}
