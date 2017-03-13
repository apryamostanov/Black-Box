package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_destination_variable extends T_destination {

    static String p_log_line = GC_EMPTY_STRING

    @Override
    void store(I_event i_source_event) {
        p_log_line = p_formatter.format_event(i_source_event)
    }

    static String line() {
        return p_log_line
    }

}