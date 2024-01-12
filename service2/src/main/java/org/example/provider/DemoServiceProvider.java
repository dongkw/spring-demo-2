package org.example.provider;

import lombok.extern.slf4j.Slf4j;
import org.example.api.DemoService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DemoServiceProvider implements DemoService{
    @Override
    public String sayHello(String str) {
        log.info("~~~~hello");
        return "hello rpc1"+str;
    }
}
