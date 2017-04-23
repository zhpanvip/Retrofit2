package com.cypoem.retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.cypoem.retrofit.module.bean.Meizi;
import com.cypoem.retrofit.module.wrapper.MeiziWrapper;
import com.cypoem.retrofit.net.DefaultObserver;
import com.cypoem.retrofit.net.SrcbApi;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseRxActivity implements View.OnClickListener {
    private CompositeDisposable disposables2Stop;// 管理Stop取消订阅者者
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
                .getMeizi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<MeiziWrapper>(this) {
                    @Override
                    public void onSuccess(MeiziWrapper response) {
                        Toast.makeText(MainActivity.this, "请求数据成功", Toast.LENGTH_SHORT).show();
                        List<Meizi.ResultsBean> content = response.getResults();
                        for (int i = 0; i < 3; i++) {
                            Toast.makeText(MainActivity.this, "第" + (i + 1) + "个妹子Url:" + content.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
