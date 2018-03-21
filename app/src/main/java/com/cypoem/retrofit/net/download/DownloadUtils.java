package com.cypoem.retrofit.net.download;

import com.cypoem.retrofit.activity.BaseActivity;
import com.cypoem.retrofit.net.CommonNetService;
import com.cypoem.retrofit.net.IdeaApiService;
import com.cypoem.retrofit.utils.LogUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

/**
 * Created by zhpan on 2018/3/21.
 */

public class DownloadUtils {
    private static final String TAG = "DownloadUtils";
    private IdeaApiService ideaApiService;
    private BaseActivity activity;
    private DownListener mDownListener;

    public DownloadUtils(BaseActivity activity) {
        this.activity=activity;
    }

    /**
     * 开始下载
     *
     * @param url
     */
    public void download(@NonNull String url,DownListener downListener) {
        mDownListener=downListener;
        OkHttpClient.Builder httpClientBuilder = CommonNetService.getOkHttpClientBuilder();
        ProgressHelper.addProgress(httpClientBuilder);
        ideaApiService = CommonNetService.getRetrofitBuilder()
                .client(httpClientBuilder.build())
                .build()
                .create(IdeaApiService.class);
        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                LogUtils.e("下载了-----"+bytesRead+"contentLength------------"+contentLength);
                int progress= (int) ((100*bytesRead)/contentLength);
                mDownListener.onProgress(progress);
            }
        });

        ideaApiService
                .download(url)
                .compose(activity.<ResponseBody>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        mDownListener.onSuccess(responseBody);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DownloadObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        mDownListener.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mDownListener.onFail(e.getMessage());
                    }
                });

    }
}
