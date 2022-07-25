package com.kb.user.controller;

import com.kb.common.base.BaseResponse;
import com.kb.user.pojo.userMoment.UserMoment;
import com.kb.user.service.api.UserMomentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-22 - 21:47
 */
@RestController
@Slf4j
public class UserMomentController {

    @Resource
    private UserMomentService userMomentService;

    /**
     * userid暂时由前端拿取,后续如需后端拿取,会进行改造
     * @param userMoment
     * @return
     */
    @PostMapping("/user-moments")
    public BaseResponse addUserMoments(@RequestBody UserMoment userMoment){
        return userMomentService.addUserMoments(userMoment);
    }

    @GetMapping("/user-moments")
    public BaseResponse getMoments(Long userId){
        return userMomentService.getMoments(userId);
    }
}
