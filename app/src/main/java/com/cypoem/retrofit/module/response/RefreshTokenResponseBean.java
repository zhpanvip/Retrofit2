package com.cypoem.retrofit.module.response;


import com.zhpan.idea.net.module.BasicResponse;

/**
 * Created by zhpan on 2017/10/25.
 * Description:
 */

public class RefreshTokenResponseBean extends BasicResponse {
    private String token;
    private String secret;
    private long expired;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpired() {
        return expired;
    }

    public void setExpired(long expired) {
        this.expired = expired;
    }
}
