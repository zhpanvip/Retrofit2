package com.cypoem.retrofit.net;

import com.cypoem.retrofit.module.response.LoginResponse;
import com.cypoem.retrofit.module.request.ArticleWrapper;
import com.cypoem.retrofit.module.request.LoginRequest;
import com.cypoem.retrofit.module.response.LoginResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by dell on 2017/4/1.
 */

public interface IdeaApiService {

    @GET("article/list/0/json")
    Observable<ArticleWrapper> getArticle();

    /**
     * 登录 appId secret
     * 使用实体类作为参数
     * @return
     */
    @POST("user/login")
    Observable<LoginResponse> login(@Body LoginRequest request);

    /**
     * 使用map作为参数
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginResponse> login(@FieldMap Map<String, Object> map);
}
