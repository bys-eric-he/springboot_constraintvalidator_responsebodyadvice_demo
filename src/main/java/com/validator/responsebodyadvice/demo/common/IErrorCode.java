package com.validator.responsebodyadvice.demo.common;

/**
 * 封装API的错误码
 */
public interface IErrorCode {
    String getCode();
    String getMessage();
}