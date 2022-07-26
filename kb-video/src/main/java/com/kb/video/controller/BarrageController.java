package com.kb.video.controller;


import com.kb.common.base.BaseResponse;
import com.kb.video.pojo.BarrageInfo;
import com.kb.video.service.BarrageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@Api("barrage")
public class BarrageController {

    @Resource
    BarrageService barrageService;


    /**
     * 以时间为单位分片获取
     *
     * @param videoId
     * @param date
     * @return
     */
    @GetMapping("/bar/{videoId}/{date}")
    @ApiOperation("以时间为单位分片获取dm")
    public BaseResponse getBarrageByTime(@PathVariable("videoId") Long videoId, @PathVariable("date") Date date) {

        List<BarrageInfo> barrages = barrageService.getBarrageByTime(videoId, date);

        return BaseResponse.success(barrages);

    }

    /**
     * 获取所有
     *
     * @param videoId
     * @return
     */
    @GetMapping("/bar/{videoId}")
    @ApiOperation("获取所有dm")
    public BaseResponse getAllBarrage(@PathVariable("videoId") Long videoId) {

        List<BarrageInfo> res = barrageService.getAllBarrage(videoId);

        return BaseResponse.success(res);

    }

    /**
     * 添加一条
     *
     * @param videoId
     * @param date
     * @param barrageInfo
     * @return
     */
    @PostMapping("/bar/{videoId}/{date}")
    @ApiOperation("添加一条dm")
    public BaseResponse addOneBarrage(@PathVariable("videoId") Long videoId, @PathVariable("date") Date date, @RequestBody BarrageInfo barrageInfo) {

        barrageService.addOneBarrage(videoId, date, barrageInfo);

        return BaseResponse.success(null);


    }
}
