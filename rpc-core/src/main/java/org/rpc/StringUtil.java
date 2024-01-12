package org.rpc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class StringUtil {
    public static String createUuid(String identityId) {
        if (isEmpty(identityId)) {
            return createUuid();
        }
        if (identityId.length() == 36) {
            return identityId;
        }
        String md5Str = DigestUtils.md5DigestAsHex(identityId.getBytes());
        return md5Str.substring(0, 8) + "-"
                + md5Str.substring(8, 12) + "-"
                + md5Str.substring(12, 16) + "-"
                + md5Str.substring(16, 20) + "-"
                + md5Str.substring(20, 32);
    }

    public static String createUuid() {
        return UUID.randomUUID().toString();
    }

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }


    public static <T> String jsonEncode(T obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "";
        try {
            s = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static <T> T jsonDecode(String jsonString, Class<T> c) {
        T o = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            o = objectMapper.readValue(jsonString, c);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static String randomNumber(int length) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < length; i++) {
            s.append((int) (Math.random() * 10));
        }
        return s.toString();
    }
}
