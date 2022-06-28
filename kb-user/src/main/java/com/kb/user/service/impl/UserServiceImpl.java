package com.kb.user.service.impl;

import com.kb.common.base.BaseResponse;
import com.kb.common.utils.AssertUtil;
import com.kb.user.dao.UserMapper;
import com.kb.user.pojo.user.User;
import com.kb.user.pojo.userInfo.UserInfo;
import com.kb.user.service.api.UserService;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0.0
 * @description
 * @date 2022/6/28 10:51
 */
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public BaseResponse detail(Long id) {
        User user=userMapper.detail(id);
        return BaseResponse.success(user);
    }

    @Override
    public BaseResponse add(User user) {
        Integer count=userMapper.add(user);
        AssertUtil.assertNotEqual(1,count,"添加操作失败！");
        return BaseResponse.success("添加成功！");
    }

    @Override
    public BaseResponse update(User user) {
        AssertUtil.assertNull(user.getId(),"用户不存在");
        User temp=userMapper.detail(user.getId());
        AssertUtil.assertNull(temp,"用户不存在");
        checkParams(user);

        Integer count=userMapper.update(user);
        AssertUtil.assertNotEqual(1,count,"更新操作失败！");
        return BaseResponse.success("更新成功！");
    }

    @Override
    public BaseResponse delete(Long id) {
        Integer count=userMapper.delete(id);
        AssertUtil.assertNotEqual(1,count,"删除操作失败！");
        return BaseResponse.success("删除成功！");
    }

    private void checkParams(User user) {
    }
}
