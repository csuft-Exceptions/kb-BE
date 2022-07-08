package com.kb.user.dao;

import com.kb.user.pojo.user.User;
import org.springframework.stereotype.Repository;

/**
 * @author mawz
 * @version 1.0.0
 * @description
 * @date 2022/6/28 11:11
 */
@Repository
public interface UserMapper {

    /**
     * 详情操作
     * @param id
     * @return
     */
    User detail(Long id);

    /**
     * 添加操作
     * @param user
     * @return
     */
    Integer add(User user);

    /**
     * 更新操作
     * @param user
     * @return
     */
    Integer update(User user);

    /**
     * 删除操作
     * @param id
     * @return
     */
    Integer delete(Long id);

    /**
     * 根据key获取User
     * @param key
     * @return
     */
    User detailByKey(String key);
}
