package com.kb.video.service;

import com.kb.video.dao.VideoDao;
import com.kb.video.pojo.VideoInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VideoService {


    @Resource
    VideoDao videoDao;


    public List<VideoInfo> getAllVideoInfo() {
        return videoDao.getAllVideoInfo();
    }

    public List<VideoInfo> getVideoInfoByUserId(Long userId) {
        return videoDao.getVideoInfoByUserId(userId);
    }

    public VideoInfo getVideoInfoById(Long id) {
        return videoDao.getVideoInfoById(id);
    }

    public Long addVideoInfo(VideoInfo videoInfo) {

        videoDao.addVideoInfo(videoInfo);

        return videoInfo.getId();
    }
}
