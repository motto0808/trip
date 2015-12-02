package com.easylife.letsgo.utils;


import android.util.Log;

import com.easylife.letsgo.AppContext;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Package com.easylife.letsgo.utils
 * @Description:
 * @Author Motto Yin
 * @Date 2015/12/2
 */
public class NetUtil {
    private static String Tag = "NetUtil";
    private static final String BASE_URL = "http://192.168.1.2:8080/";


    /**
     * 发送GET请求方法
     *
     * @param requestUrl 请求的URL
     * @return 响应的数据
     */
    public static String sendGetRequest(String requestUrl) {
        try {
            URL url = new URL(BASE_URL + requestUrl);
            URLConnection urlConnection = url.openConnection();

            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setRequestMethod("Get");

            if (AppContext.getInstance().getSharedPreferences() != null) {
                httpUrlConnection.addRequestProperty("COOKIE", AppContext.getInstance().getSharedPreferences().getString("DEMO_COOKIE", null));
            } else {
                Log.e(Tag, "DEMO_COOKIE  null ----:");
            }

            httpUrlConnection.connect();

            InputStream is = httpUrlConnection.getInputStream();

            return StringUtil.convertStreamToString(is);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
