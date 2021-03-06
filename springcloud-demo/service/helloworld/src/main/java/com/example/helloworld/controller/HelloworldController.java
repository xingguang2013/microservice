package com.example.helloworld.controller;

import com.example.helloworld.model.HelloMessage;
import com.example.helloworld.model.HelloworldMessage;
import com.example.helloworld.model.WorldMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author billjiang 475572229@qq.com
 * @create 17-8-22
 */
@RestController
public class HelloworldController {

    private static final Logger log = LoggerFactory.getLogger(HelloworldController.class);

    private static final String HELLO_SERVICE_NAME = "hello";

    private static final String WORLD_SERVICE_NAME = "world";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    public String home() {
        return "hello world";
    }

    @GetMapping("/message")
    public HelloworldMessage getMessage() {
        HelloMessage hello = getMessageFromHelloService();
        WorldMessage world = getMessageFromWorldService();
        HelloworldMessage helloworld = new HelloworldMessage();
        helloworld.setHello(hello);
        helloworld.setWord(world);
        log.debug("Result helloworld message:{}", helloworld);
        return helloworld;
    }

    private HelloMessage getMessageFromHelloService() {
        HelloMessage hello = restTemplate.getForObject("http://hello/message", HelloMessage.class);
        log.debug("From hello service : {}.", hello);
        return hello;
    }

    private WorldMessage getMessageFromWorldService() {
        WorldMessage world = restTemplate.getForObject("http://world/message", WorldMessage.class);
        log.debug("From world service : {}.", world);
        return world;
    }


}