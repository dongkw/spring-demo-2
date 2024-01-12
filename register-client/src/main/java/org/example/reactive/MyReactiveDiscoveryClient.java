/*
 * Copyright 2019-2022 the original author or authors.
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

package org.example.reactive;


import org.example.cache.ApplicationsHolder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * A {@link ReactiveDiscoveryClient} implementation for Eureka.
 *
 * @author Tim Ysewyn
 */
public class MyReactiveDiscoveryClient implements ReactiveDiscoveryClient {

	private final ApplicationsHolder applicationsHolder;

	public MyReactiveDiscoveryClient( ApplicationsHolder applicationsHolder) {
		this.applicationsHolder = applicationsHolder;
	}

	@Override
	public String description() {
		return "Spring Cloud Eureka Reactive Discovery Client";
	}

	@Override
	public Flux<ServiceInstance> getInstances(String serviceId) {
		return Flux.defer(() -> Flux.fromIterable(applicationsHolder.getApplication(serviceId).getInstances()))
				.map(MyServiceInstance::new);
	}

	@Override
	public Flux<String> getServices() {
		return Flux.defer(() -> Mono.justOrEmpty(applicationsHolder.getApplication()))
				.flatMapIterable(t->t.getApplicationMap().keySet());
	}

	@Override
	public int getOrder() {
		return ReactiveDiscoveryClient.DEFAULT_ORDER;
	}

}
