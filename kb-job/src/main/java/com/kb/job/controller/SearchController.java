package com.kb.job.controller;

import com.kb.job.pojo.search.Video;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-24 - 17:27
 */
public class SearchController {

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @PostMapping("/findOpenVideoByVideoName")
    public Page<Video> findOpenUserByUserName(@RequestParam(value = "videoName") String videoName,
                                              @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (StringUtils.isBlank(videoName)) {
            return null;
        }
        if (pageNum == null || pageNum < 0) {
            pageNum = 0;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = 10;
        }
        // 分页，根据时间倒序
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "createTime");
        // 查询姓名
        QueryBuilder builder = null;
        if (videoName.matches("^[A-Za-z0-9]+$")) {
            builder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.wildcardQuery("userName", ("*" + videoName + "*").toLowerCase()));
        } else {
            builder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchPhraseQuery("userName", videoName.toLowerCase()));
        }
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(builder).withPageable(pageable).build();
        return elasticsearchTemplate.queryForPage((Query)nativeSearchQuery,Video.class,null);
    }
}
