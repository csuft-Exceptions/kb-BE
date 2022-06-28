package com.kb.user.controller;

import com.kb.user.pojo.userInfo.UserInfo;
import com.kb.common.base.BaseResponse;
import com.kb.user.service.api.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-06-16 - 23:30
 */
@RestController
@Slf4j
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @GetMapping("/userinfo")
    public BaseResponse detail(Long id){
        return userInfoService.detail(id);
    }

    @PostMapping("/userinfo")
    public BaseResponse add(UserInfo userInfo){
        return userInfoService.add(userInfo);
    }

    @PutMapping("/userinfo")
    public BaseResponse update(UserInfo userInfo){
        return userInfoService.update(userInfo);
    }

    @DeleteMapping("/userinfo")
    public BaseResponse delete(Long id){
        return userInfoService.delete(id);
    }
}
