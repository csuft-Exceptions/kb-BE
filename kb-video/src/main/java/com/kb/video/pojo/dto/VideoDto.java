package com.kb.video.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class VideoDto {

    private long userId;

    private String name;

    private int category;


}
