package com.clarkez.redis.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Publisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Publisher.class);
    private final RedisMessagePublisher pub;
    List<String> users = Arrays.asList(new String[]{"jack","joe","jill"});
    int i = 0;

    public Publisher(RedisMessagePublisher pub){
        this.pub = pub;
    }

    @Scheduled(fixedRate = 5000)
    public void send(){
        pub.publish("Hello");
        int ix = i++ % (users.size());
        String user = users.get(ix);
        pub.publish(user,"Hello "+user + " "+i);
    }

    public List<String> getUsers() {
        return users;
    }
}
