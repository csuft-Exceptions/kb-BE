package com.kb.common.advice;

import com.kb.common.base.BaseResponse;
import com.kb.common.exception.InfoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-06-15 - 9:19
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(InfoException.class)
    public BaseResponse handlerInfoException(InfoException e, HttpServletRequest request){
        log.error(">>>>>>>>>>>InfoException Catch,request URL:{}>>>>>>>>>>>",request.getRequestURL(),e);
        return BaseResponse.failed(e.getMessage());
    }
}
