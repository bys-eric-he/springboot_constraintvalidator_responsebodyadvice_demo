package com.validator.responsebodyadvice.demo.response;

import com.validator.responsebodyadvice.demo.common.IErrorCode;

/**
 * 异常结果
 */
public class ErrorResult {
    private String code;
    private String msg;
    private Boolean hasException;

    public ErrorResult(String code, String msg, Boolean hasException) {
        this.code = code;
        this.msg = msg;
        this.hasException = hasException;
    }

    public ErrorResult(IErrorCode errorCode){
        this.code=errorCode.getCode();
        this.msg=errorCode.getMessage();
        this.hasException = true;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getHasException() {
        return hasException;
    }

    public void setHasException(Boolean hasException) {
        this.hasException = hasException;
    }
}
