package com.zhpan.idea.net.exception;

/**
 * 服务器返回的异常
 */
public class NoDataExceptionException extends BaseException {
    public NoDataExceptionException() {
        super(-1,"服务器没有返回对应的Data数据");
    }
}
