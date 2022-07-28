package com.kb.video.service;

import com.aliyun.oss.model.OSSObjectSummary;
import com.kb.common.exception.InfoException;
import com.kb.video.dao.VideoDao;
import com.kb.video.dao.VideoPicDao;
import com.kb.video.pojo.VideoInfo;
import com.kb.video.pojo.dto.VideoDto;
import com.kb.video.pojo.rich.VideoInfoRich;
import com.kb.video.utils.OSSUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VideoService {


    @Resource
    VideoDao videoDao;

    @Resource
    VideoPicDao videoPicDao;

    public List<VideoInfoRich> getAllVideoInfo() {
        List<VideoInfo> videoInfoList = videoDao.getAllVideoInfo();
        return videoInfoListToRich(videoInfoList);
    }

    private List<VideoInfoRich> videoInfoListToRich(List<VideoInfo> videoInfoList) {
        List<VideoInfoRich> videoInfoRichList = new ArrayList<>();
        for (VideoInfo videoInfo : videoInfoList) {
            String picUrl = videoPicDao.getPicUrlById(videoInfo.getPicId());
            videoInfoRichList.add(VideoInfoRich.builder()
                    .category(videoInfo.getCategory())
                    .id(videoInfo.getId())
                    .name(videoInfo.getName())
                    .userId(videoInfo.getUserId())
                    .url(videoInfo.getUrl())
                    .picUrl(picUrl)
                    .build());
        }
        return videoInfoRichList;
    }

    public List<VideoInfoRich> getVideoInfoByUserId(Long userId) {
        List<VideoInfo> videoInfoList = videoDao.getVideoInfoByUserId(userId);
        return videoInfoListToRich(videoInfoList);
    }

    public VideoInfo getVideoInfoById(Long id) {
        VideoInfo videoInfo = videoDao.getVideoInfoById(id);
        return videoInfo;
    }

    public Long addVideoInfo(VideoDto videoDto) {
        OSSUtil ossUtil = new OSSUtil();
        List<OSSObjectSummary> urls = ossUtil.getUrl();
        Set<String> set = urls.stream().map(x -> x.getKey()).collect(Collectors.toSet());
        if (!set.contains(videoDto.getName())) {
            throw new InfoException("添加失败");
        }
        String url = ossUtil.getUrl(videoDto.getName());
        VideoInfo videoInfo = VideoInfo.builder()
                .id(null)
                .userId(videoDto.getUserId())
                .createTime(new Date())
                .updateTime(new Date())
                .category(videoDto.getCategory())
                .name(videoDto.getName())
                .url(url)
                .build();
        videoDao.addVideoInfo(videoInfo);

        return videoInfo.getId();
    }
}
