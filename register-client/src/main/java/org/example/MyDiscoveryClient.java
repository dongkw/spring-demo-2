package org.example;/*
 * Copyright 2013-2022 the original author or authors.
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


import java.util.*;


import lombok.val;
import org.example.RegisterService;
import org.example.bean.Application;
import org.example.bean.Applications;
import org.example.bean.Instance;
import org.example.cache.ApplicationsHolder;
import org.example.reactive.MyServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.Ordered;

/**
 * A {@link DiscoveryClient} implementation for Eureka.
 *
 * @author Spencer Gibb
 * @author Tim Ysewyn
 */
public class MyDiscoveryClient implements DiscoveryClient {

    /**
     * Client description {@link String}.
     */
    public static final String DESCRIPTION = "Spring Cloud Eureka Discovery Client";

    private final ApplicationsHolder applicationsHolder;

    public MyDiscoveryClient(ApplicationsHolder applicationsHolder) {
        this.applicationsHolder = applicationsHolder;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        Applications applications = this.applicationsHolder.getApplication();
        List<ServiceInstance> instances = new ArrayList<>();
        Map<String,Application> applicationMap = applications.getApplicationMap();
        Application application=applicationMap.get(serviceId);
        for (Instance info : application.getInstances()) {
            instances.add(new MyServiceInstance(info));
        }
        return instances;
    }

    @Override
    public List<String> getServices() {
        Applications applications = this.applicationsHolder.getApplication();
        if (applications == null) {
            return Collections.emptyList();
        }
        List<String> names = new ArrayList<>();
        for (Map.Entry<String, Application> app : applications.getApplicationMap().entrySet()) {
            if (app.getValue().getInstances().isEmpty()) {
                continue;
            }
            names.add(app.getKey().toLowerCase());

        }
        return names;
    }

    @Override
    public int getOrder() {
        return DiscoveryClient.DEFAULT_ORDER;
    }

}
