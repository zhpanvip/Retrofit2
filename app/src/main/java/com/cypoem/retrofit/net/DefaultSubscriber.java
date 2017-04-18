package com.cypoem.retrofit.net;

import android.text.TextUtils;
import android.util.Log;
import com.cypoem.retrofit.module.BasicResponse;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by zhpan on 2017/4/18.
 */

public abstract class DefaultSubscriber<T extends BasicResponse> implements Subscriber<T> {
    private static Gson sGson = new Gson();


    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T response) {
        onOk(response);
            switch (response.getStatus()) {
                case STATUS_OK:
                    onOk(response);
                    break;
                case STATUS_FAIL:
                    onFail(response);
                    break;
                case STATUS_ERROR:
                default:
            }
    }

    @Override
    public void onError(Throwable e) {
        Log.e("Retrofit", e.getMessage());
        if (e != null) {
            String className = e.getClass().getCanonicalName();
            if (e instanceof HttpException
                    || className.startsWith("java.net")
                    || className.startsWith("javax.net")) {
                onNetworkFail(DefaultSubscriber.NetworkFailReason.BAD_NETWORK);
                return;
            }
        }
        onNetworkFail(DefaultSubscriber.NetworkFailReason.PARSE_ERROR);
    }

    @Override
    public void onComplete() {

    }

    /**
     * 且请求状态被置为成功(resultCode == 1)
     */
    abstract public void onOk(T response);

    /**
     * 但请求状态被置为不成功(resultCode != 1)
     */
    public void onFail(T response) {
        String message = response.getMessage();

        if (TextUtils.isEmpty(message)) {
            message = response.errMsg;
        }
        if (TextUtils.isEmpty(message)) {
            // MyToast.showText(R.string.response_return_error);
        } else {
            //  MyToast.showText(message);
        }
    }

    /**
     * 由于网络问题引起的失败
     */
    public void onNetworkFail(DefaultSubscriber.NetworkFailReason reason) {
        switch (reason) {
            case PARSE_ERROR:
                //  MyToast.showText(R.string.worklight_response_parse_error);
                break;
            case BAD_NETWORK:
            default:
                //  MyToast.showText(R.string.worklight_response_fail);
                break;
        }
    }

    // endregion

    public enum NetworkFailReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
    }
}
