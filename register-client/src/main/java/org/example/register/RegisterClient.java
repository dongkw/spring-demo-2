package org.example.register;

import org.example.bean.Applications;
import org.example.bean.Instance;
import org.example.util.HttpResponse;

import java.util.List;

public interface RegisterClient {
    HttpResponse<Void> registerService(String serviceName, String groupName, Instance instance);


    void batchRegisterService(String serviceName, String groupName, List<Instance> instances);
    Instance sendHeartBeat(String serviceName, String groupName,  Instance info);

    void batchDeregisterService(String serviceName, String groupName, List<Instance> instances);


    HttpResponse<Void> deregisterService(String serviceName, String groupName, Instance instance);


    void updateInstance(String serviceName, String groupName, Instance instance) ;


    Applications getApplications(String groupName) ;


    Instance getInstance(String instanceId);;


    boolean deleteService(String serviceName, String groupName);


    void shutdown();
}
