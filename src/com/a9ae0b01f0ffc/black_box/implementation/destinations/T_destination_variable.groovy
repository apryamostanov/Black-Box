package com.a9ae0b01f0ffc.black_box.implementation.destinations

import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.main.T_const
import com.a9ae0b01f0ffc.black_box.main.T_s

class T_destination_variable extends T_destination {

    static String p_log_line = T_const.GC_EMPTY_STRING

    @Override
    void store(ArrayList<I_trace> i_trace_list, I_event i_source_event) {
        p_log_line = p_formatter.format_traces(i_trace_list, i_source_event)
    }

    static String l() {
        return p_log_line
    }

}