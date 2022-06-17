package com.kb.user.controller;

import cn.hutool.system.UserInfo;
import com.kb.common.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-06-16 - 23:30
 */
@RequestMapping("/user")
@Controller
@Slf4j
public class UserInfoController {

    @Resource
    private  UserInfoService userInfoService;

    @GetMapping("/info")
    public BaseResponse detail(Long id){
        return userInfoService.detail(id);
    }

    @PostMapping("/info")
    public BaseResponse add(UserInfo userInfo){
        return userInfoService.add(userInfo);
    }

    @PutMapping("/info")
    public BaseResponse update(UserInfo userInfo){
        return userInfoService.update(userInfo);
    }

    @DeleteMapping("/info")
    public BaseResponse delete(UserInfo userInfo){
        return userInfoService.delete(userInfo);
    }
}
