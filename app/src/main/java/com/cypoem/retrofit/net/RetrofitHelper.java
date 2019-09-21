package com.cypoem.retrofit.net;


import com.cypoem.retrofit.module.bean.UserInfoTools;
import com.zhpan.idea.net.common.Constants;
import com.zhpan.idea.net.common.IdeaApiProxy;
import com.zhpan.idea.net.token.IGlobalManager;
import com.zhpan.idea.net.token.RefreshTokenResponse;
import com.zhpan.idea.utils.Utils;


/**
 * Created by zhpan on 2018/3/22.
 */

public class RetrofitHelper {

    private static IdeaApiService mIdeaApiService;

    public static IdeaApiService getApiService() {
        if (mIdeaApiService == null)
            mIdeaApiService = new IdeaApiProxy().getApiService(IdeaApiService.class,
                    ServerConfig.BASE_URL, new IGlobalManager() {
                        @Override
                        public void logout() {
                          UserInfoTools.logout(Utils.getContext());
                        }

                        @Override
                        public void tokenRefresh(RefreshTokenResponse response) {
                          UserInfoTools.updateToken(Utils.getContext(),response);
                        }
                    });
        return mIdeaApiService;
    }
}
