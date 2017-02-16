package com.a9ae0b01f0ffc.black_box.tests.mockup

import com.a9ae0b01f0ffc.black_box.interfaces.I_logger_builder
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box.main.T_logging_context
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.black_box_base.annotations.I_black_box_base
import com.a9ae0b01f0ffc.commons.exceptions.E_application_exception

class T_case_investigations extends T_logging_context {
    private static final String PC_HOST_TO_VTS = "Host to VTS"
    private static final String PC_VTS_TO_HOST = "VTS to Host"
    private static final Integer PC_VTS_LOG_VALUE_POSITION_SENT = 41
    private static final Integer PC_VTS_LOG_VALUE_POSITION_RECEIVED = 66
    private String p_current_direction = T_logging_const.GC_EMPTY_STRING
    Integer z

    @I_black_box_base
    Integer get_value_position() {
        if (p_current_direction != PC_VTS_TO_HOST) {
            return PC_VTS_LOG_VALUE_POSITION_SENT
        } else {
            return PC_VTS_LOG_VALUE_POSITION_RECEIVED
        }
    }

    @I_black_box_base
    void process_line(String i_line) {
        z = get_value_position()
    }

    @I_black_box_base("error")
    void test_error() {
        throw new E_application_exception(T_s.s().SOME_ERROR)
    }

    @I_black_box_base
    void init_logger(String i_logger_conf_file_name) {
        //com.a9ae0b01f0ffc.black_box.interfaces.I_logger l_logger = com.a9ae0b01f0ffc.black_box.main.T_s.l()
        //l_logger.log_enter("com.a9ae0b01f0ffc.black_box.main.T_logging_context", "init_logger", com.a9ae0b01f0ffc.black_box.main.T_s.r(i_logger_conf_file_name, "i_logger_conf_file_name"), com.a9ae0b01f0ffc.black_box.main.T_s.r(this, "this"))
        //try {
        p_context_thread_local.get().p_logger = ((I_logger_builder) get_ioc().instantiate(T_logging_const.GC_LOGGER_BUILDER_INTERFACE)).create_logger(i_logger_conf_file_name)
        //com.a9ae0b01f0ffc.black_box.main.T_s.l().log_exit("com.a9ae0b01f0ffc.black_box.main.T_logging_context", "init_logger")
        //} catch (Throwable e_others) {
        //l_logger.log_exception("com.a9ae0b01f0ffc.black_box.main.T_logging_context", "init_logger", e_others)
        //throw e_others
        //}
    }

}
