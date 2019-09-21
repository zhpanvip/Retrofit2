package com.cypoem.retrofit.module.request;

/**
 * Created by zhpan on 2017/10/25.
 * Description:登录请求实体类
 */

public class LoginRequest extends BasicRequest {
    private String username;
    private String password;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
