package org.example.http.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsResponse<T> {

    @Builder.Default
    public String code = "50000";
    @Builder.Default
    public String message = "";
    @Builder.Default
    public String time = LocalDateTime.now().toString();

    public T data;

    public static MsResponse success() {
        return MsResponse.builder()
                .code("00000")
                .message("请求成功")
                .build();
    }

    public static <K> MsResponse<K> success(K data) {
        return MsResponse.<K>builder()
                .data(data)
                .code("00000")
                .message("请求成功")
                .build();
    }

    public static <K> MsResponse<K> fail() {
        return MsResponse.<K>builder().message("系统繁忙").build();
    }

    public static MsResponse fail(String code) {
        return MsResponse.builder().code(code).build();
    }

    public static <K> MsResponse<K> fail(String code, String message) {
        return MsResponse.<K>builder().code(code).message(message).build();
    }

    protected <K> MsResponse<K> fail(String code, String message, K data) {
        return MsResponse.<K>builder().code(code).message(message).data(data).build();
    }
}
