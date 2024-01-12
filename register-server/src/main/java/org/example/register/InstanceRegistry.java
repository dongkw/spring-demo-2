package org.example.register;

import org.example.bean.Applications;
import org.example.bean.Instance;

import java.util.List;

public interface InstanceRegistry {

    void register(Instance instance);

    void renew(Instance instance);

    void remove(Instance instance);

    Applications getInstance();


}
