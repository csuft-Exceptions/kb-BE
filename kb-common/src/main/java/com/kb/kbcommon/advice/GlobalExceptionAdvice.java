package com.kb.kbcommon.advice;

import com.kb.kbcommon.exception.BusinessException;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
@Aspect
public class GlobalExceptionAdvice {

    /**
     * example
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity businessExceptionAdvide(BusinessException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getCode());
    }
    @Pointcut("execution(* com.kb..*.*(..))")
    public void pointcut(){}

    @AfterThrowing(pointcut = "pointcut()",throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e){
        log.error("............捕获到异常..............");
        //纪录错误信息
        log.error("系统错误:{}", e.getMessage());
        // todo


    }

}
