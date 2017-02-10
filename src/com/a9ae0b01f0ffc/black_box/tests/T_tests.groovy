package com.a9ae0b01f0ffc.black_box.tests

import a9ae0b01f0ffc.commons.main.T_const
import com.a9ae0b01f0ffc.black_box.tests.mockup.T_case_investigations
import com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_non_sensitive
import com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_sensitive
import com.a9ae0b01f0ffc.black_box.tests.mockup.T_sample_class_for_annotation_test
import a9ae0b01f0ffc.commons.exceptions.E_application_exception
import com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination_variable
import com.a9ae0b01f0ffc.black_box.main.T_context
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan
import com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable
import org.junit.Test

class T_tests {

    static final String PC_TEST_CONF_PATH = "./src/com/a9ae0b01f0ffc/black_box/tests/conf/"
    static
    final String PC_COMMONS_CONF_NAME = "./src/com/a9ae0b01f0ffc/black_box/conf/commons.conf"
    static final String PC_PAN = "4447778899992222"
    static final String PC_CLASS_NAME = "T_tests"

    @Test
    void test_001() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_001.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD)
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD|T_method_invocation{p_class_name='Unknown Class', p_method_name='Unknown Method', p_method_arguments=[]}|[]" + System.lineSeparator()
    }

    @Test
    void test_002() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_002.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD)
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD" + System.lineSeparator()
    }

    @Test
    void test_003() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_003.conf")
        T_s.l().put_to_context(new T_pan(PC_PAN), "pan")
        T_s.l().log_info(T_s.s().HELLO_WORLD)
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD|com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan(4447778899992222)" + System.lineSeparator()
    }

    @Test
    void test_004() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_004.conf")
        T_s.l().put_to_context(new T_pan(PC_PAN), "pan")
        T_s.l().log_info(T_s.s().HELLO_WORLD)
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD|Trace missing" + System.lineSeparator()
    }

    @Test
    void test_005() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_005.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD|com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan(4447778899992222)" + System.lineSeparator()
    }

    @Test
    void test_006() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_006.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD|Trace missing" + System.lineSeparator()
    }

    @Test
    void test_007() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_007.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD|Trace masked" + System.lineSeparator()
    }

    @Test
    void test_008() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_008.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan_maskable(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD|Trace masked" + System.lineSeparator()
    }

    @Test
    void test_009() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_009.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan_maskable(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD|444777******2222" + System.lineSeparator()
    }

    @Test
    void test_010() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_010.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD|Trace masked" + System.lineSeparator()
    }

    @Test
    void test_011() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_011.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan(4447778899992222)" + System.lineSeparator()
    }

    @Test
    void test_012() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_012.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Trace masked" + System.lineSeparator()
    }

    @Test
    void test_013() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_013.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan_maskable(PC_PAN), "pan"))
        assert T_destination_variable.l() == "444777******2222" + System.lineSeparator()
    }

    @Test
    void test_014() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_014.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan_maskable(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Trace masked" + System.lineSeparator()
    }

    @Test
    void test_015() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_015.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan_maskable(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Trace masked" + System.lineSeparator()
    }

    @Test
    void test_016() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_016.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan(4447778899992222)" + System.lineSeparator()
    }

    @Test
    void test_017() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_017.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Trace masked" + System.lineSeparator()
    }

    @Test
    void test_018() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_018.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan_maskable(PC_PAN), "pan"))
        assert T_destination_variable.l() == "444777******2222" + System.lineSeparator()
    }

    @Test
    void test_019() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_019.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Trace masked|444777******2222" + System.lineSeparator()
    }

    @Test
    void test_020() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_020.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Trace masked|444777******2222" + System.lineSeparator()
    }

    @Test
    void test_021() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_021.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan("4447778899992223"), "pan"))
        assert T_destination_variable.l() == "com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan(4447778899992223)|**********2222" + System.lineSeparator()
    }

    @Test
    void test_022() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_022.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan("4447778899992223"), "pan"))
        assert T_destination_variable.l() == "Trace masked|**********2222" + System.lineSeparator()
    }

    @Test
    void test_023() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_023.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        try {
            throw new E_application_exception(T_s.s().TEST_EXCEPTION, new T_pan("4447778899992224"))
        } catch (Exception e_exception) {
            T_s.l().log_exception(T_s.c().GC_DEFAULT_CLASS_NAME, T_s.c().GC_DEFAULT_METHOD_NAME, e_exception, T_s.r(new T_pan("4447778899992223"), "pan"))
        }
        assert T_destination_variable.l() == "TEST EXCEPTION|Trace masked|**********2222|Trace masked" + System.lineSeparator()
    }

    @Test
    void test_024() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_024.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        try {
            throw new E_application_exception(T_s.s().TEST_EXCEPTION, new T_pan_maskable("4447778899992224"))
        } catch (Exception e_exception) {
            T_s.l().log_exception(T_s.c().GC_DEFAULT_CLASS_NAME, T_s.c().GC_DEFAULT_METHOD_NAME, e_exception, T_s.r(new T_pan_maskable("4447778899992223"), "pan"))
        }
        assert T_destination_variable.l() == "TEST EXCEPTION|**********2222|**********2224|**********2223" + System.lineSeparator()
    }

    @Test
    void test_025() {
        final String LC_METHOD_NAME = "test_025"
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_025.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(new T_pan("4447778899992223"), "i_pan"))
        E_application_exception.set_is_tokenization_enabled(T_const.GC_FALSE)
        String l_expected = "    <event>\n" +
                "        <trace name=\"class\" serialized_representation=\"T_tests\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"method\" serialized_representation=\"test_025\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"depth\" serialized_representation=\"1\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"event\" serialized_representation=\"enter\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"exception\" serialized_representation=\"Trace missing\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"exception_message\" serialized_representation=\"Trace missing\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"message\" serialized_representation=\"null\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"invocation\" serialized_representation=\"T_method_invocation{p_class_name='T_tests', p_method_name='test_025', p_method_arguments=[com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan(4447778899992223)]}\" source=\"predefined\" mask=\"\" ref_class_name=\"com.a9ae0b01f0ffc.black_box.implementation.T_method_invocation\" masked=\"false\" />\n" +
                "        <trace name=\"stack\" serialized_representation=\"[T_method_invocation{p_class_name='T_tests', p_method_name='test_025', p_method_arguments=[com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan(4447778899992223)]}]\" source=\"predefined\" mask=\"\" ref_class_name=\"java.util.LinkedList\" masked=\"false\" />\n" +
                "        <trace name=\"i_pan\" serialized_representation=\"com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan(4447778899992223)\" source=\"runtime\" mask=\"\" ref_class_name=\"com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan\" masked=\"false\" />\n" +
                "        <trace name=\"pan\" serialized_representation=\"com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable(4447778899992222)\" source=\"context\" mask=\"\" ref_class_name=\"com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable\" masked=\"false\" />\n" +
                "    </event>\n"
        E_application_exception.set_is_tokenization_enabled(T_const.GC_TRUE)
        assert T_destination_variable.l() == l_expected.replaceAll("\n", "\r\n")
    }

    @Test
    void test_026() {
        final String LC_METHOD_NAME = "test_026"
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_026.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(new T_pan("4447778899992223"), "i_pan"))
        T_s.l().log_exit(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(new T_pan("4447778899992225"), "result_pan"))
        String l_expected = "    <event>\n" +
                "        <trace name=\"class\" serialized_representation=\"T_tests\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"method\" serialized_representation=\"test_026\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"depth\" serialized_representation=\"1\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"event\" serialized_representation=\"exit\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"exception\" serialized_representation=\"Trace missing\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"exception_message\" serialized_representation=\"Trace missing\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"message\" serialized_representation=\"null\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"invocation\" serialized_representation=\"T_method_invocation{p_class_name='T_tests', p_method_name='test_026', p_method_arguments=[com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan(4447778899992223)]}\" source=\"predefined\" mask=\"\" ref_class_name=\"com.a9ae0b01f0ffc.black_box.implementation.T_method_invocation\" masked=\"false\" />\n" +
                "        <trace name=\"stack\" serialized_representation=\"[T_method_invocation{p_class_name='T_tests', p_method_name='test_026', p_method_arguments=[com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan(4447778899992223)]}]\" source=\"predefined\" mask=\"\" ref_class_name=\"java.util.LinkedList\" masked=\"false\" />\n" +
                "        <trace name=\"result_pan\" serialized_representation=\"com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan(4447778899992225)\" source=\"runtime\" mask=\"\" ref_class_name=\"com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan\" masked=\"false\" />\n" +
                "        <trace name=\"pan\" serialized_representation=\"com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable(4447778899992222)\" source=\"context\" mask=\"\" ref_class_name=\"com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable\" masked=\"false\" />\n" +
                "    </event>\n"
        assert T_destination_variable.l() == l_expected.replaceAll("\n", "\r\n")
    }

    @Test
    void test_027() {
        final String LC_METHOD_NAME = "test_027"
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_027.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(new T_pan("4447778899992223"), "i_pan"))
        try {
            throw new E_application_exception(T_s.s().TEST_EXCEPTION, new T_pan_maskable("4447778899992224"))
        } catch (Exception e_exception) {
            T_s.l().log_exception(PC_CLASS_NAME, LC_METHOD_NAME, e_exception, T_s.r(new T_pan_maskable("4447778899992223"), "exception_pan"))
        }
        String l_expected = "    <event>\n" +
                "        <trace name=\"class\" serialized_representation=\"T_tests\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"method\" serialized_representation=\"test_027\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"depth\" serialized_representation=\"1\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"event\" serialized_representation=\"error\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"exception_message\" serialized_representation=\"TEST EXCEPTION\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"message\" serialized_representation=\"null\" source=\"predefined\" mask=\"\" ref_class_name=\"\" masked=\"false\" />\n" +
                "        <trace name=\"invocation\" serialized_representation=\"T_method_invocation{p_class_name='T_tests', p_method_name='test_027', p_method_arguments=[com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan(4447778899992223)]}\" source=\"predefined\" mask=\"\" ref_class_name=\"com.a9ae0b01f0ffc.black_box.implementation.T_method_invocation\" masked=\"false\" />\n" +
                "        <trace name=\"stack\" serialized_representation=\"[T_method_invocation{p_class_name='T_tests', p_method_name='test_027', p_method_arguments=[com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan(4447778899992223)]}]\" source=\"predefined\" mask=\"\" ref_class_name=\"java.util.LinkedList\" masked=\"false\" />\n" +
                "        <trace name=\"exception_pan\" serialized_representation=\"com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable(4447778899992223)\" source=\"runtime\" mask=\"\" ref_class_name=\"com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable\" masked=\"false\" />\n" +
                "        <trace name=\"pan\" serialized_representation=\"com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable(4447778899992222)\" source=\"context\" mask=\"\" ref_class_name=\"com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable\" masked=\"false\" />\n" +
                "        <trace name=\"\" serialized_representation=\"com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable(4447778899992224)\" source=\"exception_traces\" mask=\"\" ref_class_name=\"com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable\" masked=\"false\" />\n" +
                "    </event>\n"
        assert T_destination_variable.l() == l_expected.replaceAll("\n", "\r\n")
    }

    @Test
    void test_028() {
        final String LC_METHOD_NAME = "test_028"
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_028.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(new T_pan("4447778899992223"), "i_pan"))
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan("4447778899992223"), "pan"))
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(new T_pan("4447778899992223"), "i_pan"))
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan("4447778899992223"), "pan"))
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan("4447778899992223"), "pan"))
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(new T_pan("4447778899992223"), "i_pan"))
        T_s.l().log_exit(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(new T_pan("4447778899992225"), "result_pan"))
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(new T_pan("4447778899992223"), "i_pan"))
        T_s.l().log_exit(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(new T_pan("4447778899992225"), "result_pan"))
        T_s.l().log_exit(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(new T_pan("4447778899992225"), "result_pan"))
        try {
            throw new E_application_exception(T_s.s().TEST_EXCEPTION, new T_pan_maskable("4447778899992224"))
        } catch (Exception e_exception) {
            T_s.l().log_exception(PC_CLASS_NAME, LC_METHOD_NAME, e_exception, T_s.r(new T_pan_maskable("4447778899992223"), "exception_pan"))
        }
    }

    @Test
    void test_029() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_029.conf")
        String w = new T_sample_class_for_annotation_test().do_something("aaa", "bbb")
    }

    @Test
    void test_030() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_030.conf")
        String w = new T_sample_class_for_annotation_test().do_something3("aaa", "aa2a")
    }

    @Test
    void test_031() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_031.conf")
        try {
            String w = new T_sample_class_for_annotation_test().do_something4("aaa", "aaa")
        } catch (Exception e) {

        }
    }

    @Test
    void test_032() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_032.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan("4447778899992223"), "pan"))
        assert T_destination_variable.l() == "info|HELLO_WORLD|Trace masked" + System.lineSeparator()
    }

    @Test
    void test_033() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_033.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan_maskable("4447778899992223"), "pan"))
        assert T_destination_variable.l() == "info|HELLO_WORLD|**********2223" + System.lineSeparator()
    }

    @Test
    void test_034() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_034.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan_maskable("4447778899992221"), "T_pan_maskable"), T_s.r(new T_pan_sensitive("4447778899992222"), "T_pan_sensitive"), T_s.r(new T_pan_non_sensitive("4447778899992223"), "T_pan_non_sensitive"), T_s.r(new T_pan("4447778899992224"), "T_pan"))
        assert T_destination_variable.l() == "info|HELLO_WORLD|444777******2221|Trace masked|Trace masked|Trace masked" + System.lineSeparator()
    }

    @Test
    void test_035() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_035.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan_maskable("4447778899992221"), "T_pan_maskable"), T_s.r(new T_pan_sensitive("4447778899992222"), "T_pan_sensitive"), T_s.r(new T_pan_non_sensitive("4447778899992223"), "T_pan_non_sensitive"), T_s.r(new T_pan("4447778899992224"), "T_pan"))
        assert T_destination_variable.l() == "info|HELLO_WORLD|444777******2221|Trace masked|com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_non_sensitive(4447778899992223)|com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan(4447778899992224)" + System.lineSeparator()
    }

    @Test
    void test_036() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_036.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan_maskable("4447778899992221"), "T_pan_maskable"), T_s.r(new T_pan_sensitive("4447778899992222"), "T_pan_sensitive"), T_s.r(new T_pan_non_sensitive("4447778899992223"), "T_pan_non_sensitive"), T_s.r(new T_pan("4447778899992224"), "T_pan"))
        assert T_destination_variable.l() == "info|HELLO_WORLD|444777******2221|Trace masked|com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_non_sensitive(4447778899992223)|Trace masked" + System.lineSeparator()
    }

    @Test
    void test_037() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_037.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan_maskable("4447778899992221"), "T_pan_maskable"), T_s.r(new T_pan_sensitive("4447778899992222"), "T_pan_sensitive"), T_s.r(new T_pan_non_sensitive("4447778899992223"), "T_pan_non_sensitive"), T_s.r(new T_pan("4447778899992224"), "T_pan"))
        assert T_destination_variable.l() == "info|HELLO_WORLD|com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable(4447778899992221)|com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_sensitive(4447778899992222)|com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_non_sensitive(4447778899992223)|com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan(4447778899992224)" + System.lineSeparator()
    }

    @Test
    void test_038() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_038.conf")
        String w = new T_sample_class_for_annotation_test().do_something5("aaa", "a1aa")
    }

    @Test
    void test_039() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_039.conf")
        String w = new T_sample_class_for_annotation_test().reissue_card("555555555555555")
    }

    @Test
    void test_040() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_040.conf")
        assert 41 == new T_case_investigations().get_value_position()
    }

    @Test
    void test_041() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_041.conf")
        new T_case_investigations().process_line("test")
        T_s.l().print_stats()
    }

    @Test
    void test_042() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_042.conf")
        try {
            String w = new T_case_investigations().test_error()
        } catch (Exception e_others) {
            assert T_destination_variable.l() == "error|SOME ERROR" + System.lineSeparator()
            return
        }
        assert false
    }

    @Test
    void test_043() {
        T_context.getInstance().init_custom_with_custom_logger(PC_COMMONS_CONF_NAME, PC_TEST_CONF_PATH + "main_043.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD_Z1, T_s.r(new T_pan_maskable("4447778899992221"), "T_pan_maskable"), T_s.r(new T_pan_sensitive("4447778899992222"), "T_pan_sensitive"), T_s.r(new T_pan_non_sensitive("4447778899992223"), "T_pan_non_sensitive"), T_s.r(new T_pan("4447778899992224"), "T_pan"))
        assert T_destination_variable.l() == "info|HELLO WORLD com.a9ae0b01f0ffc.black_box.tests.mockup.T_pan_maskable(4447778899992221)" + System.lineSeparator()
    }

}