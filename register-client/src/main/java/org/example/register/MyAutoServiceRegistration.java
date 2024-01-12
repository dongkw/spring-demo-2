package org.example.register;

import org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.util.Assert;

public class MyAutoServiceRegistration extends AbstractAutoServiceRegistration<MyRegistration> {
    private MyRegistration registration;

    public MyAutoServiceRegistration(ServiceRegistry<MyRegistration> serviceRegistry,
                                     AutoServiceRegistrationProperties properties,
                                     MyRegistration registration) {
        super(serviceRegistry, properties);
        this.registration=registration;
    }

    @Override
    protected Object getConfiguration() {
        return null;
    }

    @Override
    protected boolean isEnabled() {
        return true;

    }

    @Override
    protected MyRegistration getRegistration() {
        if (this.registration.getPort() < 0 && this.getPort().get() > 0) {
            this.registration.setPort(this.getPort().get());
        }
        Assert.isTrue(this.registration.getPort() > 0, "service.port has not been set");
        return this.registration;
    }

    @Override
    protected MyRegistration getManagementRegistration() {
        return null;
    }
}
