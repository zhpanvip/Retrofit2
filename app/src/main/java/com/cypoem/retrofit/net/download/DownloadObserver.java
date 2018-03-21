package com.cypoem.retrofit.net.download;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhpan on 2018/3/20.
 */

public abstract   class DownloadObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onComplete() {
    }

    abstract public void onSuccess(T t);
}
