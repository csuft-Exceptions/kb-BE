package com.kb.oauth.service.Impl;

import com.alibaba.fastjson.JSON;
import com.kb.common.base.BaseResponse;
import com.kb.common.utils.AssertUtil;
import com.kb.common.utils.Base64Util;
import com.kb.oauth.service.api.AuthService;
import com.kb.oauth.util.AuthToken;
import com.kb.oauth.util.CookieUtil;
import com.kb.oauth.vo.params.RegisterParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wjx
 * @create 2022/7/7 20:47
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${auth.clientId}")
    private String clientId;
    @Value("${auth.clientSecret}")
    private String clientSecret;
    @Value("${auth.cookieDomain}")
    private String cookieDomain;
    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;
    @Value("${auth.ttl}")
    private int ttl;

    @Override
    public BaseResponse loginByPass(RegisterParam loginParamByPass) {
        AssertUtil.assertNull(loginParamByPass,"传入对象为空");
        String username = loginParamByPass.getUsername();
        String password = loginParamByPass.getPassword();
        AssertUtil.assertEmptyStr(username,"用户名为空");
        AssertUtil.assertEmptyStr(password,"密码为空");

        //申请令牌
        AuthToken authToken = this.applyToken(username,password,clientId,clientSecret);
        String accessToken = authToken.getAccessToken();
        //存储到Redis中的内容
        String jsonData = JSON.toJSONString(authToken);
        boolean result = this.saveTokenToRedis(accessToken,authToken,ttl);
        this.saveCookie(jsonData);
        if (!result){
            //todo
        }
        return BaseResponse.success("登录成功");
    }

    /**
     * 将token存入Redis
     * @param accessToken 用户身份令牌
     * @param value 内容 authToken
     * @param ttl 过期时间
     * @return
     */
    private boolean saveTokenToRedis(String accessToken, AuthToken value, long ttl) {
        String key = "user_key:"+accessToken;
        redisTemplate.opsForValue().set(key,value,ttl, TimeUnit.SECONDS);
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire>0;
    }

    /**
     * 从Redis中删除token
     * @param token
     */
    private void delToken(String token){
        String key = "user_key"+token;
        redisTemplate.delete(key);
    }

    /**
     * 从Redis取出数据
     * @param token
     * @return
     */
    private AuthToken getToken(String token){
        String key = "user_key" + token;
        Object value = redisTemplate.opsForValue().get(key);
        AuthToken authToken = (AuthToken) value;
        return authToken;
    }

    //将令牌存储到cookie
    private void saveCookie(String token){

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //HttpServletResponse response,String domain,String path, String name, String value, int maxAge,boolean httpOnly
        CookieUtil.addCookie(response,cookieDomain,"/","uid",token,cookieMaxAge,false);

    }
    //从cookie删除token
    private void clearCookie(String token){

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //HttpServletResponse response,String domain,String path, String name, String value, int maxAge,boolean httpOnly
        CookieUtil.addCookie(response,cookieDomain,"/","uid",token,0,false);

    }

    //取出cookie中的身份令牌
    private String getTokenFormCookie(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> map = CookieUtil.readCookie(request, "uid");
        if(map!=null && map.get("uid")!=null){
            String uid = map.get("uid");
            return uid;
        }
        return null;
    }


    /**
     * 申请令牌
     * @param username
     * @param password
     * @param clientId
     * @param clientSecret
     * @return
     */
    private AuthToken applyToken(String username, String password, String clientId, String clientSecret) {
        String authUrl = "http://localhost:9002" + "/oauth/token";
        //MultiValueMap与hashmap区别？
        //header信息，包括了http basic 认证信息(请求头)
        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        //进行base64编码，并将编码后的认证数据放到文件中
        String httpBasic = httpBasic("kaibai","kaibai");
        headers.add("Authorization",httpBasic);
        //指定认证类型，账号，密码,(请求体的设置)
        MultiValueMap<String,String> body = new LinkedMultiValueMap<String,String>();
        //采用密码模式
        body.add("grant_type","password");
        body.add("username",username);
        body.add("password",password);
        //封装一个http请求
        HttpEntity<MultiValueMap<String, String>> multiValueMapHttpEntity = new HttpEntity<MultiValueMap<String, String>>(body, headers);
        //指定 restTemplate当遇到400或401响应时候也不要抛出异常，也要正常返回值
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                //当响应的值为400或401时候也要正常响应，不要抛出异常
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });


        //远程调用申请令牌，返回一个响应请求
        ResponseEntity<Map> exchange = restTemplate.exchange(authUrl, HttpMethod.POST, multiValueMapHttpEntity, Map.class);
        //获取响应的响应体,拿到令牌
        Map bodyMap = exchange.getBody();
        if (bodyMap == null ||
                bodyMap.get("access_token") == null ||
                bodyMap.get("refresh_token") == null ||
                bodyMap.get("jti") == null){
            throw new RuntimeException("申请令牌失败");
        }
        //对结果进行封装
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken((String)bodyMap.get("access_token"));
        authToken.setRefreshToken((String)bodyMap.get("refresh_token"));
        authToken.setJti((String)bodyMap.get("jti"));
        return authToken;
    }

    /**
     * 将客户端id与秘钥 base64编码
     * @param clientId
     * @param clientSecret
     * @return
     */
    private String httpBasic(String clientId, String clientSecret) {
        String str = clientId + ":" + clientSecret;
        String encode = Base64Util.encode(str);
        return encode;
    }
}
