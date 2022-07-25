package com.kb.video.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kb.video.dao.VideoDao;
import com.kb.video.dao.mapper.VideoMapper;
import com.kb.video.pojo.VideoInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VideoService {


    @Resource
    VideoDao videoDao;

    public List<VideoInfo> getAllVideoInfo() {
        List<VideoInfo> videoInfoList = videoDao.getAllVideoInfo();
        return videoInfoList;
    }

    public List<VideoInfo> getVideoInfoByUserId(Long userId) {
        List<VideoInfo> videoInfoList = videoDao.getVideoInfoByUserId(userId);
        return videoInfoList;
    }

    public VideoInfo getVideoInfoById(Long id) {
        VideoInfo videoInfo = videoDao.getVideoInfoById(id);
        return videoInfo;
    }
}
