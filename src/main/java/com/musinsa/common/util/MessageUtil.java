package com.musinsa.common.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class MessageUtil {

    private MessageUtil() {

    }

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    public static String getMsg(String key) {
        return resourceBundle.getString(key);
    }

    public static String getMsg(String key, Object... args) {
        return MessageFormat.format(getMsg(key), args);
    }


}
