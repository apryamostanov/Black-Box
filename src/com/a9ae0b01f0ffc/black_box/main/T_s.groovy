package com.a9ae0b01f0ffc.black_box.main

import com.a9ae0b01f0ffc.black_box.interfaces.I_logger
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box_base.implementation.annotations.I_black_box
import com.a9ae0b01f0ffc.commons.ioc.T_class_loader
import com.a9ae0b01f0ffc.commons.static_string.T_static_string
import com.a9ae0b01f0ffc.commons.static_string.T_static_string_builder

class T_s {

    @I_black_box("error")
    static I_logger l() {
        return x().get_logger()
    }

    @I_black_box("error")
    static I_trace r(Object i_object, String i_trace_name) {
        I_trace l_trace = l().object2trace(i_object, T_logging_const.GC_TRACE_SOURCE_RUNTIME)
        l_trace.set_name(i_trace_name)
        return l_trace
    }

    @I_black_box("error")
    static I_trace t(Object i_object, T_static_string i_trace_name) {
        return r(i_object, i_trace_name.toString())
    }

    @I_black_box("error")
    static T_logging_context x() {
        return (T_logging_context) T_logging_context.get_context()
    }

    @I_black_box("error")
    static T_logging_commons c() {
        return x().get_commons()
    }

    @I_black_box("error")
    static T_class_loader ioc() {
        return x().get_ioc()
    }

    @I_black_box("error")
    static final T_logging_commons commons() {
        return T_logging_context.get_context().get_commons()
    }

    @I_black_box("error")
    static final T_static_string_builder s() {
        return T_logging_const.GC_STATIC_STRING_BUILDER
    }

}
