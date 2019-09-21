package com.zhpan.idea.net.module;


import com.zhpan.idea.utils.SharedPreferencesHelper;
import com.zhpan.idea.utils.Utils;

/**
 * Created by jokerlee on 16/9/28.
 */
public class BaseRequest {
    public String token;


    public BaseRequest() {
        token= (String) SharedPreferencesHelper.get(Utils.getContext(),"token","");
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void updateToken(String token) {
        this.token = token;
    }
}
