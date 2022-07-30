package com.kb.video.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
@Builder
public class VideoInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String name;

    private String url;

    private Integer category;//分类

    private Date expirationTime;

    private Date createTime;

    private Date updateTime;

    private Integer deleteState;

    private Long likes;

    private Long plays;

    private Long barrages;

    private Long barrageId;

    private Long picId;

}