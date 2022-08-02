package com.kb.video.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class HistoryDto {

    Long userId;

    Long videoId;

}
