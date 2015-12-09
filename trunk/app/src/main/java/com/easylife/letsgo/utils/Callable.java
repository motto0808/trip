package com.easylife.letsgo.utils;

/**
 * @Package com.easylife.letsgo.utils
 * @Description:
 * @Author Motto Yin
 * @Date 2015/12/9
 */
public interface Callable<T> {
    public T call() throws Exception;
}
