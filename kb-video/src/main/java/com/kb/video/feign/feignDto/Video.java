package com.kb.video.feign.feignDto;

import lombok.Data;

import java.util.Date;

@Data
public class Video {

    private Long id;

    private Long userId;

    private String name;

    private String url;

    private Integer category;

    private String duration;

    private Date createTime;

    private Date updateTime;

    private Integer deleteState;

    private Long likes;

    private Long plays;

    private Long barrages;

    private Long barrageId;

    private String picUrl;

    private String Introduction;

}
