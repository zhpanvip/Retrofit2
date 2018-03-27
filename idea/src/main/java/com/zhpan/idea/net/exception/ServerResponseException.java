package com.zhpan.idea.net.exception;

/**
 * 服务器返回的异常
 */
public class ServerResponseException extends RuntimeException {
    public ServerResponseException(int errorCode, String cause) {
        super("服务器响应失败，错误码："+errorCode+"，错误原因"+cause, new Throwable("Server error"));
    }
}
