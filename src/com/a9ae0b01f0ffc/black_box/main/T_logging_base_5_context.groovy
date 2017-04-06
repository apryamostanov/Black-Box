package com.a9ae0b01f0ffc.black_box.main

import com.a9ae0b01f0ffc.black_box.conf.T_logging_conf
import com.a9ae0b01f0ffc.black_box.implementation.T_logger
import com.a9ae0b01f0ffc.black_box.implementation.T_logger_builder
import com.a9ae0b01f0ffc.commons.implementation.exceptions.E_application_exception
import com.a9ae0b01f0ffc.commons.implementation.ioc.T_class_loader
import com.a9ae0b01f0ffc.commons.implementation.thread_local.T_thread_local

class T_logging_base_5_context extends T_logging_base_4_const {

    protected static T_thread_local<T_logging_base_5_context> p_context_thread_local = new T_thread_local<T_logging_base_5_context>()
    private T_class_loader p_ioc = GC_NULL_OBJ_REF as T_class_loader
    private T_logging_conf p_conf = GC_NULL_OBJ_REF as T_logging_conf
    protected T_logger p_logger = GC_NULL_OBJ_REF as T_logger

    static void init_custom(String i_commons_conf_file_name, Boolean is_no_async = GC_FALSE) {
        p_context_thread_local.set(new T_logging_base_5_context())
        get_context().p_conf = new T_logging_conf(i_commons_conf_file_name)
        get_context().p_ioc = new T_class_loader(c().GC_CLASS_LOADER_CONF_FILE_NAME)
        get_context().p_logger = new T_logger_builder().create_logger(c().GC_DEFAULT_LOGGER_CONF_FILE_NAME, i_commons_conf_file_name, is_no_async)
    }

    static void deinit() {
        get_context().p_logger.deinit()
        p_context_thread_local.remove(T_logging_base_5_context.class)
    }

    static T_class_loader get_ioc() {
        return p_context_thread_local.get(T_logging_base_5_context.class).p_ioc
    }

    static T_logging_conf c() {
        return get_context().p_conf
    }

    static T_logger l() {
        return get_context().p_logger
    }

    static T_logging_base_5_context get_context() {
        if (p_context_thread_local.get(T_logging_base_5_context.class) == GC_NULL_OBJ_REF) {
            throw new E_application_exception(s.Context_not_initialized_for_thread_Z1, Thread.currentThread().getId())
        }
        return p_context_thread_local.get(T_logging_base_5_context.class)
    }

}
