package com.cypoem.retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cypoem.retrofit.module.DataWrapper;
import com.cypoem.retrofit.module.ListData;
import com.cypoem.retrofit.net.DefaultObserver;
import com.cypoem.retrofit.net.SrcbApi;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseRxActivity implements View.OnClickListener {
    private CompositeDisposable disposables2Stop;// 管理Stop取消订阅者者
    //  加载进度的dialog
    private CustomProgressDialog mProgressDialog;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    public void getData(){

        SrcbApi.getApiService()
                .getData("json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<DataWrapper>(this) {
                    @Override
                    public void onSuccess(DataWrapper response) {
                        Toast.makeText(MainActivity.this, "请求数据成功", Toast.LENGTH_SHORT).show();
                        List<ListData.ListBean> content = response.getList();
                        for (int i = 0; i < content.size(); i++) {
                            Toast.makeText(MainActivity.this, "第" + (i + 1) + "条数据Password:" + content.get(i).getPsw()+content.get
                                    (i).getUser(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean addRxStop(Disposable disposable) {
        if (disposables2Stop == null) {
            throw new IllegalStateException(
                    "addUtilStop should be called between onStart and onStop");
        }
        disposables2Stop.add(disposable);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                getData();
                break;
        }
    }
}
