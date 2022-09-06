package com.kb.video.service;

import com.kb.video.dao.VideoDao;
import com.kb.video.feign.SearchFeign;
import com.kb.video.feign.feignDto.Video;
import com.kb.video.pojo.VideoInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VideoService {


    @Resource
    VideoDao videoDao;


    @Resource
    SearchFeign searchFeign;

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

        Video video = buildVideo(videoInfo);
        //添加到es
        searchFeign.addVideo(video);

        return videoInfo.getId();
    }

    private Video buildVideo(VideoInfo videoInfo) {
        Video video = new Video();

        video.setName(videoInfo.getName());
        video.setCategory(videoInfo.getCategory());
        video.setCreateTime(videoInfo.getCreateTime());
        video.setBarrages(0L);
        video.setDuration(videoInfo.getDuration());
        video.setLikes(0L);
        video.setDeleteState(0);
        video.setIntroduction(videoInfo.getIntroduction());
        video.setPicUrl(videoInfo.getPicUrl());
        video.setPlays(0L);
        video.setUserId(videoInfo.getUserId());
        video.setUpdateTime(videoInfo.getUpdateTime());

        return video;
    }
}
