package com.clarkez.redis.publisher.pubsub;

import com.clarkez.redis.publisher.EurekaClientServiceInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.support.collections.DefaultRedisMap;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class RedisMessagePublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisMessagePublisher.class);


    private RedisTemplate<String, String> redisTemplate;
    private ChannelTopic topic;

    @Autowired
    EurekaClientServiceInfo eurekaClientServiceInfo;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public Map<String,ChannelTopic> userTopics;
    public Map<String,String> userTopicStrings;


    public Collection<String> getUsers(){
        return userTopics.keySet();
    }
 
    public RedisMessagePublisher(
            RedisTemplate<String, String> stringTopicRedisTemplate, ChannelTopic topic){
      this.redisTemplate = stringTopicRedisTemplate;
      this.topic = topic;
      this.userTopics = new LinkedHashMap<>();
      this.userTopicStrings = new DefaultRedisMap<String, String>(stringTopicRedisTemplate.boundHashOps("clarke:publisher:users"));
    }

    public void addUser(String user){
        ChannelTopic userTopic = new ChannelTopic("/chat/"+user);
        userTopics.put(user,userTopic);
        userTopicStrings.put(user,userTopic.getTopic());
        try {
            login(user, userTopic);
        }catch (Throwable e){
            LOGGER.error("Error trying to login user {} {}",user,e.getMessage());
        }
    }

    private void login(String user, ChannelTopic userTopic) throws JsonProcessingException,RuntimeException {
        InstanceInfo nextInstance = eurekaClientServiceInfo.nextInstancesByApplicationName("receiver");
        String instanceID = nextInstance.getInstanceId();
        ChannelTopic loginInstanceTopic = new ChannelTopic("/chat/login/"+instanceID);

        Map<String,String> msg = new LinkedHashMap<>();
        msg.put("user",user);
        msg.put("source",eurekaClientServiceInfo.getAppId());
        String sMsg = objectMapper.writeValueAsString(msg);

        LOGGER.info("Sending login msg {} {}",user,loginInstanceTopic.getTopic());

        redisTemplate.convertAndSend(loginInstanceTopic.getTopic(), sMsg);
    }

    public void removeUser(String user){
        userTopics.remove(user);
        userTopicStrings.remove(user);

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