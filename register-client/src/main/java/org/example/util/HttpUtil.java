package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//package org.example.util;
//
//
//import org.example.core.SystemConfig;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.net.Inet4Address;
//import java.net.NetworkInterface;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//
public class HttpUtil {

    private static RestTemplate restTemplate = null;
    @Autowired
    private static WebClient webClient;

    @Autowired
    private static OkHttpClient okHttpClient;



    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        HttpUtil.restTemplate = restTemplate;
    }

    @Autowired
    public void setWebClient(WebClient webClient) {
        HttpUtil.webClient = webClient;
    }

    private static RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public static String handlerResponse(String url) {

        return restTemplate.getForObject(url,String.class);
    }

}
//
//    private static SystemConfig systemConfig = BeanUtil.getBean(SystemConfig.class);
//
//    public static String post(String url, String requestBody, Map<String, String> header) {
//        Request request = new Request.Builder()
//                .url(url)
//                .headers(Headers.of(header))
//                .post(RequestBody.create(requestBody, MediaType.parse("application/json;charset=UTF-8")))
//                .build();
//        LogUtil.info("[HttpUtil][header][" + url + "][" + StringUtil.jsonEncode(header) + "]");
//        LogUtil.info("[HttpUtil][request][" + url + "][" + requestBody + "]");
//        String response = handlerResponse(request);
//        LogUtil.info("[HttpUtil][response][" + url + "][" + response + "]");
//        return response;
//    }
//
//    public static String post(String url, String requestBody) {
//        Request request = new Request.Builder()
//                .url(url)
//                .post(RequestBody.create(requestBody, MediaType.parse("application/json;charset=UTF-8")))
//                .build();
//        LogUtil.info("[HttpUtil][request][" + url + "][" + requestBody + "]");
//        String response = handlerResponse(request);
//        LogUtil.info("[HttpUtil][response][" + url + "][" + response + "]");
//        return response;
//    }
//
//    private static String handlerResponse(Request request) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        try (Response response = okHttpClient.newCall(request).execute()) {
//            ResponseBody responseBody = response.body();
//            if (responseBody != null) {
//                return responseBody.string();
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }
//
//    public static Map<String, String> getHeader(HttpServletRequest request) {
//        Map<String, String> headerMap = new HashMap<>();
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String name = headerNames.nextElement().toLowerCase();
//            headerMap.put(name, request.getHeader(name));
//        }
//        return headerMap;
//    }
//
//    public static Map<String, String> createHeader() throws UnknownHostException {
//        return new HashMap<String, String>() {{
//            put("x-app_channel_id", systemConfig.getAppChannelId());
//            put("x-data_channel_id", systemConfig.getDataChannelId());
//            put("x-api_key", systemConfig.getApiKey());
//            put("x-real_ip", Inet4Address.getLocalHost().getHostAddress());
//        }};
//    }
//
//    /**
//     * 根据IP地址获取mac地址
//     *
//     * @return
//     */
//    public static String getLocalMac() {
//        try {
//            // 获取本地主机上的所有网络接口
//            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
//            while (networkInterfaces.hasMoreElements()) {
//                NetworkInterface networkInterface = networkInterfaces.nextElement();
//                // 获取网络接口的硬件地址
//                byte[] macAddressBytes = networkInterface.getHardwareAddress();
//                if (macAddressBytes != null) {
//                    // 将硬件地址转换为十六进制字符串
//                    StringBuilder macAddressBuilder = new StringBuilder();
//                    for (int i = 0; i < macAddressBytes.length; i++) {
//                        macAddressBuilder.append(String.format("%02X%s", macAddressBytes[i], (i < macAddressBytes.length - 1) ? ":" : ""));
//                    }
//                    return macAddressBuilder.toString();
//                }
//            }
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//}
