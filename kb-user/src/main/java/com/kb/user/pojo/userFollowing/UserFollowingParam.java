package com.kb.user.pojo.userFollowing;

import com.kb.common.base.BaseQuery;

/**
 * @author mawz
 * @version 1.0.0
 * @description
 * @date 2022/7/12 10:14
 */
public class UserFollowingParam extends BaseQuery {

    private Long userId;

    private Long groupId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
