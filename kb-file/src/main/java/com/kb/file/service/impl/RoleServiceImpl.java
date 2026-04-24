package com.kb.file.service.impl;

import com.kb.common.base.BaseResponse;
import com.kb.file.pojo.role.Role;
import com.kb.file.service.api.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-29 - 16:36
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public BaseResponse addRoles(Role role) {
        return BaseResponse.failed("角色服务暂未实现");
    }

    @Override
    public BaseResponse update(Role role) {
        return BaseResponse.failed("角色服务暂未实现");
    }

    @Override
    public BaseResponse deleteRole(Integer[] ids) {
        return BaseResponse.failed("角色服务暂未实现");
    }

    @Override
    public BaseResponse addGrant(Integer roleId, Integer[] ids) {
        return BaseResponse.failed("角色服务暂未实现");
    }
}
