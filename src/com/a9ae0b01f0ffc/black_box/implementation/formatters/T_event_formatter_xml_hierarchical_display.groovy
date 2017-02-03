package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination
import com.a9ae0b01f0ffc.black_box.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.main.T_s

class T_event_formatter_xml_hierarchical_display extends T_event_formatter implements I_event_formatter {

    HashMap<String, HashMap<String, I_trace>> make_traces_by_source_by_name(ArrayList<I_trace> i_event_traces) {
        HashMap<String, HashMap<String, I_trace>> l_traces_by_source_by_name = new HashMap<String, HashMap<String, I_trace>>()
        for (I_trace l_trace in i_event_traces) {
            if (!l_traces_by_source_by_name.containsKey(l_trace.get_source())) {
                HashMap<String, I_trace> l_traces_by_name = new HashMap<String, I_trace>()
                l_traces_by_name.put(l_trace.get_name(), l_trace)
                l_traces_by_source_by_name.put(l_trace.get_source(), l_traces_by_name)
            } else {
                HashMap<String, I_trace> l_traces_by_name = l_traces_by_source_by_name.get(l_trace.get_source())
                l_traces_by_name.put(l_trace.get_name(), l_trace)
            }
        }
    }

    @Override
    String format_traces(ArrayList<I_trace> i_event_traces) {
        String l_result = T_s.c().GC_EMPTY_STRING
        HashMap<String, HashMap<String, I_trace>> l_traces_by_source_by_name = make_traces_by_source_by_name(i_event_traces)
        I_trace l_event_type_trace = l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_EVENT_TYPE.get_name())
        if (l_event_type_trace.get_val() == "enter") {
            l_result += "<invocation class=\"${l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_CLASS_NAME.get_name())}\" method=\"${l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_METHOD_NAME.get_name())}\">" + System.lineSeparator()
            if (l_traces_by_source_by_name.containsKey(T_s.c().GC_TRACE_SOURCE_RUNTIME)) {
                l_result += "    <arguments>" + System.lineSeparator()
                for (I_trace l_runtime_trace in l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_RUNTIME).values()) {
                    l_result += "        <argument class=\"${l_runtime_trace.get_ref_class_name()}\" name=\"${l_runtime_trace.get_name()}\">${l_runtime_trace.toString()}</argument>" + System.lineSeparator()
                }
                l_result += "    </arguments>" + System.lineSeparator()
                l_result += "    <execution>" + System.lineSeparator()
            }
        } else if (l_event_type_trace.get_val() == "exit") {
            for (I_trace l_runtime_trace in l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_RUNTIME).values()) {
                l_result += "    <result class=\"${l_runtime_trace.get_ref_class_name()}\">${l_runtime_trace.toString()}</result>"
            }
        } else if (l_event_type_trace.get_val() == "info") {
            process_log_info()
        } else if (l_event_type_trace.get_val() == "warning") {
            process_log_warning()
        } else if (l_event_type_trace.get_val() == "debug") {
            process_log_debug()
        } else if (l_event_type_trace.get_val() == "error") {
            process_log_error()
        } else {
            process_other()
        }
        return l_result
    }
}
