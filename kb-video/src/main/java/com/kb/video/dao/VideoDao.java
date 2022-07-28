package com.kb.video.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kb.common.exception.InfoException;
import com.kb.video.dao.mapper.VideoMapper;
import com.kb.video.pojo.VideoInfo;
import com.kb.video.pojo.VideoPic;
import com.kb.video.pojo.dto.VideoDto;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class VideoDao extends ServiceImpl<VideoMapper, VideoInfo> {

    @Resource
    VideoMapper videoMapper;

    public List<VideoInfo> getAllVideoInfo() {
        List<VideoInfo> list = this.list(new QueryWrapper<VideoInfo>()
                .lambda()
                .eq(VideoInfo::getDeleteState, 0)
        );

        return list;
    }

    public List<VideoInfo> getVideoInfoByUserId(Long userId) {
        List<VideoInfo> list = this.list(new QueryWrapper<VideoInfo>()
                .lambda()
                .eq(VideoInfo::getUserId, userId)
                .eq(VideoInfo::getDeleteState, 0)
        );

        return list;
    }


    public VideoInfo getVideoInfoById(Long id) {
        VideoInfo videoInfo = this.getOne(new QueryWrapper<VideoInfo>()
                .lambda()
                .eq(VideoInfo::getId, id)
                .eq(VideoInfo::getDeleteState, 0)
        );

        return videoInfo;
    }

    public Long addVideoInfo(VideoInfo videoInfo) {
        int save = videoMapper.insert(videoInfo);
        if (save <= 0) {
            throw new InfoException("添加失败");
        }
        return videoInfo.getId();
    }


}
