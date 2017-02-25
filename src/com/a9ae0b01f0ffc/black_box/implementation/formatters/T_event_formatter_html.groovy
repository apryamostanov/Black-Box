package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination
import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box_base.annotations.I_black_box_base
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_event_formatter_html extends T_event_formatter implements I_event_formatter {

    Boolean p_header_was_added = GC_FALSE

    @I_black_box_base("error")
    static String attr(String i_attr_name, String i_attr_val) {
        return i_attr_name + "=\"" + i_attr_val + "\" "
    }

    @I_black_box_base("error")
    static String format_trace(I_trace i_trace) {
        String l_result = GC_EMPTY_STRING
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
    @I_black_box_base("error")
    String format_traces(ArrayList<I_trace> i_event_traces, I_event i_source_event) {
        String l_result = GC_EMPTY_STRING
        if (!p_header_was_added) {
            l_result += "<html><head><style type=\"text/css\"></style></head><body><table border=1><tr><td>Datetimestamp</td><td>Process</td><td>Event</td><td>Class</td><td>Method</td><td>Depth</td><td>Trace Data</td></tr>"
            p_header_was_added = GC_TRUE
        }
        l_result += "<tr>"
        String l_datetimestamp = GC_EMPTY_STRING
        String l_processid = GC_EMPTY_STRING
        String l_event_type = GC_EMPTY_STRING
        String l_class_name = GC_EMPTY_STRING
        String l_method_name = GC_EMPTY_STRING
        String l_depth = GC_EMPTY_STRING
        String l_trace_data = GC_EMPTY_STRING
        for (I_trace l_trace in i_event_traces) {
            if (l_trace.get_name() == T_destination.PC_STATIC_TRACE_NAME_DATETIMESTAMP.get_name()) {
                l_datetimestamp = l_trace.format_trace(i_source_event)
            } else if (l_trace.get_name() == T_destination.PC_STATIC_TRACE_NAME_PROCESSID.get_name()) {
                l_processid = l_trace.format_trace(i_source_event)
            } else if (l_trace.get_name() == T_destination.PC_STATIC_TRACE_NAME_EVENT_TYPE.get_name()) {
                l_event_type = l_trace.format_trace(i_source_event)
            } else if (l_trace.get_name() == T_destination.PC_STATIC_TRACE_NAME_CLASS_NAME.get_name()) {
                l_class_name = l_trace.format_trace(i_source_event)
            } else if (l_trace.get_name() == T_destination.PC_STATIC_TRACE_NAME_METHOD_NAME.get_name()) {
                l_method_name = l_trace.format_trace(i_source_event)
            } else if (l_trace.get_name() == T_destination.PC_STATIC_TRACE_NAME_DEPTH.get_name()) {
                l_depth = l_trace.format_trace(i_source_event)
            } else {
                l_trace_data += (format_trace(l_trace) + "<br/>")
            }
        }
        l_result += "<td>" + l_datetimestamp + "</td>"
        l_result += "<td>" + l_processid + "</td>"
        l_result += "<td>" + l_event_type + "</td>"
        l_result += "<td>" + l_class_name + "</td>"
        l_result += "<td>" + l_method_name + "</td>"
        l_result += "<td>" + l_depth + "</td>"
        l_result += "<td><textarea style=\"border:none;\">" + l_trace_data + "</textarea></td>"
        l_result += "</tr>" + System.lineSeparator()
        return l_result
    }
}
