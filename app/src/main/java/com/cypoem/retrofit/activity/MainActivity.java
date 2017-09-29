package com.cypoem.retrofit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cypoem.retrofit.R;
import com.cypoem.retrofit.module.BasicResponse;
import com.cypoem.retrofit.module.bean.MeZi;
import com.cypoem.retrofit.net.DefaultObserver;
import com.cypoem.retrofit.net.IdeaApi;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private Button btn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }


    public void getData() {
        IdeaApi.getApiService()
                .getMezi()
                .compose(this.<BasicResponse<List<MeZi>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<MeZi>>>(this) {
                    @Override
                    public void onSuccess(BasicResponse<List<MeZi>> response) {
                        List<MeZi> results = response.getResults();
                        showToast("请求成功，妹子个数为"+results.size());
                    }
                });
    }

}
