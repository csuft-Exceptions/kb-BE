package com.kb.oauth.dao;

import com.kb.common.base.BaseResponse;
import com.kb.oauth.pojo.User;
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
    public BaseResponse add(@RequestBody User user);

    @GetMapping("/user/{key}")
    public BaseResponse detailByKey(@PathVariable String key);
}
