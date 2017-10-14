package com.clarkez.redis.receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
public class ReceiverApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ReceiverApplication.class, args);
    }

}
