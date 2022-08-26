package com.kb.video.feign;


import com.kb.common.base.BaseResponse;
import com.kb.video.feign.feignDto.Video;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value = "search")
public interface SearchFeign {

    @PostMapping("/es-addVideo")
    BaseResponse addVideo(Video video);
}
