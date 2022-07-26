package com.kb.oauth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kb.common.base.BaseResponse;
import com.kb.oauth.dao.SocialInfoMapper;
import com.kb.oauth.pojo.SocialInfo;
import com.kb.oauth.service.Impl.AuthServiceImpl;
import com.kb.oauth.service.api.AuthService;
import com.kb.oauth.util.GithubHttpClient;
import com.kb.oauth.vo.params.RegisterParam;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wjx
 * @create 2022/7/10 17:38
 */
@Controller
@RequestMapping("oauth")
@Slf4j
public class AuthController {


    /**
     * github授权中提供的 appid 和 appkey
     */
    @Value("${github.oauth.clientid}")
    public String CLIENTID;
    @Value("${github.oauth.clientsecret}")
    public String CLIENTSECRET;
    @Value("${github.oauth.callback}")
    public String URL;

    @Resource
    private SocialInfoMapper socialInfoMapper;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private AuthService authService;
    @PostMapping("/login")
    @ResponseBody
    public BaseResponse loginByPwd(@RequestBody RegisterParam loginParamByPass){
        BaseResponse response = authService.loginByPass(loginParamByPass);
        return response;
    }


    @GetMapping("/logout")
    @ResponseBody
    public BaseResponse logout(HttpServletRequest request){
        BaseResponse response = authService.logout(request);
        return response;
    }

    /**
     * 请求授权页面
     * @return
     */
    @GetMapping("/github")
    public void loginBySocial(HttpSession session,HttpServletResponse response){
        // 用于第三方应用防止CSRF攻击
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        session.setAttribute("state", uuid);
        // Step1：获取Authorization Code
        String url = "https://github.com/login/oauth/authorize?scope=user" +
                "&client_id=" + CLIENTID +
                "&redirect_uri=" + URLEncoder.encode(URL) +
                "&state=" + uuid;
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/callback")
    public BaseResponse callback(HttpServletRequest request,HttpSession session) throws Exception {
        HttpSession session1 = request.getSession();
        // 得到Authorization Code
        String code = request.getParameter("code");
        // 我们放在地址中的状态码
        String state = request.getParameter("state");
        String uuid = (String) session1.getAttribute("state");

        // 验证信息我们发送的状态码
        if (null != uuid) {
            // 状态码不正确，直接返回登录页面
            if (!uuid.equals(state)) {
                return BaseResponse.failed("状态码有误！");
            }
        }
        // Step2：通过Authorization Code获取Access Token
        String url = "https://github.com/login/oauth/access_token?" +
                "client_id=" + CLIENTID +
                "&client_secret=" + CLIENTSECRET +
                "&redirect_uri=" + URL +
                "&code=" + code +
                "&state=" + state;
        JSONObject accessToken = GithubHttpClient.getAccessToken(url);
        System.out.println(accessToken.getString("access_token"));
        // Step3: 获取用户信息
        /*url = "https://api.github.com/user?access_token=" + accessTokenJson.getString("access_token");*/
        url = "https://api.github.com/user";
        JSONObject userInfoJson = GithubHttpClient.getUserInfo(url, accessToken.getString("access_token"));
        //todo (GitHub用户信息与站内账号建立联系)
        String socialId = userInfoJson.getString("id");
        String username = userInfoJson.getString("login");
        String avatarUrl = userInfoJson.getString("avatar_url");
        Map<String,Object> resultMap = new HashMap<>(8);
        resultMap.put("username",username);
        resultMap.put("avatar_url",avatarUrl);
        Object data = JSON.toJSON(resultMap);
        session.setAttribute("socialId",socialId);
        return BaseResponse.success(data);
    }

    @GetMapping("/bind/{username}")
    public BaseResponse bind(@PathVariable String username,HttpServletRequest request){
        HttpSession session = request.getSession();
        String socialId = (String) session.getAttribute("socialId");
        BaseResponse response = authService.bind(username,socialId);
        return response;
    }


}
