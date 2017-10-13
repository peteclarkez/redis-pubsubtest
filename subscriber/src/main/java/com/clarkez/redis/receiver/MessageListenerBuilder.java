package com.clarkez.redis.receiver;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MessageListenerBuilder {

    private String user ;

    public MessageListenerBuilder(){

    }

    public MessageListenerBuilder withUser(String user){
        this.user = user;
        return this;
    }

    public MessageListener build(){
        if(user==null){
            throw new IllegalArgumentException("User needs to be set");
        }
        return new Receiver(user);
    }

    @JsonIgnoreType
    private class Receiver implements MessageListener {
        private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
        private final String user;

        @Autowired
        public Receiver(String user) {
            this.user = user;
        }

        public void receiveMessage(String message) {
            LOGGER.info("{} Received <{}>",user,message);
        }

        @Override
        public void onMessage(Message message, byte[] bytes) {
            String topic = new String(bytes, Charset.defaultCharset());
            String msg = new String(message.getBody(), Charset.defaultCharset());
            String channel= new String(message.getChannel(), Charset.defaultCharset());

            receiveMessage(msg);

        }
    }
}
