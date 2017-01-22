package com.a9ae0b01f0ffc.mighty_logger.implementation.formatters

import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_trace
import com.a9ae0b01f0ffc.mighty_logger.main.T_s

class T_event_formatter_xml implements I_event_formatter {

    @Override
    String format_traces(ArrayList<I_trace> i_event_traces) {
        String l_result_string = T_s.c().GC_EMPTY_STRING
        for (l_trace in i_event_traces) {
            if (l_result_string != T_s.c().GC_EMPTY_STRING) {
                l_result_string += T_s.c().GC_LOG_CSV_SEPARATOR
            }
            if (l_trace.get_formatter() != T_s.c().GC_NULL_OBJ_REF) {
                l_result_string += l_trace.get_formatter().format_trace(l_trace)
            } else {
                l_result_string += l_trace.toString()
            }
        }
        return l_result_string
    }
}
