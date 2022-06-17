package com.kb.user.service.impl;

import cn.hutool.system.UserInfo;
import com.kb.common.base.BaseResponse;
import com.kb.common.utils.AssertUtil;
import com.kb.user.dao.UserInfoMapper;
import com.kb.user.service.api.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-06-17 - 15:29
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public BaseResponse detail(Long id) {
        userInfoMapper.detail(id);
        return null;
    }

    @Override
    public BaseResponse add(UserInfo userInfo) {
        // todo 有个地方能取到操作人/前端取
        // log.info("{} 尝试添加一条userInfo",user);
        // 参数校验
        checkParams(userInfo);

        Integer count=userInfoMapper.add(userInfo);
        AssertUtil.assertNotEquals(1,count,"添加操作失败,请重试!");
        return BaseResponse.success("添加成功!");
    }

    @Override
    public BaseResponse update(UserInfo userInfo) {
        // todo log
        AssertUtil.assertNull(userInfo.getId(),"用户不存在");
        UserInfo temp=userInfoMapper.detail(userInfo.getId());
        AssertUtil.assertNull(temp,"用户不存在");
        checkParams(userInfo);

        Integer count=userInfoMapper.update(userInfo);
        AssertUtil.assertNotEquals(1,count,"更新操作失败,请重试!");
        return BaseResponse.success("更新成功!");
    }

    @Override
    public BaseResponse delete(UserInfo userInfo) {
        AssertUtil.assertNull(userInfo.getId(),"用户不存在");
        UserInfo temp=userInfoMapper.detail(userInfo.getId());
        AssertUtil.assertNull(temp,"用户不存在");

        Integer count=userInfoMapper.delete(userInfo);
        AssertUtil.assertNotEquals(1,count,"删除操作失败,请重试!");
        return BaseResponse.success("删除成功!");
    }
}
