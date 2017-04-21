package com.cypoem.retrofit.net;

import com.cypoem.retrofit.module.DataWrapper;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dell on 2017/4/1.
 */

public interface SrcbApiService {

    //对应HTTP的状态码
     int UNAUTHORIZED = 401;
     int FORBIDDEN = 403;
     int NOT_FOUND = 404;
     int REQUEST_TIMEOUT = 408;
     int INTERNAL_SERVER_ERROR = 500;
     int BAD_GATEWAY = 502;
     int SERVICE_UNAVAILABLE = 503;
     int GATEWAY_TIMEOUT = 504;
    /**
     * 网络请求超时时间毫秒
     */
    public static final int DEFAULT_TIMEOUT=5000;

    //http://192.168.155.5:8080/springMvc/student.do?method=json
    String HOST = "http://192.168.155.5:8080/";
    String API_SERVER_URL = HOST + "springMvc/";
    String URL_COMMENT_LIST = "student.do";


    @GET(URL_COMMENT_LIST)
    Observable<DataWrapper> getData(@Query("method") String method);


}
