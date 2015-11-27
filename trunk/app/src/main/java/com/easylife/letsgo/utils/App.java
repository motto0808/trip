package com.easylife.letsgo.utils;

import android.app.Application;

import io.rong.imkit.RongIM;

/**
 * Created by yangwl-a on 2015/11/27.
 */
public class App extends Application {
    @Override
    public void onCreate(){
        super.onCreate();

        /**
         * 注意：
         *
         * IMKit SDK调用第一步 初始化
         *
         * context上下文
         *
         * 只有两个进程需要初始化，主进程和 push 进程
         */
        //if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
               // "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

        RongIM.init(this);
    }

}
