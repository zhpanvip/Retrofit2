package com.cypoem.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cypoem.retrofit.module.DataWrapper;
import com.cypoem.retrofit.module.ListData;
import com.cypoem.retrofit.net.AppClient;
import com.cypoem.retrofit.net.DefaultObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
        AppClient.getApiService()
                .getData("json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<DataWrapper>() {
                    @Override
                    public void onOk(DataWrapper response) {
                        Toast.makeText(MainActivity.this, "请求数据成功", Toast.LENGTH_SHORT).show();
                        List<ListData.ListBean> content = response.getList();
                        for (int i = 0; i < content.size(); i++) {
                            Toast.makeText(MainActivity.this, "第" + (i + 1) + "条数据Password:" + content.get(i).getPsw(), Toast.LENGTH_SHORT).show();
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
