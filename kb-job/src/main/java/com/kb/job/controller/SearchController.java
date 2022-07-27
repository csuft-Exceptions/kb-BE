package com.kb.job.controller;

import com.kb.job.service.impl.ElasticSearchService;
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
    @GetMapping("/es-videos")
    public void getEsVideos(@RequestParam String keyword){
        elasticSearchService.getVideo(keyword);
    }
}
