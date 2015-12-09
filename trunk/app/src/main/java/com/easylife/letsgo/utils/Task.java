package com.easylife.letsgo.utils;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @Package com.easylife.letsgo.utils
 * @Description:
 * @Author Motto Yin
 * @Date 2015/12/9
 */
public class Task {

    /**
     * 封装的asynctask方法，此方法没有进度框.
     *
     * @param mCallable 运行于异步线程,第二执行此方法.
     * @param mCallback 运行于主线程,最后执行此方法.
     */
    public static <T> void doAsync(final Callable<T> pCallable, final Callback<T> pCallback) {

        new AsyncTask<Void, Void, T>() {
            /**
             * 工作线程，这个方法运行在异步线程中
             */
            @Override
            protected T doInBackground(Void... params) {

                try {
                    return pCallable.call();
                } catch (Exception e) {
                    Log.e("error", e.toString());
                }
                return null;
            }

            /**
             * 工作线程执行完后回调，运行于主线程
             * */
            protected void onPostExecute(T result) {
                if(pCallable != null)
                {
                    pCallback.onCallback(result);
                }
            }
        }.execute((Void[]) null);
    }

}
