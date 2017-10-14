package com.clarkez.redis.receiver;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationIDController {

    @Autowired
    private EurekaClient eurekaClient;

    @RequestMapping("/appid")
    public String getAppId() {
        return this.eurekaClient.getApplicationInfoManager().getEurekaInstanceConfig().getInstanceId();
    }
}
