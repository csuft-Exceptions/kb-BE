package com.kb.oauth.controller;

import com.kb.common.base.BaseResponse;
import com.kb.oauth.service.Impl.AuthServiceImpl;
import com.kb.oauth.service.api.AuthService;
import com.kb.oauth.vo.params.RegisterParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wjx
 * @create 2022/7/10 17:38
 */
@RestController
@RequestMapping("oauth")
@Slf4j
public class AuthController {

    @Resource
    private AuthService authService;
    @PostMapping("/login")
    public BaseResponse loginByPwd(@RequestBody RegisterParam loginParamByPass){
        log.info("username:",loginParamByPass.getUsername());
        log.info("password:",loginParamByPass.getPassword());
        BaseResponse response = authService.loginByPass(loginParamByPass);
        return response;
    }


    @PostMapping("/logout")
    public BaseResponse logout(HttpServletRequest request){
        BaseResponse response = authService.logout(request);
        return response;
    }
}
