package com.a9ae0b01f0ffc.black_box.tests

import com.a9ae0b01f0ffc.black_box.main.T_logging_base_5_context
import org.junit.Test

class T_tests_black_box {

    @Test
    void test_001() {
        T_logging_base_5_context.x().init_custom("C:/COMPILE/with_logging/commons.conf")
        T_logging_base_5_context.l().log_trace()
    }

    @Test
    void test_002() {
        String z = {
            try {
                return "w"
            } catch (Throwable e) {

            }
        }
    }

}
