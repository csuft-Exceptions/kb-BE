package com.kb.user.service.impl;

import com.kb.common.base.BaseResponse;
import com.kb.common.utils.AssertUtil;
import com.kb.user.dao.UserFollowingMapper;
import com.kb.user.pojo.userFollowing.UserFollowing;
import com.kb.user.service.api.UserFollowingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0.0
 * @description
 * @date 2022/7/11 12:41
 */
@Service
public class UserFollowingServiceImpl implements UserFollowingService {

    @Resource
    private UserFollowingMapper userFollowingMapper;

    @Override
    public BaseResponse add(UserFollowing userFollowing) {
        Integer count=userFollowingMapper.add(userFollowing);
        AssertUtil.assertNotEquals(1,count,"关注操作失败,请重试!");
        return BaseResponse.success("关注成功!");
    }

    @Override
    public BaseResponse delete(Long id) {
        Integer count=userFollowingMapper.delete(id);
        AssertUtil.assertNotEquals(1,count,"取关操作失败,请刷新重试!");
        return BaseResponse.success("取关成功!");
    }
}
