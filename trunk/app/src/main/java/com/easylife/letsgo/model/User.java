package com.easylife.letsgo.model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * @Package com.easylife.letsgo.model
 * @Description:
 * @Author Motto Yin
 * @Date 2015/12/2
 */
public class User implements Serializable {
    /**
     * id : 0
     * username : username
     * password : password
     */

    private int id;
    private String username;
    private String password;

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}