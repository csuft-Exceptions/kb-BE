package com.kb.video.config;

import com.kb.common.exception.InfoException;
import com.kb.common.utils.RocketMQUtil;
import com.kb.common.utils.TimeUtil;
import com.kb.video.pojo.VideoInfo;
import com.kb.video.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;


/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-26 - 14:45
 */
@Configuration
@Slf4j
public class RocketMQConfig {
    @Value("{rocketmq.name.server.address}")
    private String nameSeverAddr;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private VideoService videoService;

    @Resource
    private ApplicationContext applicationContext;


    @Bean("topProducer")
    public DefaultMQProducer topProducer(){
        DefaultMQProducer producer=new DefaultMQProducer("topGroup");
        producer.setNamesrvAddr(nameSeverAddr);
        try {
            producer.start();
            // 启动时发送第一条消息
            Message msg2=new Message("Topic-top",new byte[1]);
            RocketMQUtil.asyncSendMsg(producer,msg2,true);
        } catch (MQClientException e) {
            log.error("TopGroup消息队列启动失败",e);
            throw new InfoException("RockerMQ:top Group启动失败",e);
        }
        return producer;
    }

    @Bean("topConsumer")
    public DefaultMQPushConsumer topConsumer(){
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("topGroup");
        consumer.setNamesrvAddr(nameSeverAddr);
        // *代表订阅所有
        try {
            consumer.subscribe("Topic-top","*");
            // 监听者,注册并发的监听器,只有一条消息,所以只取第一个
            consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
                MessageExt msg=list.get(0);
                if(msg==null){
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                Set<String> set = redisTemplate.opsForSet().members("top");
                if (set == null) {
                    return null;
                }
                for (String s : set) {
                    VideoInfo videoInfo = videoService.getVideoInfoById(Long.valueOf(s));
                    redisTemplate.opsForZSet().remove("topZSet", s);
                    //分类
                    redisTemplate.opsForZSet().remove("topZSet" + videoInfo.getCategory(), s);
                    if (redisTemplate.hasKey(s)) {
                        double score = getScore(videoInfo);
                        redisTemplate.opsForZSet().add("topZSet", s, score);
                        redisTemplate.opsForZSet().add("topZSet" + videoInfo.getCategory(), s, score);
                    } else {
                        redisTemplate.opsForSet().remove("top", s);
                    }
                }
                Message msg2=new Message("Topic-top",new byte[1]);
                DefaultMQProducer producer=(DefaultMQProducer)applicationContext.getBean("topProducer");
                RocketMQUtil.asyncSendMsg(producer,msg2,true);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
        } catch (MQClientException e) {
            log.error("接收top消息失败,消费者出现异常",e);
            throw new InfoException("接收top消息失败,消费者出现异常",e);
        }
        return consumer;
    }

    private double getScore(VideoInfo videoInfo) {
        return videoInfo.getBarrages()*10+(Long) redisTemplate.opsForHash().get("like",videoInfo.getId())*5+
                redisTemplate.opsForHyperLogLog().size("hl" + videoInfo.getId())*20+
                (71-TimeUtil.between(TimeUtil.getDateTime(videoInfo.getCreateTime()),
                        TimeUtil.getDateTime(new Date()), ChronoUnit.HOURS))*15;
    }
}
