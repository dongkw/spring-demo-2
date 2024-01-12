package org.example.register;

import org.example.bean.Application;
import org.example.bean.Applications;
import org.example.bean.Instance;
import org.example.cache.ApplicationsHolder;
import org.example.core.MyRegisterProperties;
import org.example.register.RegisterClient;
import org.example.util.HttpResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.example.util.HttpResponse.anHttpResponse;

public class RegisterLocalProxy implements RegisterClient {


    private InstanceRegistry instanceRegistry;

    public RegisterLocalProxy(InstanceRegistry instanceRegistry) {
        this.instanceRegistry = instanceRegistry;
    }

    @Override
    public HttpResponse<Void> registerService(String serviceName, String groupName, Instance info) {
        info.addMetadata("zoon","register");
        instanceRegistry.register(info);
        return HttpResponse.anHttpResponse(200).build();
    }

    @Override
    public void batchRegisterService(String serviceName, String groupName, List<Instance> instances) {

    }

    @Override
    public Instance sendHeartBeat(String serviceName, String groupName, Instance instance) {
        instance.addMetadata("zoon","register");
        instanceRegistry.register(instance);
        return instance;
    }

    @Override
    public void batchDeregisterService(String serviceName, String groupName, List<Instance> instances) {

    }

    @Override
    public HttpResponse<Void> deregisterService(String serviceName, String groupName, Instance instance) {
        instance.addMetadata("zoon","register");
        instanceRegistry.remove(instance);
        return HttpResponse.anHttpResponse(200).build();

    }

    @Override
    public void updateInstance(String serviceName, String groupName, Instance instance) {

    }

    @Override
    public Applications getApplications(String groupName) {
        return instanceRegistry.getInstance();
    }

    @Override
    public Instance getInstance(String instanceId) {
        return null;
    }

    @Override
    public boolean deleteService(String serviceName, String groupName) {
        return false;
    }

    private Mono<? extends Throwable> ignoreError(ClientResponse response) {
        return Mono.empty();
    }

    private static Map<String, String> headersOf(ResponseEntity<?> response) {
        return response.getHeaders().toSingleValueMap();
    }

    private int statusCodeValueOf(ResponseEntity<?> response) {
        return response.getStatusCode().value();
    }

    private HttpResponse<Void> httpResponse(ResponseEntity<?> response) {
        return anHttpResponse(statusCodeValueOf(response)).headers(headersOf(response)).build();
    }

    @Override
    public void shutdown() {

    }
}
