package com.kb.common.exception;

/**
 * @author mawz
 * @version 1.0.0
 * @description
 * @date 2022/7/8 16:34
 */
public class AuthException extends RuntimeException {
    private String msg;

    public AuthException(){
        super("权限不足！");
    }
    public AuthException(String msg){
        super(msg);
        this.msg=msg;
    }
    public AuthException(String msg,Throwable e){
        super(msg,e);
        this.msg=msg;
    }
}
