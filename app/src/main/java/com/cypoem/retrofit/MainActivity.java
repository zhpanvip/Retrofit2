package com.cypoem.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.cypoem.retrofit.module.ListData;
import com.cypoem.retrofit.module.DataWrapper;
import com.cypoem.retrofit.net.AppClient;
import com.cypoem.retrofit.net.DefaultObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppClient.getApiService()
                .getData("json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<DataWrapper>() {
                    @Override
                    public void onOk(DataWrapper response) {
                        List<ListData.ListBean> content = response.getList();
                        Toast.makeText(MainActivity.this, "请求数据成功", Toast.LENGTH_SHORT).show();

                    }
                });

    }
}
