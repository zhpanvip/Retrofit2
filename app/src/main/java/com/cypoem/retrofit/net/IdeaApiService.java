package com.cypoem.retrofit.net;

import com.cypoem.retrofit.module.reponse.LoginResponse;
import com.cypoem.retrofit.module.request.ArticleWrapper;
import com.cypoem.retrofit.module.request.LoginRequest;
import com.zhpan.idea.net.common.BasicResponse;
import com.zhpan.idea.net.download.DwonloadRequest;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

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


    /**
     * 单文件上传 方法一
     * @param partList
     * @return
     */
    @Multipart
    @POST("upload/uploadFile.do")
    Observable<BasicResponse> uploadFiles(@Part List<MultipartBody.Part> partList);

    /**
     * 单文件上传 方法二
     * @return
     */
    @Multipart
    @POST("upload/uploadFile.do")
    Observable<BasicResponse<BasicResponse>> uploadFiles(@Part("phone") RequestBody phone,@Part("password") RequestBody password,@Part MultipartBody.Part image);

    /**
     * 多文件上传 方法一
     * @param description
     * @param imgs1
     * @param imgs2
     * @return
     */
    @POST("upload/uploadFile.do")
    Observable<BasicResponse> uploadFiles(@Part("filename") String description,
                                          @Part("pic\"; filename=\"image1.png") RequestBody imgs1,
                                          @Part("pic\"; filename=\"image2.png") RequestBody imgs2);

    /**
     * 多文件上传 方法二
     * @param description
     * @param maps
     * @return
     */
    @POST("upload/uploadFile.do")
    Observable<BasicResponse> uploadFiles(@Part("filename") String description, @PartMap() Map<String, RequestBody> maps);

    @Streaming
    @GET//("download.do")
    Observable<ResponseBody> download(@Url String url);//直接使用网址下载

    @POST("file/download")
    Observable<ResponseBody> downloadFile(@Body DwonloadRequest downloadRequest);

    @FormUrlEncoded
    @POST("file/download")
    Observable<ResponseBody> downloadFile(@FieldMap Map<String, Object> map);
}
