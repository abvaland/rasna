package com.spiderdevelopers.rasna.extras;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by frenzin05 on 6/20/2017.
 */

public class PreferenceManager {

    private static PreferenceManager sInstance;
    private static SharedPreferences sPref;
    private static SharedPreferences.Editor sEditor;

    private PreferenceManager(Context context)
    {
        sPref = context.getSharedPreferences("com.spiderdevelopers.rasna", Context.MODE_PRIVATE);
        sEditor = sPref.edit();
    }
    public static PreferenceManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferenceManager(context);
        }
        return sInstance;
    }


    public boolean getBoolean(String key) {
        return sPref.getBoolean(key, false);
    }

    public void setBoolean(String key, boolean value) {
        sEditor.putBoolean(key, value).commit();
    }

    public int getInt(String key) {
        return sPref.getInt(key, 0);
    }

    public long getLong(String key) {
        return sPref.getLong(key, 0);
    }

    public void setInt(String key, int value) {
        sEditor.putInt(key, value).commit();
    }

    public void setLong(String key, long value) {
        sEditor.putLong(key, value).commit();

    }
    public boolean contains(String key)
    {
        return sPref.contains(key);
    }

    public void setString(String key, String value) {
        sEditor.putString(key, value).commit();
    }


    public void remove(String key) {
        sEditor.remove(key).commit();
    }

    public String getString(String key) {
        return sPref.getString(key, "");
    }

    public boolean isLoggedIn()
    {
        return sPref.contains(Constatnts.LOGIN_ID);
    }

    public void clearData() {
        String token=sPref.getString(Constatnts.FCMToken,"");
        sEditor.clear().commit();
        setString(Constatnts.FCMToken,token);
    }
}
