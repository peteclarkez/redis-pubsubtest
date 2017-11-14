package com.clarkez.redis.publisher.actuator;


import com.clarkez.redis.publisher.ScheduledMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

@Component
public class PublisherActuatorEndPoint implements Endpoint<ScheduledMessagePublisher> {

    @Autowired
    ScheduledMessagePublisher scheduledMessagePublisher;

    @Override
    public String getId() {
        return "publisher";
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
    public ScheduledMessagePublisher invoke() {
        return scheduledMessagePublisher;
    }
}
