package com.kb.kbcommon.exception;

public class BusinessException extends Exception{
    String msg;
    String code;
    public BusinessException(){
        super();
    }
    public BusinessException(String msg){
        super(msg);
        this.msg = msg;
    }
    public BusinessException(String msg,String code){
        super(msg);
        this.msg = msg;
        this.code = code;
    }
    public String getCode(){
        return code;
    }

}
