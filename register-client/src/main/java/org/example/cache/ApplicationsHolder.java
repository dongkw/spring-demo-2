package org.example.cache;


import org.example.bean.Application;
import org.example.bean.Applications;
import org.example.bean.Instance;

public class ApplicationsHolder {

    Applications applications;

    public ApplicationsHolder() {
        applications=new Applications();
    }

    public Applications getApplication(){
        applications=new Applications();
        return applications;
    }
    public Application getApplication(String serviceName){
        return applications.getApplicationMap().get(serviceName);
    }

    public Applications processApplications(Applications applications){
        this.applications=applications;
        return this.applications;
    }
}
