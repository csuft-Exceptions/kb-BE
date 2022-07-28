package com.kb.video.pojo.rich;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

/**
 * 首页
 */
@Data
@Builder
@ApiModel
public class VideoInfoRich {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String name;

    private String url;

    private Integer category;//分类

    private String picUrl;
}
