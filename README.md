# Retrofit2

**关于token处理请切换到token分支**

封装针对如下json数据格式结合Rxjava2和Retrofit2的二次封装

\n{
 \n"code": 200,
 \n"message": "成功",
 \n"results": {
    \n...
    \n}
\n}


使用方法：

Get/Post请求
```
RetrofitHelper.getApiService()
                .getMezi()
                .compose(this.<List<MeiZi>>bindToLifecycle())
                .compose(ProgressUtils.<List<MeiZi>>applyProgressBar(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<List<MeiZi>>() {
                    @Override
                    public void onSuccess(List<MeiZi> response) {
                        showToast("请求成功，妹子个数为" + response.size());
                    }
                });
```

上传文件
```
 //  上传文件
    public void uploadFile(View view) {
        /********************************方法一**********************************/
        String fileStoreDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filePath = fileStoreDir+"/test/test.txt";
        FileUtils.createOrExistsFile(filePath);
        //文件路径
        File file = new File(filePath);
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("phone", "12345678901")
                .addFormDataPart("password", "123123")
                .addFormDataPart("uploadFile", file.getName(), fileBody);
        List<MultipartBody.Part> parts = builder.build().parts();

        /********************************方法二**********************************/
        /*//  图片参数
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestFile);
        //  手机号参数
        RequestBody phoneBody = RequestBody.create(MediaType.parse("multipart/form-data"), phone);
        //  密码参数
        RequestBody pswBody = RequestBody.create(MediaType.parse("multipart/form-data"), password);*/

        RetrofitHelper.getApiService()
                .uploadFiles(parts)
                .subscribeOn(Schedulers.io())
                .compose(this.<BasicResponse>bindToLifecycle())
                .compose(ProgressUtils.<BasicResponse>applyProgressBar(this,"上传文件..."))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>() {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        ToastUtils.show("文件上传成功");
                    }
                });
    }
```


下载
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
取消文件下载
```
 public void cancelDownload(View view) {
        if (downloadUtils != null) {
            downloadUtils.cancelDownload();
        }
    }
```


[点击查看详细介绍](http://blog.csdn.net/qq_20521573/article/details/70991850)
