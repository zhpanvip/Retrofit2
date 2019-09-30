package com.cypoem.retrofit.activity;

import android.os.Bundle;
import android.view.View;

import com.cypoem.retrofit.R;
import com.cypoem.retrofit.module.request.ArticleWrapper;
import com.cypoem.retrofit.module.response.LoginResponse;
import com.cypoem.retrofit.net.RetrofitHelper;
import com.zhpan.idea.net.common.ResponseObserver;
import com.zhpan.idea.utils.RxUtil;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
    }

    /**
     * Post请求
     */
    public void login(View view) {
        RetrofitHelper.getApiService()
                .login(getParameters())
                .compose(RxUtil.rxSchedulerHelper(this, true))
                .subscribe(new ResponseObserver<LoginResponse>() {
                    @Override
                    public void onSuccess(LoginResponse response) {
                        showToast("登录成功");
                    }
                });
    }

    private Map<String, Object> getParameters() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "110120");
        map.put("password", "123456");
        return map;
    }

    /**
     * Get请求
     *
     * @param view
     */
    public void getData(View view) {
        RetrofitHelper.getApiService()
                .getArticle()
                .compose(RxUtil.rxSchedulerHelper(this, true))
                .subscribe(new ResponseObserver<ArticleWrapper>() {
                    @Override
                    public void onSuccess(ArticleWrapper response) {
                        showToast("Request Success，size is：" + response.getDatas().size());
                    }
                });
    }

}
