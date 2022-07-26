package com.kb.video.controller;

import com.kb.common.base.BaseResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-24 - 18:17
 */
public class RateController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @GetMapping("/top")
    public BaseResponse top() {
        redisTemplate.opsForZSet().reverseRange("topZSet", 1, 10);
        return null;
    }

    @GetMapping("/topCategory")
    public BaseResponse top(Integer category) {
        redisTemplate.opsForZSet().reverseRange("topZSet" + category, 1, 10);
        return null;
    }

}
