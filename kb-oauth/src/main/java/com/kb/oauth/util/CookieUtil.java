package com.kb.oauth.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wjx
 * @create 2022/7/8 22:42
 */
public class CookieUtil {

    /**
     * 设置cookie
     * @param response
     * @param domain 有效域
     * @param path 有效路径
     * @param name 名称
     * @param value cookie的值
     * @param maxAge 生命周期，在客户端上存活时间 秒
     * @param httpOnly
     */
    public static void addCookie(HttpServletResponse response, String domain, String path, String name,
                                 String value, int maxAge, boolean httpOnly) {
        String encodeValue = null;
        try {
            encodeValue = URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Cookie cookie = new Cookie(name, encodeValue);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(httpOnly);
        response.addCookie(cookie);
    }



    /**
     * 根据cookie名称读取cookie
     * @param request
     * @return map<cookieName,cookieValue>
     */

    public static Map<String,String> readCookie(HttpServletRequest request, String ... cookieNames) {
        Map<String,String> cookieMap = new HashMap<String,String>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                String cookieValue = null;
                try {
                    cookieValue = URLDecoder.decode(cookie.getValue(),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                for(int i=0;i<cookieNames.length;i++){
                    if(cookieNames[i].equals(cookieName)){
                        cookieMap.put(cookieName,cookieValue);
                    }
                }
            }
        }
        return cookieMap;
    }
}
