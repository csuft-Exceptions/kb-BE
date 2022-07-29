package com.kb.job.service.impl;

import com.kb.common.base.BaseResponse;
import com.kb.job.pojo.role.Role;
import com.kb.job.service.api.RoleService;
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
        return null;
    }

    @Override
    public BaseResponse update(Role role) {
        return null;
    }

    @Override
    public BaseResponse deleteRole(Integer[] ids) {
        return null;
    }

    @Override
    public BaseResponse addGrant(Integer roleId, Integer[] ids) {
        return null;
    }
}
