package com.a9ae0b01f0ffc.black_box.tests.mockup

import com.a9ae0b01f0ffc.black_box.implementation.annotations.I_black_box
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.exceptions.E_application_exception

class T_case_investigations {
    private static final String PC_HOST_TO_VTS = "Host to VTS"
    private static final String PC_VTS_TO_HOST = "VTS to Host"
    private static final Integer PC_VTS_LOG_VALUE_POSITION_SENT = 41
    private static final Integer PC_VTS_LOG_VALUE_POSITION_RECEIVED = 66
    private String p_current_direction = T_s.c().GC_EMPTY_STRING
    Integer z

    @I_black_box
    private Integer get_value_position() {
        if (p_current_direction != PC_VTS_TO_HOST) {
            return PC_VTS_LOG_VALUE_POSITION_SENT
        } else {
            return PC_VTS_LOG_VALUE_POSITION_RECEIVED
        }
    }

    @I_black_box
    void process_line(String i_line) {
        z = get_value_position()
    }

    @I_black_box("error")
    void test_error() {
        throw new E_application_exception(T_s.s().SOME_ERROR)
    }

}
