package com.kb.user.controller;

import com.kb.common.base.BaseResponse;
import com.kb.user.pojo.collections.Collections;
import com.kb.user.service.api.CollectionsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-23 - 16:38
 */
public class CollectionsController {
    @Resource
    private CollectionsService collectionsService;

    @GetMapping("/followingGroup/list/{userId}")
    public BaseResponse list(@PathVariable Long userId){
        return collectionsService.list(userId);
    }

    @PostMapping("/followingGroup")
    public BaseResponse add(@RequestBody Collections collections){
        return collectionsService.add(collections);
    }

    @PutMapping("/followingGroup")
    public BaseResponse update(@RequestBody Collections collections){
        return collectionsService.update(collections);
    }

    @DeleteMapping("/followingGroup/{id}")
    public BaseResponse delete(@PathVariable Long id){
        return collectionsService.delete(id);
    }

    @GetMapping("/followingGroup/{id}")
    public BaseResponse detail(@PathVariable Long id){
        return collectionsService.detail(id);
    }
}
