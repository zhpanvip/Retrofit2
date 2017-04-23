package com.cypoem.retrofit.module;

/**
 *
 */
public class BasicResponse {

    private int code;
    private String message;
    private String errMsg;

    private boolean error;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
