package com.easylife.letsgo.ui.activity;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * @Package com.easylife.letsgo.ui.activity
 * @Description:
 * @Author Motto Yin
 * @Date 2015/12/2
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);// 使得音量键控制媒体声音

    }
}
