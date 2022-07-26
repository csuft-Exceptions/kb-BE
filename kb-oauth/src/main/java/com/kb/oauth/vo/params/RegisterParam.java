package com.kb.oauth.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wjx
 * @create 2022/7/10 16:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterParam {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
