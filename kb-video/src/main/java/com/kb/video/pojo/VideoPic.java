package com.kb.video.pojo;


import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

@Data
@ApiModel
@Builder
public class VideoPic {

    long id;

    String url;
}
