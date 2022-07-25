package com.kb.user.dao;

import com.kb.user.pojo.followingGroup.FollowingGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mawz
 * @version 1.0.0
 * @description
 * @date 2022/7/11 12:42
 */
@Repository
public interface FollowingGroupMapper {
    /**
     * 查询分列表
     * @param userId
     * @return
     */
    List<FollowingGroup> list(Long userId);

    /**
     * 添加用户自定义列表
     * @param followingGroup
     * @return
     */
    Integer add(FollowingGroup followingGroup);

    /**
     * 更新分组（name）
     * @param followingGroup
     * @return
     */
    Integer update(FollowingGroup followingGroup);

    /**
     * 删除分组
     * @param id
     * @return
     */
    Integer delete(Long id);

    /**
     * 详情接口
     * @param id
     * @return
     */
    FollowingGroup detail(Long id);
}
