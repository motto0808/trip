package com.easylife.letsgo.utils;


import android.util.Log;

import com.easylife.letsgo.AppContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
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
    //改成外网地址或者同一个局域网内的地址
    private static final String BASE_URL = "http://192.168.1.2:8080/";
    private static final CookieManager cookieManager = new CookieManager();

    /**
     * 发送GET请求方法
     *
     * @param requestUrl 请求的URL
     * @return 响应的数据
     */
    public static String sendGetRequest(String request) {
        try {
            URL url = new URL(BASE_URL + request);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.connect();
            InputStream is = httpUrlConnection.getInputStream();
            return StringUtil.convertStreamToString(is);
        } catch(IOException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendPostRequest(String request)
    {
        try {
            URL url = new URL(BASE_URL + request);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.connect();
            InputStream is = httpUrlConnection.getInputStream();
            return StringUtil.convertStreamToString(is);
        } catch(IOException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
