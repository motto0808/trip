package com.easylife.letsgo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @Package com.easylife.letsgo
 * @Description:
 * @Author Motto Yin
 * @Date 2015/12/2
 */
public class AppContext {

    private static AppContext mInstance;
    private Context mContext;
    private SharedPreferences mPreferences;

    public static void init(Context context) {
        mInstance = new AppContext(context);
    }

    public static AppContext getInstance() {
        return mInstance;
    }

    private AppContext(Context context) {
        mContext = context;

        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SharedPreferences getSharedPreferences() {
        return mPreferences;
    }
}
