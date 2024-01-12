package org.example.http;

import org.example.bean.Application;
import org.example.bean.Applications;
import org.example.bean.Instance;
import org.example.cache.ApplicationsHolder;
import org.example.core.MyRegisterProperties;
import org.example.http.request.DeleteServiceInfoRequest;
import org.example.http.request.GetServiceInfoListRequest;
import org.example.http.request.PushServiceInfoRequest;
import org.example.http.response.MsResponse;
import org.example.register.RegisterClient;
import org.example.util.HttpResponse;
import org.example.util.JsonUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.example.util.HttpResponse.anHttpResponse;

public class RegisterHttpProxy implements RegisterClient {


    private WebClient webClient;

    private final MyRegisterProperties myRegisterProperties;
    private final ApplicationsHolder applicationsHolder;

    public RegisterHttpProxy(MyRegisterProperties myRegisterProperties, ApplicationsHolder applicationsHolder) {
        this.myRegisterProperties = myRegisterProperties;

        this.applicationsHolder = applicationsHolder;
    }

    public WebClient getWebClient() {
        if (webClient == null) {
            synchronized (this) {
                if (webClient == null) {

                    String url = myRegisterProperties.getServiceAddr();
                    Application application = applicationsHolder.getApplication(myRegisterProperties.getDefaultZone());
                    if (Objects.nonNull(application)) {
                        List<String> urls = application.getInstances().stream()
                                .map(t -> t.toInetAddr())
                                .collect(Collectors.toList());
                        urls.add(url);
                    }

                    this.webClient = WebClient.create("http://" + url);
                }
            }
        }
        return webClient;
    }

    @Override
    public HttpResponse<Void> registerService(String serviceName, String groupName, Instance instance) {
        instance.addMetadata("heart", "enable");


        ResponseEntity<MsResponse> httpResponse = getWebClient().post().uri(myRegisterProperties.getDefaultRegisterUrl())
                .body(BodyInserters.fromValue(new PushServiceInfoRequest(instance)))
                .header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("x-app_channel_id", myRegisterProperties.getAppChannelId())
                .header("x-data_channel_id", myRegisterProperties.getDataChannelId())
                .retrieve()
                .onStatus(HttpStatus::isError, this::ignoreError).toEntity(MsResponse.class)
                .block();
        MsResponse response = httpResponse.getBody();
        return HttpResponse.status(200);
    }

    @Override
    public void batchRegisterService(String serviceName, String groupName, List<Instance> instances) {

    }

    @Override
    public Instance sendHeartBeat(String serviceName, String groupName, Instance instance) {
        instance.addMetadata("heart", "enable");

        HttpResponse<Void> httpResponse = getWebClient().post()
                .uri(myRegisterProperties.getDefaultHeartBeatUrl())
                .body(BodyInserters.fromValue(new PushServiceInfoRequest(instance)))
                .header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("x-app_channel_id", myRegisterProperties.getAppChannelId())
                .header("x-data_channel_id", myRegisterProperties.getDataChannelId())
                .retrieve()
                .onStatus(HttpStatus::isError, this::ignoreError).toBodilessEntity().map(this::httpResponse)
                .block();
        return instance;
    }

    @Override
    public void batchDeregisterService(String serviceName, String groupName, List<Instance> instances) {

    }

    @Override
    public HttpResponse<Void> deregisterService(String serviceName, String groupName, Instance instance) {
        return getWebClient().post().uri(myRegisterProperties.getDefaultDeregisterUrl())
                .body(BodyInserters.fromValue(new DeleteServiceInfoRequest(instance)))
                .header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("x-app_channel_id", myRegisterProperties.getAppChannelId())
                .header("x-data_channel_id", myRegisterProperties.getDataChannelId())

                .retrieve()
                .onStatus(HttpStatus::isError, this::ignoreError).toBodilessEntity().map(this::httpResponse)
                .block();
    }

    @Override
    public void updateInstance(String serviceName, String groupName, Instance instance) {

    }

    @Override
    public Applications getApplications(String groupName) {
        MsResponse<Applications> response = getWebClient().post()
                .uri(myRegisterProperties.getDefaultGetApplicationsUrl())
                .body(BodyInserters.fromValue(new GetServiceInfoListRequest()))
                .header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("x-app_channel_id", myRegisterProperties.getAppChannelId())
                .header("x-data_channel_id", myRegisterProperties.getDataChannelId())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<MsResponse<Applications>>() {
                }).block();
        if (response != null&&!response.getCode().equals("50000")){
            return response.getData();
        }
        throw new RuntimeException(response.getCode());
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
