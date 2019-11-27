package com.validator.responsebodyadvice.demo.exception;

/**
 * 业务异常类型
 */
public class BusinessException extends Exception{

    private static final long serialVersionUID = 1L;

    //业务类型
    private String type;
    //业务代码
    private int code;
    //错误信息
    private String message;

    public BusinessException(String type, int code, String message){
        super(message);
        this.type = type;
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message){
        super(message);
        this.type = "";
        this.code = -1;
        this.message = message;
    }

    public BusinessException(String type, String message){
        super(message);
        this.type = type;
        this.code = -1;
        this.message = message;
    }

    public BusinessException(int bizCode, String message){
        super(message);
        this.type = "";
        this.code = bizCode;
        this.message = message;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}