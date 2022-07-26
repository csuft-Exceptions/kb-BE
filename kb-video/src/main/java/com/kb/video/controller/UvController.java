package com.kb.video.controller;

import com.kb.common.base.BaseResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-24 - 18:01
 */
@RestController
public class UvController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/hlP")
    public BaseResponse hlP(String videoId, String userId) {
        redisTemplate.opsForHyperLogLog().add("hl" + videoId, userId);
        return BaseResponse.success("success");
    }

    @GetMapping("/hlG")
    public BaseResponse hlG(String videoId) {
        Long res = redisTemplate.opsForHyperLogLog().size("hl" + videoId);
        return BaseResponse.success(res);
    }
}
