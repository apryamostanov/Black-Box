package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace_formatter
import com.a9ae0b01f0ffc.black_box.main.T_u
import com.a9ae0b01f0ffc.black_box_base.annotations.I_black_box_base
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_trace_formatter_shorten_class implements I_trace_formatter {
    @Override
    @I_black_box_base("error")
    String format_trace(I_trace i_trace, I_event i_parent_event) {
        return T_u.get_short_name(i_trace.get_val())
    }
}
