package org.example.config;

import org.example.RpcHttpController;
import org.example.RpcProcess;
import org.example.cache.BeanHolders;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RpcProviderAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public BeanHolders beanHolders() {
        return new BeanHolders();
    }

    @Bean
    @ConditionalOnMissingBean
    public RpcProcess rpcProcess(BeanHolders beanHolders) {
        return new RpcProcess(beanHolders);
    }

    @Bean
    @ConditionalOnMissingBean
    public RpcHttpController rpcHttpController(RpcProcess rpcProcess) {

        return new RpcHttpController(rpcProcess);
    }

    @Bean
    public MyBeanPostProcessor myBeanPostProcessor(BeanHolders beanHolders){
        return new MyBeanPostProcessor(beanHolders);
    }
}
