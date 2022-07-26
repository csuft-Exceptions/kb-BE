package com.kb.video.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class BarrageInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private int offsetTime;//偏移量（相对于0）  视频播放时间

    private String content;

    private Date time;//发送时间


}
