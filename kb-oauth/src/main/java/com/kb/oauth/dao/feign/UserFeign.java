package com.kb.oauth.dao.feign;

import com.alibaba.fastjson.JSONObject;
import com.kb.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wjx
 * @create 2022/7/10 16:16
 */
@FeignClient(value = "user")
public interface UserFeign {
    @PostMapping("/user")
     BaseResponse add(@RequestBody JSONObject jsonObject);

    @GetMapping("/user/{key}")
     BaseResponse detailByKey(@PathVariable String key);
}
