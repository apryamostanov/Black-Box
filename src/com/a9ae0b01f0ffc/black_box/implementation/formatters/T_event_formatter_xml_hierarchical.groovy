package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination
import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.black_box.interfaces.I_method_invocation
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.black_box.main.T_u
import com.a9ae0b01f0ffc.black_box_base.annotations.I_black_box_base
import groovy.transform.ToString
import org.codehaus.groovy.runtime.StackTraceUtils

@ToString(includeNames = true, includeFields = true)
class T_event_formatter_xml_hierarchical extends T_event_formatter implements I_event_formatter {

    static final String PC_TAG_INVOCATION = "invocation"
    static final String PC_TAG_ARGUMENTS = "arguments"
    static final String PC_TAG_ARGUMENT = "argument"
    static final String PC_TAG_EXECUTION = "execution"
    static final String PC_TAG_RESULT = "result"
    static final String PC_TAG_ELAPSED_TIME = "elapsed_time"
    static final String PC_TAG_EXCEPTION = "exception"
    static final String PC_TAG_EXCEPTION_STACKTRACE = "stacktrace"
    static final String PC_TAG_EXCEPTION_TRACES = "exception_traces"
    static final String PC_TAG_EXCEPTION_TRACE = "exception_trace"
    static final String PC_TAG_TRACES = "traces"
    static final String PC_TAG_TRACE = "trace"
    ThreadLocal<Integer> p_relative_xml_depth = new ThreadLocal<Integer>()
    ThreadLocal<Integer> p_absolute_xml_depth = new ThreadLocal<Integer>()

    T_event_formatter_xml_hierarchical() {
        p_relative_xml_depth.set(T_logging_const.GC_ZERO)
    }

    @I_black_box_base("error")
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

    @I_black_box_base("error")
    String get_class_name_short(I_trace i_trace) {
        return make_class_name_attribute(T_u.get_short_name(i_trace.get_ref_class_name()))
    }

    @I_black_box_base("error")
    String make_class_name_attribute(String i_short_name) {
        return "class=\"" + i_short_name + "\""
    }

    @I_black_box_base("error")
    String get_name(I_trace i_trace) {
        return "name=\"" + i_trace.get_name() + "\""
    }

    @I_black_box_base("error")
    String make_line(String i_source_line) {
        return i_source_line == T_logging_const.GC_EMPTY_STRING ? T_logging_const.GC_EMPTY_STRING : i_source_line + System.lineSeparator()
    }

    @I_black_box_base("error")
    String get_indent() {
        return T_logging_const.GC_EMPTY_STRING.padLeft(p_absolute_xml_depth.get() * Integer.parseInt(T_s.c().GC_XML_PAD_DEPTH) + p_relative_xml_depth.get(), T_logging_const.GC_SPACE)
    }

    @I_black_box_base("error")
    String get_attributes_predefined(ArrayList<I_trace> i_event_traces, I_event i_source_event) {
        String l_attribute_string = T_logging_const.GC_EMPTY_STRING
        for (l_trace in i_event_traces) {
            if (l_trace.get_source() == T_destination.PC_TRACE_SOURCE_PREDEFINED.get_name()) {
                l_attribute_string += T_logging_const.GC_SPACE + l_trace.get_name() + T_logging_const.GC_EQUALS + T_logging_const.GC_XML_DOUBLE_QUOTE + l_trace.format_trace(i_source_event) + T_logging_const.GC_XML_DOUBLE_QUOTE
            }
        }
        return l_attribute_string
    }

    @I_black_box_base("error")
    String open_tag(String i_tag_name, String i_attributes = T_logging_const.GC_EMPTY_STRING) {
        p_relative_xml_depth.set(p_relative_xml_depth.get() + Integer.parseInt(T_s.c().GC_XML_PAD_DEPTH))
        String l_tag_string = get_indent() + T_logging_const.GC_XML_LESS + i_tag_name + (i_attributes == T_logging_const.GC_EMPTY_STRING ? T_logging_const.GC_EMPTY_STRING : T_logging_const.GC_SPACE + i_attributes) + T_logging_const.GC_XML_GREATER
        return l_tag_string
    }

