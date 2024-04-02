package com.mtech.sjmsjob.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MaskUtilTest {

    @Test
    void testMaskUtil_AbleToMaskResponse() {
        String input = "{ \"name\": \"Test123\", \"email\": \"test@test.com\" }";

        String maskedValue = MaskUtil.applyMask(input);

        String expectedValue = "{\"name\":\"*******\",\"email\":\"*************\"}";

        Assertions.assertEquals(expectedValue, maskedValue);
    }
}
