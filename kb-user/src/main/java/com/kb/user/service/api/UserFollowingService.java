package com.kb.user.service.api;

import com.kb.common.base.BaseResponse;
import com.kb.user.pojo.userFollowing.UserFollowing;
import com.kb.user.pojo.userFollowing.UserFollowingParam;

/**
 * @author mawz
 * @version 1.0.0
 * @description
 * @date 2022/7/11 12:39
 */
public interface UserFollowingService {
    /**
     * 关注
     * @param userFollowing
     * @return
     */
    BaseResponse add(UserFollowing userFollowing);

    /**
     * 取关
     * @param id
     * @return
     */
    BaseResponse delete(Long id);

    /**
     * 根据userId和groupId查询
     * @param userFollowingParam
     * @return
     */
    BaseResponse list(UserFollowingParam userFollowingParam);
}
