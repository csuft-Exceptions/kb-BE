package com.kb.video.service;


import com.kb.common.utils.AssertUtil;
import com.kb.video.pojo.MyTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class HistoryService {

    @Resource
    RedisTemplate<String, ZSetOperations.TypedTuple<Long>> redisTemplate;

    public void addHistory(Long userId, Long videoId) {
        String key = "history" + userId;
        long value = videoId;
        Long time = System.currentTimeMillis();

        ZSetOperations.TypedTuple<Long> objectTypedTuple1 = new MyTypedTuple<>(value, time.doubleValue());

        Double score = redisTemplate.opsForZSet().score(key, value);

//        if (score != null) {
        Long remove = redisTemplate.opsForZSet().remove(key, value);
//        }

        Boolean add = redisTemplate.opsForZSet().add(key, objectTypedTuple1, time);

        AssertUtil.assertEqual(add, false, "历史记录添加失败");
    }

    public Map<Long, Long> getHistory(Long userId) {
        String key = "history" + userId;
        long length = 100L;

        Set<ZSetOperations.TypedTuple<Long>> set = redisTemplate.opsForZSet().reverseRange(key, 0, length - 1);

        if (set == null || set.size() == 0) {
            return null;
        }

        Map<Long, Long> res = set.stream().collect(HashMap::new,
                (map, v) -> {
                    map.put(v.getValue(), v.getScore().longValue());
                },
                HashMap::putAll
        );

        return res;
    }

    public void removeOneHistory(Long userId, Long videoId) {
        String key = "history" + userId;

        Long remove = redisTemplate.opsForZSet().remove(key, videoId);

        AssertUtil.assertEqual(remove,0L,"删除失败");

    }
}
