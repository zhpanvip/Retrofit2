
package com.zhpan.idea.net.exception;

public class RefreshTokenExpiredException extends BaseException {

    public RefreshTokenExpiredException(int errorCode, String cause) {
        super(errorCode, cause);
    }
}
