package com.clarkez.redis.receiver;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisMessageReceiver {

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

    public  RedisMessageReceiver( MessageListenerBuilder listenerBuilder, RedisMessageListenerContainer redisMessageListenerContainer){
        this.redisMessageListenerContainer = redisMessageListenerContainer;
        this.listenerBuilder = listenerBuilder;
        this.userTopics = new HashMap<>();
    }


    public Collection<String> getUsers(){
        return userTopics.keySet();
    }

    public void addUser(String user){
        ChannelTopic userTopic = new ChannelTopic("/chat/"+user);
        MessageListener listener = listenerBuilder.withUser(user).build();
        redisMessageListenerContainer.addMessageListener(listener,userTopic);

        ReceiverRecord rec = new ReceiverRecord(user,userTopic,listener);
        userTopics.put(user,rec);
    }

    public void removeUser(String user){
        ReceiverRecord rec = userTopics.remove(user);
        if(null == rec){
            return;
        }
        redisMessageListenerContainer.removeMessageListener(rec.getMessageListener(),rec.getTopic());
    }


}

