package com.kb.user.service.api;

import com.kb.common.base.BaseResponse;
import com.kb.user.pojo.followingGroup.FollowingGroup;

/**
 * @author mawz
 * @version 1.0.0
 * @description
 * @date 2022/7/11 12:39
 */
public interface FollowingGroupService {

    /**
     * 查询分组列表 公用和UserId,暂时不做分页,id降序排列
     * @param userId
     * @return
     */
    BaseResponse list(Long userId);

    /**
     * 添加用户自定义列表
     * @param followingGroup
     * @return
     */
    BaseResponse add(FollowingGroup followingGroup);

    /**
     * 更新分组（name）
     * @param followingGroup
     * @return
     */
    BaseResponse update(FollowingGroup followingGroup);

    /**
     * 删除分组
     * @param id
     * @return
     */
    BaseResponse delete(Long id);

    /**
     * 详情接口
     * @param id
     * @return
     */
    BaseResponse detail(Long id);
}
