package org.rpc;

import lombok.Data;

import java.io.Serializable;

@Data
public class Instance implements Serializable {

    private String className;

    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] args;
}
