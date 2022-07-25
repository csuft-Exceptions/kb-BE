package com.kb.oauth.util;


import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

/**
 * @author wjx
 * @create 2022/6/30 14:46
 * jwt工具类
 */
public class JwtUtil {

    /**
     * 过期时间
     */
    private static long tokenExpiration = 24 * 60 * 60 * 1000;
    /**签名秘钥*/
    private static String tokenSignKey = "kb_123456";

    /**
     * 根据参数生成token
     */
    public static String createToken(Long userId, String userName) {
        String token = Jwts.builder()
                // 设置头信息
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS512")
                //
                .setSubject("kb-user")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .claim("userName", userName)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    /**
     * 判断token是否存在于有效（通过秘钥判断）
     * @param token
     * @return
     */
    public static boolean checkToken(String token){
        if (StringUtils.isEmpty(token)){
            return false;
        }
        try {
            //判断是否为同一个秘钥
            Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效（通过reqiest 头信息判断）
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) {
                return false;
            }
            Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token字符串得到用户id
     * @param token
     * @return
     */
    public static Long getUserId(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer) claims.get("userId");
        return userId.longValue();
    }

    /**
     * 根据token字符串得到用户名称
     */
    public static String getUserName(String token) {
        if (StringUtils.isEmpty(token)) {
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("userName");
    }

    public static void main(String[] args) {
        String token = JwtUtil.createToken(1L, "lucy");
        System.out.println(token);
        System.out.println(JwtUtil.getUserId(token));
        System.out.println(JwtUtil.getUserName(token));
    }


}
