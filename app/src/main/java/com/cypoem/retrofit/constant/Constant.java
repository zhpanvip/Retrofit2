package com.cypoem.retrofit.constant;

/**
 * Created by dell on 2017/4/1.
 */

public class Constant {
    /**
     * BaseUrl
     */
    public static final String SRCB_BASE_URL="http://192.168.155.5:8080/springMvc/";
    /**
     * ActiveMQ链接地址
     */
    public static final String BROKER_URL = "tcp://192.168.155.6";

    public static final int MQTT_PORT=1883;

    /**
     * 网络请求超时时间毫秒
     */
    public static final int DEFAULT_TIMEOUT=5000;

    /**
     * 业务类型--审核
     */
    public static final int BUSINESS_AUDITING=1;

    /**
     * 业务类型--帮助
     */
    public static final int BUSINESS_APPEAL=2;

    /**
     * ActiveMQ-MQTT-Client
     */
    public static final String clientId = "android-client";

    /**
     * topic
     */
    public static final String TOPIC = "myqueue";


}
