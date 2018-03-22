package com.cypoem.retrofit.net.download;

import com.cypoem.retrofit.net.CommonNetService;
import com.cypoem.retrofit.net.IdeaApiService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

/**
 * Created by zhpan on 2018/3/21.
 */

public class DownloadUtils {
    private static final String TAG = "DownloadUtils";
    private DownloadListener mDownloadListener;
    private CompositeDisposable mDisposables;

    public DownloadUtils() {
        mDisposables = new CompositeDisposable();
    }

    /**
     * 开始下载
     *
     * @param url
     */
    public void download(@NonNull String url, DownloadListener downloadListener) {
        mDownloadListener = downloadListener;
        getApiService().download(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(getConsumer())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }

    /**
     * 取消下载
     */
    public void cancelDownload() {
        mDisposables.clear();
    }

    private IdeaApiService getApiService() {
        OkHttpClient.Builder httpClientBuilder = CommonNetService.getOkHttpClientBuilder();
        ProgressHelper.addProgress(httpClientBuilder);
        IdeaApiService ideaApiService = CommonNetService.getRetrofitBuilder()
                .client(httpClientBuilder.build())
                .build()
                .create(IdeaApiService.class);
        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                mDownloadListener.onProgress((int) ((100 * bytesRead) / contentLength));
            }
        });
        return ideaApiService;
    }


    private Consumer<ResponseBody> getConsumer() {
        return new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody responseBody) throws Exception {
                mDownloadListener.onSuccess(responseBody);
            }
        };
    }

    private Observer<ResponseBody> getObserver() {
        return new Observer<ResponseBody>() {

            @Override
            public void onSubscribe(Disposable d) {
                mDisposables.add(d);
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mDownloadListener.onComplete();
            }

            @Override
            public void onError(Throwable e) {
                mDownloadListener.onFail(e.getMessage());
            }

            @Override
            public void onComplete() {
                mDownloadListener.onComplete();
            }
        };
    }
}
