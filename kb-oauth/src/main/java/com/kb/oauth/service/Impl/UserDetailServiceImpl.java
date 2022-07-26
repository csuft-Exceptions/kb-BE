package com.kb.oauth.service.Impl;

import com.alibaba.fastjson.JSON;
import com.kb.common.base.BaseResponse;
import com.kb.oauth.dao.feign.UserFeign;
import com.kb.oauth.util.UserJwt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;


/**
 * @author yk
 * @version 1.0
 * @date 2022/6/10 15:42
 */
@Service("KbUserDetailService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private ClientDetailsService clientDetailsService;

    @Resource
    private UserFeign userFeign;

    /**
     * 用户登录的
     * @param  username 账号
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if (authentication == null){
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if (clientDetails != null){
                String clientSecret = clientDetails.getClientSecret();
                return new User(username,clientSecret,AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        if (StringUtils.isEmpty(username)){
            return null;
        }
        BaseResponse response = userFeign.detailByKey(username);
        //todo
        //若表中数据不存在即response为fail的处理
        Object data = response.getData();
        String jsonString = JSON.toJSONString(data);
        com.kb.oauth.pojo.User user = JSON.parseObject(jsonString, com.kb.oauth.pojo.User.class);
        //返回空 通知security该用户不存在
        if (user == null){
            return null;
        }
        String password = user.getPwd();
        String permission = "all";
        UserJwt userDetail = new UserJwt(username,password,AuthorityUtils.commaSeparatedStringToAuthorityList(permission));
        return userDetail;
    }
}
