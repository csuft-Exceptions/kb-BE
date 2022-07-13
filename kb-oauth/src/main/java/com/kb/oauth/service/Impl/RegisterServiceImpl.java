package com.kb.oauth.service.Impl;
import com.alibaba.fastjson.JSONObject;
import com.kb.common.base.BaseResponse;
import com.kb.common.utils.AssertUtil;
import com.kb.common.utils.PhoneUtil;
import com.kb.oauth.dao.UserFeign;
import com.kb.oauth.dao.UserInfoFeign;
import com.kb.oauth.service.api.RegisterService;
import com.kb.oauth.vo.params.RegisterParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
    @Resource
    private PasswordEncoder passwordEncoder;
    @Override
    public BaseResponse register(RegisterParam registerParam) {
        AssertUtil.assertNull(registerParam,"注册参数对象为空");
        String username = registerParam.getUsername();
        String password = registerParam.getPassword();
        AssertUtil.assertEmptyStr(username,"用户名为空");
        AssertUtil.assertEmptyStr(password,"密码为空");
        boolean isMobile = PhoneUtil.isMobile(username);
        if (!isMobile){
            return BaseResponse.failed(new String("手机号格式有误"));
        }
        //判断手机号是否已经注册过
        BaseResponse userDetailResp = userFeign.detailByKey(username);
        AssertUtil.assertEquals(userDetailResp.getCode(),200,"该用户已经注册");
        //user
        Map<String,Object> UserMap = new HashMap<>(8);
        UserMap.put("phone",username);
        UserMap.put("pwd",passwordEncoder.encode(password));
        JSONObject UserParam = new JSONObject(UserMap);
        BaseResponse addUser = userFeign.add(UserParam);
        AssertUtil.assertNotEquals(addUser.getCode(),200,"注册失败");
        //todo （两个表原子性插入）
        String userId = String.valueOf(addUser.getData());
        Map<String,Object> UserInfoMap = new HashMap<>(8);
        UserInfoMap.put("userId",userId);
        UserInfoMap.put("nickname","kb_"+userId);
        JSONObject UserInfoParam = new JSONObject(UserInfoMap);
        BaseResponse addUserinfo = userInfoFeign.add(UserInfoParam);
        AssertUtil.assertNotEquals(addUserinfo.getCode(),200,"注册失败");
        return BaseResponse.success("注册成功");
    }
}
