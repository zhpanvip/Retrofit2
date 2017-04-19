package com.cypoem.retrofit;

import android.app.Application;
import android.content.Context;

import com.cypoem.retrofit.utils.Utils;

/**
 * Created by edianzu on 2017/4/18.
 */

public class App extends Application {
    private static App app;
    public static Context getAppContext() {
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        app=this;
    }
}
