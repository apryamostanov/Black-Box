package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.implementation.T_inherited_configurations
import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.main.T_s

class T_event_formatter_csv extends T_event_formatter implements I_event_formatter {

    @Override
    String format_traces(ArrayList<I_trace> i_event_traces, I_event i_source_event) {
        String l_result_string = T_s.c().GC_EMPTY_STRING
        for (I_trace l_trace in i_event_traces) {
            if (l_result_string != T_s.c().GC_EMPTY_STRING) {
                l_result_string += T_s.c().GC_LOG_CSV_SEPARATOR
            }
            l_result_string += l_trace.format_trace(i_source_event)
        }
        return l_result_string
    }
}
