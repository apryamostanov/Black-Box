package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.exceptions.E_application_exception
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace_formatter
import com.a9ae0b01f0ffc.black_box.main.T_s

class T_trace_formatter_exception_details_xml implements I_trace_formatter {

    @Override
    String format_trace(I_trace i_trace) {
        String l_result_string = T_s.c().GC_EMPTY_STRING
        if (i_trace.get_ref() instanceof E_application_exception) {
            E_application_exception l_application_exception = i_trace.get_ref() as E_application_exception
            l_result_string = l_application_exception.getClass().getSimpleName() + T_s.c().GC_COLON + l_application_exception.getMessage()
        } else if (i_trace.get_ref() instanceof Exception) {
            Exception l_exception = i_trace.get_ref() as Exception
            l_result_string = l_exception.getClass().getName() + T_s.c().GC_COLON + l_exception.getMessage()
        } else {
            l_result_string = i_trace.toString()
        }
        return l_result_string
    }
}
