package org.example.util;


import org.example.bean.Instance;
import org.example.http.request.DeleteServiceInfoRequest;
import org.example.http.request.PushServiceInfoRequest;
import org.example.util.InstanceUtil;

import java.util.Date;
import java.util.Map;

public class InstanceFactory {

    public static Instance api(PushServiceInfoRequest request){
        Instance instance = new Instance();

        instance.setInstanceId(request.getInstanceId());
        instance.setAppChannelId(request.getAppChannelId());
        instance.setServiceName(request.getServiceName());

        instance.setIp(request.getIp());
        instance.setPort(request.getPort());
        instance.setMetadata(request.getExtraContent());
        return instance;
    }
    public static Instance genDeleteInstance(DeleteServiceInfoRequest request){
        Instance instance = new Instance();

        instance.setAppChannelId(request.getAppChannelId());
        instance.setIp(request.getIp());
        instance.setPort(request.getPort());
        return instance;
    }

}
