package com.kb.oauth.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * @author wjx
 * @description
 */
public class GithubHttpClient {

    /**
     * 获取access token，post请求
     * @param url
     * @return
     * @throws IOException
     */
    public static JSONObject getAccessToken(String url) throws Exception {
        HttpClient client = SSLClientCustom.getCloseableHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept","application/json");
        HttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (null != entity){
            String res = EntityUtils.toString(entity, "UTF-8");
            return JSONObject.parseObject(res);
        }
        //释放连接
        httpPost.releaseConnection();
        return null;
    }

    /**
     * 获取用户信息,get
     * @param url
     * @param token
     * @return
     * @throws IOException
     */
    public static JSONObject getUserInfo(String url,String token) throws Exception {
        CloseableHttpClient client = SSLClientCustom.getCloseableHttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept","application/json");
        httpGet.setHeader("Authorization","token "+token);
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null){
            String res = EntityUtils.toString(entity, "UTF-8");
            return JSONObject.parseObject(res);
        }
        httpGet.releaseConnection();
        return null;
    }


}
