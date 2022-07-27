package com.kb.job.service.impl;

import com.kb.job.dao.repository.VideoRepository;
import com.kb.job.pojo.search.Video;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-27 - 22:12
 */
@Service
public class ElasticSearchService {
    @Resource
    private VideoRepository videoRepository;

    public  void addVideo(Video video){
        videoRepository.save(video);
    }

    public  Video getVideo(String keyword){
        return videoRepository.findByTitleLike(keyword);
    }

    public  void deleteAllVideos(){
        videoRepository.deleteAll();
    }

}
