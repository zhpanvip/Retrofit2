package com.cypoem.retrofit.fragment;

import android.os.Bundle;

import com.cypoem.retrofit.R;
import com.cypoem.retrofit.module.BasicResponse;
import com.cypoem.retrofit.module.bean.MeZi;
import com.cypoem.retrofit.net.DefaultObserver;
import com.cypoem.retrofit.net.IdeaApi;

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

        IdeaApi.getApiService()
                .getMezi()
                .compose(this.<BasicResponse<List<MeZi>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<MeZi>>>(getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<List<MeZi>> response) {
                        List<MeZi> results = response.getResults();
                        showToast("请求成功，妹子个数为"+results.size());
                    }
                });
    }
}
