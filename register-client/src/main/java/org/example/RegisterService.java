package org.example;

import org.example.bean.Applications;
import org.example.bean.Instance;

import java.util.List;

public interface RegisterService {
    Applications getApplications();
    List<Instance> getAllInstances(String serviceName, String groupName);
    Instance selectOneHealthyInstance(String serviceName, String groupName);
    void registerInstance(String serviceName, String ip, int port, String clusterName);
    void deregisterInstance(String serviceName, String ip, int port, String clusterName);
    String getServerStatus();

}
