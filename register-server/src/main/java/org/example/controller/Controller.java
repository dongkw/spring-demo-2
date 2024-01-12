package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.bean.Applications;
import org.example.bean.Instance;
import org.example.http.request.DeleteServiceInfoRequest;
import org.example.http.request.GetServiceInfoListRequest;
import org.example.http.request.PushServiceInfoRequest;
import org.example.http.response.MsResponse;
import org.example.register.InstanceRegistry;
import org.example.util.HttpResponse;
import org.example.util.InstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class Controller {

    @Autowired
    private InstanceRegistry instanceRegistry;

    @PostMapping("/register")
    public MsResponse pushServiceInfo(@RequestBody @Valid PushServiceInfoRequest request) {
        instanceRegistry.register(InstanceFactory.api(request));
        return MsResponse.success();
    }
    @PostMapping("/heart")
    public MsResponse renew(@RequestBody @Valid PushServiceInfoRequest request) {
        instanceRegistry.renew(InstanceFactory.api(request));
        return MsResponse.success();
    }
    @PostMapping("/deregister")
    public MsResponse deleteServiceInfo(@RequestBody @Valid DeleteServiceInfoRequest request) {
        instanceRegistry.remove(InstanceFactory.genDeleteInstance(request));
        return MsResponse.success();
    }

    @PostMapping("/application")
    public MsResponse<Applications> getServiceInfoList(@RequestBody GetServiceInfoListRequest request) {
        Applications applications = instanceRegistry.getInstance();
        return MsResponse.success(applications);
    }
}
