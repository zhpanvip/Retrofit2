package com.cypoem.retrofit.module.request;


import com.zhpan.idea.utils.KeyTools;
import com.zhpan.idea.utils.SharedPreferencesHelper;
import com.zhpan.idea.utils.Utils;

import java.security.SignatureException;


/**
 * Created by zhpan on 2017/10/25.
 * Description:
 */

public class RefreshTokenRequest extends BasicRequest {
    private String userId;
    private String refresh_token;
    private String signed;

    public RefreshTokenRequest() {
        this.userId = (String) SharedPreferencesHelper.get(Utils.getContext(), "userId", "");
        this.refresh_token = (String) SharedPreferencesHelper.get(Utils.getContext(), "refresh_token", "");
        long timestamp = System.currentTimeMillis();
        String refresh_secret = (String) SharedPreferencesHelper.get(Utils.getContext(), "refresh_secret", "");
        try {
            signed = KeyTools.getHmacSHA1(refresh_token + timestamp, refresh_secret);
        } catch (SignatureException e) {
            e.printStackTrace();
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getSigned() {
        return signed;
    }

    public void setSigned(String signed) {
        this.signed = signed;
    }
}
