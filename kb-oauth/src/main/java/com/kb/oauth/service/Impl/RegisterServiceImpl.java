package com.kb.oauth.service.Impl;

import cn.hutool.core.lang.Snowflake;
import com.kb.common.base.BaseResponse;
import com.kb.common.utils.AssertUtil;
import com.kb.common.utils.PhoneUtil;
import com.kb.oauth.dao.UserFeign;
import com.kb.oauth.dao.UserInfoFeign;
import com.kb.oauth.pojo.User;
import com.kb.oauth.pojo.UserInfo;
import com.kb.oauth.service.api.RegisterService;
import com.kb.oauth.vo.params.RegisterParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wjx
 * @create 2022/7/10 16:07
 * 注册
 */
@Service
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    @Resource
    private UserFeign userFeign;
    @Resource
    private UserInfoFeign userInfoFeign;
    @Override
    public BaseResponse register(RegisterParam registerParam) {
        AssertUtil.assertNull(registerParam,"注册参数对象为空");
        String username = registerParam.getUsername();
        String password = registerParam.getPassword();
        AssertUtil.assertEmptyStr(username,"用户名为空");
        AssertUtil.assertEmptyStr(password,"密码为空");
        boolean isMobile = PhoneUtil.isMobile(username);
        if (!isMobile){
            return BaseResponse.failed(new String("用户名格式有误"));
        }
        User user = new User();
        user.setPhone(username);
        user.setPwd(password);
        UserInfo userInfo = new UserInfo();
        //用户id：雪花算法？
        long id = new Snowflake(0, 0).nextId();
        userInfo.setUserId(id);
        userInfo.setNickname("kb"+id);
        BaseResponse addUserinfo = userInfoFeign.add(userInfo);
        BaseResponse addUser = userFeign.add(user);

        return BaseResponse.success("注册成功");
    }
}
