package com.cypoem.retrofit.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.cypoem.retrofit.BaseRxActivity;
import com.cypoem.retrofit.MainActivity;
import com.cypoem.retrofit.module.BasicResponse;
import com.cypoem.retrofit.utils.ToastUtils;
import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.cypoem.retrofit.net.DefaultObserver.NetworkFailReason.BAD_NETWORK;
import static com.cypoem.retrofit.net.DefaultObserver.NetworkFailReason.CONNECT_ERROR;
import static com.cypoem.retrofit.net.DefaultObserver.NetworkFailReason.PARSE_ERROR;
import static com.cypoem.retrofit.net.DefaultObserver.NetworkFailReason.CONNECT_TIMEOUT;
import static com.cypoem.retrofit.net.DefaultObserver.NetworkFailReason.UNKNOWN_ERROR;
import static com.cypoem.retrofit.net.SrcbApiService.BAD_GATEWAY;
import static com.cypoem.retrofit.net.SrcbApiService.FORBIDDEN;
import static com.cypoem.retrofit.net.SrcbApiService.GATEWAY_TIMEOUT;
import static com.cypoem.retrofit.net.SrcbApiService.INTERNAL_SERVER_ERROR;
import static com.cypoem.retrofit.net.SrcbApiService.NOT_FOUND;
import static com.cypoem.retrofit.net.SrcbApiService.REQUEST_TIMEOUT;
import static com.cypoem.retrofit.net.SrcbApiService.SERVICE_UNAVAILABLE;
import static com.cypoem.retrofit.net.SrcbApiService.UNAUTHORIZED;

/**
 * Created by zhpan on 2017/4/18.
 */

public abstract class DefaultObserver<T extends BasicResponse> implements Observer<T> {
    private BaseRxActivity mActivity;
    //  Activity 是否在执行onStop()时取消订阅
    private boolean isAddInStop=false;


    public DefaultObserver(BaseRxActivity activity) {
        mActivity = activity;
    }

    public DefaultObserver(BaseRxActivity activity,boolean isAddInStop) {
        this.isAddInStop=isAddInStop;
        mActivity = activity;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if(isAddInStop){
            mActivity.addRxStop(d);
        }else {
            mActivity.addRxDestroy(d);
        }
    }

    @Override
    public void onNext(T response) {
        // TODO 根据后台返回数据进行配置
        onOk(response);
        /*switch (response.getStatus()) {
            case STATUS_OK:
                onOk(response);
                break;
            case STATUS_FAIL:
                onFail(response);
                break;
            case STATUS_ERROR:
            default:
        }*/
    }

    @Override
    public void onError(Throwable e) {
        Log.e("Retrofit", e.getMessage());
       /* String className = e.getClass().getCanonicalName();
        if (e instanceof HttpException
                || className.startsWith("java.net")
                || className.startsWith("javax.net")) {
            onNetworkFail(DefaultObserver.NetworkFailReason.BAD_NETWORK);
            return;
        }
        onNetworkFail(DefaultObserver.NetworkFailReason.PARSE_ERROR);*/


        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    onNetworkFail(BAD_NETWORK); //均视为网络错误
                    break;
            }
            return;
        } /*else if (e instanceof ServerException) {    //服务器返回的错误
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.getCode());
            ex.setDisplayMessage(resultException.getMsg());
            return ex;
        }*/ else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            onNetworkFail(PARSE_ERROR);
            return;
        } else if (e instanceof ConnectException) {
            onNetworkFail(CONNECT_ERROR);
            return;
        } else if (e instanceof InterruptedIOException) {
            onNetworkFail(CONNECT_TIMEOUT);
        } else {
            onNetworkFail(UNKNOWN_ERROR);
            return;
        }
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
            ToastUtils.show("");
        } else {
            ToastUtils.show(message);
        }
    }


    public void onNetworkFail(DefaultObserver.NetworkFailReason reason) {
        switch (reason) {
            case PARSE_ERROR:
                ToastUtils.show("数据解析错误", Toast.LENGTH_SHORT);
                break;
            case BAD_NETWORK:
                ToastUtils.show("服务器异常", Toast.LENGTH_SHORT);
                break;
            case CONNECT_ERROR:
                ToastUtils.show("网络连接失败,请检查您的网络", Toast.LENGTH_SHORT);
                break;
            case CONNECT_TIMEOUT:
                ToastUtils.show("网络连接超时", Toast.LENGTH_SHORT);
                break;
            case UNKNOWN_ERROR:
            default:
                ToastUtils.show("未知错误", Toast.LENGTH_SHORT);
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
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
