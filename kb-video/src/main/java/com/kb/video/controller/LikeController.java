package com.kb.video.controller;

import com.kb.common.base.BaseResponse;
import com.kb.video.service.LikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api("like相关")
public class LikeController {

    @Resource
    private LikeService likeService;


    @PostMapping("/likeIn/{videoId}")
    @ApiOperation("like+1")
    public BaseResponse increaseLike(@PathVariable("videoId") Long videoId) {

        likeService.increaseLike(videoId);

        return BaseResponse.success(likeService.getLikes(videoId));
    }

    @PostMapping("/likeDe/{videoId}")
    @ApiOperation("like-1")
    public BaseResponse decreaseLike(@PathVariable("videoId") Long videoId) {

        likeService.decreaseLike(videoId);

        return BaseResponse.success(likeService.getLikes(videoId));
    }

    @GetMapping("/like/{videoId}")
    @ApiOperation("获得like数量")
    public BaseResponse getLikes(@PathVariable("videoId") Long videoId) {

        Long count = likeService.getLikes(videoId);

        if(count == null)count = 0L;

        return BaseResponse.success(count);
    }
}
