package com.kb.oauth.service.api;

import com.kb.common.base.BaseResponse;
import com.kb.oauth.vo.params.RegisterParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wjx
 * @create 2022/7/7 20:47
 */
public interface AuthService {
    /**
     * 密码登录
     * @param loginParamByPass
     * @return
     */
    BaseResponse loginByPass(RegisterParam loginParamByPass);

    /**
     * 注销
     * @param request
     * @return
     */
    BaseResponse logout(HttpServletRequest request);
}
