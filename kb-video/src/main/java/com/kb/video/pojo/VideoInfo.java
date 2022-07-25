package com.kb.video.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class VideoInfo {
    private Long id;

    private Long userId;

    private String name;

    private Integer category;//分类

    private String url;

    private Date expirationTime;

    private Date createTime;

    private int deleteState;

    private long likes;

    private long plays;

    private long barrages;

    private long barragesId;

}
