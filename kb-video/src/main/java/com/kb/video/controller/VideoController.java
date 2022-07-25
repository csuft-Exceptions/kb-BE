package com.kb.video.controller;

import com.aliyun.oss.model.OSSObjectSummary;
import com.kb.common.base.BaseResponse;
import com.kb.common.exception.InfoException;
import com.kb.video.pojo.VideoInfo;
import com.kb.video.service.VideoService;
import com.kb.video.utils.OSSUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yk
 * @version 1.0
 * @date 2022/6/12 8:21
 */
@RestController
public class VideoController {

    @Resource
    VideoService videoService;

    @RequestMapping("/getVideo")
    public String downloadVideo(){
        OSSUtil ossUtil = new OSSUtil();
        String objectName="trailer.mp4";
        String pathName = "C:\\Users\\杨奎\\Desktop\\test.mp4";
        ossUtil.downloadVideo(objectName,pathName);
        return "hello";
    }

    /**
     * 获取指定key的url
     * @param key
     * @return
     */
    @GetMapping ("/url/{key}")
    public BaseResponse getUrl(@PathVariable("key") String key){
        OSSUtil ossUtil = new OSSUtil();
        System.out.println(key);
        return BaseResponse.success(ossUtil.getUrl(key));
    }

    /**
     * 列举bucketname下所有的key(name)
     * @return
     */
    @GetMapping("/key")
    public BaseResponse getKey(){
        OSSUtil ossUtil = new OSSUtil();
        List<OSSObjectSummary> sums = ossUtil.getUrl();
        List<String> collect = sums.stream().map(item -> item.getKey()).collect(Collectors.toList());
        for (OSSObjectSummary s : sums) {
            System.out.println("\t" + s.getKey());
        }
        return BaseResponse.success(collect,collect.size());
    }
    @GetMapping("/videoinfo")
    public BaseResponse getAllVideoInfo(){
        List<VideoInfo> videoInfoList = videoService.getAllVideoInfo();
        return BaseResponse.success(videoInfoList);
    }
    @GetMapping("/videoinfo/{userId}")
    public BaseResponse getVideoInfoByUserId(@PathVariable("userId") Long userId){
        List<VideoInfo> videoInfoList = videoService.getVideoInfoByUserId(userId);
        return BaseResponse.success(videoInfoList);
    }
    @GetMapping("/videoinfo/{id}")
    public BaseResponse getVideoInfoById(@PathVariable("id") Long id){
        VideoInfo videoInfo = videoService.getVideoInfoById(id);
        return BaseResponse.success(videoInfo);
    }




}
