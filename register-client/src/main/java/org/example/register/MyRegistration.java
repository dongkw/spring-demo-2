package org.example.register;


import lombok.Getter;
import lombok.Setter;
import org.example.core.MyRegisterProperties;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.serviceregistry.Registration;

import java.net.URI;
import java.util.Map;

@Getter
@Setter
public class MyRegistration implements Registration {


    @Override
    public String getServiceId() {
        return myRegisterProperties.getService();
    }

    @Override
    public String getHost() {
        return myRegisterProperties.getIp();
    }

    @Override
    public int getPort() {
        return myRegisterProperties.getPort();
    }
    public void setPort(int port) {
         myRegisterProperties.setPort(port);
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public URI getUri() {
        return DefaultServiceInstance.getUri(this);
    }

    @Override
    public Map<String, String> getMetadata() {
        return null;
    }

   private MyRegisterProperties myRegisterProperties;

    public MyRegistration(MyRegisterProperties myRegisterProperties) {
        this.myRegisterProperties=myRegisterProperties;

    }
}
