package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace_formatter
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.black_box_base.implementation.annotations.I_black_box_base
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_trace_formatter_for_messages implements I_trace_formatter {
    @Override
    @I_black_box_base("error")
    String format_trace(I_trace i_trace, I_event i_parent_event) {
        String l_formatted_trace = T_logging_const.GC_EMPTY_STRING
        if (i_trace.get_val() != T_logging_const.GC_EMPTY_STRING) {
            l_formatted_trace = i_trace.get_val().replace(T_s.c().GC_MESSAGE_FORMAT_TOKEN_SEPARATOR_BEFORE.toString(), T_s.c().GC_MESSAGE_FORMAT_TOKEN_SEPARATOR_AFTER.toString())
        } else {
            l_formatted_trace = i_trace.get_ref().toString().replace(T_s.c().GC_MESSAGE_FORMAT_TOKEN_SEPARATOR_BEFORE.toString(), T_s.c().GC_MESSAGE_FORMAT_TOKEN_SEPARATOR_AFTER.toString())
        }
        Integer l_trace_seqno = T_logging_const.GC_ZERO
        for (I_trace l_runtime_trace in i_parent_event.get_traces_runtime()) {
            l_trace_seqno ++
            l_formatted_trace = l_formatted_trace.replace((T_s.c().GC_MESSAGE_FORMAT_TOKEN_TRACE + l_trace_seqno.toString()), l_runtime_trace.toString())
        }
        return l_formatted_trace
    }
}
