package org.example.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanHolders {

    private Map<String, Class<?>> classMap;

    public BeanHolders() {
        classMap = new ConcurrentHashMap<>();
    }

    public void addBean(String interfaceName,Class<?> clazz){
        classMap.put(interfaceName,clazz);
    }

    public Class<?> getClassByInterfaceName(String interfaceName){
        return classMap.get(interfaceName);
    }
}
