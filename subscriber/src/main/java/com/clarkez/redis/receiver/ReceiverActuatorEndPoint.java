package com.clarkez.redis.receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReceiverActuatorEndPoint implements Endpoint<RedisMessageReceiver> {

    @Autowired
    RedisMessageReceiver redisMessageReceiver;

    @Override
    public String getId() {
        return "receiver";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isSensitive() {
        return false;
    }

    @Override
    public RedisMessageReceiver invoke() {
        return redisMessageReceiver;
    }
}
