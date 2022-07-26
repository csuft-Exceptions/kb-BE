package com.kb.video.controller;

import com.kb.common.base.BaseResponse;
import com.kb.video.pojo.VideoInfo;
import com.kb.video.service.VideoService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-24 - 18:17
 */
public class RateController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private VideoService videoService;

    /**
     * 视频信息落库前做的处理
     *
     * @param videoInfo
     * @return
     */
    @GetMapping("/pre")
    public BaseResponse preHandler(@RequestBody VideoInfo videoInfo) {
        redisTemplate.opsForSet().add("top", videoInfo.getId().toString());
        // 过期时间为3天 实际为71小时
        redisTemplate.opsForValue().set(videoInfo.getId().toString(), videoInfo.getCategory().toString(), 255600, TimeUnit.SECONDS);
        return null;
    }

    /**
     * 定时更新时的处理
     *
     * @param
     * @return
     */
    @GetMapping("/up")
    public BaseResponse upHandler() {
        Set<String> set = redisTemplate.opsForSet().members("top");
        if (set == null) {
            return null;
        }
        for (String s : set) {
            VideoInfo videoInfo = videoService.getVideoInfoById(Long.valueOf(s));
            redisTemplate.opsForZSet().remove("topZSet", s);
            //分类
            redisTemplate.opsForZSet().remove("topZSet" + videoInfo.getCategory(), s);
            if (redisTemplate.hasKey(s)) {
                double score = videoService.getVideoInfoById(videoInfo.getId()).getPlays();
                redisTemplate.opsForZSet().add("topZSet", s, score);
                redisTemplate.opsForZSet().add("topZSet" + videoInfo.getCategory(), s, score);
            } else {
                redisTemplate.opsForSet().remove("top", s);
            }
        }
        return null;
    }

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
