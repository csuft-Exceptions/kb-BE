package com.kb.video.controller;

import com.kb.common.base.BaseResponse;
import com.kb.video.feign.SearchFeign;
import com.kb.video.pojo.VideoInfo;
import com.kb.video.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yk
 * @version 1.0
 * @date 2022/6/12 8:21
 */
@RestController
@Api("video")
public class VideoController {

    @Resource
    VideoService videoService;

    @Resource
    RedisTemplate<String,String> redisTemplate;

    @Resource
    SearchFeign searchFeign;

    /**
     * 查询所有视频信息
     *
     * @return
     */
    @ApiOperation("查询所有视频信息")
    @GetMapping("/videoInfo")
    public BaseResponse getAllVideoInfo() {
        List<VideoInfo> videoInfoList = videoService.getAllVideoInfo();
        return BaseResponse.success(videoInfoList, videoInfoList.size());
    }

    /**
     * 查询用户的所有视频
     *
     * @param userId
     * @return
     */
    @ApiOperation("查询用户的所有视频")
    @GetMapping("/userVideo/{userId}")
    public BaseResponse getVideoInfoRichByUserId(@PathVariable("userId") Long userId) {
        List<VideoInfo> videoInfoList = videoService.getVideoInfoByUserId(userId);
        return BaseResponse.success(videoInfoList, videoInfoList.size());
    }

    /**
     * 根据视频id查询视频
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id查询视频")
    @GetMapping("/videoInfo/{videoId}")
    public BaseResponse getVideoInfoById(@PathVariable("videoId") Long id) {
        VideoInfo videoInfo = videoService.getVideoInfoById(id);
        return BaseResponse.success(videoInfo);
    }

    /**
     * （视频添加成功后调用）添加视频
     *
     * @param videoInfo
     * @return
     */
    @ApiOperation("添加视频")
    @PostMapping("/videoInfo")
    public BaseResponse addVideoInfo(@RequestBody VideoInfo videoInfo) {

        Long id = videoService.addVideoInfo(videoInfo);
        redisTemplate.opsForSet().add("top", id.toString());
        // 过期时间为3天 实际为71小时
        redisTemplate.opsForValue().set(id.toString(), String.valueOf(videoInfo.getCategory()), 255600, TimeUnit.SECONDS);
        return BaseResponse.success(id);
    }


}
