package com.zhpan.idea.net.exception;

/**
 * 服务器返回的异常
 */
public class ServerResponseException extends BaseException {

    public ServerResponseException(int errorCode, String cause) {
        super(errorCode, cause);
    }
}
