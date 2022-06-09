package com.kb.kbcommon.utils;

import com.kb.kbcommon.exception.BusinessException;
import lombok.SneakyThrows;

public class AssertUtil {

    //如果不是true，则抛异常
    @SneakyThrows
    public static void isTrue(boolean expression, String msg) {
        if (!expression) {
            throw new BusinessException(msg);
        }
    }
    //如果不是是false，则抛异常
    @SneakyThrows
    public static void isFalse(boolean expression, String msg) {
        if (expression) {
            throw new BusinessException(msg);
        }
    }
}
