package com.easylife.letsgo;

/**
 * Created by xtgsy on 2015/11/12.
 */
import android.app.Application;

public class CrashApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}