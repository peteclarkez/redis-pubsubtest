package com.clarkez.redis.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RedisMessagePublisher {
 
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ChannelTopic topic;

    public Map<String,ChannelTopic> userTopics;

    public RedisMessagePublisher() {
    }

    public Collection<String> getUsers(){
        return userTopics.keySet();
    }
 
    public RedisMessagePublisher(
      RedisTemplate<String, String> redisTemplate, ChannelTopic topic) {
      this.redisTemplate = redisTemplate;
      this.topic = topic;
      this.userTopics = new HashMap<>();
    }

    public void addUser(String user){
        ChannelTopic userTopic = new ChannelTopic("/chat/"+user);
        userTopics.put(user,userTopic);
    }

    public void removeUser(String user){
        userTopics.remove(user);
    }

    public void publish(String message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

    public void publish(String who,String message) {
        ChannelTopic userTopic = userTopics.get(who);
        if(who!=null) {
            redisTemplate.convertAndSend(userTopic.getTopic(), message);
        }
    }
}