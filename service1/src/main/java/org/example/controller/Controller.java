package org.example.controller;

import org.example.api.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Value("${server.port}")
    private String port;

    @Autowired
    private InetUtils inetUtils;

    @Autowired
    private DemoService demoService;

    @GetMapping("/test")
    public ResponseEntity<String> test(){
       String str= inetUtils.findFirstNonLoopbackHostInfo().getIpAddress()+":"+port;
        System.out.println("~~~~~"+str);
        return ResponseEntity.ok(str);
    }


    @GetMapping("/hello")
    public ResponseEntity<String> rpcTest(){
        String str= demoService.sayHello("test");
        return ResponseEntity.ok(str);
    }
}
