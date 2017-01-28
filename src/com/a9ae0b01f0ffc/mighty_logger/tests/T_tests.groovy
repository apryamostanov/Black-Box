package com.a9ae0b01f0ffc.mighty_logger.tests

import com.a9ae0b01f0ffc.exceptions.E_application_exception
import com.a9ae0b01f0ffc.mighty_logger.implementation.destinations.T_destination_variable
import com.a9ae0b01f0ffc.mighty_logger.main.T_context
import com.a9ae0b01f0ffc.mighty_logger.main.T_s
import com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan
import com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan_maskable
import org.junit.Test

class T_tests {

    static final String PC_TEST_CONF_PATH = "src/com/a9ae0b01f0ffc/mighty_logger/tests/conf/"
    static final String PC_PAN = "4447778899992222"
    static final String PC_CLASS_NAME = "T_tests"

    @Test
    void test_001() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_001.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD)
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|Trace missing|Trace missing|HELLO_WORLD"
    }

    @Test
    void test_002() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_002.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD)
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD"
    }

    @Test
    void test_003() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_003.conf")
        T_s.l().put_to_context(new T_pan(PC_PAN), "pan")
        T_s.l().log_info(T_s.s().HELLO_WORLD)
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD|com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan(4447778899992222)"
    }

    @Test
    void test_004() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_004.conf")
        T_s.l().put_to_context(new T_pan(PC_PAN), "pan")
        T_s.l().log_info(T_s.s().HELLO_WORLD)
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD|Trace missing"
    }

    @Test
    void test_005() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_005.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD|com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan(4447778899992222)"
    }

    @Test
    void test_006() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_006.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD|Trace missing"
    }

    @Test
    void test_007() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_007.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD|Trace masked"
    }

    @Test
    void test_008() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_008.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan_maskable(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD|Trace masked"
    }

    @Test
    void test_009() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_009.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan_maskable(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD|444777******2222"
    }

    @Test
    void test_010() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_010.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Unknown Class|Unknown Method|0|info|HELLO_WORLD|Trace masked"
    }

    @Test
    void test_011() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_011.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan(4447778899992222)"
    }

    @Test
    void test_012() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_012.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Trace masked"
    }

    @Test
    void test_013() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_013.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan_maskable(PC_PAN), "pan"))
        assert T_destination_variable.l() == "444777******2222"
    }

    @Test
    void test_014() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_014.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan_maskable(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Trace masked"
    }

    @Test
    void test_015() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_015.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan_maskable(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Trace masked"
    }

    @Test
    void test_016() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_016.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan(4447778899992222)"
    }

    @Test
    void test_017() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_017.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Trace masked"
    }

    @Test
    void test_018() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_018.conf")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan_maskable(PC_PAN), "pan"))
        assert T_destination_variable.l() == "444777******2222"
    }

    @Test
    void test_019() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_019.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Trace masked|444777******2222"
    }

    @Test
    void test_020() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_020.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan(PC_PAN), "pan"))
        assert T_destination_variable.l() == "Trace masked|444777******2222"
    }

    @Test
    void test_021() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_021.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan("4447778899992223"), "pan"))
        assert T_destination_variable.l() == "com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan(4447778899992223)|**********2222"
    }

    @Test
    void test_022() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_022.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        T_s.l().log_info(T_s.s().HELLO_WORLD, T_s.r(new T_pan("4447778899992223"), "pan"))
        assert T_destination_variable.l() == "Trace masked|**********2222"
    }

    @Test
    void test_023() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_023.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        try {
            throw new E_application_exception(T_s.s().TEST_EXCEPTION, new T_pan("4447778899992224"))
        } catch (Exception e_exception) {
            T_s.l().log_exception(T_s.c().GC_DEFAULT_CLASS_NAME, T_s.c().GC_DEFAULT_METHOD_NAME, e_exception, T_s.r(new T_pan("4447778899992223"), "pan"))
        }
        assert T_destination_variable.l() == "TEST_EXCEPTION|Trace masked|**********2222|com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan(4447778899992224)"
    }

    @Test
    void test_024() {
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_024.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        try {
            throw new E_application_exception(T_s.s().TEST_EXCEPTION, new T_pan_maskable("4447778899992224"))
        } catch (Exception e_exception) {
            T_s.l().log_exception(T_s.c().GC_DEFAULT_CLASS_NAME, T_s.c().GC_DEFAULT_METHOD_NAME, e_exception, T_s.r(new T_pan_maskable("4447778899992223"), "pan"))
        }
        assert T_destination_variable.l() == "TEST_EXCEPTION|**********2222|**********2224|**********2223"
    }

    @Test
    void test_025() {
        final String LC_METHOD_NAME = "test_025"
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_025.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(new T_pan("4447778899992223"), "i_pan"))
        assert T_destination_variable.l() == "<event><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"class\" source=\"predefined\" config_class=\"\" search_name_config=\"class\" ref_class_name=\"class\" serialized_representation=\"T_tests\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"method\" source=\"predefined\" config_class=\"\" search_name_config=\"method\" ref_class_name=\"method\" serialized_representation=\"test_025\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"depth\" source=\"predefined\" config_class=\"\" search_name_config=\"depth\" ref_class_name=\"depth\" serialized_representation=\"1\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"event\" source=\"predefined\" config_class=\"\" search_name_config=\"event\" ref_class_name=\"event\" serialized_representation=\"enter\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"exception\" source=\"predefined\" config_class=\"\" search_name_config=\"exception\" ref_class_name=\"exception\" serialized_representation=\"Trace missing\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"exception_message\" source=\"predefined\" config_class=\"\" search_name_config=\"exception_message\" ref_class_name=\"exception_message\" serialized_representation=\"Trace missing\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"message\" source=\"predefined\" config_class=\"\" search_name_config=\"message\" ref_class_name=\"message\" serialized_representation=\"null\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"i_pan\" source=\"runtime\" config_class=\"\" search_name_config=\"i_pan\" ref_class_name=\"com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan\" serialized_representation=\"com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan(4447778899992223)\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"pan\" source=\"context\" config_class=\"\" search_name_config=\"pan\" ref_class_name=\"com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan_maskable\" serialized_representation=\"com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan_maskable(4447778899992222)\" /></event>"
    }

    @Test
    void test_026() {
        final String LC_METHOD_NAME = "test_026"
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_026.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(new T_pan("4447778899992223"), "i_pan"))
        T_s.l().log_exit(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(new T_pan("4447778899992225"), "result_pan"))
        assert T_destination_variable.l() == "<event><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"class\" source=\"predefined\" config_class=\"\" search_name_config=\"class\" ref_class_name=\"class\" serialized_representation=\"T_tests\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"method\" source=\"predefined\" config_class=\"\" search_name_config=\"method\" ref_class_name=\"method\" serialized_representation=\"test_026\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"depth\" source=\"predefined\" config_class=\"\" search_name_config=\"depth\" ref_class_name=\"depth\" serialized_representation=\"1\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"event\" source=\"predefined\" config_class=\"\" search_name_config=\"event\" ref_class_name=\"event\" serialized_representation=\"exit\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"exception\" source=\"predefined\" config_class=\"\" search_name_config=\"exception\" ref_class_name=\"exception\" serialized_representation=\"Trace missing\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"exception_message\" source=\"predefined\" config_class=\"\" search_name_config=\"exception_message\" ref_class_name=\"exception_message\" serialized_representation=\"Trace missing\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"message\" source=\"predefined\" config_class=\"\" search_name_config=\"message\" ref_class_name=\"message\" serialized_representation=\"null\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"result_pan\" source=\"runtime\" config_class=\"\" search_name_config=\"result_pan\" ref_class_name=\"com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan\" serialized_representation=\"com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan(4447778899992225)\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"pan\" source=\"context\" config_class=\"\" search_name_config=\"pan\" ref_class_name=\"com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan_maskable\" serialized_representation=\"com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan_maskable(4447778899992222)\" /></event>"
    }

    @Test
    void test_027() {
        final String LC_METHOD_NAME = "test_026"
        T_context.getInstance().init_custom(PC_TEST_CONF_PATH + "main_026.conf")
        T_s.l().put_to_context(new T_pan_maskable(PC_PAN), "pan")
        T_s.l().log_enter(PC_CLASS_NAME, LC_METHOD_NAME, T_s.r(new T_pan("4447778899992223"), "i_pan"))
        try {
            throw new E_application_exception(T_s.s().TEST_EXCEPTION, new T_pan_maskable("4447778899992224"))
        } catch (Exception e_exception) {
            T_s.l().log_exception(PC_CLASS_NAME, LC_METHOD_NAME, e_exception, T_s.r(new T_pan_maskable("4447778899992223"), "exception_pan"))
        }
        assert T_destination_variable.l() == "<event><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"class\" source=\"predefined\" config_class=\"\" search_name_config=\"class\" ref_class_name=\"class\" serialized_representation=\"T_tests\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"method\" source=\"predefined\" config_class=\"\" search_name_config=\"method\" ref_class_name=\"method\" serialized_representation=\"test_026\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"depth\" source=\"predefined\" config_class=\"\" search_name_config=\"depth\" ref_class_name=\"depth\" serialized_representation=\"1\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"event\" source=\"predefined\" config_class=\"\" search_name_config=\"event\" ref_class_name=\"event\" serialized_representation=\"exit\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"exception\" source=\"predefined\" config_class=\"\" search_name_config=\"exception\" ref_class_name=\"exception\" serialized_representation=\"Trace missing\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"exception_message\" source=\"predefined\" config_class=\"\" search_name_config=\"exception_message\" ref_class_name=\"exception_message\" serialized_representation=\"Trace missing\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"message\" source=\"predefined\" config_class=\"\" search_name_config=\"message\" ref_class_name=\"message\" serialized_representation=\"null\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"result_pan\" source=\"runtime\" config_class=\"\" search_name_config=\"result_pan\" ref_class_name=\"com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan\" serialized_representation=\"com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan(4447778899992225)\" /><trace muted=\"false\" masked=\"false\" mask=\"\" name=\"pan\" source=\"context\" config_class=\"\" search_name_config=\"pan\" ref_class_name=\"com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan_maskable\" serialized_representation=\"com.a9ae0b01f0ffc.mighty_logger.tests.mockup.T_pan_maskable(4447778899992222)\" /></event>"
    }


}