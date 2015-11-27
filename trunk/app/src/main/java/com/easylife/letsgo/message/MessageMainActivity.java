package com.easylife.letsgo.message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.easylife.letsgo.R;

public class MessageMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_main);
        //继承的是ActionBarActivity，直接调用 自带的 Actionbar，
        // 下面是Actionbar 的配置，如果不用可忽略…
        getSupportActionBar().setTitle("聊天");
        //getSupportActionBar().setLogo(R.drawable.de_bar_logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setHomeAsUpIndicator(R.drawable.de_actionbar_back);
    }
}
