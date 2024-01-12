package org.example.config;

import org.example.cache.ApplicationsHolder;
import org.example.core.MyRegisterProperties;
import org.example.http.RegisterHttpProxy;
import org.example.register.InstanceRegistry;
import org.example.register.RegisterClient;
import org.example.register.RegisterLocalProxy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRegisterServiceAutoConfig {

    @Bean
    public RegisterClient registerClient(InstanceRegistry instanceRegistry) {
        return new RegisterLocalProxy(instanceRegistry);
    }
}
