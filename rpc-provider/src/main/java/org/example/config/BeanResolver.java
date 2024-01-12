package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
public class BeanResolver {

    @Autowired
    private ApplicationContext applicationContext;



    public List<Class<?>> getClassesWithAnnotation(Class<? extends Annotation> annotationClass) {
        List<Class<?>> annotatedClasses = new ArrayList<>();

        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(annotationClass);
        for (Object bean : beansWithAnnotation.values()) {
            Class<?> beanClass = bean.getClass();
            if (AnnotationUtils.findAnnotation(beanClass, annotationClass) != null) {
                annotatedClasses.add(beanClass);
            }
        }
        return annotatedClasses;
    }

    public  List<Class<?>> findInterfaces(Class<?> implementationClass) {
        List<Class<?>> interfaces = new ArrayList<>();

        Class<?>[] allInterfaces = implementationClass.getInterfaces();
        for (Class<?> interfaceClass : allInterfaces) {
            if (interfaceClass.isInterface()) {
                interfaces.add(interfaceClass);
            }
        }
        return interfaces;
    }
}
