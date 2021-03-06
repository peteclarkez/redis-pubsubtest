package com.clarkez.redis.receiver.pubsub;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.support.collections.DefaultRedisList;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisMessageReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginMessageReceiver.class);

    @Data
    @RequiredArgsConstructor
    private class ReceiverRecord{
        private final String user;
        private final ChannelTopic topic;
        private final MessageListener messageListener;
    }


    private final MessageListenerBuilder listenerBuilder;
    private final RedisMessageListenerContainer redisMessageListenerContainer;

    public Map<String,ReceiverRecord> userTopics;
    public List<String> users;


    public  RedisMessageReceiver( MessageListenerBuilder listenerBuilder, RedisMessageListenerContainer redisMessageListenerContainer,
                                  RedisTemplate template, String appID){
        this.redisMessageListenerContainer = redisMessageListenerContainer;
        this.listenerBuilder = listenerBuilder;
        this.userTopics = new HashMap<>();
        users = new DefaultRedisList<String>(template.boundListOps("clarkez:receiver:"+appID+":users"));
    }


    public Collection<String> getUsers(){
        return userTopics.keySet();
    }

    public void addUser(String user){
        if(userTopics.containsKey(user)){
            LOGGER.warn("User already Subscribed {}",user);
            return;
        }
        ChannelTopic userTopic = new ChannelTopic("/chat/"+user);
        MessageListener listener = listenerBuilder.withUser(user).build();
        redisMessageListenerContainer.addMessageListener(listener,userTopic);

        ReceiverRecord rec = new ReceiverRecord(user,userTopic,listener);
        userTopics.put(user,rec);
        users.add(user);
    }

    public void removeUser(String user){
        users.remove(user);
        ReceiverRecord rec = userTopics.remove(user);
        if(null == rec){
            return;
        }
        redisMessageListenerContainer.removeMessageListener(rec.getMessageListener(),rec.getTopic());
    }


}

