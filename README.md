# Retrofit2

该项目针对如下json数据格式进行封装

```json
{
  "code": 200,
  "message": "成功",
  "results": {
    ...
   }
}
```


## 一、使用方法：

### 1.Post请求

```java
RetrofitHelper.getApiService()
                .login(getParameters())
                .compose(RxUtil.rxSchedulerHelper(this, true))
                .subscribe(new ResponseObserver<LoginResponse>() {
                    @Override
                    public void onSuccess(LoginResponse response) {
                        showToast("登录成功");
                    }

                    @Override
                    public void onFail(String message) {
                        super.onFail(message);
                        
                    }
                });

```

### 2.Get请求

```java

    public void getData(View view) {
        RetrofitHelper.getApiService()
                .getArticle()
                .compose(RxUtil.rxSchedulerHelper(this, true))
                .subscribe(new ResponseObserver<ArticleWrapper>() {
                    @Override
                    public void onSuccess(ArticleWrapper response) {
                        showToast("Request Success，size is：" + response.getDatas().size());
                    }

                    @Override
                    public void onFail(String message) {
                        super.onFail(message);
                    }
                });
    }

```
## 二、Token自动刷新原理剖析

源码中采用OAuth2.0协议实现token验证机制，主要步骤如下：

- 通过用户名和密码登录成功获取access token和refreshToken并保存到本地。
- access token的有效期为2小时，refreshToken的有效期为15天。
- 每次网络请求都需要带上access token，而不必带上refreshToken。
- 如果服务器端判断access token过期，则返回对应的错误码，客户端判断错误码后调用刷新 token接口,重新获取access token和refreshToken并存储。
- 如果连续15天未使用app或者用户修改了密码，则refreshToken过期，需要重新登录获取token和refreshToken。

### 1.核心实现代码

该项目中采用动态代理实现access token过期自动刷新，核心代码在项目的ProxyHandler类中。

#### (1) 捕获access token过期异常

注意动态代理ProxyHandler类的invoke方法。该方法即为我们发出的网络请求，正常情况下invoke方法会通过flatMap操作执行method.invoke方法进行正常的网络请求。但若此时access token已过期，则会在在Converer中拦截到access token过期的错误码并抛出TokenInvalidException（该异常是自定义异常）异常，紧接着我们需要在retryWhen中拦截TokenInvalidException异常来通过refresh token刷新access token。代码如下：

如果access token
```java
public class ProxyHandler implements InvocationHandler {
	// ....省略无关代码
    @Override
    public Object invoke(Object proxy, final Method method, final Object[] args) {
        return Observable.just(true).flatMap((Function<Object, ObservableSource<?>>) o -> {
            try {
                try {
                	 /**
                     * 如果needResetAccessToken为true,则说明本次请求是在成功access token成功刷新后
                     * 自动调用，而此时方法中的access token参数仍然为旧的access token，因此需要
                     * 将这个方法中的access token参数重置为刷新后的access token参数
                     */
                    if (needResetAccessToken) {
                        updateMethodToken(method, args);
                    }
                    return (Observable<?>) method.invoke(mProxyObject, args);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).retryWhen(observable -> observable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
            if (throwable instanceof TokenInvalidException) { // 捕捉到token过期异常，则需要刷新token
                return refreshTokenWhenTokenInvalid();
            } else if (throwable instanceof TokenNotExistException) {
                // Token 不存在，执行退出登录的操作。（为了防止多个请求，都出现 Token 不存在的问题，
                // 这里需要取消当前所有的网络请求）
                mGlobalManager.logout();
                return Observable.error(throwable);
            }
            return Observable.error(throwable);
        }));
    }
}
```

#### (2)刷新access token

在access token成功刷新后将needResetAccessToken置为true。
```java
public class ProxyHandler implements InvocationHandler {

	// ....省略无关代码
	
	/**
	 *  通过refresh token换取新的access token和refresh token并存储到本地，
	 *  刷新成功后将needResetAccessToken置为true，后边请求会根据needResetAccessToken来重置刷新后的token。
	 */
	private Observable<?> refreshTokenWhenTokenInvalid() {
	        synchronized (ProxyHandler.class) {
	            // Have refreshed the token successfully in the valid time.
	            if (new Date().getTime() - tokenChangedTime < REFRESH_TOKEN_VALID_TIME) {
	                mIsTokenNeedRefresh = true;
	                return Observable.just(true);
	            } else {
	                RetrofitService
	                        .getRetrofitBuilder(mBaseUrl)
	                        .build()
	                        .create(CommonService.class)
	                        .refreshToken()
	                        .subscribe(new ResponseObserver<RefreshTokenResponse>() {
	                            @Override
	                            public void onSuccess(RefreshTokenResponse response) {
	                                if (response != null) {
	                                    mGlobalManager.tokenRefresh(response);
	                                    needResetAccessToken= true;
	                                    tokenChangedTime = new Date().getTime();
	                                }
	                            }
	
	                            @Override
	                            public void onError(Throwable e) {
	                                super.onError(e);
	                                mRefreshTokenError = e;
	                            }
	                        });
	                if (mRefreshTokenError != null) {
	                    Observable<Object> error = Observable.error(mRefreshTokenError);
	                    // 这里必须将mRefreshTokenError置空，否则会有问题。
	                    mRefreshTokenError = null;
	                    return error;
	                } else {
	                    return Observable.just(true);
	                }
	            }
	        }
	    }
}
```
紧接着会继续上面的invoke方法重新发起请求，而此时由于needResetAccessToken为true，则会调用到updateMethodToken来重置请求中旧的access token。代码如下：

```java
 /**
     * Update the access token of the args in the method.
     * <p>
     * access token刷新成功后，再次发起请求需要使用刷新后的access token，
     * 这个方法会拦截本次请求，根据请求方法注入新的access token。
     */
    @SuppressWarnings("unchecked")
    private void updateMethodToken(Method method, Object[] args) {
        String token = (String) SharedPreferencesHelper.get(Utils.getContext(), "token", "");
        if (needResetAccessToken && !TextUtils.isEmpty(token)) {
            Annotation[][] annotationsArray = method.getParameterAnnotations();
            Annotation[] annotations;
            if (annotationsArray != null && annotationsArray.length > 0) {
                for (int i = 0; i < annotationsArray.length; i++) {
                    annotations = annotationsArray[i];
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof FieldMap || annotation instanceof QueryMap) {
                            if (args[i] instanceof Map)
                                ((Map<String, Object>) args[i]).put(ACCESS_TOKEN, token);
                        } else if (annotation instanceof Query) {
                            if (ACCESS_TOKEN.equals(((Query) annotation).value()))
                                args[i] = token;
                        } else if (annotation instanceof Field) {
                            if (ACCESS_TOKEN.equals(((Field) annotation).value()))
                                args[i] = token;
                        } else if (annotation instanceof Part) {
                            if (ACCESS_TOKEN.equals(((Part) annotation).value())) {
                                RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
                                args[i] = tokenBody;
                            }
                        } else if (annotation instanceof Body) {
                            if (args[i] instanceof BaseRequest) {
                                BaseRequest requestData = (BaseRequest) args[i];
                                requestData.setToken(token);
                                args[i] = requestData;
                            }
                        }
                    }
                }
            }
            needResetAccessToken = false;
        }
    }
```

[点击查看详细介绍](http://blog.csdn.net/qq_20521573/article/details/70991850)
