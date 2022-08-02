package com.kb.video.service;


import com.kb.common.utils.AssertUtil;
import com.kb.video.pojo.MyTypedTuple;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class HistoryService {

    @Resource
    RedisTemplate<String,String> redisTemplate;

    public void addHistory(Long userId, Long videoId) {
        String key = "history" + userId;
        String value = videoId.toString();
        Long time = System.currentTimeMillis();

        ZSetOperations.TypedTuple<String> objectTypedTuple1 = new DefaultTypedTuple<>(value, time.doubleValue());

        Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>();
        set.add(objectTypedTuple1);

        Long add = redisTemplate.opsForZSet().add(key,set);

        AssertUtil.assertEqual(add, false, "历史记录添加失败");
    }

    public Map<String, Long> getHistory(Long userId) {
        String key = "history" + userId;
        long length = 100L;

        Set<ZSetOperations.TypedTuple<String>> set = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, length - 1);

        if (set == null || set.size() == 0) {
            return null;
        }

        Map<String, Long> res = set.stream().collect(HashMap::new,
                (map, v) -> {
                    map.put(v.getValue(), v.getScore().longValue());
                },
                HashMap::putAll
        );

        return res;
    }

    public void removeOneHistory(Long userId, Long videoId) {
        String key = "history" + userId;

        Long remove = redisTemplate.opsForZSet().remove(key, videoId.toString());

        AssertUtil.assertEqual(remove.intValue(),0,"删除失败");

    }
}
