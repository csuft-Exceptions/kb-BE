package com.kb.job.service.impl;

import com.kb.common.base.BaseResponse;
import com.kb.job.pojo.permission.Permission;
import com.kb.job.service.api.PermissionService;
import org.springframework.stereotype.Service;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-29 - 16:36
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Override
    public BaseResponse add(Permission permission) {
        return null;
    }

    @Override
    public BaseResponse update(Permission permission) {
        return null;
    }

    @Override
    public BaseResponse delete(Integer[] ids) {
        return null;
    }

    @Override
    public BaseResponse addPermission(Integer roleId, Integer[] ids) {
        return null;
    }
}
