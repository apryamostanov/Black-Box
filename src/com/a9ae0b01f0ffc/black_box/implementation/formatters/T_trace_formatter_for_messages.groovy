package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace_formatter
import com.a9ae0b01f0ffc.black_box.main.T_s

class T_trace_formatter_for_messages implements I_trace_formatter {
    @Override
    String format_trace(I_trace i_trace) {
        if (i_trace.get_val() != T_s.c().GC_EMPTY_STRING) {
            return i_trace.get_val().replace("_", " ")
        } else {
            return i_trace.get_ref().toString().replace("_", " ")
        }
    }
}
