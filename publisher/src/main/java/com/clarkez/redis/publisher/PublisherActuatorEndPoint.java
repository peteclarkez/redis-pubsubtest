package com.clarkez.redis.publisher;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

@Component
public class PublisherActuatorEndPoint implements Endpoint<Publisher> {

    @Autowired
    Publisher publisher;

    @Override
    public String getId() {
        return "publsher";
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
    public Publisher invoke() {
        return publisher;
    }
}
