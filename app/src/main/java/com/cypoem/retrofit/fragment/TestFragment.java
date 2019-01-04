package com.cypoem.retrofit.fragment;

import android.os.Bundle;

import com.cypoem.retrofit.R;
import com.cypoem.retrofit.module.response.MeiZi;
import com.cypoem.retrofit.net.RetrofitHelper;
import com.zhpan.idea.net.common.DefaultObserver;
import com.zhpan.idea.utils.ProgressUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhpan on 2017/9/30.
 * Fragment没有运行 内容仅供参考
 */

public class TestFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getData();
    }

    public void getData() {

        RetrofitHelper.getApiService()
                .getMezi()
                .compose(this.<List<MeiZi>>bindToLifecycle())
                .compose(ProgressUtils.<List<MeiZi>>applyProgressBar(getActivity()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<List<MeiZi>>() {

                    @Override
                    public void onSuccess(List<MeiZi> response) {

                    }
                });
    }
}
