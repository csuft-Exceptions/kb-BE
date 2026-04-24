package com.kb.video.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Hash("like",<videoId,1/-1>)
 */
@Service
public class LikeService {

    @Resource
    RedisTemplate<String, Map<Integer, Integer>> redisTemplate;


    public void increaseLike(Long videoId) {
        redisTemplate.opsForHash().increment("like", videoId, 1L);
    }

    public void decreaseLike(Long videoId) {
        redisTemplate.opsForHash().increment("like", videoId, -1L);
    }

    public Long getLikes(Long videoId) {
        Object value = redisTemplate.opsForHash().get("like", videoId);
        if (value == null) {
            return 0L;
        }
        return Long.valueOf(String.valueOf(value));
    }
}
