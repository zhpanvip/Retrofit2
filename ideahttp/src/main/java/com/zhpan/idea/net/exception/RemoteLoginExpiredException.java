
package com.zhpan.idea.net.exception;

import com.zhpan.idea.net.common.ErrorCode;

/**
 * Created by zhpan on 2018/3/27.
 */
public class RemoteLoginExpiredException extends BaseException {

    public RemoteLoginExpiredException(int errorCode, String cause) {
        super(errorCode, cause);
    }

}