package com.easylife.letsgo.destination;

/**
 * @Author Motto Yin
 * @Package com.easylife.letsgo.search
 * @Description:
 * @Date 2015/11/22
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.easylife.letsgo.R;

public class DestinationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        InitToolbar();

    }

    private void InitToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.destination_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView = (TextView) toolbar.findViewById(R.id.destination_bar_title);

        Intent intent = getIntent();
        textView.setText(intent.getStringExtra(Intent.EXTRA_TITLE));
    }
}
