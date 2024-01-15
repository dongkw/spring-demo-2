package org.example.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.util.JsonUtil;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class Applications implements Serializable {
   @ApiModelProperty("服务实例map")
   Map<String,Application> applicationMap;

   public Applications() {
      this.applicationMap = new ConcurrentHashMap<>();
   }

   public void addInstance(Instance instance){
      Application application = applicationMap.getOrDefault(instance.getServiceName(), new Application(instance.getServiceName()));
      application.addInstance(instance);
      applicationMap.put(instance.getServiceName(), application);
   }
   public void removeInstance(Instance instance){
      Application application = applicationMap.getOrDefault(instance.getServiceName(), new Application(instance.getServiceName()));
      application.removeInstance(instance);
      if (application.getInstances().isEmpty()){
         applicationMap.remove(instance.getServiceName());
      }
   }

   public void renew(Instance instance){


      Application application = applicationMap.getOrDefault(instance.getServiceName(), new Application(instance.getServiceName()));
      application.addInstance(instance);
      applicationMap.put(instance.getServiceName(), application);
   }

   @Override
   public String toString() {
      return JsonUtil.jsonEncode(this);
   }
}
