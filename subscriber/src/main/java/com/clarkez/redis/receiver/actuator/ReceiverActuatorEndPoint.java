package com.clarkez.redis.receiver.actuator;

import com.clarkez.redis.receiver.pubsub.RedisMessageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

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
