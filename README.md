# Retrofit2


封装针对如下json数据格式结合Rxjava2和Retrofit2的二次封装

{
  "code": 200,
  "message": "成功",
  "results": {
    ...
   }
}


# 使用方法：

## Post请求
```
          public void login(View view) {
                 Map<String, Object> map = new HashMap<>();
                 map.put("username", "110120");
                 map.put("password", "123456");
                 RetrofitHelper.getApiService()
                         .login(map)
                         .compose(RxUtil.rxSchedulerHelper(this, true))
                         .subscribe(new DefaultObserver<LoginResponse>() {
                             @Override
                             public void onSuccess(LoginResponse response) {
                                 showToast("登录成功");
                             }
                         });
             }


```

## Get请求

```

           public void getData(View view) {
                   RetrofitHelper.getApiService()
                           .getArticle()
                           .compose(RxUtil.rxSchedulerHelper(this, true))
                           .subscribe(new DefaultObserver<ArticleWrapper>() {
                               @Override
                               public void onSuccess(ArticleWrapper response) {
                                   showToast("Request Success，size is：" + response.getDatas().size());
                               }
                           });
               }

```

[点击查看详细介绍](http://blog.csdn.net/qq_20521573/article/details/70991850)
