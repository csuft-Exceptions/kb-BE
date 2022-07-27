package com.kb.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kb.common.base.BaseResponse;
import com.kb.common.utils.RocketMQUtil;
import com.kb.user.dao.UserMomentMapper;
import com.kb.user.pojo.userMoment.UserMoment;
import com.kb.user.service.api.UserMomentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-22 - 22:12
 */
@Service
@Slf4j
public class UserMomentServiceImpl implements UserMomentService {

    @Resource
    private UserMomentMapper userMomentMapper;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public BaseResponse addUserMoments(UserMoment userMoment) {
        Integer count=userMomentMapper.addUserMoments(userMoment);
        DefaultMQProducer producer=(DefaultMQProducer)applicationContext.getBean("momentProducer");
        Message msg=new Message("Topic-moments", JSONObject.toJSONString(userMoment).getBytes(StandardCharsets.UTF_8));
        RocketMQUtil.syncSendMsg(producer,msg);
        return BaseResponse.success("创建动态成功");
    }

    @Override
    public BaseResponse getMoments(Long userId) {
        String key="subscribed-"+userId;
        String listStr=redisTemplate.opsForValue().get(key);
        return BaseResponse.success(JSONArray.parseArray(listStr,UserMoment.class));
    }
}
