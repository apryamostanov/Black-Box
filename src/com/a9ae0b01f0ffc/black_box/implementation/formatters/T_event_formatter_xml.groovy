package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box_base.implementation.annotations.I_black_box
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_event_formatter_xml extends T_event_formatter implements I_event_formatter {

    @I_black_box("error")
    static String attr(String i_attr_name, String i_attr_val) {
        return i_attr_name + "=\"" + i_attr_val + "\" "
    }

    Boolean p_header_was_added = T_logging_const.GC_FALSE

    @I_black_box("error")
    String format_trace(I_trace i_trace, I_event i_source_event) {
        String l_result = T_logging_const.GC_EMPTY_STRING
        l_result += "        <trace "
        l_result += attr("name", i_trace.get_name())
        l_result += attr("serialized_representation", i_trace.format_trace(i_source_event))
        l_result += attr("source", i_trace.get_source())
        l_result += attr("mask", i_trace.get_mask())
        l_result += attr("ref_class_name", i_trace.get_ref_class_name())
        if (is_guid()) {
            l_result += attr("guid", i_trace.get_guid())
            l_result += attr("ref_guid", i_trace.get_ref_guid())
        }
        l_result += attr("masked", i_trace.is_masked().toString())
        l_result += "/>" + System.lineSeparator()
        return l_result
    }

    @Override
    @I_black_box("error")
    String format_traces(ArrayList<I_trace> i_event_traces, I_event i_source_event) {
        String l_result = T_logging_const.GC_EMPTY_STRING
        if (!p_header_was_added) {
            p_header_was_added = T_logging_const.GC_TRUE
        }
        l_result += "    <event>" + System.lineSeparator()
        for (I_trace l_trace : i_event_traces) {
            l_result += format_trace(l_trace, i_source_event)
        }
        l_result += "    </event>" + System.lineSeparator()
        return l_result
    }
}
