package com.kb.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;


public class GlobalFilter implements org.springframework.cloud.gateway.filter.GlobalFilter, Ordered {
    private static final String AUTHORIZE_TOKEN = "token";
    private static final String AUTHORIZE_UID = "uid";

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 测试全局网关拦截
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(AUTHORIZE_TOKEN);
        String uid = headers.getFirst(AUTHORIZE_UID);
        //token为空直接返回
        ServerHttpResponse response = exchange.getResponse();
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(uid)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //从redis中拿到token并比较
        String authToken = (String) redisTemplate.opsForValue().get(uid);
        if (authToken == null || !authToken.equals(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return chain.filter(exchange);
    }

    /**
     * 优先级
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
