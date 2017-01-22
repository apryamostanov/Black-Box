package com.a9ae0b01f0ffc.mighty_logger.tests

import com.a9ae0b01f0ffc.mighty_conf.implementation.T_conf
import com.a9ae0b01f0ffc.mighty_logger.main.T_context
import com.a9ae0b01f0ffc.mighty_logger.main.T_s
import com.a9ae0b01f0ffc.static_string.T_static_string_builder
import org.junit.Test

class T_tests {

    @Test
    void test_001() {
        T_context.getInstance().init_custom("src/com/a9ae0b01f0ffc/mighty_logger/tests/conf/main_001.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD)
    }
}