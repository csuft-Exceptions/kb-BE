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
    RedisTemplate<String, Map<Long, Long>> redisTemplate;


    public void increaseLike(Long videoId) {
        redisTemplate.opsForHash().increment("like", videoId, 1L);
    }

    public void decreaseLike(Long videoId) {
        redisTemplate.opsForHash().increment("like", videoId, -1L);
    }

    public Long getLikes(Long videoId) {

        return (Long) redisTemplate.opsForHash().get("like", videoId);

    }
}
