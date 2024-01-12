package org.example.config;

import org.example.cache.BeanHolders;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import java.util.Arrays;


public class MyBeanPostProcessor implements BeanPostProcessor {


    private final BeanHolders beanHolders;

    public MyBeanPostProcessor(BeanHolders beanHolders) {
        this.beanHolders = beanHolders;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        boolean present = bean.getClass().isAnnotationPresent(Service.class);
        if (present) {
            Class<?>[] clazz = bean.getClass().getInterfaces();
            Arrays.stream(clazz).forEach(t -> {
                beanHolders.addBean(t.getSimpleName(), bean.getClass());
            });
        }
        return bean;

    }
}
