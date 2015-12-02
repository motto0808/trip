package com.easylife.letsgo.search;

import android.app.SearchManager;
import android.os.Handler;
import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.easylife.letsgo.LocationUtil;
import com.easylife.letsgo.R;

public class SearchActivity extends AppCompatActivity implements LocationUtil.LocationListener{

    private TextView m_location_city;
    private TextView m_location_addr;

    private TextView m_search_keyword;

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
