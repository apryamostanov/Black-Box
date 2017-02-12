package com.a9ae0b01f0ffc.black_box.main

import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.interfaces.I_logger_builder
import com.a9ae0b01f0ffc.black_box_base.annotations.I_black_box_base
import com.a9ae0b01f0ffc.commons.ioc.T_class_loader

class T_logging_context {

    protected static ThreadLocal<T_logging_context> p_context_thread_local = new ThreadLocal<T_logging_context>()
    private T_class_loader p_ioc = T_logging_const.GC_NULL_OBJ_REF as T_class_loader
    private T_logging_commons p_commons = T_logging_const.GC_NULL_OBJ_REF as T_logging_commons
    protected I_logger p_logger = T_logging_const.GC_NULL_OBJ_REF as I_logger
    private Boolean p_is_init = T_logging_const.GC_FALSE

    static {
        p_context_thread_local.set(new T_logging_context())
    }

    @I_black_box_base("error")
    void init_custom(String i_commons_conf_file_name) {
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
        //com.a9ae0b01f0ffc.black_box_base.interfaces.I_logger l_logger = com.a9ae0b01f0ffc.black_box_base.main.T_s.l()
        //l_logger.log_enter("com.a9ae0b01f0ffc.black_box.main.T_logging_context", "init_logger", com.a9ae0b01f0ffc.black_box_base.main.T_s.r(i_logger_conf_file_name, "i_logger_conf_file_name"), com.a9ae0b01f0ffc.black_box_base.main.T_s.r(this, "this"))
        //try {
        p_context_thread_local.get().p_logger = ((I_logger_builder) get_ioc().instantiate(T_logging_const.GC_LOGGER_BUILDER_INTERFACE)).create_logger(i_logger_conf_file_name)
        //com.a9ae0b01f0ffc.black_box_base.main.T_s.l().log_exit("com.a9ae0b01f0ffc.black_box.main.T_logging_context", "init_logger")
        //} catch (Throwable e_others) {
        //l_logger.log_exception("com.a9ae0b01f0ffc.black_box.main.T_logging_context", "init_logger", e_others)
        //throw e_others
        //}
    }

    @I_black_box_base("error")
    void init_custom_with_custom_logger(String i_commons_conf_file_name, String i_logger_conf_file_name) {
        //com.a9ae0b01f0ffc.black_box_base.interfaces.I_logger l_logger = com.a9ae0b01f0ffc.black_box_base.main.T_s.l()
        //l_logger.log_enter("com.a9ae0b01f0ffc.black_box.main.T_logging_context", "init_custom_with_custom_logger", com.a9ae0b01f0ffc.black_box_base.main.T_s.r(i_commons_conf_file_name, "i_commons_conf_file_name"), com.a9ae0b01f0ffc.black_box_base.main.T_s.r(i_logger_conf_file_name, "i_logger_conf_file_name"), com.a9ae0b01f0ffc.black_box_base.main.T_s.r(this, "this"))
        //try {
        if (get_commons() == T_logging_const.GC_NULL_OBJ_REF) {
            p_context_thread_local.get().p_commons = new T_logging_commons(i_commons_conf_file_name)
        }
        p_context_thread_local.get().p_ioc = new T_class_loader(T_s.c().GC_CLASS_LOADER_CONF_FILE_NAME)
        init_logger(i_logger_conf_file_name)
        p_is_init = T_logging_const.GC_TRUE
        //com.a9ae0b01f0ffc.black_box_base.main.T_s.l().log_exit("com.a9ae0b01f0ffc.black_box.main.T_logging_context", "init_custom_with_custom_logger")
        //} catch (Throwable e_others) {
        //l_logger.log_exception("com.a9ae0b01f0ffc.black_box.main.T_logging_context", "init_custom_with_custom_logger", e_others)
        //throw e_others
        //}
    }

    @I_black_box_base
    T_logging_commons get_commons() {
        return (p_context_thread_local.get()).p_commons
    }

    @I_black_box_base("error")
    I_logger get_logger() {
        return (p_context_thread_local.get()).p_logger
    }


    @I_black_box_base("error")
    static T_logging_context get_context() {
        return p_context_thread_local.get()
    }

}
