package org.example;

import org.example.aspect.EnableRpcClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
//@EnableDiscoveryClient
@SpringBootApplication
@EnableRpcClients(basePackages = {"org.example.api"})
//@EnableFeignClients
public class Service1 {
    public static void main(String[] args) {
        SpringApplication.run(Service1.class,args);
    }

}