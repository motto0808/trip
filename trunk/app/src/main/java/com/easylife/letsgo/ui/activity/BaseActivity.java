package com.easylife.letsgo.ui.activity;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.easylife.letsgo.utils.Callable;
import com.easylife.letsgo.utils.Callback;
import com.easylife.letsgo.utils.Task;

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

    /**
     * 封装的asynctask方法，此方法没有进度框.
     *
     * @param mCallable     运行于异步线程,第二执行此方法.
     * @param mCallback     运行于主线程,最后执行此方法.
     */
    public <T> void doAsync(final Callable<T> mCallable, final Callback<T> mCallback) {
        Task.doAsync(mCallable, mCallback);
    }

}
