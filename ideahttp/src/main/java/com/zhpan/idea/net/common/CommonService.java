package com.zhpan.idea.net.common;



import com.zhpan.idea.net.token.RefreshTokenResponse;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by dell on 2017/4/1.
 */

public interface CommonService {
    @GET("refresh_token")
    Observable<RefreshTokenResponse> refreshToken();
}
