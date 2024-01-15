package org.example.core;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
public class MyRegisterProperties {
    private static final String IPV4 = "IPv4";

    private static final String IPV6 = "IPv6";

    /**
     * Default availability zone if none is resolved based on region.
     */
    public static final String DEFAULT_ZONE = "defaultZone";
    public static final String DEFAULT_GROUP_NAME = "default";
    @Value("${spring.cloud.nacos.discovery.service:${spring.application.name:}}")
    private String service;
    @Value("${spring.cloud.discovery.name:localhost:8761}")
    private String serviceAddr;

    @Value("${spring.cloud.discovery.server:MicroService-Config}")
    private String defaultZone;

    private String ip;


    private int port = -1;
    @Value("${spring.cloud.discovery.register_url:/register}")
    private String defaultRegisterUrl;
    @Value("${spring.cloud.discovery.heart_beat_url:/heart}")
    private String defaultHeartBeatUrl;
    @Value("${spring.cloud.discovery.deregister_url:/deregister}")
    private String defaultDeregisterUrl;
    @Value("${spring.cloud.discovery.application_url:/application}")
    private String defaultGetApplicationsUrl;

    public void setPort(int port) {
        this.port = port;
    }
    private String networkInterface = "";

    private String ipType;

    private Map<String, String> metadata = new HashMap<>();

    @Value("${spring.cloud.discovery.register-enable:true}")
    private boolean registerEnabled;

    @Value("${spring.cloud.discovery.instance-info-replication-interval-seconds:30}")
    private long replicationIntervalSeconds;

    @Value("${spring.cloud.discovery.registry-fetch-interval-seconds:30}")
    private long registryFetchIntervalSeconds;
    @Value("${spring.cloud.discovery.instance-timeout:90}")
    private long instanceTimeout;
    @Value("${server.appChannelId}")
    private String appChannelId;
    @Value("${server.dataChannelId}")
    private String dataChannelId;
    @Autowired
    private InetUtils inetUtils;



    @PostConstruct
    public void init(){
        if (StringUtils.isEmpty(ip)) {
            // traversing network interfaces if didn't specify an interface
            if (StringUtils.isEmpty(networkInterface)) {
                if (ipType == null) {
                    ip = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
                }
                else if (IPV4.equalsIgnoreCase(ipType)) {
                    ip = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
                }
                else {
                    throw new IllegalArgumentException(
                            "please checking the type of IP " + ipType);
                }
            }
        }

    }
}
