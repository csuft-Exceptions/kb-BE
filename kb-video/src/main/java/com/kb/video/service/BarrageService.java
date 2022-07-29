package com.kb.video.service;

import com.kb.video.pojo.BarrageInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class BarrageService {

    @Resource
    RedisTemplate redisTemplate;

    public List<BarrageInfo> getBarrageByTime(Long videoId, int offset) {

        offset = offset - offset % 60;

        return (List<BarrageInfo>) redisTemplate.opsForHash().get("barrage" + videoId, offset);
    }

    public List<BarrageInfo> getAllBarrage(Long videoId) {

        Map<String, List<BarrageInfo>> map = redisTemplate.opsForHash().entries("barrage" + videoId);

        if(map.isEmpty())return null;

        List<BarrageInfo> res = map.entrySet().stream().reduce((x, y) -> {
            x.getValue().addAll(y.getValue());
            return x;
        }).get().getValue();

        return res;
    }

    public void addOneBarrage(Long videoId, BarrageInfo barrageInfo) {

        int offset = barrageInfo.getOffsetTime();

        offset = offset - offset % 60;

        List<BarrageInfo> barrages = (List<BarrageInfo>) redisTemplate.opsForHash().get("barrage" + videoId, offset);

        if (barrages == null) barrages = new ArrayList<>();

        barrages.add(barrageInfo);

        redisTemplate.opsForHash().put("barrage" + videoId, offset, barrages);
    }
}
