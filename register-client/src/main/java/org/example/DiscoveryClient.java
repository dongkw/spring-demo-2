package org.example;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.example.bean.Applications;
import org.example.bean.Instance;
import org.example.cache.ApplicationsHolder;
import org.example.core.MyRegisterProperties;
import org.example.register.RegisterClient;
import org.example.util.InstanceUtil;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DiscoveryClient implements RegisterService {

    private volatile long lastSuccessfulHeartbeatTimestamp = -1;
    private static final int DEFAULT_DELAY = 1;
    private ScheduledExecutorService schedule;
    private final ApplicationsHolder applicationsHolder;
    private final RegisterClient registerClient;
    private final MyRegisterProperties properties;
    private Instance instance;

    private boolean registerSuccess;

    public DiscoveryClient(ApplicationsHolder applicationsHolder,
                           RegisterClient registerClient,MyRegisterProperties myRegisterProperties) {
        this.applicationsHolder = applicationsHolder;
        this.registerClient = registerClient;
        this.properties=myRegisterProperties;
        this.schedule = new ScheduledThreadPoolExecutor(1,
                new ThreadFactoryBuilder()
                        .setNameFormat("DiscoveryClient--%d")
                        .setDaemon(true)
                        .build());

        schedule.schedule(new CacheRefreshTask(), DEFAULT_DELAY, TimeUnit.SECONDS);

        schedule.schedule(new HeartbeatTask(), DEFAULT_DELAY, TimeUnit.SECONDS);
    }


    public Applications getApplications() {
        return registerClient.getApplications(MyRegisterProperties.DEFAULT_GROUP_NAME);
    }

    @Override
    public List<Instance> getAllInstances(String serviceName, String groupName) {

        return null;
    }

    @Override
    public Instance selectOneHealthyInstance(String serviceName, String groupName) {
        return null;
    }

    @Override
    public void registerInstance(String serviceName, String ip, int port, String clusterName) {
        if (!properties.isRegisterEnabled()){
            return;
        }

        Instance instance = new Instance();
        instance.setIp(ip);
        instance.setPort(port);
        instance.setWeight(1.0);
        instance.setClusterName(clusterName);
        instance.setServiceName(serviceName);
        instance.setAppChannelId(properties.getAppChannelId());

        InstanceUtil.setInstanceIdIfEmpty(instance, "default");
        this.instance = instance;
        try {
            registerClient.registerService(serviceName, "", instance);
            registerSuccess = true;
            log.info("注册成功,{}",instance);
        } catch (Exception e) {
            log.info("注册失败{}", e.getMessage());
            schedule.schedule(new InstanceReplicatorTask(), DEFAULT_DELAY * properties.getRegistryFetchIntervalSeconds(),
                    TimeUnit.SECONDS);
            registerSuccess = false;
        }

    }

    @Override
    public void deregisterInstance(String serviceName, String ip, int port, String clusterName) {

        Instance instance = new Instance();
        instance.setIp(ip);
        instance.setPort(port);
        instance.setWeight(1.0);
        instance.setClusterName(clusterName);
        instance.setServiceName(serviceName);
        instance.setAppChannelId(properties.getAppChannelId());

        InstanceUtil.setInstanceIdIfEmpty(instance, MyRegisterProperties.DEFAULT_GROUP_NAME);
        this.instance = instance;
        registerClient.deregisterService(serviceName,MyRegisterProperties.DEFAULT_GROUP_NAME,instance);
    }

    @Override
    public String getServerStatus() {
        return null;
    }


    void renew() {
        try {
            registerClient.sendHeartBeat(instance.getServiceName(), MyRegisterProperties.DEFAULT_GROUP_NAME, instance);
            log.debug("发送心跳,{}",instance);
        } catch (Exception e) {
            registerInstance();
        }
    }

    void registerInstance() {

        registerInstance(instance.getServiceName(), instance.getIp(), instance.getPort(), instance.getClusterName());

    }

    public class CacheRefreshTask implements Runnable {


        @Override
        public void run() {
            try {
                Applications applications = getApplications();
                applicationsHolder.processApplications(applications);
                log.debug("查询application,{}",applications);
            }catch (Exception e){
                log.error("查询Application失败");
            }finally {
                schedule.schedule(new CacheRefreshTask(), DEFAULT_DELAY * properties.getReplicationIntervalSeconds(),
                        TimeUnit.SECONDS);
            }

        }
    }

    public class HeartbeatTask implements Runnable {
        @Override
        public void run() {

            try {
                if (!registerSuccess) {
                    return;
                }
                renew();
            } catch (Exception e) {
                log.info("异常");
            } finally {
                schedule.schedule(new HeartbeatTask(), DEFAULT_DELAY * properties.getRegistryFetchIntervalSeconds(), TimeUnit.SECONDS);
            }


        }
    }

    class InstanceReplicatorTask implements Runnable {
        @Override
        public void run() {
            registerInstance();
        }
    }
}
