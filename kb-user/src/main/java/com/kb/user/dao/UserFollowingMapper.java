package com.kb.user.dao;

import com.kb.user.pojo.userFollowing.UserFollowing;
import com.kb.user.pojo.userFollowing.UserFollowingParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mawz
 * @version 1.0.0
 * @description
 * @date 2022/7/11 12:42
 */
@Repository
public interface UserFollowingMapper {

    /**
     * 关注
     * @param userFollowing
     * @return
     */
    Integer add(UserFollowing userFollowing);

    /**
     * 取关
     * @param id
     * @return
     */
    Integer delete(Long id);

    /**
     * 关注列表
     * @param userFollowingParam
     * @return
     */
    List<UserFollowing> list(UserFollowingParam userFollowingParam);

    /**
     * 粉丝列表
     * @param followingId
     * @return
     */
    List<UserFollowing> fans(Long followingId);

    /**
     * 粉丝数
     * @param followingId
     * @return
     */
    Long fansCount(Long followingId);

    /**
     * 关注数
     * @param userId
     * @return
     */
    Long followingCount(Long userId);
}
