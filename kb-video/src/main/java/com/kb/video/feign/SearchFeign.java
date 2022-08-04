package com.kb.video.feign;


import com.kb.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value = "search")
public interface SearchFeign {

    @PostMapping("/es-addVideo")
    public BaseResponse addVideo(Video video);
}
