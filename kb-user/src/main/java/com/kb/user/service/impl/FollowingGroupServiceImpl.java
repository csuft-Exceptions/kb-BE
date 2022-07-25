package com.kb.user.service.impl;

import com.kb.common.base.BaseResponse;
import com.kb.common.utils.AssertUtil;
import com.kb.user.dao.FollowingGroupMapper;
import com.kb.user.pojo.followingGroup.FollowingGroup;
import com.kb.user.service.api.FollowingGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mawz
 * @version 1.0.0
 * @description
 * @date 2022/7/11 12:41
 */
@Service
public class FollowingGroupServiceImpl implements FollowingGroupService {

    @Resource
    private FollowingGroupMapper followingGroupMapper;
    @Override
    public BaseResponse list(Long userId) {
        List<FollowingGroup> list =followingGroupMapper.list(userId);

        return BaseResponse.success(list,list.size());
    }

    @Override
    public BaseResponse add(FollowingGroup followingGroup) {
        Integer count=followingGroupMapper.add(followingGroup);
        AssertUtil.assertNotEquals(1,count,"添加操作失败,请重试!");
        return BaseResponse.success("添加成功!");
    }

    @Override
    public BaseResponse update(FollowingGroup followingGroup) {
        AssertUtil.isTrue(isPublic(followingGroup.getType()),"公共类型不能更新");

        Integer count=followingGroupMapper.update(followingGroup);
        AssertUtil.assertNotEquals(1,count,"更新操作失败,请重试!");
        return BaseResponse.success("更新成功!");
    }

    @Override
    public BaseResponse delete(Long id) {
        AssertUtil.assertNull(id,"分组不存在");
        FollowingGroup temp=followingGroupMapper.detail(id);
        AssertUtil.assertNull(temp,"分组不存在");
        AssertUtil.isTrue(isPublic(temp.getType()),"公共类型不能删除");

        Integer count=followingGroupMapper.delete(id);
        AssertUtil.assertNotEquals(1,count,"删除操作失败,请重试!");
        return BaseResponse.success("删除成功!");
    }

    @Override
    public BaseResponse detail(Long id) {
        FollowingGroup followingGroup=followingGroupMapper.detail(id);
        return BaseResponse.success(followingGroup);
    }

    public boolean isPublic(Integer type){
        return type <= 3;
    }
}
