package org.example.util;



import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class NamingUtils {
    public static final String NUMBER_PATTERN_STRING = "^\\d+$";
    private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_PATTERN_STRING);

    public static boolean isNumber(String str) {
        return !StringUtils.isEmpty(str) && NUMBER_PATTERN.matcher(str).matches();
    }
}