    @I_black_box_base("error")
    String close_tag(String i_tag_name) {
        String l_tag_string = get_indent() + T_logging_const.GC_XML_LESS + T_logging_const.GC_XML_END + i_tag_name + T_logging_const.GC_XML_GREATER
        p_relative_xml_depth.set(p_relative_xml_depth.get() - Integer.parseInt(T_s.c().GC_XML_PAD_DEPTH))
        return l_tag_string
    }

    @I_black_box_base("error")
    String get_elapsed_time(HashMap<String, HashMap<String, I_trace>> i_traces_by_source_by_name, I_event i_source_event) {
        String l_message = T_logging_const.GC_EMPTY_STRING
        if (i_traces_by_source_by_name.containsKey(T_logging_const.GC_TRACE_SOURCE_PREDEFINED)) {
            if (i_traces_by_source_by_name.get(T_logging_const.GC_TRACE_SOURCE_PREDEFINED).containsKey(T_destination.PC_STATIC_TRACE_NAME_METHOD_INVOCATION.get_name())) {
                Long l_elapsed_time = ((I_method_invocation) i_traces_by_source_by_name.get(T_logging_const.GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_METHOD_INVOCATION.get_name()).get_ref()).get_elapsed_time()
                if (l_elapsed_time != T_logging_const.GC_NULL_OBJ_REF) {
                    l_message = get_indent() + "<elapsed_time milliseconds=\"" + l_elapsed_time.toString() + "\"/>"
                }
            }
        }
        return l_message
    }

