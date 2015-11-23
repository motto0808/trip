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
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.TextOptions;
import com.easylife.letsgo.LocationUtil;
import com.easylife.letsgo.R;

import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class SearchActivity extends AppCompatActivity implements LocationUtil.LocationListener{

    private TextView m_location_city;
    private TextView m_location_addr;

    private TextView m_search_keyword;
    private Handler m_handler;

    private LocationUtil location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar)findViewById(R.id.search_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        m_search_keyword = (TextView) findViewById(R.id.search_keyword);
        m_location_city = (TextView) findViewById(R.id.location_city);
        m_location_addr = (TextView) findViewById(R.id.location_addr);

        Intent intent = getIntent();
        if(intent.getAction().equals(Intent.ACTION_SEARCH) || intent.getAction().equals(Intent.ACTION_VIEW)){
            String query = intent.getStringExtra(SearchManager.QUERY);
            m_search_keyword.setText(query);
        }
        else{
            Snackbar.make(null, "Invalid way to start Search Activity!", Snackbar.LENGTH_SHORT);
        }

        location = new LocationUtil(this);
        location.SetListener(this);

    }

    @Override
    protected void onResume(){
        super.onResume();
        location.startMonitor();
    }
    @Override
    protected void onPause(){
        super.onPause();
        location.stopMonitor();
    }

    @Override
    public void onReceiveLocation(String city, String address) {
        m_location_city.setText(city);
        m_location_addr.setText(address);
    }
}
