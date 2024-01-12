package org.example.reactive;

import org.example.bean.Instance;
import org.springframework.cloud.client.ServiceInstance;

import java.net.URI;
import java.util.Map;

public class MyServiceInstance implements ServiceInstance {

    private final Instance instance;

    public MyServiceInstance(Instance instance){
        this.instance=instance;
    }

    @Override
    public String getServiceId() {
        return instance.getServiceName();
    }

    @Override
    public String getHost() {
        return instance.getIp();
    }

    @Override
    public int getPort() {
        return instance.getPort();
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public URI getUri() {
        return getUri(this);
    }
    public static URI getUri(ServiceInstance instance) {
        String scheme = instance.isSecure() ? "https" : "http";
        int port = instance.getPort();
        if (port <= 0) {
            port = instance.isSecure() ? 443 : 80;
        }

        String uri = String.format("%s://%s:%s", scheme, instance.getHost(), port);
        return URI.create(uri);
    }

    @Override
    public Map<String, String> getMetadata() {
        return instance.getMetadata();
    }
}
