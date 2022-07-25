package com.kb.video.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class BarrageInfo {

    private Long id;

    private int offsetTime;//偏移量（相对于0）  视频播放时间

    private String content;

    private Date time;//发送时间


}
