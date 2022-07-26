package com.kb.video.controller;

import com.kb.common.base.BaseResponse;
import com.kb.video.service.LikeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class LikeController {

    @Resource
    private LikeService likeService;


    @PostMapping("/likeIn/{videoId}")
    public BaseResponse increaseLike(@PathVariable("videoId") Long videoId) {

        likeService.increaseLike(videoId);

        return BaseResponse.success(null);
    }

    @PostMapping("/likeDe/{videoId}")
    public BaseResponse decreaseLike(@PathVariable("videoId") Long videoId) {

        likeService.decreaseLike(videoId);

        return BaseResponse.success(null);
    }

    @GetMapping("/like/{videoId}")
    public BaseResponse getLikes(@PathVariable("videoId") Long videoId) {

        Long count = likeService.getLikes(videoId);

        return BaseResponse.success(count);
    }
}
