package com.cypoem.retrofit.module.request;

import android.app.Activity;
import android.content.Context;
import com.zhpan.idea.utils.KeyTools;


/**
 * Created by zhpan on 2017/10/25.
 * Description:登录请求实体类
 */

public class LoginRequest extends BasicRequest {
    private String userId;
    private String password;
    private String appKey;

    public LoginRequest(Activity activity) {
        appKey = generateAppKey(activity);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * imei + timestamp md5 得到app key
     *
     * @param context
     * @return
     */
    public static String generateAppKey(Context context) {
        String deviceId = "12345";
        String timeStamp = System.currentTimeMillis() + "";
        return KeyTools.getMD5(deviceId+timeStamp);
    }
}
