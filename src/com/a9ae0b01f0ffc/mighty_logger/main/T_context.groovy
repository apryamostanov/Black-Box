package com.a9ae0b01f0ffc.mighty_logger.main

import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_logger
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_logger_builder

@Singleton
class T_context {

    ThreadLocal<I_logger> p_logger_thread_local = T_commons.GC_NULL_OBJ_REF as ThreadLocal<I_logger>
    I_logger_builder p_logger_builder = T_commons.GC_NULL_OBJ_REF as I_logger_builder

    void init_default() {
        init_custom(T_commons.GC_CONST_CONF_FILE_NAME)
    }

    void init_custom(String i_conf_file_name) {
        T_commons.init_custom(i_conf_file_name)
        p_logger_builder = new T_logger_builder(T_commons.GC_DEFAULT_LOGGER_CONF_FILE_NAME, T_commons.GC_CLASS_LOADER_CONF_FILE_NAME)
        p_logger_thread_local = new ThreadLocal<I_logger>() {
            @Override
            protected I_logger initialValue() {
                return p_logger_builder.create_logger(i_conf_file_name)
            }
        }
    }

}
