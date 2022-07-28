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
     *
     * 分页查询
     * @param userCollectionParam
     * @return
     */
    @GetMapping("collect")
    public BaseResponse list(UserCollectionParam userCollectionParam){
        return userCollectionService.list(userCollectionParam);
    }

    @GetMapping("videoCollectCount")
    public BaseResponse count(Long contentId){
        return userCollectionService.count(contentId);
    }

    @GetMapping("userCollectCount")
    public BaseResponse userCollectCount(Long userId){
        return userCollectionService.userCollectCount(userId);
    }
}
