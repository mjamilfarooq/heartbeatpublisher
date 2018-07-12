package org.test.remotee.messaging;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;


@Service
public class RedisMessagePublisher implements MessagePublisher{


    JedisPoolConfig poolConfig;
    JedisPool jedisPool;


    @Value("${redismessagepublisher.channel}")
    private String channel;

    public RedisMessagePublisher()
    {
        poolConfig = buildPoolConfig();
        jedisPool = new JedisPool(poolConfig, "localhost");
    }


    private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }

    @Override
    public void publish(final String message)
    {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.publish(channel, message);
        }

    }
}
