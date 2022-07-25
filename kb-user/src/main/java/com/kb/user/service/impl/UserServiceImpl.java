package com.kb.user.service.impl;

import com.kb.common.base.BaseResponse;
import com.kb.common.utils.AssertUtil;
import com.kb.common.utils.EmailUtil;
import com.kb.common.utils.PhoneUtil;
import com.kb.user.dao.UserMapper;
import com.kb.user.pojo.user.User;
import com.kb.user.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mawz
 * @version 1.0.0
 * @description
 * @date 2022/6/28 10:51
 */
@Service
@Slf4j
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
        checkParams(user);
        Integer count=userMapper.add(user);
        checkParams(user);
        AssertUtil.assertNotEqual(1,count,"添加操作失败！");
        return BaseResponse.success(user.getId());
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

    @Override
    public BaseResponse detailByKey(String key) {
        AssertUtil.assertEmptyStr(key,"获取失败");
        User user =userMapper.detailByKey(key);
        AssertUtil.assertNull(user,"用户信息不存在");
        return BaseResponse.success(user);
    }

    private void checkParams(User user) {
        AssertUtil.assertNull(user.getPhone(),"绑定手机不能为空");
        AssertUtil.isNotTrue(PhoneUtil.isMobile(user.getPhone()),"手机格式不正确");
        if(StringUtils.isNotBlank(user.getEmail())){
            AssertUtil.isNotTrue(EmailUtil.isEmail(user.getEmail()),"邮箱格式不正确");
        }
    }

}
