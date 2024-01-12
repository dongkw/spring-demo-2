package org.example.register;

import org.example.RegisterService;
import org.example.core.MyRegisterProperties;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

public class MyServiceRegistry implements ServiceRegistry<MyRegistration> {
    RegisterService discoveryClient;

    public MyServiceRegistry(RegisterService discoveryClient){
        this.discoveryClient=discoveryClient;
    }

    @Override
    public void register(MyRegistration registration) {
        discoveryClient.registerInstance(registration.getServiceId(),registration.getHost(),registration.getPort(),MyRegisterProperties.DEFAULT_GROUP_NAME);
    }

    @Override
    public void deregister(MyRegistration registration) {
        discoveryClient.deregisterInstance(registration.getServiceId(),registration.getHost(),registration.getPort(), MyRegisterProperties.DEFAULT_GROUP_NAME);
        System.out.println("从注册中心注销");

    }

    @Override
    public void close() {
        System.out.println("关闭");
    }

    @Override
    public void setStatus(MyRegistration registration, String status) {

    }

    @Override
    public <T> T getStatus(MyRegistration registration) {
        return null;
    }
}
