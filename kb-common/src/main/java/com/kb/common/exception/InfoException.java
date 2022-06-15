package com.kb.common.exception;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-06-14 - 23:03
 */
public class InfoException extends RuntimeException{
    String msg;

    public InfoException(String msg){
        super(msg);
        this.msg=msg;
    }

    public InfoException(String msg,Throwable e){
        super(msg,e);
        this.msg=msg;
    }

}
