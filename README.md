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
文件下载
```
DownloadUtils downloadUtils = new DownloadUtils();
public void download(View view) {
        btn.setClickable(false);
        downloadUtils.download(Constants.DOWNLOAD_URL, new DownloadListener() {
            @Override
            public void onProgress(int progress) {
                LogUtils.e("--------下载进度：" + progress);
                Log.e("onProgress", "是否在主线程中运行:" + String.valueOf(Looper.getMainLooper() == Looper.myLooper()));
                progressBar.setProgress(progress);
                mTvPercent.setText(String.valueOf(progress) + "%");
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {  //  运行在子线程
                saveFile(responseBody);
                Log.e("onSuccess", "是否在主线程中运行:" + String.valueOf(Looper.getMainLooper() == Looper.myLooper()));
            }

            @Override
            public void onFail(String message) {
                btn.setClickable(true);
                ToastUtils.show("文件下载失败,失败原因：" + message);
                Log.e("onFail", "是否在主线程中运行:" + String.valueOf(Looper.getMainLooper() == Looper.myLooper()));
            }

            @Override
            public void onComplete() {  //  运行在主线程中
                ToastUtils.show("文件下载成功");
                btn.setClickable(true);
            }
        });
    }
```
取消下载
```
 public void cancelDownload(View view) {
        if (downloadUtils != null) {
            downloadUtils.cancelDownload();
        }
    }
```


[点击查看详细介绍](http://blog.csdn.net/qq_20521573/article/details/70991850)
