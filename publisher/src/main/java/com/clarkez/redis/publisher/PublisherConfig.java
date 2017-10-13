package com.clarkez.redis.publisher;

import com.clarkez.redis.publisher.RedisMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

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
        RedisMessagePublisher redisPublisher = new RedisMessagePublisher(template(connectionFactory),topic());
        return redisPublisher;
    }

}
