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

    /**
     * 网络请求超时时间毫秒
     */
    public static final int DEFAULT_TIMEOUT=5000;

    //http://192.168.155.5:8080/springMvc/student.do?method=json
    String HOST = "http://192.168.155.5:8080/";
    String API_SERVER_URL = HOST + "springMvc/";
    String URL_COMMENT_LIST = "student.do";


    @GET(URL_COMMENT_LIST)
    Flowable<DataWrapper> getData(@Query("method") String method);


}
