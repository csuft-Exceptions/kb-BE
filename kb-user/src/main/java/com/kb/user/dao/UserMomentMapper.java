package com.kb.user.dao;

import com.kb.user.pojo.userMoment.UserMoment;
import org.springframework.stereotype.Repository;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-22 - 22:14
 */
@Repository
public interface UserMomentMapper {
    /**
     * 添加动态,返回动态id
     * @param userMoment
     * @return
     */
    Integer addUserMoments(UserMoment userMoment);
}
