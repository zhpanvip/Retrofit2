package com.cypoem.retrofit.module.bean;

import android.content.Context;

import com.cypoem.retrofit.module.response.LoginResponse;
import com.zhpan.idea.net.token.RefreshTokenResponse;
import com.zhpan.idea.utils.SharedPreferencesHelper;


/**
 * Created by zhpan on 2018/4/14.
 */
public class UserInfoTools {
    private static UserInfoBean sUserInfoBean;

    public static void init(Context context) {
        sUserInfoBean = getUserInfoBean(context);
        if (sUserInfoBean == null) {
            sUserInfoBean = new UserInfoBean();

            sUserInfoBean.setTokenId("");
        }
        setUserInfoBean(context, sUserInfoBean);
    }

    private static UserInfoBean getUserInfoBean(Context context) {
        if (sUserInfoBean == null) {
            sUserInfoBean = SharedPreferencesHelper.getObject(context.getApplicationContext(), UserInfoBean.class);
        }
        if (sUserInfoBean == null) {
            sUserInfoBean = new UserInfoBean();
        }
        return sUserInfoBean;
    }

    /**
     * Save user info to SharedPreferences
     */
    private static void setUserInfoBean(Context context, UserInfoBean userInfoBean) {
        sUserInfoBean = userInfoBean;
        SharedPreferencesHelper.saveObject(context, sUserInfoBean);
    }

    /**
     * login success，save user information to SharedPreferences and post
     * an event to update UI
     */
    public static void login(Context context, LoginResponse response) {
        sUserInfoBean.setLogin(true);
        sUserInfoBean.setUser(response.getUser());
        setUserInfoBean(context, sUserInfoBean);
    }

    /**
     * Logout and clear data，post an Event to update UI
     */
    public static void logout(Context context){
        SharedPreferencesHelper.remove(context,sUserInfoBean.getClass().getSimpleName());
        sUserInfoBean=null;
        init(context);
    }

    /**
     * update token
     * @param response token response
     */
    public static void updateToken(Context context,RefreshTokenResponse response){
        SharedPreferencesHelper.put(context,"token", response.getToken());
    }
}
