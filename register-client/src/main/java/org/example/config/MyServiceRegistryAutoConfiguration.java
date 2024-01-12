
/*
 * Copyright 2013-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.example.config;

import org.example.DiscoveryClient;
import org.example.RegisterService;
import org.example.cache.ApplicationsHolder;
import org.example.core.MyRegisterProperties;
import org.example.http.RegisterHttpProxy;
//import org.example.loadbalancer.MyLoadBalancer;
import org.example.loadbalancer.MyLoadBalancer;
import org.example.register.MyAutoServiceRegistration;
import org.example.register.MyRegistration;
import org.example.register.MyServiceRegistry;
import org.example.register.RegisterClient;
import org.example.util.HttpUtil;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationAutoConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationConfiguration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;

import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiaojing
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties
@ConditionalOnProperty(value = "spring.cloud.service-registry.auto-registration.enabled",
		matchIfMissing = true)
@ConditionalOnDiscoveryEnabled
@AutoConfigureAfter({ AutoServiceRegistrationConfiguration.class,
		              AutoServiceRegistrationAutoConfiguration.class,
		AutoServiceRegistrationProperties.class
})
public class MyServiceRegistryAutoConfiguration {

	@Bean
	public ApplicationsHolder applicationsHolder(){
		return new ApplicationsHolder();
	}

	@Bean
    @ConditionalOnMissingBean(RegisterClient.class)
	public RegisterClient registerClient(
			 MyRegisterProperties properties,ApplicationsHolder applicationsHolder) {
        return  new RegisterHttpProxy(properties,applicationsHolder);
    }
	@Bean
	public ApplicationsHolder getApplicationHolder(){
		return new ApplicationsHolder();
	}

	@Bean
	public RegisterService myRegisterService(ApplicationsHolder applicationsHolder,
											 RegisterClient registerClient,
											 MyRegisterProperties properties) {
		return new DiscoveryClient(applicationsHolder, registerClient,properties);
	}

	@Bean
	public MyServiceRegistry myServiceRegistry(RegisterService registerService
											   ) {
		return new MyServiceRegistry(registerService);
	}

	@Bean
	public MyRegisterProperties registerProperties(){
		return new MyRegisterProperties();
	}
	@Bean
//	@ConditionalOnBean(AutoServiceRegistrationProperties.class)
	public MyRegistration myRegistration(
			MyRegisterProperties registerProperties
			) {
		return new MyRegistration(registerProperties);
	}
	@Bean //将 RestTemplate 注入到容器中
	@LoadBalanced //在客户端使用 RestTemplate 请求服务端时，开启负载均衡（Ribbon）
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	@Bean
	public HttpUtil httpUtil(){
		return new HttpUtil();
	}
	@Bean
//	@ConditionalOnBean(AutoServiceRegistrationProperties.class)
	public MyAutoServiceRegistration myAutoServiceRegistration(
			MyServiceRegistry registry,
			AutoServiceRegistrationProperties autoServiceRegistrationProperties,
			MyRegistration registration) {
		return new MyAutoServiceRegistration(registry,
				autoServiceRegistrationProperties, registration);
	}



}
