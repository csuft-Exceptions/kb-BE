package com.kb.user.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kb.common.exception.InfoException;
import com.kb.user.dao.UserFollowingMapper;
import com.kb.user.pojo.userFollowing.UserFollowing;
import com.kb.user.pojo.userMoment.UserMoment;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-10 - 14:45
 */
@Configuration
@Slf4j
public class RocketMQConfig {
    @Value("${rocketmq.name.server.address}")
    private String nameSeverAddr;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private UserFollowingMapper userFollowingMapper;

    @Bean("momentsProducer")
    public DefaultMQProducer momentsProducer(){
        DefaultMQProducer producer=new DefaultMQProducer("MomentsGroup");
        producer.setNamesrvAddr(nameSeverAddr);
        producer.setSendMsgTimeout(60000);
        producer.setVipChannelEnabled(false);
        try {
            producer.start();
            log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<启动成功{}<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<",nameSeverAddr);
        } catch (MQClientException e) {
            log.error("MomentsGroup消息队列启动失败",e);
            throw new InfoException("RockerMQ启动失败",e);
        }
        return producer;
    }

    @Bean("momentsConsumer")
    public DefaultMQPushConsumer momentsConsumer(){
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("MomentsGroup");
        consumer.setNamesrvAddr(nameSeverAddr);
        consumer.setVipChannelEnabled(false);
        // *代表订阅所有
        try {
            consumer.subscribe("Topic-Moments","*");
            // 监听者,注册并发的监听器,只有一条消息,所以只取第一个
            consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
                MessageExt msg=list.get(0);
                if(msg==null){
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                String bodyStr=new String(msg.getBody());
                UserMoment userMoment=JSONObject.toJavaObject(JSONObject.parseObject(bodyStr),UserMoment.class);
                Long userId= userMoment.getUserId();
                List<UserFollowing> fanList=userFollowingMapper.fans(userId);
                // 将内容发送到redis中, 用户通过redis查询结果
                for(UserFollowing fan:fanList){
                    String key="subscribed-" +fan.getUserId();
                    String subscribedListStr=redisTemplate.opsForValue().get(key);
                    List<UserMoment> subList;
                    if(StringUtils.isBlank(subscribedListStr)){
                        subList=new ArrayList<>();
                    }else{
                        subList= JSONArray.parseArray(subscribedListStr,UserMoment.class);
                    }
                    subList.add(userMoment);
                    redisTemplate.opsForValue().set(key,JSONObject.toJSONString(subList));
                    Collections.reverse(subList);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
        } catch (MQClientException e) {
            log.error("接收moments消息失败,消费者出现异常",e);
            throw new InfoException("接收moments消息失败,消费者出现异常",e);
        }
        return consumer;
    }
}
