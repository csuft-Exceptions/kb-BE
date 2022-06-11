package com.kb.kboauth.service;

import com.kb.kboauth.pojo.User;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yk
 * @version 1.0
 * @date 2022/6/10 15:42
 */
@Service
public class UserService implements UserDetailsService {
    private static List<User> userList;

    @Resource
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        String password = passwordEncoder.encode("123456");
        userList = new ArrayList<>(3);
        userList.add(new User("aaa", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        userList.add(new User("bbb", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
        userList.add(new User("ccc", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
    }

    /**
     * 用户登录的
     *
     * @param  username 账号
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 暂时不校验密码
        List<User> collect = userList.stream().filter(item ->
                item.getUsername().equals(username))
                .collect(Collectors.toList());
        return collect.get(0);
    }
}
