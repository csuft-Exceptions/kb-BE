package com.kb.video.feign;


import com.kb.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "search")
public interface SearchFeign {

    @GetMapping("/es-videos")
    BaseResponse getEsVideos(@RequestParam String keyword);
}
