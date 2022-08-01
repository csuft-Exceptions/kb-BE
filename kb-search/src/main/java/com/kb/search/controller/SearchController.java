package com.kb.search.controller;

import com.kb.common.base.BaseResponse;
import com.kb.search.service.impl.ElasticSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-24 - 17:27
 */
public class SearchController {

    @Resource
    private ElasticSearchService elasticSearchService;
    @GetMapping("/es")
    public BaseResponse getEsVideos(@RequestParam String keyword){
        return BaseResponse.success(elasticSearchService.getVideo(keyword));
    }
    @GetMapping("contents")
    public BaseResponse getContents(@RequestParam String keyword,
                                    @RequestParam Integer pageNo,
                                    @RequestParam Integer pageSize){
        return BaseResponse.success(elasticSearchService.getContents(keyword, pageNo, pageSize));
    }

}
