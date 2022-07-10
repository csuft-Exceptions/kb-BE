package com.kb.oauth.util;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wjx
 * @create 2022/7/8 10:21
 */
@Data
public class AuthToken implements Serializable {
    /**令牌信息 jwt*/
    private String accessToken;
    /**刷新token*/
    private String refreshToken;
    /**jwt短令牌*/
    private String jti;
}
