package org.example.config;

import org.example.core.MyRegisterProperties;
import org.example.loadbalancer.MyLoadBalancer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class LoadBalancerConfiguration {
//    @Bean
//    @ConditionalOnMissingBean
//    public ReactorLoadBalancer<ServiceInstance> myLoadBalancer(Environment environment,
//                                                                  LoadBalancerClientFactory loadBalancerClientFactory,
//                                                                  MyRegisterProperties nacosDiscoveryProperties) {
//        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//        return new MyLoadBalancer(
//                loadBalancerClientFactory.getLazyProvider(name,
//                        ServiceInstanceListSupplier.class),
//                name, nacosDiscoveryProperties);
//    }
//    @Bean
//    @ConditionalOnMissingBean
//    public ReactorLoadBalancer<ServiceInstance> roundRobinLoadLoadBalancer(Environment environment,
//                                                                  LoadBalancerClientFactory loadBalancerClientFactory,
//                                                                  MyRegisterProperties nacosDiscoveryProperties) {
//        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//        return new RoundRobinLoadBalancer(
//                loadBalancerClientFactory.getLazyProvider(name,
//                        ServiceInstanceListSupplier.class),
//                name);
//    }

}
