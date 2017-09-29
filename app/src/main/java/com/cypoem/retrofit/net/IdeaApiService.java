package com.cypoem.retrofit.net;

import com.cypoem.retrofit.module.BasicResponse;
import com.cypoem.retrofit.module.bean.MeiZi;
import com.cypoem.retrofit.module.wrapper.MeiziWrapper;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

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


    @GET("福利/10/1")
    Observable<MeiziWrapper> getData();

    @GET("福利/10/1")
    Observable<BasicResponse<List<MeiZi>>> getMezi();

    /**
     *  设置缓存 缓存时间为100s
     * @param page
     * @param number
     * @return
     */
    @Headers("Cache-Control: public, max-age=100")
    @GET("everySay/selectAll.do")
    Observable<BasicResponse<List<MeiZi>>> lookBack(@Query("page") int page, @Query("rows") int number);

}
