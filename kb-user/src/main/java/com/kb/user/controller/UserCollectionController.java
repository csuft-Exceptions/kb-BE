package com.kb.user.controller;

import com.kb.common.base.BaseResponse;
import com.kb.user.pojo.userCollection.UserCollection;
import com.kb.user.pojo.userCollection.UserCollectionParam;
import com.kb.user.service.api.UserCollectionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-23 - 16:24
 */
@RestController
public class UserCollectionController {
    @Resource
    private UserCollectionService userCollectionService;

    @PostMapping("collect")
    public BaseResponse add(@RequestBody UserCollection userCollection){
        return   userCollectionService.add(userCollection);
    }

    /**
     * problem 可能不一定是id
     * @param id
     * @return
     */
    @DeleteMapping("collect")
    public BaseResponse delete(Long id){
        return  userCollectionService.delete(id);
    }

    /**
     * todo 根据userid和groupId查询list建个联合索引，userid查询粉丝列表
     * 分页查询
     * @param userCollectionParam
     * @return
     */
    @GetMapping("collect")
    public BaseResponse list(UserCollectionParam userCollectionParam){
        return userCollectionService.list(userCollectionParam);
    }

    @GetMapping("collectCount")
    public BaseResponse count(Long videoId){
        return userCollectionService.count(videoId);
    }
}
