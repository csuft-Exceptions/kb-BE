package com.kb.video.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LikeService {

    @Resource
    RedisTemplate<Long, String> redisTemplate;


    public void increaseLike(Long videoId) {
        redisTemplate.opsForValue().increment(videoId);
    }

    public void decreaseLike(Long videoId) {
        redisTemplate.opsForValue().decrement(videoId);
    }
}
