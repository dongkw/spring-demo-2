package org.example.bean;

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

   private String name;
   private Set<Instance> instances;
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
