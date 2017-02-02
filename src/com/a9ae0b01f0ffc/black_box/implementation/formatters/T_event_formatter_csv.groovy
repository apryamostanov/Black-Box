package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.main.T_s

class T_event_formatter_csv implements I_event_formatter {

    @Override
    String format_traces(ArrayList<I_trace> i_event_traces) {
        String l_result_string = T_s.c().GC_EMPTY_STRING
        for (I_trace l_trace in i_event_traces) {
            if (l_result_string != T_s.c().GC_EMPTY_STRING) {
                l_result_string += T_s.c().GC_LOG_CSV_SEPARATOR
            }
            l_result_string += l_trace.toString()
        }
        return l_result_string
    }
}