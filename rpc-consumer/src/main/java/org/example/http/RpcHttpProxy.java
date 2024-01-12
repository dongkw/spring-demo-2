package org.example.http;

import org.rpc.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class RpcHttpProxy {

    private static WebClient webClient;


    @Autowired
    public void setHttpProxyImpl(WebClient webClient) {
        RpcHttpProxy.webClient = webClient;
    }


    public static String send(String url,String path,Instance instance) {

        String response = webClient.post()
                .uri(url+path)
                .body(BodyInserters.fromValue(instance))
                .header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<String>() {
                }).block();
        return response;
    }

}
