package org.example.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.example.constants.Constants;
import org.example.constants.PreservedMetadataKeys;
import org.example.util.NamingUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Data
public class Instance implements Serializable {

    private static final long serialVersionUID = -742906310567291979L;


    /**
     * unique id of this instance.
     */
    @ApiModelProperty("实例id")
    private String instanceId;
    @ApiModelProperty("服务渠道id")
    private String appChannelId;
    @ApiModelProperty("服务过期时间")
    private Long instanceTimeout;
    /**
     * instance ip.
     */
    @ApiModelProperty("服务ip")
    private String ip;

    /**
     * instance port.
     */
    @ApiModelProperty("服务端口号")
    private int port;

    /**
     * instance weight.
     */
    @ApiModelProperty("服务权重")
    private double weight = 1.0D;

    /**
     * instance health status.
     */
    private boolean healthy = true;

    /**
     * If instance is enabled to accept request.
     */
    private boolean enabled = true;

    /**
     * If instance is ephemeral.
     *
     * @since 1.0.0
     */
    private boolean ephemeral = true;

    /**
     * cluster information of instance.
     */
    private String clusterName;

    /**
     * Service information of instance.
     */
    @ApiModelProperty("服务名称")
    private String serviceName;

    /**
     * user extended attributes.
     */
    @ApiModelProperty("元数据map")
    private Map<String, String> metadata = new HashMap<>();


    /**
     * add meta data.
     *
     * @param key   meta data key
     * @param value meta data value
     */
    public void addMetadata(final String key, final String value) {
        if (metadata == null) {
            metadata = new HashMap<>(4);
        }
        metadata.put(key, value);
    }


    @Override
    public String toString() {
        return "Instance{" + "instanceId='" + instanceId + '\'' + ", ip='" + ip + '\'' + ", port=" + port + ", weight="
                + weight + ", healthy=" + healthy + ", enabled=" + enabled + ", ephemeral=" + ephemeral
                + ", clusterName='" + clusterName + '\'' + ", serviceName='" + serviceName + '\'' + ", metadata="
                + metadata + '}';
    }

    public String toInetAddr() {
        return ip + ":" + port;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Instance)) {
            return false;
        }

        final Instance host = (Instance) obj;
        return Instance.strEquals(host.toString(), toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    private static boolean strEquals(final String str1, final String str2) {
        return Objects.equals(str1, str2);
    }

    public long getInstanceHeartBeatInterval() {
        return getMetaDataByKeyWithDefault(PreservedMetadataKeys.HEART_BEAT_INTERVAL,
                Constants.DEFAULT_HEART_BEAT_INTERVAL);
    }

    public long getInstanceHeartBeatTimeOut() {
        return getMetaDataByKeyWithDefault(PreservedMetadataKeys.HEART_BEAT_TIMEOUT,
                Constants.DEFAULT_HEART_BEAT_TIMEOUT);
    }

    public long getIpDeleteTimeout() {
        return getMetaDataByKeyWithDefault(PreservedMetadataKeys.IP_DELETE_TIMEOUT,
                Constants.DEFAULT_IP_DELETE_TIMEOUT);
    }

    public String getInstanceIdGenerator() {
        return getMetaDataByKeyWithDefault(PreservedMetadataKeys.INSTANCE_ID_GENERATOR,
                Constants.DEFAULT_INSTANCE_ID_GENERATOR);
    }

    /**
     * Returns {@code true} if this metadata contains the specified key.
     *
     * @param key metadata key
     * @return {@code true} if this metadata contains the specified key
     */
    public boolean containsMetadata(final String key) {
        if (getMetadata() == null || getMetadata().isEmpty()) {
            return false;
        }
        return getMetadata().containsKey(key);
    }

    private long getMetaDataByKeyWithDefault(final String key, final long defaultValue) {
        if (getMetadata() == null || getMetadata().isEmpty()) {
            return defaultValue;
        }
        final String value = getMetadata().get(key);
        if (NamingUtils.isNumber(value)) {
            return Long.parseLong(value);
        }
        return defaultValue;
    }

    private String getMetaDataByKeyWithDefault(final String key, final String defaultValue) {
        if (getMetadata() == null || getMetadata().isEmpty()) {
            return defaultValue;
        }
        return getMetadata().get(key);
    }

}
