package com.cypoem.retrofit.net;


import com.zhpan.idea.net.common.Constants;
import com.zhpan.idea.net.common.IdeaApi;

public class RetrofitHelper {
    private static IdeaApiService mIdeaApiService;

    public static IdeaApiService getApiService(){
        return mIdeaApiService;
    }
    static {
       mIdeaApiService= IdeaApi.getApiService(IdeaApiService.class, Constants.API_SERVER_URL);
    }
}
