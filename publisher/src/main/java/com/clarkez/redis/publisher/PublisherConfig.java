package com.clarkez.redis.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

@Configuration
public class PublisherConfig {


    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherConfig.class);

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("/chat");
    }

    @Bean
    RedisMessagePublisher redisPublisher(RedisConnectionFactory connectionFactory){
        RedisMessagePublisher redisPublisher =
                new RedisMessagePublisher(template(connectionFactory),
                                          topic());
        return redisPublisher;
    }
}
