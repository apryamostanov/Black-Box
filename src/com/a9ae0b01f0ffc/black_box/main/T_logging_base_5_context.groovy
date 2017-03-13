package com.a9ae0b01f0ffc.black_box.main

import com.a9ae0b01f0ffc.black_box.conf.T_logging_conf
import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.interfaces.I_logger_builder
import com.a9ae0b01f0ffc.commons.implementation.config.T_common_conf
import com.a9ae0b01f0ffc.commons.implementation.exceptions.E_application_exception
import com.a9ae0b01f0ffc.commons.implementation.ioc.T_class_loader

class T_logging_base_5_context extends T_logging_base_4_const {

    protected static ThreadLocal<T_logging_base_5_context> p_context_thread_local = new ThreadLocal<T_logging_base_5_context>()
    private T_class_loader p_ioc = GC_NULL_OBJ_REF as T_class_loader
    private T_logging_conf p_common_conf = GC_NULL_OBJ_REF as T_logging_conf
    protected I_logger p_logger = GC_NULL_OBJ_REF as I_logger
    private Boolean p_is_init = GC_FALSE

    static void init_custom(String i_commons_conf_file_name) {
        check_init()
        p_context_thread_local.get().p_common_conf = new T_logging_conf(i_commons_conf_file_name)
        init_custom_with_custom_logger(i_commons_conf_file_name, c().GC_DEFAULT_LOGGER_CONF_FILE_NAME)
    }

    static T_class_loader get_ioc() {
        check_init()
        T_class_loader l_class_loader = p_context_thread_local.get().p_ioc
        if (l_class_loader == GC_NULL_OBJ_REF) {
            throw new E_application_exception(s.Unable_to_access_class_loader_as_it_has_not_been_initialized)
        } else {
            return l_class_loader
        }
    }

    static void init_logger(String i_logger_conf_file_name) {
        check_init()
        p_context_thread_local.get().p_logger = ((I_logger_builder) get_ioc().instantiate(GC_LOGGER_BUILDER_INTERFACE)).create_logger(i_logger_conf_file_name)
    }

    static void init_custom_with_custom_logger(String i_commons_conf_file_name, String i_logger_conf_file_name) {
        check_init()
        if (c() == GC_NULL_OBJ_REF) {
            p_context_thread_local.get().p_common_conf = new T_logging_conf(i_commons_conf_file_name)
        }
        p_context_thread_local.get().p_ioc = new T_class_loader(c().GC_CLASS_LOADER_CONF_FILE_NAME)
        init_logger(i_logger_conf_file_name)
    }

    private static void check_init() {
        if (p_context_thread_local.get() == GC_NULL_OBJ_REF) {
            p_context_thread_local.set(new T_logging_base_5_context())
        }
    }

    static T_logging_conf c() {
        check_init()
        T_common_conf l_common_conf = ((T_logging_base_5_context) p_context_thread_local.get()).p_common_conf
        if (l_common_conf == GC_NULL_OBJ_REF) {
            throw new E_application_exception(s.Unable_to_access_configuration_as_it_has_not_been_initialized)
        } else {
            return l_common_conf
        }
    }

    static Boolean is_init() {
        if ((p_context_thread_local.get()).p_logger != GC_NULL_OBJ_REF) {
            return GC_TRUE
        } else {
            return GC_FALSE
        }
    }

    static I_logger l() {
        check_init()
        return (p_context_thread_local.get()).p_logger
    }

    static T_logging_base_5_context get_context() {
        check_init()
        return p_context_thread_local.get()
    }

    static T_logging_base_5_context x() {
        check_init()
        return p_context_thread_local.get()
    }

}
