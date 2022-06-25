package com.kb.user.dao;

import com.kb.user.pojo.userInfo.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-06-17 - 15:30
 */
@Repository
public interface UserInfoMapper {
    /**
     * 详情
     * @param id
     * @return UserInfo
     */
    UserInfo detail(Long id);

    /**
     * 添加
     * @param userInfo
     * @return
     */
    Integer add(UserInfo userInfo);

    /**
     * 更新
     * @param userInfo
     * @return
     */
    Integer update(UserInfo userInfo);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer delete(Long id);
}
