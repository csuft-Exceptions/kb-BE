package com.kb.common.advice;

import com.kb.common.base.BaseResponse;
import com.kb.common.exception.AuthException;
import com.kb.common.exception.InfoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-06-15 - 9:19
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(InfoException.class)
    public BaseResponse handlerInfoException(InfoException e, HttpServletRequest request){
        log.error(">>>>>>>>>>>InfoException Catch,request URL:{}>>>>>>>>>>>",request.getRequestURL(),e);
        return BaseResponse.failed(e.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    public BaseResponse handlerAuthException(AuthException e, HttpServletRequest request){
        log.error(">>>>>>>>>>>AuthException Catch,request URL:{}>>>>>>>>>>>",request.getRequestURL(),e);
        return BaseResponse.failed(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse handlerException(Exception e, HttpServletRequest request){
        if(e instanceof InfoException){
            return handlerInfoException((InfoException) e,request);
        }else if(e instanceof  AuthException){
            return handlerAuthException((AuthException) e,request);
        }
        log.error(">>>>>>>>>>>Exception Catch,request URL:{}>>>>>>>>>>>",request.getRequestURL(),e);
        return BaseResponse.failed("系统出错！请稍后再试！");
    }
}
