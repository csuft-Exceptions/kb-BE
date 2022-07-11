package com.kb.video.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kb.video.mapper.VideoMapper;
import com.kb.video.pojo.VideoInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VideoService {

    @Resource
    VideoMapper videoMapper;

    public List<VideoInfo> getAllVideoInfo() {
        List<VideoInfo> videoInfoList = videoMapper.selectList(new QueryWrapper<>());
        return videoInfoList;
    }
}
