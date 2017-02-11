package com.a9ae0b01f0ffc.black_box.main

import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.interfaces.I_logger_builder
import com.a9ae0b01f0ffc.commons.ioc.T_class_loader

final class T_logging_context {

    protected static ThreadLocal<T_logging_context> p_context_thread_local = new ThreadLocal<T_logging_context>()
    private T_class_loader p_ioc = T_logging_const.GC_NULL_OBJ_REF as T_class_loader
    private T_logging_commons p_commons = T_logging_const.GC_NULL_OBJ_REF as T_logging_commons
    private I_logger p_logger = T_logging_const.GC_NULL_OBJ_REF as I_logger

    static {
        p_context_thread_local.set(new T_logging_context())
    }

    void init_custom(String i_commons_conf_file_name) {
        p_context_thread_local.get().p_commons = new T_logging_commons(i_commons_conf_file_name)
        init_custom_with_custom_logger(i_commons_conf_file_name, get_commons().GC_DEFAULT_LOGGER_CONF_FILE_NAME)
    }

    T_class_loader get_ioc() {
        return p_context_thread_local.get().p_ioc
    }

    void init_logger(String i_logger_conf_file_name) {
        p_context_thread_local.get().p_logger = ((I_logger_builder) get_ioc().instantiate(T_logging_const.GC_LOGGER_BUILDER_INTERFACE)).create_logger(i_logger_conf_file_name)
    }

    void init_custom_with_custom_logger(String i_commons_conf_file_name, String i_logger_conf_file_name) {
        if (get_commons() == T_logging_const.GC_NULL_OBJ_REF) {
            p_context_thread_local.get().p_commons = new T_logging_commons(i_commons_conf_file_name)
        }
        p_context_thread_local.get().p_ioc = new T_class_loader(T_s.c().GC_CLASS_LOADER_CONF_FILE_NAME)
        init_logger(i_logger_conf_file_name)
    }

    T_logging_commons get_commons() {
        return ((T_logging_context) p_context_thread_local.get()).p_commons
    }

    I_logger get_logger() {
        return ((T_logging_context) p_context_thread_local.get()).p_logger
    }


    static T_logging_context get_context() {
        return p_context_thread_local.get()
    }

}
