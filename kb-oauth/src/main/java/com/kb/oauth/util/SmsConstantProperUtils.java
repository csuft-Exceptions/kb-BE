package com.kb.oauth.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wjx
 * @create 2022/6/27 17:01
 * 读取短信发送接口配置参数
 */
@Component
public class SmsConstantProperUtils implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {

    }
//
//    @Value("${aliyun.sms.regionId}")
//    private String regionId;
//    @Value("${aliyun.sms.accessKeyId}")
//    private String accessKeyId;
//    @Value("${aliyun.sms.secret}")
//    private String secret;
//    public static String REGION_ID;
//    public static String ACCESS_KEY_ID;
//    public static String SECRET;
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        REGION_ID = regionId;
//        ACCESS_KEY_ID = accessKeyId;
//        SECRET = secret;
//    }
}