    @Override
    @I_black_box_base("error")
    String format_traces(ArrayList<I_trace> i_event_traces, I_event i_source_event) {
        String l_result = T_logging_const.GC_EMPTY_STRING
        p_absolute_xml_depth.set(i_source_event.get_depth())
        HashMap<String, HashMap<String, I_trace>> l_traces_by_source_by_name = make_traces_by_source_by_name(i_event_traces)
        I_trace l_event_type_trace = l_traces_by_source_by_name.get(T_logging_const.GC_TRACE_SOURCE_PREDEFINED).get(T_destination.PC_STATIC_TRACE_NAME_EVENT_TYPE.get_name())
        if (l_event_type_trace.get_val() == "enter") {
            l_result += make_line(open_tag(PC_TAG_INVOCATION, get_attributes_predefined(i_event_traces, i_source_event)))
            if (l_traces_by_source_by_name.containsKey(T_logging_const.GC_TRACE_SOURCE_RUNTIME)) {
                l_result += make_line(open_tag(PC_TAG_ARGUMENTS))
                for (I_trace l_runtime_trace in l_traces_by_source_by_name.get(T_logging_const.GC_TRACE_SOURCE_RUNTIME).values()) {
                    l_result += make_line(open_tag(PC_TAG_ARGUMENT, get_class_name_short(l_runtime_trace) + T_logging_const.GC_SPACE + get_name(l_runtime_trace)))
                    l_result += make_line(get_indent() + "    " + T_u.escape_xml(l_runtime_trace.format_trace(i_source_event)).replace(System.lineSeparator(), System.lineSeparator() + get_indent() + "    "))
                    l_result += make_line(close_tag(PC_TAG_ARGUMENT))
                }
                l_result += make_line(close_tag(PC_TAG_ARGUMENTS))
            }
            l_result += make_line(open_tag(PC_TAG_EXECUTION))
        } else if (l_event_type_trace.get_val() == "exit") {
            l_result += make_line(close_tag(PC_TAG_EXECUTION))
            if (l_traces_by_source_by_name.containsKey(T_logging_const.GC_TRACE_SOURCE_RUNTIME)) {
                for (I_trace l_runtime_trace in l_traces_by_source_by_name.get(T_logging_const.GC_TRACE_SOURCE_RUNTIME).values()) {
                    l_result += make_line(open_tag(PC_TAG_RESULT, get_class_name_short(l_runtime_trace) + T_logging_const.GC_SPACE + get_name(l_runtime_trace)))
                    l_result += make_line(get_indent() + "    " + T_u.escape_xml(l_runtime_trace.format_trace(i_source_event)).replace(System.lineSeparator(), System.lineSeparator() + get_indent() + "    "))
                    l_result += make_line(close_tag(PC_TAG_RESULT))
                }
            }
            l_result += make_line(get_elapsed_time(l_traces_by_source_by_name, i_source_event))
            l_result += make_line(close_tag(PC_TAG_INVOCATION))
        } else if (l_event_type_trace.get_val() == "error") {
            l_result += make_line(open_tag(PC_TAG_EXCEPTION, make_class_name_attribute(i_source_event.get_throwable().getClass().getSimpleName()) + T_logging_const.GC_SPACE + get_attributes_predefined(i_event_traces, i_source_event)))
            if (i_source_event.get_throwable() != T_logging_const.GC_NULL_OBJ_REF) {
                l_result += make_line(open_tag(PC_TAG_EXCEPTION_STACKTRACE))
                l_result += make_line(get_indent() + "    " + T_u.escape_xml(Arrays.toString(new StackTraceUtils().sanitizeRootCause(i_source_event.get_throwable())?.getStackTrace()).replace(T_logging_const.GC_COMMA, System.lineSeparator() + get_indent())).replace(System.lineSeparator(), System.lineSeparator() + get_indent() + "    "))
                l_result += make_line(close_tag(PC_TAG_EXCEPTION_STACKTRACE))
            }
            if (l_traces_by_source_by_name.containsKey(T_logging_const.GC_TRACE_SOURCE_EXCEPTION_TRACES)) {
                l_result += make_line(open_tag(PC_TAG_EXCEPTION_TRACES))
                for (I_trace l_runtime_trace in l_traces_by_source_by_name.get(T_logging_const.GC_TRACE_SOURCE_EXCEPTION_TRACES).values()) {
                    l_result += make_line(open_tag(PC_TAG_EXCEPTION_TRACE, get_class_name_short(l_runtime_trace)))
                    l_result += make_line(get_indent() + "    " + T_u.escape_xml(l_runtime_trace.format_trace(i_source_event)).replace(System.lineSeparator(), System.lineSeparator() + get_indent() + "    "))
                    l_result += make_line(close_tag(PC_TAG_EXCEPTION_TRACE))
                }
                l_result += make_line(close_tag(PC_TAG_EXCEPTION_TRACES))
            }
            if (l_traces_by_source_by_name.containsKey(T_logging_const.GC_TRACE_SOURCE_RUNTIME)) {
                l_result += make_line(open_tag(PC_TAG_TRACES))
                for (I_trace l_runtime_trace in l_traces_by_source_by_name.get(T_logging_const.GC_TRACE_SOURCE_RUNTIME).values()) {
                    l_result += make_line(open_tag(PC_TAG_TRACE, get_class_name_short(l_runtime_trace)))
                    l_result += make_line(get_indent() + "    " + T_u.escape_xml(l_runtime_trace.format_trace(i_source_event)).replace(System.lineSeparator(), System.lineSeparator() + get_indent() + "    "))
                    l_result += make_line(close_tag(PC_TAG_TRACE))
                }
                l_result += make_line(close_tag(PC_TAG_TRACES))
            }
            l_result += make_line(close_tag(PC_TAG_EXCEPTION))
            l_result += make_line(close_tag(PC_TAG_EXECUTION))
            l_result += make_line(get_elapsed_time(l_traces_by_source_by_name, i_source_event))
            l_result += make_line(close_tag(PC_TAG_INVOCATION))
        } else {
            l_result += make_line(open_tag(i_source_event.get_event_type(), get_attributes_predefined(i_event_traces, i_source_event)))
            if (l_traces_by_source_by_name.containsKey(T_logging_const.GC_TRACE_SOURCE_RUNTIME)) {
                l_result += make_line(open_tag(PC_TAG_TRACES))
                for (I_trace l_runtime_trace in l_traces_by_source_by_name.get(T_logging_const.GC_TRACE_SOURCE_RUNTIME).values()) {
                    l_result += make_line(open_tag(PC_TAG_TRACE, get_class_name_short(l_runtime_trace) + T_logging_const.GC_SPACE + get_name(l_runtime_trace)))
                    l_result += make_line(get_indent() + "    " + T_u.escape_xml(l_runtime_trace.format_trace(i_source_event)).replace(System.lineSeparator(), System.lineSeparator() + get_indent() + "    "))
                    l_result += make_line(close_tag(PC_TAG_TRACE))
                }
                l_result += make_line(close_tag(PC_TAG_TRACES))
            }
            l_result += make_line(close_tag(i_source_event.get_event_type()))
        }
        return l_result
    }
}
