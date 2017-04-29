package com.cypoem.retrofit.net;

import com.cypoem.retrofit.module.wrapper.MeiziWrapper;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by dell on 2017/4/1.
 */

public interface IdeaApiService {
    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 20000;

    String HOST = "http://gank.io/";
    String API_SERVER_URL = HOST + "api/data/";

    @Headers("Cache-Control: public, max-age=86400") //  设置缓存
    @GET("福利/10/1")
    Observable<MeiziWrapper> getData();
}
