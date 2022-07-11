package com.kb.user.dao;

import com.kb.user.pojo.userFollowing.UserFollowing;
import org.springframework.stereotype.Repository;

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
}
