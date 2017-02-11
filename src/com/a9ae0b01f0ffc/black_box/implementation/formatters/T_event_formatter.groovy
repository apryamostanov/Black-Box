package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box_base.implementation.annotations.I_black_box

abstract class T_event_formatter implements I_event_formatter{

    String p_print_trace_guid = T_logging_const.GC_TRUE_STRING

    @I_black_box("error")
    String get_print_trace_guid() {
        return p_print_trace_guid
    }

    @I_black_box("error")
    void set_print_trace_guid(String i_print_trace_guid) {
        p_print_trace_guid = i_print_trace_guid
    }

    @I_black_box("error")
    Boolean is_guid() {
        return p_print_trace_guid == T_logging_const.GC_TRUE_STRING ? T_logging_const.GC_TRUE : T_logging_const.GC_FALSE
    }

}
