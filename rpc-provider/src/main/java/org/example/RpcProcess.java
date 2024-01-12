package org.example;

import org.example.cache.BeanHolders;
import org.rpc.Instance;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class RpcProcess {

    private final BeanHolders beanHolders;

    public RpcProcess(BeanHolders beanHolders) {
        this.beanHolders = beanHolders;
    }

    public Object execute(Instance instance) {
        Class<?> clazz = beanHolders.getClassByInterfaceName(instance.getClassName());
        Object obj = null;
        try {
            Method method = clazz.getMethod(instance.getMethodName(), instance.getParameterTypes());
            obj = method.invoke(clazz.getConstructor().newInstance(), instance.getArgs());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 InstantiationException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

}
