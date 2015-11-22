package com.easylife.letsgo.search;

import android.app.SearchManager;
import android.os.Handler;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Message;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.widget.TextView;
import android.widget.Toast;

import com.easylife.letsgo.R;

import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private TextView m_location_city;
    private Handler m_handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        m_handler = new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    String s_loc = (String)msg.obj;
                    m_location_city.setText(s_loc);
                }
            }
        };

        Toolbar toolbar = (Toolbar) findViewById(R.id.search_bar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String query = intent.getStringExtra(SearchManager.QUERY);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SearchSuggestionsProvider.AUTHORITY, SearchSuggestionsProvider.MODE);
            suggestions.saveRecentQuery(query, null);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Toast.makeText(this, "By Suggestion", Toast.LENGTH_SHORT).show();
        }

        TextView textView = (TextView) findViewById(R.id.search_keyword);
        textView.setText(query);

        m_location_city = (TextView) findViewById(R.id.location_city);

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                getLocationByCellLocation();
            }
        }).start();

        try {
            LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            if (lm.getProvider(LocationManager.NETWORK_PROVIDER) != null)
            {
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0,
                        new CustomLocationListener());

                Toast.makeText(this, "NETWORK_PROVIDER", Toast.LENGTH_SHORT).show();
            }
            else if(lm.getProvider(LocationManager.GPS_PROVIDER) != null)
            {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0,
                        new CustomLocationListener());

                Toast.makeText(this, "GPS_PROVIDER", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "无法定位", Toast.LENGTH_SHORT).show();
            }

        } catch (SecurityException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // 更新位置信息
    private void updateLocation(Location location) {
        if (location != null) {
            m_location_city.setText("定位对象信息如下：" + location.toString() + "\n\t其中经度：" + location.getLongitude() + "\n\t其中纬度："
                    + location.getLatitude());
        } else {
            m_location_city.setText("没有获取到定位对象Location");
        }
    }

    private void onLocationStatusChanged(String provider, int status, Bundle extras)
    {
        switch (status) {
            //GPS状态为可见时
            case LocationProvider.AVAILABLE:
                Toast.makeText(this, "当前GPS状态为可见状态", Toast.LENGTH_SHORT).show();
                break;
            //GPS状态为服务区外时
            case LocationProvider.OUT_OF_SERVICE:
                Toast.makeText(this, "当前GPS状态为服务区外状态", Toast.LENGTH_SHORT).show();
                break;
            //GPS状态为暂停服务时
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Toast.makeText(this, "当前GPS状态为暂停服务状态", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void getLocationByCellLocation() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        GsmCellLocation gsmCell = (GsmCellLocation) tm.getCellLocation();
        int cid = gsmCell.getCid();
        int lac = gsmCell.getLac();
        String netOperator = tm.getNetworkOperator();
        int mcc = Integer.valueOf(netOperator.substring(0, 3));
        int mnc = Integer.valueOf(netOperator.substring(3, 5));
        JSONObject holder = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject data = new JSONObject();
        try {
            holder.put("homeMobileCountryCode", mcc);
            holder.put("homeMobileNetworkCode", mnc);
            holder.put("radioType", "gsm");
            holder.put("carrier", "HTC");
            data.put("cellId", cid);
            data.put("locationAreaCode", lac);
            data.put("mobileCountryCode", mcc);
            data.put("mobileNetworkCode", mnc);
            array.put(data);
            holder.put("cellTowers", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://www.minigps.net/minigps/map/google/location");

        try {
            StringEntity stringEntity = new StringEntity(holder.toString());
            httpPost.setEntity(stringEntity);
            HttpResponse httpResponse = client.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();

            InputStream is = httpEntity.getContent();

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            StringBuffer stringBuffer = new StringBuffer();

            String result = "";
            while ((result = reader.readLine()) != null) {
                stringBuffer.append(result);
            }

            result = stringBuffer.toString();
            if(result.isEmpty())
            {
                result = "No Location Info";
            }

            Message msg = new Message();
            msg.what = 0;
            msg.obj = result;
            m_handler.sendMessage(msg);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    final public class CustomLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            updateLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            onLocationStatusChanged(provider, status, extras);
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
