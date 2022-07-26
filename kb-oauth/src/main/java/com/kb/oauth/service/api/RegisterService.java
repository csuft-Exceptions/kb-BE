package com.kb.oauth.service.api;

import com.kb.common.base.BaseResponse;
import com.kb.oauth.vo.params.RegisterParam;

/**
 * @author wjx
 * @create 2022/7/10 16:07
 */
public interface RegisterService {
    /**
     * 注册
     * @param registerParam
     * @return
     */
    BaseResponse register(RegisterParam registerParam);
}
