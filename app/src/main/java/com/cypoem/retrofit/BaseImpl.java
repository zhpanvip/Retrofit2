package com.cypoem.retrofit;

import android.view.View;

import io.reactivex.disposables.Disposable;

/**
 * Created by zhpan on 2017/4/22.
 */

public interface BaseImpl {



    boolean addRxStop(Disposable disposable);

    boolean addRxDestroy(Disposable disposable);

    void remove(Disposable disposable);

}
