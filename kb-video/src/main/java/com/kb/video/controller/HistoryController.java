package com.kb.video.controller;


import com.kb.common.base.BaseResponse;
import com.kb.video.pojo.dto.HistoryDto;
import com.kb.video.service.HistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@Api("history")
public class HistoryController {

    @Resource
    HistoryService historyService;


    @PostMapping("/history")
    @ApiOperation("add history")
    public BaseResponse addHistory(@RequestBody HistoryDto historyDto){

        historyService.addHistory(historyDto.getUserId(),historyDto.getVideoId());

        return BaseResponse.success(null);
    }

    @GetMapping("/history/{userId}")
    @ApiOperation("get history")
    public BaseResponse getHistory(@PathVariable("userId") Long userId){

        Map<Long, Long> history = historyService.getHistory(userId);

        return BaseResponse.success(history,history.size());
    }

    @DeleteMapping("/history")
    @ApiOperation("delete history")
    public BaseResponse removeOneHistory(@RequestBody HistoryDto historyDto){

        historyService.removeOneHistory(historyDto.getUserId(),historyDto.getVideoId());

        return BaseResponse.success(null);
    }

}
