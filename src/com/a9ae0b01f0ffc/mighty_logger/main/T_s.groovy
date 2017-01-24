package com.a9ae0b01f0ffc.mighty_logger.main

import com.a9ae0b01f0ffc.implementation.T_class_loader
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_logger
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_trace
import com.a9ae0b01f0ffc.static_string.T_static_string_builder

class T_s {

    static T_commons c() {
        return T_context.getInstance().p_commons_thread_local.get()
    }

    static T_class_loader ioc() {
        return T_context.getInstance().get_logger_builder_thread_local().get().get_class_loader()
    }

    static T_static_string_builder s() {
        return c().GC_STATIC_STRING_BUILDER
    }

    static I_logger l() {
        return T_context.getInstance().p_logger_thread_local.get()
    }

    static I_trace t(Object i_object, String i_trace_name) {
        I_trace l_trace = l().object2trace(i_object)
        l_trace.set_name(i_trace_name)
        return l_trace
    }

}
