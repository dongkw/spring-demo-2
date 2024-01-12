package org.example.http.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.bean.Instance;

import java.util.HashMap;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetServiceInfoListRequest {
    private Map<String, Object> condition;


    public GetServiceInfoListRequest(Instance instance) {
        condition=new HashMap<>();
        condition.put("appChannelId",instance.getAppChannelId());
    }
}
