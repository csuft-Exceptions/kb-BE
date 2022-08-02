package com.kb.oauth.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kb.common.base.BaseResponse;
import com.kb.common.utils.AssertUtil;
import com.kb.common.utils.Base64Util;
import com.kb.oauth.common.CookieToken;
import com.kb.oauth.common.RedisToken;
import com.kb.oauth.dao.SocialInfoMapper;
import com.kb.oauth.dao.feign.UserFeign;
import com.kb.oauth.pojo.SocialInfo;
import com.kb.oauth.service.api.AuthService;
import com.kb.oauth.util.AuthToken;
import com.kb.oauth.vo.params.RegisterParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wjx
 * @create 2022/7/7 20:47
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RedisToken redisToken;
    @Resource
    private RestTemplate restTemplate;
    @Autowired
    private CookieToken cookieToken;
    @Resource
    private UserFeign userFeign;
    @Resource
    private SocialInfoMapper socialInfoMapper;
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
        boolean result = redisToken.saveTokenToRedis(accessToken,jsonData,ttl);
        //获取与当前请求相关的response
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //设置cookie
        cookieToken.saveCookie(response,accessToken,cookieDomain,cookieMaxAge);
        if (!result){
            //todo
        }
        return BaseResponse.success(accessToken);
    }

    @Override
    public BaseResponse logout(HttpServletRequest request) {
        //获取cookie中的身份令牌
        String accessToken = cookieToken.getTokenFormCookie(request);
        //删除Redis token
        boolean result = redisToken.delToken(accessToken);
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //清除cookie
        cookieToken.clearCookie(response,accessToken,cookieDomain);
        return null;
    }

    @Override
    public BaseResponse bind(String username,String socialId) {
        AssertUtil.assertEmptyStr(username,"用户名不能为空");
        SocialInfo socialInfo = socialInfoMapper.selectOne(socialId);
        AssertUtil.assertNotNull(socialInfo,"已绑定");
        BaseResponse userDetail = userFeign.detailByKey(username);
        AssertUtil.assertNotEquals(userDetail.getCode(),200,"该账号未注册请先注册");
        JSONObject userDetailJson = JSONObject.parseObject(userDetail.getData().toString());
        Integer userId = Integer.valueOf(userDetailJson.getString("id"));

        Integer insert = socialInfoMapper.insertOne(socialId, userId);
        if (insert.equals(1)){
            return BaseResponse.success("绑定成功");
        }
        return BaseResponse.failed("绑定失败");
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

        //todo (url的IP和port 应该为负载均衡选择的结果)
        String url = "http://localhost:9002/oauth/token";
        HttpHeaders httpHeaders = new HttpHeaders();
        //header信息，包括了http basic 认证信息(请求头)
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //进行base64编码，并将编码后的认证数据放到文件中
        String httpBasic = httpBasic(clientId,clientSecret);
        httpHeaders.setBasicAuth(httpBasic);
        //MultiValueMap与hashmap区别？
        MultiValueMap<String,String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("grant_type","password");
        paramsMap.add("username",username);
        paramsMap.add("password",password);
        paramsMap.set("client_id",clientId);
        paramsMap.set("client_secret",clientSecret);
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
        //获取响应的响应体,拿到令牌
        Map map = restTemplate.postForObject(url, paramsMap, Map.class);
        if (map == null ||
                map.get("access_token") == null ||
                map.get("refresh_token") == null ||
                map.get("jti") == null){
            throw new RuntimeException("申请令牌失败");
        }
        //对结果进行封装
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken((String)map.get("access_token"));
        authToken.setRefreshToken((String)map.get("refresh_token"));
        authToken.setJwtToken((String)map.get("jti"));
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
