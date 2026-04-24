package com.kb.common.utils;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.csp.sentinel.util.StringUtil;
import lombok.SneakyThrows;

import java.util.Base64;

/**
 * Base64 utility.
 *
 * @author mawz
 * @version 1.0
 */
public class Base64Util {

    public static final String CODE_FORMAT = "UTF-8";

    public static String encode(final String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public static String encode(byte[] binaryData) {
        return Base64.getEncoder().encodeToString(binaryData);
    }

    @SneakyThrows
    public static String decode(final String base64) {
        if (StringUtil.isEmpty(base64)) {
            return null;
        }
        return new String(Base64.getDecoder().decode(base64), CODE_FORMAT);
    }

    @SneakyThrows
    public static String decode(byte[] binaryData) {
        return new String(Base64.getDecoder().decode(binaryData), CODE_FORMAT);
    }

    public static boolean isBase64(String base64) {
        if (StringUtil.isEmpty(base64)) {
            return false;
        }
        String de = encode(decode(base64));
        AssertUtil.assertNull(de, "传入的参数为空");
        String en = de.replaceAll("[\\s*\t\n\r]", "");
        return base64.equals(en);
    }

    public static boolean isNotBase64(final String base64) {
        return !isBase64(base64);
    }

    public static long base64file_size(String base64String) {
        if (StringUtil.isEmpty(base64String)) {
            return 0L;
        }
        int size0 = base64String.length();
        int tailStart = Math.max(size0 - 10, 0);
        String tail = base64String.substring(tailStart);
        int equalIndex = tail.indexOf('=');
        if (equalIndex >= 0) {
            size0 = size0 - (tail.length() - equalIndex);
        }
        return size0 - ((long) size0 / 8) * 2;
    }
}
