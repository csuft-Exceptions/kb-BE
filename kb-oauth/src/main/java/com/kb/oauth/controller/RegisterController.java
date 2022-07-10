package com.kb.oauth.controller;

import com.kb.common.base.BaseResponse;
import com.kb.oauth.service.api.RegisterService;
import com.kb.oauth.vo.params.RegisterParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wjx
 * @create 2022/7/10 16:04
 */
@RestController
@RequestMapping("/")
public class RegisterController {

    @Resource
    private RegisterService registerService;

    @PostMapping("/register")
    public BaseResponse register(@RequestBody RegisterParam registerParam){
        return registerService.register(registerParam);
    }
}
