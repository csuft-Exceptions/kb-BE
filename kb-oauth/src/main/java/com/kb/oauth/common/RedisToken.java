package com.kb.oauth.common;

import com.alibaba.fastjson.JSON;
import com.kb.oauth.util.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author wjx
 * @create 2022/7/13 11:49
 */
@Component
public class RedisToken {

    private static final String TOKEN_CACHE_PREFIX = "user_key:";

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 将token存入Redis
     * @param accessToken 用户身份令牌
     * @param value 内容 authToken
     * @param ttl 过期时间
     * @return
     */
    public boolean saveTokenToRedis(String accessToken, String value, long ttl) {
        String key = TOKEN_CACHE_PREFIX + accessToken;
        redisTemplate.opsForValue().set(key,value,ttl, TimeUnit.SECONDS);
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire>0;
    }

    /**
     * 从Redis中删除token
     * @param token
     */
    public boolean delToken(String token){
        String key = TOKEN_CACHE_PREFIX + token;
        return redisTemplate.delete(key);
    }

    /**
     * 从Redis取出数据
     * @param token
     * @return
     */
    public AuthToken getToken(String token){
        String key = TOKEN_CACHE_PREFIX + token;
        String value = (String) redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        AuthToken authToken = JSON.parseObject(value, AuthToken.class);
        return authToken;
    }


}
