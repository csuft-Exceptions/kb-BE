package com.kb.oauth.controller;

import com.kb.common.base.BaseResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author yk
 * @version 1.0
 * @date 2022/6/10 16:00
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String SIGN_KEY = "KaiBai_key";

    @GetMapping("/getCurrentUser")
    public BaseResponse getCurrentUser(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            return BaseResponse.failed("无效的Authorization头");
        }
        String token = header.substring(BEARER_PREFIX.length()).trim();
        if (token.isEmpty()) {
            return BaseResponse.failed("token不能为空");
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SIGN_KEY.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
            return BaseResponse.success(claims);
        } catch (Exception e) {
            return BaseResponse.failed("token无效");
        }
    }
}
