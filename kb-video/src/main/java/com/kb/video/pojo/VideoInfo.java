package com.kb.video.pojo;

import lombok.Data;

@Data
public class VideoInfo {
    long id;

    String name;

    int type;//分类

    String url;

    long picId;//fm

}
