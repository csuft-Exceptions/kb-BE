package com.kb.job.service.api;

import com.kb.common.base.BaseResponse;
import com.kb.job.pojo.permission.Permission;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-29 - 16:30
 */
public interface PermissionService {
    /**
     * 添加
     * @param permission
     * @return
     */
    BaseResponse add(Permission permission);

    /**
     * 更新
     * @param permission
     * @return
     */
    BaseResponse update(Permission permission);

    /**
     * 删除
     * @param ids
     * @return
     */
    BaseResponse delete(Integer[] ids);

    /**
     * 授权
     * @param roleId
     * @param ids
     * @return
     */
    BaseResponse addPermission(Integer roleId, Integer[] ids);
}
