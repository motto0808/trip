package com.easylife.letsgo.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * @Package com.easylife.letsgo
 * @Description:
 * @Author Motto Yin
 * @Date 2015/11/22
 */
public class LocationUtil {
    public interface LocationListener{
        void onReceiveLocation(String city, String address);
    }

    private final static boolean DEBUG = true;
    private final static String TAG = "LocationUtil";

    private LocationListener m_listener;
    private BDLocation mLocation = null;
    private MLocation  mBaseLocation = new MLocation();


    public BDLocationListener myListener = new MyLocationListener();
    private LocationClient mLocationClient;

    public LocationUtil(Context context) {
        mLocationClient = new LocationClient(context.getApplicationContext());
        initParams();
        mLocationClient.registerLocationListener(myListener);
    }

    public void SetListener(@Nullable LocationListener listener){
        m_listener = listener;
    }

    public void startMonitor() {
        if (DEBUG) Log.d(TAG, "start monitor location");
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.requestLocation();
        } else {
            Log.d("LocSDK3", "locClient is null or not started");
        }
    }

    public void stopMonitor() {
        if (DEBUG) Log.d(TAG, "stop monitor location");
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

    public BDLocation getLocation() {
        if (DEBUG) Log.d(TAG, "get location");
        return mLocation;
    }

    public MLocation getBaseLocation() {
        if (DEBUG) Log.d(TAG, "get location");
        return mBaseLocation;
    }

    private void initParams() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setPriority(LocationClientOption.NetWorkFirst);
        option.setAddrType("all");//返回的定位结果包含地址信息
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
        option.disableCache(true);//禁止启用缓存定位
        //option.setPoiNumber(5);    //最多返回POI个数
        //option.setPoiDistance(1000); //poi查询距离
        //option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息
        mLocationClient.setLocOption(option);
    }


    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return ;
            }
            mLocation = location;
            mBaseLocation.latitude = mLocation.getLatitude();
            mBaseLocation.longitude = mLocation.getLongitude();

            int ret = location.getLocType();
            if(ret == BDLocation.TypeGpsLocation || ret == BDLocation.TypeOffLineLocation || ret == BDLocation.TypeNetWorkLocation)
            {
                if(m_listener != null)
                {
                    m_listener.onReceiveLocation(location.getCity(), location.getAddrStr());
                }
            }
            else
            {
                Log.d(TAG, "Location failed with error " + location.getLocType());
            }
        }
    }

    public class MLocation {
        public double latitude;
        public double longitude;
    }
}