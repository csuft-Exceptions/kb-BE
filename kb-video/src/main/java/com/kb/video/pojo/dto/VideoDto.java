package com.kb.video.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class VideoDto implements Serializable {

    private long userId;

    private String name;

    private int category;

    private String Introduction;

}
