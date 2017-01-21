package com.a9ae0b01f0ffc.mighty_logger.implementation

import com.a9ae0b01f0ffc.implementation.T_class_loader

import static com.a9ae0b01f0ffc.mighty_logger.implementation.T_commons.GC_NULL_OBJ_REF

@Singleton
class T_context {

    T_class_loader p_class_loader = GC_NULL_OBJ_REF
    ThreadLocal<I_logger> p_logger_thread_local = GC_NULL_OBJ_REF as ThreadLocal<I_logger>
    I_logger_builder p_logger_builder = GC_NULL_OBJ_REF

    void init_default() {
        p_logger_thread_local = new ThreadLocal<I_logger>() {
            @Override
            protected I_logger initialValue() {
                return p_logger_builder.create_logger()
            }
        }
        p_logger_exception_thread_local = new ThreadLocal<I_logger>() {
            @Override
            protected I_logger initialValue() {
                return p_logger_builder.create_logger(T_commons.GC_EXCEPTION_LOGGER_CONF_FILE_NAME)
            }
        }
    }

    void init_custom(String i_conf_file_name) {
        p_logger_thread_local = new ThreadLocal<I_logger>() {
            @Override
            protected I_logger initialValue() {
                return p_logger_builder.create_logger(i_conf_file_name)
            }
        }
        p_logger_exception_thread_local = new ThreadLocal<I_logger>() {
            @Override
            protected I_logger initialValue() {
                return p_logger_builder.create_logger(T_commons.GC_EXCEPTION_LOGGER_CONF_FILE_NAME)
            }
        }
    }

}
