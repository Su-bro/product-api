package com.musinsa.common.util;

import java.util.MissingResourceException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MessageUtilTest {

    @Test
    void getMsgReturnsCorrectMessageForValidKey() {
        String message = MessageUtil.getMsg("T001");
        assertEquals("TEST", message);
    }

    @Test
    void getMsgThrowsExceptionForInvalidKey() {
        assertThrows(MissingResourceException.class, () -> {
            MessageUtil.getMsg("invalid key");
        });
    }

    @Test
    void getMsgWithArgsReturnsFormattedMessage() {
        String message = MessageUtil.getMsg("T002", "arg1", "arg2");
        assertEquals("arg1 arg2 TEST", message);
    }

    @Test
    void getMsgWithArgsThrowsExceptionForInvalidKey() {
        assertThrows(MissingResourceException.class, () -> {
            MessageUtil.getMsg("invalid.key.with.args", "arg1", "arg2");
        });
    }

}
