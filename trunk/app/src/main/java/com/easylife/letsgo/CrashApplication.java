package com.easylife.letsgo;

/**
 * Created by xtgsy on 2015/11/12.
 */
import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class CrashApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());


        Fresco.initialize(this);

        RongIM.init(this);
    }
}