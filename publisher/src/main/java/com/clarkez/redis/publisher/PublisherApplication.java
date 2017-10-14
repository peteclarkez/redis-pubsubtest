package com.clarkez.redis.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
public class PublisherApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(PublisherApplication.class, args);
    }
}
