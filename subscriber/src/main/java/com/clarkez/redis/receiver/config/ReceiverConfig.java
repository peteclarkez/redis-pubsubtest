package com.clarkez.redis.receiver.config;

import com.clarkez.redis.receiver.ApplicationIDController;
import com.clarkez.redis.receiver.pubsub.MessageListenerBuilder;
import com.clarkez.redis.receiver.pubsub.RedisMessageReceiver;
import com.clarkez.redis.receiver.pubsub.LoginMessageReceiver;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

@Configuration
public class ReceiverConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginMessageReceiver.class);

    @Autowired
    ApplicationIDController applicationIDController;

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        return container;
    }


    @Bean
    LoginMessageReceiver receiver(RedisMessageReceiver redisMessageReceiver, RedisMessageListenerContainer container ) {

        LoginMessageReceiver receiver = new LoginMessageReceiver(redisMessageReceiver);

        String appID = applicationIDController.getAppId();
        Topic t = new ChannelTopic("/chat/login/"+appID);
        Topic c =  new PatternTopic("/chat");
        container.addMessageListener(receiver, ImmutableList.of(t,c) );

        return receiver;

    }

    @Bean
    RedisMessageReceiver redisMessageReceiver (RedisMessageListenerContainer container,RedisTemplate template){
        MessageListenerBuilder builder = new MessageListenerBuilder();

       RedisMessageReceiver receiver = new RedisMessageReceiver(builder,container,template,applicationIDController.getAppId());

        return receiver;
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

}
