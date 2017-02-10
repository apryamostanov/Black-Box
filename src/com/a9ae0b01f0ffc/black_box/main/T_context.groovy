package com.a9ae0b01f0ffc.black_box.main

import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.interfaces.I_logger_builder
import com.a9ae0b01f0ffc.commons.main.T_abstract_context

class T_context extends T_abstract_context {

    private T_commons p_commons = T_const.GC_NULL_OBJ_REF as T_commons
    private I_logger p_logger = T_const.GC_NULL_OBJ_REF as I_logger

    static {
        p_context_thread_local.set(new T_context())
    }

    void init_custom(String i_commons_conf_file_name) {
        init_custom_with_custom_logger(i_commons_conf_file_name, get_commons().GC_DEFAULT_LOGGER_CONF_FILE_NAME)
    }

    void init_logger(String i_logger_conf_file_name) {
        p_logger = ((I_logger_builder) get_ioc().instantiate(T_const.GC_LOGGER_BUILDER_INTERFACE)).create_logger(i_logger_conf_file_name)
    }

    void init_custom_with_custom_logger(String i_commons_conf_file_name, String i_logger_conf_file_name) {
        p_commons = new T_commons(i_commons_conf_file_name)
        super.init_custom(p_commons.GC_CLASS_LOADER_CONF_FILE_NAME)
        init_logger(i_logger_conf_file_name)
    }

    @Override
    T_commons get_commons() {
        return ((T_context) p_context_thread_local.get()).p_commons
    }

    I_logger get_logger() {
        return ((T_context) p_context_thread_local.get()).p_logger
    }

}
