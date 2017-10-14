package com.clarkez.redis.receiver;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class ReceiverConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceMessageReceiver.class);

    @Autowired
    ApplicationIDController applicationIDController;

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        return container;
    }


    @Bean
    ServiceMessageReceiver receiver(RedisMessageReceiver redisMessageReceiver,RedisMessageListenerContainer container ) {

        ServiceMessageReceiver receiver = new ServiceMessageReceiver(redisMessageReceiver);

        String appID = applicationIDController.getAppId();
        Topic t = new ChannelTopic("/chat/login/"+appID);
        Topic c =  new PatternTopic("/chat");
        container.addMessageListener(receiver, ImmutableList.of(t,c) );

        return receiver;

    }

    @Bean
    RedisMessageReceiver redisMessageReceiver (RedisMessageListenerContainer container){
        MessageListenerBuilder builder = new MessageListenerBuilder();

       RedisMessageReceiver receiver = new RedisMessageReceiver(builder,container);
        for (String user:new String[]{"jack","joe","jill"}) {
            receiver.addUser(user);
        }
        return receiver;
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

}
