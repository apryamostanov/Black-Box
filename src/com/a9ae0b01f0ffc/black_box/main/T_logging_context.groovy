package com.a9ae0b01f0ffc.black_box.main

import com.a9ae0b01f0ffc.black_box.conf.zero_conf.T_zero_ioc_conf
import com.a9ae0b01f0ffc.black_box.implementation.T_object_with_guid
import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.interfaces.I_logger_builder
import com.a9ae0b01f0ffc.black_box_base.annotations.I_black_box_base
import com.a9ae0b01f0ffc.commons.config_helper.T_conf
import com.a9ae0b01f0ffc.commons.ioc.T_class_loader
import groovy.util.slurpersupport.GPathResult

class T_logging_context extends T_object_with_guid {

    protected static ThreadLocal<T_logging_context> p_context_thread_local = new ThreadLocal<T_logging_context>()
    private T_class_loader p_ioc = GC_NULL_OBJ_REF as T_class_loader
    private T_logging_commons p_commons = GC_NULL_OBJ_REF as T_logging_commons
    protected I_logger p_logger = GC_NULL_OBJ_REF as I_logger
    private Boolean p_is_init = GC_FALSE
    static String p_class_guid = UUID.randomUUID()

    @I_black_box_base("error")
    String get_class_guid() {
        return p_class_guid
    }

    @I_black_box_base("error")
    static String get_static_class_guid() {
        return p_class_guid
    }

    @I_black_box_base("error")
    void init_custom(GPathResult i_zero_ioc_conf, GPathResult i_zero_logger_conf) {
        check_init()
        p_context_thread_local.get().p_commons = new T_logging_commons()
        p_context_thread_local.get().p_ioc = new T_class_loader(i_zero_ioc_conf)
        p_context_thread_local.get().p_logger = ((I_logger_builder) get_ioc().instantiate(GC_LOGGER_BUILDER_INTERFACE)).create_logger(i_zero_logger_conf)
        p_is_init = GC_TRUE
    }

    @I_black_box_base("error")
    void init_custom(String i_commons_conf_file_name) {
        check_init()
        p_context_thread_local.get().p_commons = new T_logging_commons(i_commons_conf_file_name)
        init_custom_with_custom_logger(i_commons_conf_file_name, get_commons().GC_DEFAULT_LOGGER_CONF_FILE_NAME)
    }

    @I_black_box_base("error")
    Boolean is_init() {
        return p_is_init
    }

    @I_black_box_base("error")
    T_class_loader get_ioc() {
        return p_context_thread_local.get().p_ioc
    }

    @I_black_box_base("error")
    void init_logger(String i_logger_conf_file_name) {
        check_init()
        p_context_thread_local.get().p_logger = ((I_logger_builder) get_ioc().instantiate(GC_LOGGER_BUILDER_INTERFACE)).create_logger(i_logger_conf_file_name)
    }

    @I_black_box_base("error")
    void init_custom_with_custom_logger(String i_commons_conf_file_name, String i_logger_conf_file_name) {
        check_init()
        if (get_commons() == GC_NULL_OBJ_REF) {
            p_context_thread_local.get().p_commons = new T_logging_commons(i_commons_conf_file_name)
        }
        p_context_thread_local.get().p_ioc = new T_class_loader(T_s.c().GC_CLASS_LOADER_CONF_FILE_NAME)
        init_logger(i_logger_conf_file_name)
        p_is_init = GC_TRUE
    }

    private static void check_init() {
        if (p_context_thread_local.get() == GC_NULL_OBJ_REF) {
            p_context_thread_local.set(new T_logging_context())
        }
    }

    @I_black_box_base("error")
    T_logging_commons get_commons() {
        return (p_context_thread_local.get()).p_commons
    }

    @I_black_box_base("error")
    I_logger get_logger() {
        check_init()
        return (p_context_thread_local.get()).p_logger
    }


    @I_black_box_base("error")
    static T_logging_context get_context() {
        check_init()
        return p_context_thread_local.get()
    }

}
