package com.cypoem.retrofit.net;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by zhpan on 2017/4/1.
 */

public class IdeaApi {
    private IdeaApiService service;

    private IdeaApi() {
        Retrofit retrofit = CommonNetService.getRetrofitBuilder(Constants.API_SERVER_URL).build();
        service = retrofit.create(IdeaApiService.class);
    }

    //  创建单例
    private static class SingletonHolder {
        private static final IdeaApi INSTANCE = new IdeaApi();
    }

    public static IdeaApiService getApiService() {
        return SingletonHolder.INSTANCE.service;
    }
}
