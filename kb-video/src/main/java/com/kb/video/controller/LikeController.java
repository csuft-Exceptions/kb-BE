package com.kb.video.controller;

import com.kb.common.base.BaseResponse;
import com.kb.video.service.LikeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LikeController {

    @Resource
    private LikeService likeService;


    @PostMapping("/like/{videoId}")
    public BaseResponse increaseLike(@PathVariable("videoId") Long videoId) {

        likeService.increaseLike(videoId);

        return BaseResponse.success(null);
    }

    public BaseResponse decreaseLike(@PathVariable("videoId") Long videoId) {

        likeService.decreaseLike(videoId);

        return BaseResponse.success(null);
    }
}
