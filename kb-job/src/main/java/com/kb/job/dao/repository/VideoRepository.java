package com.kb.job.dao.repository;

import com.kb.job.pojo.search.Video;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-27 - 22:14
 */
public interface VideoRepository extends ElasticsearchRepository<Video,Long> {
    /**
     * find by title(name) like
     * @param keyword
     * @return
     */
    Video findByTitleLike(String keyword);
}
