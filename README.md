# Retrofit2

**关于token处理请切换到token分支**

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
       //        LoginRequest loginRequest = new LoginRequest();
       //        loginRequest.setUsername("110120");
       //        loginRequest.setPassword("123456");
               Map<String, Object> map = new HashMap<>();
               map.put("username", "110120");
               map.put("password", "123456");
               RetrofitHelper.getApiService()
                       .login(map)
                       .compose(RxUtil.<LoginResponse>rxSchedulerHelper(this))
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
                       .compose(RxUtil.<ArticleWrapper>rxSchedulerHelper(this))
                       .subscribe(new DefaultObserver<ArticleWrapper>() {
                           @Override
                           public void onSuccess(ArticleWrapper response) {
                               showToast("Request Success，size is：" + response.getDatas().size());
                           }
                       });
           }

```


[点击查看详细介绍](http://blog.csdn.net/qq_20521573/article/details/70991850)
