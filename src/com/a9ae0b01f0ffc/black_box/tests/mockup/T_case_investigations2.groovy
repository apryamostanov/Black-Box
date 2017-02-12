package com.a9ae0b01f0ffc.black_box.tests.mockup

import com.a9ae0b01f0ffc.black_box.interfaces.I_logger_builder
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box.main.T_logging_context
import com.a9ae0b01f0ffc.black_box_base.annotations.I_black_box_base

class T_case_investigations2 extends T_logging_context {

    @I_black_box_base
    void init_logger(String i_logger_conf_file_name) {
        //com.a9ae0b01f0ffc.black_box_base.interfaces.I_logger l_logger = com.a9ae0b01f0ffc.black_box_base.main.T_s.l()
        //l_logger.log_enter("com.a9ae0b01f0ffc.black_box.main.T_logging_context", "init_logger", com.a9ae0b01f0ffc.black_box_base.main.T_s.r(i_logger_conf_file_name, "i_logger_conf_file_name"), com.a9ae0b01f0ffc.black_box_base.main.T_s.r(this, "this"))
        //try {
        System.out.println("1")
        p_context_thread_local.get().p_logger = ((I_logger_builder) get_ioc().instantiate(T_logging_const.GC_LOGGER_BUILDER_INTERFACE)).create_logger(i_logger_conf_file_name)
        System.out.println("2")
        //com.a9ae0b01f0ffc.black_box_base.main.T_s.l().log_exit("com.a9ae0b01f0ffc.black_box.main.T_logging_context", "init_logger")
        //} catch (Throwable e_others) {
        //l_logger.log_exception("com.a9ae0b01f0ffc.black_box.main.T_logging_context", "init_logger", e_others)
        //throw e_others
        //}
    }

}
