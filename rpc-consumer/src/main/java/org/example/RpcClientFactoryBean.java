/*
 * Copyright 2013-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.example;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.example.http.RpcHttpProxy;
import org.rpc.Instance;
import org.rpc.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Spencer Gibb
 * @author Venil Noronha
 * @author Eko Kurniawan Khannedy
 * @author Gregor Zurowski
 * @author Matt King
 * @author Olga Maciaszek-Sharma
 * @author Ilia Ilinykh
 * @author Marcin Grzejszczak
 * @author Jonatan Ivanov
 * @author Sam Kruglov
 * @author Jasbir Singh
 * @author Hyeonmin Park
 * @author Felix Dittrich
 * @author Dominique Villard
 * @author Can Bezmen
 */
@Setter
@Getter
@Slf4j
public class RpcClientFactoryBean
        implements FactoryBean<Object>, InitializingBean, ApplicationContextAware, BeanFactoryAware {

    /***********************************
     * WARNING! Nothing in this class should be @Autowired. It causes NPEs because of some
     * lifecycle race condition.
     ***********************************/

    private static final Log LOG = LogFactory.getLog(RpcClientFactoryBean.class);


    private Class<?> type;

    private String name;
    private String url;

    private String contextId;
    private String path;

    private boolean dismiss404;

    private boolean inheritParentContext = true;

    private ApplicationContext applicationContext;

    private BeanFactory beanFactory;


    @Override
    public Object getObject() {
        return getTarget();
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }


    <T> T getTarget() {
        return (T) Proxy.newProxyInstance(type.getClassLoader(),
                new Class[]{type},
                new CustomInvocationHandler());
    }

    private class CustomInvocationHandler implements InvocationHandler {


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 构建请求 URL
            Instance instance = new Instance();
            instance.setClassName(type.getSimpleName());
            instance.setMethodName(method.getName());
            instance.setParameterTypes(method.getParameterTypes());
            instance.setArgs(args);
            log.info(StringUtil.jsonEncode(instance));
            String str = RpcHttpProxy.send(getUrl(),getPath(method),instance);

            return str;
        }


        private String getPath(Method method){

            return "/rpc";
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
