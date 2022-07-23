package com.kb.user.service.api;

import com.kb.common.base.BaseResponse;
import com.kb.user.pojo.userMoment.UserMoment;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-22 - 22:11
 */
public interface UserMomentService {

    /**
     * 添加动态
     * @param userMoment
     * @return
     */
    BaseResponse addUserMoments(UserMoment userMoment);

    /**
     * 查询用户动态
     * @param userId
     * @return
     */
    BaseResponse getMoments(Long userId);
}
