# Retrofit2
Rxjava2+Retrofit2封装

关于token处理请切换到token分支


使用方法：

```
IdeaApi.getApiService()
                .getMezi()
                .compose(this.<BasicResponse<List<MeiZi>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<MeiZi>>>(this) {
                    @Override
                    public void onSuccess(BasicResponse<List<MeiZi>> response) {
                        List<MeiZi> results = response.getResults();
                        showToast("请求成功，妹子个数为"+results.size());
                    }
                });
```
[详细介绍](http://blog.csdn.net/qq_20521573/article/details/70991850)
