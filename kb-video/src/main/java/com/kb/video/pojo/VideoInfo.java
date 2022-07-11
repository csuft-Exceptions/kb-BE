package com.kb.video.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class VideoInfo {
    private long id;

    private String name;

    private int category;//分类

    private String url;

    private Date expirationTime;

    private Date createTime;

}
