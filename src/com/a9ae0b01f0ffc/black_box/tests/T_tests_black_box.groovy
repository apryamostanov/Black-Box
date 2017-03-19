package com.a9ae0b01f0ffc.black_box.tests

import com.a9ae0b01f0ffc.black_box.annotations.T_black_box_transformation
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_5_context
import org.junit.Test

class T_tests_black_box {

    @Test
    void test_001() {
        T_logging_base_5_context.init_custom("C:/COMPILE/with_logging/commons.conf")
        T_logging_base_5_context.l().log_trace("z")
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

    @Test
    void test_003() {
        System.out.println("l_logger.log_enter_method(, $T_logging_base_5_context.GC_EMPTY_STRING")
    }

    @Test
    void test_004() {
       // T_black_box_transformation.create_log_method_call_with_traces("l_logger.log_enter_expression(ConstructorCallExpression, new com.a9ae0b01f0ffc.VSMSGEN.implementation.T_vts_log_parser(), 126 ))
    }

    @Test
    void test_005() {
        int i = 1
        System.out.println(i.getClass().getSimpleName())
    }

}
