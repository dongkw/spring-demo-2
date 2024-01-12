package org.example.http.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.bean.Instance;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PushServiceInfoRequest extends MsBaseRequest {
    @NotEmpty(message = "应用渠道号错误")
    private String appChannelId;
    @NotEmpty(message = "请求协议错误")
    private String protocol;
    @NotEmpty(message = "实例id不能为空")
    private String instanceId;
    @NotEmpty(message = "服务名称不能为空")
    private String serviceName;
    @NotEmpty(message = "ip地址错误")
    private String ip;
    private Integer port;
    private Map<String, String> extraContent;

    public PushServiceInfoRequest(Instance instance) {
        this.appChannelId = instance.getAppChannelId();
        this.instanceId=instance.getInstanceId();
        this.serviceName=instance.getServiceName();
        this.ip=instance.getIp();
        this.port=instance.getPort();
        this.protocol="http";
        this.extraContent=instance.getMetadata();
    }
}
