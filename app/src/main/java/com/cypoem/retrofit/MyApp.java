package com.cypoem.retrofit;

import android.app.Application;

import com.zhpan.idea.utils.Utils;


/**
 * Created by edianzu on 2017/4/18.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
