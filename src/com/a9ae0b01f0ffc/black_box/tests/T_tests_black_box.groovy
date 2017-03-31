package com.a9ae0b01f0ffc.black_box.tests

import com.a9ae0b01f0ffc.black_box.annotations.T_black_box_transformation
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_5_context
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util
import org.codehaus.groovy.runtime.GStringImpl
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

    @Test
    void test_006() {
        T_logging_base_5_context.init_custom("C:/COMPILE/with_logging/commons.conf")
        T_logging_base_5_context.l().log_info(T_logging_base_5_context.s.Test_Z1, "ttt")
    }

    @Test
    void test_007() {
        T_logging_base_5_context.init_custom("C:/COMPILE/with_logging/commons.conf")
        new Z().q()
    }

    @Test
    void test_008() {
        T_logging_base_5_context.init_custom("C:/COMPILE/with_logging/commons.conf")
        T_logging_base_6_util.l().log_run_method({new Z().q()})
    }

    @Test
    void test_009() {
        T_logging_base_6_util.zip_file(new File("C:\\Users\\anton\\IdeaProjects\\VSMSGEN\\LOGS\\DEBUG\\20170325_220504557_anton.xml"))
    }

    @Test
    void test_010() {
        System.out.println(new File("C:\\Users\\anton\\IdeaProjects\\VSMSGEN\\LOGS\\DEBUG\\").list())
    }

    @Test
    void test_011() {
        T_logging_base_5_context.init_custom("C:/COMPILE/with_logging/commons.conf")
        T_logging_base_5_context.l().put_to_context("val1", "name")
        System.out.println(T_logging_base_5_context.l().get_from_context("name"))
        //System.out.println(new File("C:\\Users\\anton.pryamostanov\\IdeaProjects\\Black-Box\\src\\com\\a9ae0b01f0ffc\\black_box\\tests\\m.txt").readLines().first())
        System.out.println(T_logging_base_6_util.process_location(new File("C:\\Users\\anton.pryamostanov\\IdeaProjects\\Black-Box\\src\\com\\a9ae0b01f0ffc\\black_box\\tests\\m.txt").readLines().first(), T_logging_base_6_util.c()))
    }

}
