package com.kb.common.config;

import com.kb.common.exception.InfoException;
import lombok.extern.slf4j.Slf4j;
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

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-10 - 14:45
 */
@Configuration
@Slf4j
public class RocketMQConfig {
    @Value("{rocketmq.name.server.address}")
    private String nameSeverAddr;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Bean("momentProducer")
    public DefaultMQProducer momentProducer(){
        DefaultMQProducer producer=new DefaultMQProducer("MomentsGroup");
        producer.setNamesrvAddr(nameSeverAddr);
        try {
            producer.start();
        } catch (MQClientException e) {
            log.error("MomentsGroup消息队列启动失败",e);
            throw new InfoException("RockerMQ启动失败",e);
        }
        return producer;
    }

    @Bean("momentConsumer")
    public DefaultMQPushConsumer momentConsumer(){
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("MomentsGroup");
        consumer.setNamesrvAddr(nameSeverAddr);
        // *代表订阅所有
        try {
            consumer.subscribe("Topic-moments","*");
            // 监听者,注册并发的监听器
            consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
                for(MessageExt msg:list){
                    log.info(msg.toString());
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
