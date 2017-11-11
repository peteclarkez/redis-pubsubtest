package com.clarkez.redis.receiver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class ServiceMessageReceiver implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceMessageReceiver.class);

    @Autowired
    private RedisMessageReceiver redisMessageReceiver;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ServiceMessageReceiver() {
    }

    public ServiceMessageReceiver(RedisMessageReceiver redisMessageReceiver) {
        this.redisMessageReceiver = redisMessageReceiver;
    }

    public void receiveMessage(String message) {
        LOGGER.info("Received <" + message + ">");
    }

    public void receiveLoginMessage(String user,String source) {

        LOGGER.info("Received Login Message <" + user + "|"+source + ">");
        redisMessageReceiver.addUser(user);
    }


    @Override
    public void onMessage(Message message, byte[] bytes) {
        String topic = new String(bytes, Charset.defaultCharset());
        String channel= new String(message.getChannel(), Charset.defaultCharset());
        String msg = new String(message.getBody(), Charset.defaultCharset());

        if(topic.contains("login")){
            LOGGER.info("Received <" + topic + "|"+"FIND OUT"+ ">");

            try {
                Map<String,String> map = objectMapper.readValue(msg, new TypeReference<Map<String, String>>(){});
                receiveLoginMessage(map.get("user"),map.get("source"));
            } catch (IOException e) {
                LOGGER.warn("Error Reading Value {}",e.getMessage());
            }catch (Throwable e) {
                LOGGER.warn("Other Error Reading Value {}",e.getMessage());
            }

        }else{
            receiveMessage(msg);
        }
    }
}