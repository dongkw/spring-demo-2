package org.example.register;


import lombok.val;
import org.example.bean.Application;
import org.example.bean.Applications;
import org.example.bean.Instance;
import org.example.lease.Lease;
import org.example.notify.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


@Service
public class InstanceRegistryImpl implements InstanceRegistry {

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock read = readWriteLock.readLock();
    private final Lock write = readWriteLock.writeLock();

    private Map<String,Map<String,Lease<Instance>>> registry;


    public InstanceRegistryImpl() {
        registry=new ConcurrentHashMap<>();
    }


    @Override
    public void register(Instance instance) {

        Map<String,Lease<Instance>> leasesMap=registry.getOrDefault(instance.getServiceName(),new ConcurrentHashMap<>());
        leasesMap.put(instance.getInstanceId(),new Lease<>(instance,30));
        registry.put(instance.getServiceName(),leasesMap);

    }

    @Override
    public void renew(Instance instance) {

        Map<String,Lease<Instance>> leasesMap=registry.getOrDefault(instance.getServiceName(),new ConcurrentHashMap<>());
        if (!leasesMap.containsKey(instance.getInstanceId())){
            register(instance);
        }
        leasesMap.get(instance.getInstanceId()).renew();

    }

    @Override
    public void remove(Instance instance) {
        Map<String,Lease<Instance>> leasesMap=registry.getOrDefault(instance.getServiceName(),new ConcurrentHashMap<>());
        leasesMap.get(instance.getInstanceId()).cancel();
    }

    @Override
    public Applications getInstance() {
        Applications applications=new Applications();
        registry.forEach((key, value) -> {
            Application application = new Application(key);
            value.forEach((key1, value1) -> {
                if (!value1.isExpired()) {
                    application.addInstance(value1.getHolder());
                }
            });
            applications.getApplicationMap().put(key, application);
        });
        return applications;
    }

}
