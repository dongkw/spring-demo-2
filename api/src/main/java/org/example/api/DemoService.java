package org.example.api;


import org.example.aspect.RpcClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RpcClient(url = "server2")
@FeignClient(fallback = DemoCallBack.class,fallbackFactory = DemoCallBack.class)
public interface DemoService {
    @PostMapping("/hello")
    String sayHello(String str);
}
