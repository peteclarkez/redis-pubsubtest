package com.clarkez.redis.publisher;

import com.clarkez.redis.publisher.pubsub.RedisMessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ScheduledMessagePublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledMessagePublisher.class);
    private final RedisMessagePublisher pub;

    private List<String> users = new ArrayList<>();
    int i = 0;

    public ScheduledMessagePublisher(RedisMessagePublisher pub){
        this.pub = pub;
        users.addAll(Arrays.asList(new String[]{"jack","joe","jill"}));
        for (String user:users) {
            pub.addUser(user);
        }
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

    public void addUser(String user){
        users.add(user);
        pub.addUser(user);
    }


    public void removeUser(String user){
        users.remove(user);
        pub.removeUser(user);
    }
}
