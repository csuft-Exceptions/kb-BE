package com.kb.common.utils;

import com.kb.common.exception.InfoException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 *  断言
 *
 * @author mawz
 * @version 1.0
 * @date 2022-06-12 - 23:21
 */
public class AssertUtil {
    public static void isTrue(boolean expression, String msg) {
        if (!expression) {
            throw new InfoException(msg);
        }
    }

    public static void isNotTrue(boolean expression, String msg) {
        if (!expression) {
            throw new InfoException(msg);
        }
    }

    public static void assertNull(Object obj,String msg){
        if (obj == null) {
            throw new InfoException(msg);
        }
    }

    public static void assertNotNull(Object obj,String msg){
        if (obj != null) {
            throw new InfoException(msg);
        }
    }

    public static void assertEmptyStr(String str,String msg){
        if (StringUtils.isBlank(str)) {
            throw new InfoException(msg);
        }
    }
    public static void assertNotEmptyStr(String str,String msg){
        if (StringUtils.isNotBlank(str)) {
            throw new InfoException(msg);
        }
    }

    public static void assertEmptyCollection(Collection collection,String msg){
        if (CollectionUtils.isEmpty(collection)) {
            throw new InfoException(msg);
        }
    }

    public static void assertEmptyMap(Map collection, String msg){
        if (CollectionUtils.isEmpty(collection)) {
            throw new InfoException(msg);
        }
    }

    public static void assertNotEmptyCollection(Collection collection, String msg){
        if (!CollectionUtils.isEmpty(collection)) {
            throw new InfoException(msg);
        }
    }

    public static void assertNotEqual(Object obj1,Object obj2, String msg){
        if (obj1!=obj2){
            throw new InfoException(msg);
        }
    }
    public static void assertEqual(Object obj1,Object obj2, String msg){
        if (obj1 == obj2){
            throw new InfoException(msg);
        }
    }

    public static void assertEquals(Object obj1,Object obj2, String msg){
        if (obj1!=null && obj1.equals(obj2)){
            throw new InfoException(msg);
        }
    }

    public static void assertNotEquals(Object obj1,Object obj2, String msg){
        if (obj1==null || !obj1.equals(obj2)){
            throw new InfoException(msg);
        }
    }

}
