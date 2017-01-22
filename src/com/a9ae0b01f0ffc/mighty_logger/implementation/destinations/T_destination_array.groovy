package com.a9ae0b01f0ffc.mighty_logger.implementation.destinations

import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_trace

class T_destination_array extends T_destination {

    static ArrayList<String> p_log_lines = new ArrayList<String>()

    @Override
    void store(ArrayList<I_trace> i_trace_list) {
        p_log_lines.add(p_formatter.format_traces(i_trace_list))
    }

}