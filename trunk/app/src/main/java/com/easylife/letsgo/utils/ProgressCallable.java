package com.easylife.letsgo.utils;

/**
 * @Package com.easylife.letsgo.utils
 * @Description:
 * @Author Motto Yin
 * @Date 2015/12/9
 */
public interface ProgressCallable<T> {
    /**
     * 注册观察者对象
     *
     * @param pProgressListener
     * @return
     * @throws Exception
     */
    T call(final IProgressListener pProgressListener) throws Exception;
}
