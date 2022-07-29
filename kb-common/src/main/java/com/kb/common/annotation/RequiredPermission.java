package com.kb.common.annotation;

import java.lang.annotation.*;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-29 - 16:00
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiredPermission {
    // 权限码
    String code() default "";
}