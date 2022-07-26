package com.kb.video.controller;

import com.kb.common.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-24 - 18:17
 */
@RestController
//@Api("排行相关")
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
