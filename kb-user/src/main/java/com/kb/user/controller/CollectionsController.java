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
@RestController
public class CollectionsController {
    @Resource
    private CollectionsService collectionsService;

    @GetMapping("/collections/list/{userId}")
    public BaseResponse list(@PathVariable Long userId){
        return collectionsService.list(userId);
    }

    @PostMapping("/collections")
    public BaseResponse add(@RequestBody Collections collections){
        return collectionsService.add(collections);
    }

    @PutMapping("/collections")
    public BaseResponse update(@RequestBody Collections collections){
        return collectionsService.update(collections);
    }

    @DeleteMapping("/collections/{id}")
    public BaseResponse delete(@PathVariable Long id){
        return collectionsService.delete(id);
    }

    @GetMapping("/collections/{id}")
    public BaseResponse detail(@PathVariable Long id){
        return collectionsService.detail(id);
    }
}
