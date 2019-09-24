package com.zhpan.idea.utils;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.zhpan.idea.net.common.ProgressUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {

    /**
     * @param activity    Activity
     * @param showLoading 是否显示Loading
     * @return 转换后的ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper(final RxAppCompatActivity activity, final boolean showLoading) {
        if (activity == null) return rxSchedulerHelper();
        return observable -> {
            Observable<T> compose = observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(ProgressUtils.applyProgressBar(activity))
                    .compose(activity.bindUntilEvent(ActivityEvent.DESTROY));
            if (showLoading) {
                return compose.compose(ProgressUtils.applyProgressBar(activity));
            } else {
                return compose;
            }
        };
    }


    /**
     * @param fragment    fragment
     * @param showLoading 是否显示Loading
     * @return 转换后的ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper(final RxFragment fragment, boolean showLoading) {
        if (fragment == null || fragment.getActivity() == null) return rxSchedulerHelper();
        return observable -> {
            Observable<T> compose = observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(ProgressUtils.applyProgressBar(fragment.getActivity()))
                    .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY));
            if (showLoading) {
                return compose.compose(ProgressUtils.applyProgressBar(fragment.getActivity()));
            } else {
                return compose;
            }
        };
    }


    /**
     * 统一线程处理
     * @return 转换后的ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
