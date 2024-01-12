package org.example.http.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsBaseRequest {
    public String appChannelId;
    public String dataChannelId;
}
