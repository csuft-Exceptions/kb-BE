package com.kb.user.controller;

import com.kb.common.base.BaseResponse;
import com.kb.user.pojo.followingGroup.FollowingGroup;
import com.kb.user.service.api.FollowingGroupService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0.0
 * @description
 * @date 2022/7/11 12:34
 */
@RestController
public class FollowingGroupController {

    @Resource
    private FollowingGroupService followingGroupService;

    @GetMapping("/followingGroup/list/{userId}")
    public BaseResponse list(@PathVariable Long userId){
        return followingGroupService.list(userId);
    }

    @PostMapping("/followingGroup")
    public BaseResponse add(@RequestBody FollowingGroup followingGroup){
        return followingGroupService.add(followingGroup);
    }

    @PutMapping("/followingGroup")
    public BaseResponse update(@RequestBody FollowingGroup followingGroup){
        return followingGroupService.update(followingGroup);
    }

    @DeleteMapping("/followingGroup/{id}")
    public BaseResponse delete(@PathVariable Long id){
        return followingGroupService.delete(id);
    }

    @GetMapping("/followingGroup/{id}")
    public BaseResponse detail(@PathVariable Long id){
        return followingGroupService.detail(id);
    }
}
