package org.example.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Application implements Serializable {

   @ApiModelProperty("服务名称")
   private String name;
   @ApiModelProperty("服务实例集合")
   private Set<Instance> instances;
   @ApiModelProperty("服务实例map")
   private Map<String,Instance> instancesMap;

   public Application() {

   }
   public Application(String name) {
      this.name = name;
      instances=new HashSet<>();
      instancesMap=new ConcurrentHashMap<>();
   }

   public void addInstance(Instance instance){
      instances.add(instance);
      instancesMap.put(instance.getInstanceId(),instance);
   }
   public void removeInstance(Instance instance){
      instances.remove(instance);
      instancesMap.remove(instance.getInstanceId());
   }
}
