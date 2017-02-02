package com.a9ae0b01f0ffc.black_box.tests

import com.a9ae0b01f0ffc.mighty_conf.implementation.T_conf
import com.a9ae0b01f0ffc.static_string.T_static_string_builder
import org.junit.Test

class T_tests_jars {

    @Test
    void test_001() {
        assert new T_static_string_builder().THIS_IS_SOME_RANDOM_TEXT.toString() == "THIS_IS_SOME_RANDOM_TEXT"
    }

    @Test
    void test_002() {
        assert new T_static_string_builder().THIS_IS_NEGATIVE_TEST.toString() != "THIS_IS_SOME_RANDOM_TEXT"
    }

    @Test
    void test_003() {
        assert new T_conf("src/com/a9ae0b01f0ffc/black_box/tests/sample_config_with_any_name.conf").test_parameter321 == "test_val123"
    }

    @Test
    void test_004() {
        assert new T_conf("src/com/a9ae0b01f0ffc/black_box/tests/sample_config_with_any_name.conf").GC_TEST_PARAMETER321 == "test_val123"
    }

    @Test
    void test_005() {
        assert new T_conf("src/com/a9ae0b01f0ffc/black_box/tests/sample_config_with_any_name.conf").override("test_parameter321", "1234") == "test_val123"
    }

    @Test
    void test_006() {
        assert new T_conf("src/com/a9ae0b01f0ffc/black_box/tests/sample_config_with_any_name.conf").override("test_parameter3210", "1234") == "1234"
    }

    @Test
    void test_007() {
        assert new T_conf("src/com/a9ae0b01f0ffc/black_box/tests/sample_config_with_any_name.conf").test_parameter321("1234") == "test_val123"
    }

    @Test
    void test_008() {
        assert new T_conf("src/com/a9ae0b01f0ffc/black_box/tests/sample_config_with_any_name.conf").test_parameter3210("1234") == "1234"
    }

}