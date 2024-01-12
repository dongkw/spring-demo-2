/*
 * Copyright 1999-2020 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.example.util;



import org.example.bean.Instance;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Instance util.
 *
 * @author xiweng.yy
 */
public final class InstanceUtil {
    


    /**
     * Deepcopy one instance.
     * 
     * @param source instance to be deepcopy
     */
    public static Instance deepCopy(Instance source) {
        Instance target = new Instance();
        target.setInstanceId(source.getInstanceId());
        target.setIp(source.getIp());
        target.setPort(source.getPort());
        target.setWeight(source.getWeight());
        target.setHealthy(source.isHealthy());
        target.setEnabled(source.isEnabled());
        target.setEphemeral(source.isEphemeral());
        target.setClusterName(source.getClusterName());
        target.setServiceName(source.getServiceName());
        target.setMetadata(new HashMap<>(source.getMetadata()));
        return target;
    }
    
    /**
     * If the instance id is empty, use the default-instance-id-generator method to set the instance id.
     *
     * @param instance    instance from request
     * @param groupedServiceName groupedServiceName from service
     */
    public static void setInstanceIdIfEmpty(Instance instance, String groupedServiceName) {
        if (null != instance && StringUtils.isEmpty(instance.getInstanceId())) {
            DefaultInstanceIdGenerator idGenerator = new DefaultInstanceIdGenerator(groupedServiceName,
                    instance.getClusterName(), instance.getIp(), instance.getPort());
            instance.setInstanceId(idGenerator.generateInstanceId());
        }
    }
    
    /**
     * Batch set instance id if empty.
     *
     * @param instances   instances from request
     * @param groupedServiceName groupedServiceName from service
     */
    public static void batchSetInstanceIdIfEmpty(List<Instance> instances, String groupedServiceName) {
        if (null != instances) {
            for (Instance instance : instances) {
                setInstanceIdIfEmpty(instance, groupedServiceName);
            }
        }
    }
}
