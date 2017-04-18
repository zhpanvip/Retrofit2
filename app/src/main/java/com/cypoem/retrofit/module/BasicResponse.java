package com.cypoem.retrofit.module;

/**
 *
 */
public class BasicResponse {

    // 网络操作成功
    public static final String RESULT_CODE_OK = "1";
    // 网络操作失败
    public static final String RESULT_CODE_FAIL = "0";
    // 参数为空
    public static final String RESULT_CODE_EMPTY_PARAM_ERROR = "2";

    public String errMsg;

    String message;
    String sessionCode;
    public String resultCode;
    String latestVersion;
    boolean enforceFlag;
    String notice;
    boolean isSuccessful;
    Integer statusCode;
    String statusReason;
    String errorNo;
    /**
     * 有时候服务器返回这玩意，我也没办法，等以后统一了去掉
     */
    String returnCode;

    Status status;

    Throwable throwable;

    /**resultCode 显示成功*/
    public boolean sucByResultCode(){
        return "1".equals(resultCode);
    }

    /**
     * 网络请求的状态
     */
    public enum Status {
        /**
         *
         * 且请求状态被置为成功(resultCode == 1)
         */
        STATUS_OK,
        /**
         * 但请求状态被置为不成功(resultCode != 1)
         */
        STATUS_FAIL,
        /**

         * 回调执行过程中有
         * 任何异常发生。
         */
        STATUS_ERROR
    }

    public Status getStatus() {
        return status;
    }

    public BasicResponse setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public BasicResponse setThrowable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    public boolean isStatusOk() {
        return Status.STATUS_OK == getStatus();
    }

    public boolean isStatusFail() {
        return Status.STATUS_FAIL == getStatus();
    }

    public boolean isStatusError() {
        return Status.STATUS_ERROR == getStatus();
    }



    /**
     * Only for debug
     */
    String _rawJson;
    boolean _isFromCache;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(String latestVersion) {
        this.latestVersion = latestVersion;
    }

    public boolean isEnforceFlag() {
        return enforceFlag;
    }

    public void setEnforceFlag(boolean enforceFlag) {
        this.enforceFlag = enforceFlag;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public String getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
    }

    public String _getRawJson() {
        return _rawJson;
    }

    public void _setRawJson(String _rawJson) {
        this._rawJson = _rawJson;
    }

    public boolean _isFromCache() {
        return _isFromCache;
    }

    public void _setFromCache(boolean _isFromCache) {
        this._isFromCache = _isFromCache;
    }
}
