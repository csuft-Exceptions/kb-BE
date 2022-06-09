package com.kb.kbcommon.exception;

public class BusinessException extends Exception{
    String msg;
    public BusinessException(){
        super();
    }
    public BusinessException(String msg){
        super(msg);
        this.msg = msg;
    }

}
