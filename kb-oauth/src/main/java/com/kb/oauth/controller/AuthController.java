package com.kb.oauth.controller;

import com.kb.common.base.BaseResponse;
import com.kb.oauth.service.api.AuthService;
import com.kb.oauth.vo.params.RegisterParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wjx
 * @create 2022/7/10 17:38
 */
@RestController
@RequestMapping("oauth")
public class AuthController {

    @Resource
    private AuthService authService;


    @PostMapping("/login")
    public BaseResponse loginByOther(RegisterParam loginParamByPass){
        BaseResponse response = authService.loginByPass(loginParamByPass);
        return response;
    }
}
