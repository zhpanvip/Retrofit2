package com.cypoem.retrofit.net;

import android.text.TextUtils;

import com.cypoem.retrofit.module.BasicResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by zhpan on 2017/4/18.
 */
abstract public class DefaultObserver<T extends BasicResponse> implements Observer<T> {

    private static Gson sGson = new Gson();

    @Override
    public void onSubscribe(Disposable d) {

    }


    @Override
    public void onComplete() {

    }


    @Override
    public  void onNext(T response) {
        onOk(response);
        try {
            switch (response.getStatus()) {
                case STATUS_OK:
                    onOk(response);
                    break;
                case STATUS_FAIL:
                    onFail(response);
                    break;
                case STATUS_ERROR:
                default:
                    /**
                     * for Retrofit
                     */
                    if (response.getThrowable() != null) {
                        String className = response.getThrowable().getClass().getCanonicalName();
                        if (response.getThrowable() instanceof HttpException
                                || className.startsWith("java.net")
                                || className.startsWith("javax.net")) {
                            onNetworkFail(NetworkFailReason.BAD_NETWORK);
                            return;
                        }
                    }

                    onNetworkFail(NetworkFailReason.PARSE_ERROR);
            }
        } catch (Throwable throwable) {
            if (response == null) {
                response = sGson.fromJson("{}", new TypeToken<T>() {
                }.getType());
            }
            response.setStatus(BasicResponse.Status.STATUS_ERROR)
                    .setThrowable(throwable);
           // Timber.tag("OkHttp").w(throwable);
            onNetworkFail(NetworkFailReason.PARSE_ERROR);
        }
    }

    // region 需要被子类实现的方法

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
    public void onNetworkFail(NetworkFailReason reason) {
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

    /**
     * 为了适应 RxJava 的链式调用风格, 所有的错误都会被捕捉并以onNext方式调用, 因为 onError会切断调用链
     * 正常情况下, 本回调不会被触发, 如果被触发,说明网络请求底层发生了严重问题。
     */
    @Override
    public final void onError(Throwable e) {

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
