package org.test.remotee.redis.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

public class RedisMachinePublisher implements MachinePublisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ChannelTopic topic;

    public RedisMachinePublisher(){}

    public RedisMachinePublisher(RedisTemplate<String, Object> redisTemplate, ChannelTopic topic)
    {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }


    @Override
    public void publish(final String message)
    {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
