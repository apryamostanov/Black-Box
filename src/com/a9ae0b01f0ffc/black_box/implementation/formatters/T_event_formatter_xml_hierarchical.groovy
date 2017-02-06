package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination
import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.main.T_s
import org.codehaus.groovy.runtime.StackTraceUtils

class T_event_formatter_xml_hierarchical extends T_event_formatter implements I_event_formatter {

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
        return l_traces_by_source_by_name
    }

    String get_short_name(String i_class_name) {
        String l_short_name = T_s.c().GC_EMPTY_STRING
        if (i_class_name.contains(T_s.c().GC_POINT)) {
            l_short_name = i_class_name.substring(i_class_name.lastIndexOf(T_s.c().GC_POINT) + T_s.c().GC_ONE_CHAR)
        } else {
            l_short_name = i_class_name
        }
        return l_short_name
    }

    String make_line(String i_source_line, Integer i_depth) {
        return " ".padLeft(i_depth*4, " ") + i_source_line + System.lineSeparator()
    }

    String indent(String i_source_line, Integer i_depth) {
        return " ".padLeft(i_depth*4, " ") + i_source_line
    }

    @Override
    String format_traces(ArrayList<I_trace> i_event_traces, I_event i_source_event) {
        String l_result = T_s.c().GC_EMPTY_STRING
        HashMap<String, HashMap<String, I_trace>> l_traces_by_source_by_name = make_traces_by_source_by_name(i_event_traces)
        I_trace l_event_type_trace = l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_EVENT_TYPE.get_name())
        Integer l_depth = Integer.parseInt(l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_DEPTH.get_name()).get_val())
        if (l_event_type_trace.get_val() == "enter") {
            l_result += make_line("<invocation class=\"${get_short_name(l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_CLASS_NAME.get_name()).get_val())}\" method=\"${l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_METHOD_NAME.get_name()).get_val()}\">", l_depth)
            if (l_traces_by_source_by_name.containsKey(T_s.c().GC_TRACE_SOURCE_RUNTIME)) {
                l_result += make_line("  <arguments>", l_depth)
                for (I_trace l_runtime_trace in l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_RUNTIME).values()) {
                    l_result += make_line("    <argument class=\"${get_short_name(l_runtime_trace.get_ref_class_name())}\" name=\"${l_runtime_trace.get_name()}\">${l_runtime_trace.format_trace(i_source_event)}</argument>", l_depth)
                }
                l_result += make_line("  </arguments>", l_depth)
            }
            l_result += make_line("  <execution>", l_depth)
        } else if (l_event_type_trace.get_val() == "exit") {
            l_result += make_line("  </execution>", l_depth)
            if (l_traces_by_source_by_name.containsKey(T_s.c().GC_TRACE_SOURCE_RUNTIME)) {
                for (I_trace l_runtime_trace in l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_RUNTIME).values()) {
                    l_result += make_line("  <result class=\"${get_short_name(l_runtime_trace.get_ref_class_name())}\">${l_runtime_trace.format_trace(i_source_event)}</result>", l_depth)
                }
            }
            l_result += make_line("</invocation>", l_depth)
        } else if (l_event_type_trace.get_val() == "error") {
            if (l_traces_by_source_by_name.containsKey(T_s.c().GC_TRACE_SOURCE_EXCEPTION_TRACES)) {
                l_result += make_line("  <exception class=\"class=\"${l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_EXCEPTION.get_name()).get_ref().getClass().getSimpleName()}\" message=\"${l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_EXCEPTION_MESSAGE.get_name()).get_val()}\" stack_trace=\"${Arrays.toString(new StackTraceUtils().sanitizeRootCause((Throwable) l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_EXCEPTION.get_name()).get_ref()).getStackTrace()).replace(",", System.lineSeparator())}\">", l_depth)
                l_result += make_line("    <exception_traces>", l_depth)
                for (I_trace l_runtime_trace in l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_EXCEPTION_TRACES).values()) {
                    l_result += make_line("      <exception_trace class=\"${get_short_name(l_runtime_trace.get_ref_class_name())}\">${l_runtime_trace.format_trace(i_source_event)}</result>", l_depth)
                }
                l_result += make_line("    </exception_traces>", l_depth)
                l_result += make_line("  <exception>", l_depth)
            } else {
                l_result += make_line("  <exception class=\"class=\"${l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_EXCEPTION.get_name()).get_ref().getClass().getSimpleName()}\" message=\"${l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_EXCEPTION_MESSAGE.get_name()).get_val()}\" stack_trace=\"${Arrays.toString(new StackTraceUtils().sanitizeRootCause((Throwable) l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_EXCEPTION.get_name()).get_ref()).getStackTrace()).replace(",", System.lineSeparator())}\"/>", l_depth)
            }
            l_result += make_line("</invocation>", l_depth)
        } else {
            if (l_traces_by_source_by_name.containsKey(T_s.c().GC_TRACE_SOURCE_RUNTIME)) {
                l_result += make_line("    <${l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_EVENT_TYPE.get_name()).get_val()} message=\"${l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_MESSAGE.get_name()).get_val()}\">", l_depth)
                for (I_trace l_runtime_trace in l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_RUNTIME).values()) {
                    l_result += make_line("      <trace class=\"${get_short_name(l_runtime_trace.get_ref_class_name())}\" name=\"${l_runtime_trace.get_name()}\">${l_runtime_trace.format_trace(i_source_event)}</trace>", l_depth)
                }
                l_result += make_line("    </${l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_EVENT_TYPE.get_name()).get_val()}>", l_depth)
            } else {
                l_result += make_line("  <${l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_EVENT_TYPE.get_name()).get_val()} message=\"${l_traces_by_source_by_name.get(T_s.c().GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_MESSAGE.get_name()).get_val()}\"/>", l_depth)
            }
        }
        return l_result
    }
}
