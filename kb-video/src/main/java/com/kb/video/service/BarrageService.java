package com.kb.video.service;

import com.kb.video.pojo.BarrageInfo;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BarrageService {

    @Resource
    RedisTemplate redisTemplate;

    public List<BarrageInfo> getBarrageByTime(Long videoId, Date date) {

        return (List<BarrageInfo>) redisTemplate.opsForHash().get("barrage" + videoId, date.toString());
    }

    public List<BarrageInfo> getAllBarrage(Long videoId) {

        Map<String, List<BarrageInfo>> map = redisTemplate.opsForHash().entries("barrage" + videoId);

        List<BarrageInfo> res = new ArrayList<>();

        map.entrySet().stream().map(x -> {
            x.getValue().stream().map(y -> {
                res.add(y);
                return y;
            });
            return x;
        });
        return res;
    }

    public void addBarrage(Long videoId, Date date, BarrageInfo barrageInfo) {

        List<BarrageInfo> barrages = (List<BarrageInfo>) redisTemplate.opsForHash().get("barrage" + videoId, date.toString());

        barrages.add(barrageInfo);

        redisTemplate.opsForHash().put("barrage" + videoId, date.toString(), barrages);
    }
}
