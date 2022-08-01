package com.kb.file.service.api;

import com.kb.common.base.BaseResponse;
import com.kb.file.pojo.role.Role;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-29 - 16:11
 */

public interface RoleService {
    /**
     * 添加
     * @param role
     * @return
     */
    BaseResponse addRoles(Role role);

    /**
     * 更新
     * @param role
     * @return
     */
    BaseResponse update(Role role);

    /**
     * 删除
     * @param ids
     * @return
     */
    BaseResponse deleteRole(Integer[] ids);

    /**
     * 添加
     * @param roleId
     * @param ids
     * @return
     */
    BaseResponse addGrant(Integer roleId, Integer[] ids);
}
