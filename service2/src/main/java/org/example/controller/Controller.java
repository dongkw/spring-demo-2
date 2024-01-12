package org.example.controller;

import org.checkerframework.checker.units.qual.A;
import org.example.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class Controller {

//    @Autowired
//    private WebClient webClient;

    @GetMapping("/a")
    public ResponseEntity<String> test() {
//        Mono<String> result =  webClient.get().uri("http://server1/test")
//                .retrieve().bodyToMono(String.class);
//        result.block();
        String result = HttpUtil.handlerResponse("http://server1/test");
        return ResponseEntity.ok(result);
    }
}
