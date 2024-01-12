package org.example.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class SystemConfig {
    @JsonProperty(value = "app_channel_id")
    private String appChannelId;
    @JsonProperty(value = "data_channel_id")
    private String dataChannelId;
    @JsonProperty(value = "api_key")
    private String apiKey;
    @JsonProperty(value = "app_app_channel_id")
    private String appAppChannelId;
    @JsonProperty(value = "mini_app_channel_id")
    private String miniAppChannelId;
    @JsonProperty(value = "switch_pingan")
    private int switchPingAn;
    @JsonProperty(value = "switch_wx")
    private int switchWx;
    @JsonProperty(value = "shop_syn_receipt_callback")
    private String shopSynReceiptCallback;
    @JsonProperty(value = "shop_wx_pay_callback")
    private String shopWxPayCallback;
    @JsonProperty(value = "shop_wx_refund_callback")
    private String shopWxRefundCallback;
    @JsonProperty(value = "ms_sms_domain")
    private String msSmsDomain;
    @JsonProperty(value = "ms_log_domain")
    private String msLogDomain;
    @JsonProperty(value = "check_sms_verify_code")
    private int checkSmsVerifyCode;
    private ArrayList<String> channelWhiteList;
    @JsonProperty(value = "log_level")
    private int logLevel;
}
