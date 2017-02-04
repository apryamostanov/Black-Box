package com.a9ae0b01f0ffc.black_box.main

import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.interfaces.I_logger_builder

@Singleton
class T_context {

    ThreadLocal<T_commons> p_commons_thread_local = new ThreadLocal<T_commons>()
    ThreadLocal<I_logger> p_logger_thread_local = new ThreadLocal<I_logger>()
    ThreadLocal<I_logger_builder> p_logger_builder_thread_local = new ThreadLocal<I_logger_builder>()

    void init_default() {
        init_custom(T_commons.GC_CONST_CONF_FILE_NAME)
    }

    void init_default_with_custom_logger(String i_logger_conf_file_name) {
        T_commons.init_default()
        p_commons_thread_local.set(new T_commons())
        p_logger_builder_thread_local.set(new T_logger_builder(p_commons_thread_local.get().GC_CLASS_LOADER_CONF_FILE_NAME))
        p_logger_thread_local.set(p_logger_builder_thread_local.get().create_logger(i_logger_conf_file_name))
    }

    void init_custom(String i_commons_conf_file_name) {
        p_commons_thread_local.set(new T_commons())
        p_commons_thread_local.get().init_custom(i_commons_conf_file_name)
        p_logger_builder_thread_local.set(new T_logger_builder(p_commons_thread_local.get().GC_CLASS_LOADER_CONF_FILE_NAME))
        p_logger_thread_local.set(p_logger_builder_thread_local.get().create_logger(p_commons_thread_local.get().GC_DEFAULT_LOGGER_CONF_FILE_NAME))
    }

    ThreadLocal<T_commons> get_commons_thread_local() {
        return p_commons_thread_local
    }

    ThreadLocal<I_logger> get_logger_thread_local() {
        return p_logger_thread_local
    }

    ThreadLocal<I_logger_builder> get_logger_builder_thread_local() {
        return p_logger_builder_thread_local
    }
}
