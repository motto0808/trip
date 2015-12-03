package com.easylife.letsgo.model;

import java.io.Serializable;

/**
 * Created by user on 2015/12/3.
 */
public class ApiResponse<T extends Serializable> {

    /**
     * error : 0正确 非0错误
     * data : 正确的时候返回json object
     * reason : 错误原因
     */

    private int error;
    private T data;
    private String reason;

    public void setError(int error_code) {
        this.error = error_code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError() {
        return error;
    }

    public T getData() {
        return data;
    }

    public String getReason() {
        return reason;
    }
}
