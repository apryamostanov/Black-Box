package com.a9ae0b01f0ffc.black_box.main

import a9ae0b01f0ffc.commons.ioc.T_class_loader
import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import a9ae0b01f0ffc.commons.static_string.T_static_string
import a9ae0b01f0ffc.commons.static_string.T_static_string_builder

class T_s {

    static T_commons p_commons = null

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

    static I_trace r(Object i_object, String i_trace_name) {
        I_trace l_trace = l().object2trace(i_object, c().GC_TRACE_SOURCE_RUNTIME)
        l_trace.set_name(i_trace_name)
        return l_trace
    }

    static I_trace t(Object i_object, T_static_string i_trace_name) {
        return r(i_object, i_trace_name.toString())
    }

    static Object nvl(Object i_primary_object, Object i_backup_object) {
        Object l_result_object
        if (i_primary_object == c().GC_NULL_OBJ_REF || i_primary_object == c().GC_EMPTY_STRING) {
            l_result_object = i_backup_object
        } else {
            l_result_object = i_primary_object
        }
        return l_result_object
    }

}
