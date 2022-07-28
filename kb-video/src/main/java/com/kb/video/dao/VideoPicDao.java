package com.kb.video.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kb.video.dao.mapper.VideoMapper;
import com.kb.video.dao.mapper.VideoPicMapper;
import com.kb.video.pojo.VideoInfo;
import com.kb.video.pojo.VideoPic;
import org.springframework.stereotype.Service;

@Service
public class VideoPicDao extends ServiceImpl<VideoPicMapper, VideoPic> {

    public String getPicUrlById(Long picId) {
        VideoPic videoPic = this.getOne(new QueryWrapper<VideoPic>()
                .lambda()
                .eq(VideoPic::getId,picId)
        );
        return videoPic.getUrl();
    }


}
