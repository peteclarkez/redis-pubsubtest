package com.clarkez.redis.receiver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private List<String> users = new ArrayList<>();

    public Receiver() {
    }

    public void receiveMessage(String message) {
        LOGGER.info("Received <" + message + ">");
    }
}