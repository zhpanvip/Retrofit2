package com.cypoem.retrofit.module.bean;


/**
 * Created by zhpan on 2018/4/14.
 */

public class UserInfoBean {
    /**
     * 是否已经登录
     */
    private boolean isLogin;

    /**
     * 用户ID
     */
    private String userId="";

    /**
     * 网络请求会话ID
     */
    private String tokenId = "";

    /**
     * 客户端系统类型
     */
    private String osType = "";

    /**
     * 客户端设备版本号
     */
    private String osVersion = "";

    /**
     * 手机型号
     */
    private String brand = "";

    /**
     * 用户在服务端的SessionID
     */
    private String sessionId = "";

    /**
     * 手机唯一标识
     */
    private String imei = "";


    public String password="";


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 是否开启夜间模式
     */
    private boolean isNightMode;

    private UserBean user=new UserBean();

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public boolean isNightMode() {
        return isNightMode;
    }

    public void setNightMode(boolean nightMode) {
        isNightMode = nightMode;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }


    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

}
