
package com.zhpan.idea.net.exception;

public class TokenExpiredException extends BaseException {
    public TokenExpiredException(int errorCode, String cause) {
        super(errorCode, cause);
    }

}
