package com.a9ae0b01f0ffc.mighty_logger.implementation.formatters

import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_trace
import com.a9ae0b01f0ffc.mighty_logger.main.T_s

class T_event_formatter_xml implements I_event_formatter {

    static String attr(String i_attr_name, String i_attr_val) {
        return i_attr_name + "=\"" + i_attr_val + "\" "
    }

    static String format_trace(I_trace i_trace) {
        String l_result = T_s.c().GC_EMPTY_STRING
        l_result += "<trace "
        l_result += attr("muted", i_trace.is_muted().toString())
        l_result += attr("masked", i_trace.is_masked().toString())
        l_result += attr("mask", i_trace.get_mask())
        l_result += attr("name", i_trace.get_name())
        l_result += attr("source", i_trace.get_source())
        l_result += attr("config_class", i_trace.get_config_class())
        l_result += attr("search_name_config", i_trace.get_search_name_config())
        l_result += attr("ref_class_name", i_trace.get_ref_class_name())
        l_result += attr("serialized_representation", i_trace.toString())
        l_result += "/>"
        return l_result
    }

    @Override
    String format_traces(ArrayList<I_trace> i_event_traces) {
        String l_result = T_s.c().GC_EMPTY_STRING
        l_result += "<event>"
        for (I_trace l_trace : i_event_traces) {
            l_result += format_trace(l_trace)
        }
        l_result += "</event>"
        return l_result
    }
}
