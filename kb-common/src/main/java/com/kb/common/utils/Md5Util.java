package com.kb.common.utils;

import cn.hutool.crypto.digest.MD5;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-06-15 - 11:16
 */
public class Md5Util {
    /**
     * 生成16位摘要
     * @param data
     * @return
     */
    public static String encode(String data){
        return MD5.create().digestHex(data);
    }

}
