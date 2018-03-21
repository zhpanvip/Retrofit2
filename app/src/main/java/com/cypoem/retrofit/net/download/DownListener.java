package com.cypoem.retrofit.net.download;

import okhttp3.ResponseBody;

/**
 * Created by zhpan on 2018/3/21.
 */

public interface DownListener {
    void onProgress(int progress);

    void onSuccess(ResponseBody responseBody);

    void onFail(String message);

    void onComplete();
}
