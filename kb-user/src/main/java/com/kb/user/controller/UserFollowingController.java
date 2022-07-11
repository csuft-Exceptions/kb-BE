package com.kb.user.controller;

import com.kb.common.base.BaseResponse;
import com.kb.user.pojo.userFollowing.UserFollowing;
import com.kb.user.service.api.UserFollowingService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0.0
 * @description
 * @date 2022/7/11 12:34
 */
@RestController
public class UserFollowingController {
    @Resource
    private UserFollowingService userFollowingService;

    @PostMapping("following")
    public BaseResponse add(@RequestBody  UserFollowing userFollowing){
        return   userFollowingService.add(userFollowing);
    }

    @DeleteMapping("following")
    public BaseResponse delete(Long id){
        return  userFollowingService.delete(id);
    }

    // todo 根据userid和groupId查询list建个联合索引，userid查询粉丝列表
}
