package org.example.config;

import org.example.MyDiscoveryClient;
import org.example.RegisterService;
import org.example.cache.ApplicationsHolder;
import org.example.config.MyServiceRegistryAutoConfiguration;
import org.example.reactive.MyReactiveDiscoveryClient;
import org.example.register.MyServiceRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.ConditionalOnDiscoveryHealthIndicatorEnabled;
import org.springframework.cloud.client.ConditionalOnReactiveDiscoveryEnabled;
import org.springframework.cloud.client.ReactiveCommonsClientAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.client.discovery.composite.reactive.ReactiveCompositeDiscoveryClientAutoConfiguration;
import org.springframework.cloud.client.discovery.health.DiscoveryClientHealthIndicatorProperties;
import org.springframework.cloud.client.discovery.health.reactive.ReactiveDiscoveryClientHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnReactiveDiscoveryEnabled
@EnableConfigurationProperties
@AutoConfigureAfter({ ReactiveCompositeDiscoveryClientAutoConfiguration.class })
@AutoConfigureBefore({ReactiveCommonsClientAutoConfiguration.class})
public class MyReactiveDiscoveryClientConfiguration {

	@Bean
//	@ConditionalOnMissingBean
	public ReactiveDiscoveryClient eurekaReactiveDiscoveryClient(ApplicationsHolder applicationsHolder) {
		return new MyReactiveDiscoveryClient(applicationsHolder);
	}
	@Bean
//	@ConditionalOnMissingBean
	public DiscoveryClient discoveryClient(ApplicationsHolder applicationsHolder) {
		return new MyDiscoveryClient(applicationsHolder);
	}

	@Bean
	@ConditionalOnClass(name = "org.springframework.boot.actuate.health.ReactiveHealthIndicator")
	@ConditionalOnDiscoveryHealthIndicatorEnabled
	public ReactiveDiscoveryClientHealthIndicator eurekaReactiveDiscoveryClientHealthIndicator(
			ReactiveDiscoveryClient client, DiscoveryClientHealthIndicatorProperties properties) {
		return new ReactiveDiscoveryClientHealthIndicator(client, properties);
	}

}