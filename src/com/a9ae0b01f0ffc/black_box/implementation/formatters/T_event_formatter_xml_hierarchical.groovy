package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination
import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.black_box.main.T_u
import com.a9ae0b01f0ffc.black_box_base.annotations.I_black_box_base
import groovy.transform.ToString
import groovy.xml.XmlUtil
import org.codehaus.groovy.runtime.StackTraceUtils

@ToString(includeNames = true, includeFields = true)
class T_event_formatter_xml_hierarchical extends T_event_formatter implements I_event_formatter {

    static final String PC_TAG_INVOCATION = "invocation"
    static final String PC_TAG_ARGUMENT = "argument"
    static final String PC_TAG_RESULT = "result"
    static final String PC_TAG_ELAPSED_TIME = "elapsed_time"
    static final String PC_TAG_EXCEPTION = "exception"
    static final String PC_TAG_EXCEPTION_STACKTRACE = "stacktrace"
    static final String PC_TAG_EXCEPTION_TRACE = "exception_trace"
    static final String PC_TAG_TRACE = "trace"
    static final String PC_ATTR_MILLISECONDS = "milliseconds"
    static final String PC_ATTR_CLASS = "class"
    static final String PC_ATTR_NAME = "name"
    ThreadLocal<Integer> p_relative_xml_depth = new ThreadLocal<Integer>()

    T_event_formatter_xml_hierarchical() {
        p_relative_xml_depth.set(GC_ZERO)
    }

    @I_black_box_base("error")
    static HashMap<String, HashMap<String, I_trace>> make_traces_by_source_by_name(ArrayList<I_trace> i_event_traces) {
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
    static String get_class_name_short(I_trace i_trace) {
        return make_class_name_attribute(T_u.get_short_name(i_trace.get_ref_class_name()))
    }

    @I_black_box_base("error")
    static String make_class_name_attribute(String i_short_name) {
        return PC_ATTR_CLASS + GC_EQUALS + GC_XML_DOUBLE_QUOTE + i_short_name + GC_XML_DOUBLE_QUOTE
    }

    @I_black_box_base("error")
    static String get_name(I_trace i_trace) {
        return PC_ATTR_NAME + GC_EQUALS + GC_XML_DOUBLE_QUOTE + XmlUtil.escapeXml(i_trace.get_name()) + GC_XML_DOUBLE_QUOTE
    }

    @I_black_box_base("error")
    String make_line(String i_source_line) {
        String l_tag_string = get_indent() + i_source_line
        return l_tag_string == GC_EMPTY_STRING ? GC_EMPTY_STRING : l_tag_string + System.lineSeparator()
    }

    @I_black_box_base("error")
    String get_indent() {
        return GC_EMPTY_STRING.padLeft(p_relative_xml_depth.get(), GC_SPACE)
    }

    @I_black_box_base("error")
    static String get_attributes_predefined(ArrayList<I_trace> i_event_traces, I_event i_source_event) {
        String l_attribute_string = GC_EMPTY_STRING
        for (l_trace in i_event_traces) {
            if (l_trace.get_source() == T_destination.PC_TRACE_SOURCE_PREDEFINED.get_name()) {
                if (l_attribute_string != GC_EMPTY_STRING) {
                    l_attribute_string += GC_SPACE
                }
                l_attribute_string += l_trace.get_name() + GC_EQUALS + GC_XML_DOUBLE_QUOTE + XmlUtil.escapeXml(l_trace.format_trace(i_source_event)) + GC_XML_DOUBLE_QUOTE
            }
        }
        return l_attribute_string
    }

    @I_black_box_base("error")
    String open_tag(String i_tag_name, String i_attributes = GC_EMPTY_STRING) {
        String l_tag_string = get_indent() + GC_XML_LESS + i_tag_name + (i_attributes == GC_EMPTY_STRING ? GC_EMPTY_STRING : GC_SPACE + i_attributes) + GC_XML_GREATER
        p_relative_xml_depth.set(p_relative_xml_depth.get() + Integer.parseInt(T_s.c().GC_XML_PAD_DEPTH))
        return l_tag_string == GC_EMPTY_STRING ? GC_EMPTY_STRING : l_tag_string + System.lineSeparator()
    }

    @I_black_box_base("error")
    String tag(String i_tag_name, String i_attributes = GC_EMPTY_STRING) {
        String l_tag_string = get_indent() + GC_XML_LESS + i_tag_name + (i_attributes == GC_EMPTY_STRING ? GC_EMPTY_STRING : GC_SPACE + i_attributes) + GC_XML_END + GC_XML_GREATER
        return l_tag_string == GC_EMPTY_STRING ? GC_EMPTY_STRING : l_tag_string + System.lineSeparator()
    }

    @I_black_box_base("error")
    String close_tag(String i_tag_name) {
        p_relative_xml_depth.set(p_relative_xml_depth.get() - Integer.parseInt(T_s.c().GC_XML_PAD_DEPTH))
        String l_tag_string = get_indent() + GC_XML_LESS + GC_XML_END + i_tag_name + GC_XML_GREATER
        return l_tag_string == GC_EMPTY_STRING ? GC_EMPTY_STRING : l_tag_string + System.lineSeparator()
    }

    @I_black_box_base("error")
    static String get_elapsed_time(I_event i_source_event) {
        String l_message = GC_EMPTY_STRING
        if (i_source_event.get_invocation().get_elapsed_time() != GC_NULL_OBJ_REF) {
            l_message = GC_XML_LESS + PC_TAG_ELAPSED_TIME + GC_SPACE + PC_ATTR_MILLISECONDS + GC_EQUALS + GC_XML_DOUBLE_QUOTE + i_source_event.get_invocation().get_elapsed_time().toString() + GC_XML_DOUBLE_QUOTE + GC_XML_END + GC_XML_GREATER
        }
        return l_message
    }

    @Override
    @I_black_box_base("error")
    String format_traces(ArrayList<I_trace> i_event_traces, I_event i_source_event) {
        String l_result = GC_EMPTY_STRING
        HashMap<String, HashMap<String, I_trace>> l_traces_by_source_by_name = make_traces_by_source_by_name(i_event_traces)
        String l_event_type = i_source_event.get_event_type()
        if (l_event_type == "enter") {
            l_result += open_tag(i_source_event.get_method_name(), get_attributes_predefined(i_event_traces, i_source_event))
            if (l_traces_by_source_by_name.containsKey(GC_TRACE_SOURCE_RUNTIME)) {
                for (I_trace l_runtime_trace in l_traces_by_source_by_name.get(GC_TRACE_SOURCE_RUNTIME).values()) {
                    l_result += open_tag(l_runtime_trace.get_name(), get_class_name_short(l_runtime_trace))
                    l_result += make_line(T_u.escape_xml(l_runtime_trace.format_trace(i_source_event)).replace(System.lineSeparator(), System.lineSeparator() + get_indent()))
                    l_result += close_tag(l_runtime_trace.get_name())
                }
            }
        } else if (l_event_type == "exit") {
            if (l_traces_by_source_by_name.containsKey(GC_TRACE_SOURCE_RUNTIME)) {
                for (I_trace l_runtime_trace in l_traces_by_source_by_name.get(GC_TRACE_SOURCE_RUNTIME).values()) {
                    l_result += open_tag(PC_TAG_RESULT, get_class_name_short(l_runtime_trace) + GC_SPACE + get_name(l_runtime_trace))
                    l_result += make_line(T_u.escape_xml(l_runtime_trace.format_trace(i_source_event)).replace(System.lineSeparator(), System.lineSeparator() + get_indent()))
                    l_result += close_tag(PC_TAG_RESULT)
                }
            }
            l_result += make_line(get_elapsed_time(i_source_event))
        } else if (l_event_type == "error") {
            l_result += open_tag(PC_TAG_EXCEPTION, make_class_name_attribute(i_source_event.get_throwable().getClass().getSimpleName()) + GC_SPACE + get_attributes_predefined(i_event_traces, i_source_event))
            if (i_source_event.get_throwable() != GC_NULL_OBJ_REF) {
                l_result += open_tag(PC_TAG_EXCEPTION_STACKTRACE)
                l_result += make_line(T_u.escape_xml(Arrays.toString(new StackTraceUtils().sanitizeRootCause(i_source_event.get_throwable())?.getStackTrace()).replace(GC_COMMA, System.lineSeparator() + get_indent())).replace(System.lineSeparator(), System.lineSeparator() + get_indent()))
                l_result += close_tag(PC_TAG_EXCEPTION_STACKTRACE)
            }
            if (l_traces_by_source_by_name.containsKey(GC_TRACE_SOURCE_EXCEPTION_TRACES)) {
                for (I_trace l_runtime_trace in l_traces_by_source_by_name.get(GC_TRACE_SOURCE_EXCEPTION_TRACES).values()) {
                    l_result += open_tag(PC_TAG_EXCEPTION_TRACE, get_class_name_short(l_runtime_trace))
                    l_result += make_line(T_u.escape_xml(l_runtime_trace.format_trace(i_source_event)).replace(System.lineSeparator(), System.lineSeparator() + get_indent()))
                    l_result += close_tag(PC_TAG_EXCEPTION_TRACE)
                }
            }
            if (l_traces_by_source_by_name.containsKey(GC_TRACE_SOURCE_RUNTIME)) {
                for (I_trace l_runtime_trace in l_traces_by_source_by_name.get(GC_TRACE_SOURCE_RUNTIME).values()) {
                    l_result += open_tag(PC_TAG_TRACE, get_class_name_short(l_runtime_trace))
                    l_result += make_line(T_u.escape_xml(l_runtime_trace.format_trace(i_source_event)).replace(System.lineSeparator(), System.lineSeparator() + get_indent()))
                    l_result += close_tag(PC_TAG_TRACE)
                }
            }
            l_result += close_tag(PC_TAG_EXCEPTION)
        } else if (l_event_type == "statement") {
            l_result += tag(i_source_event.get_message().toString(), get_attributes_predefined(i_event_traces, i_source_event))
        } else {
            if (l_traces_by_source_by_name.containsKey(GC_TRACE_SOURCE_RUNTIME)) {
                l_result += open_tag(i_source_event.get_event_type(), get_attributes_predefined(i_event_traces, i_source_event))
                for (I_trace l_runtime_trace in l_traces_by_source_by_name.get(GC_TRACE_SOURCE_RUNTIME).values()) {
                    l_result += open_tag(PC_TAG_TRACE, get_class_name_short(l_runtime_trace) + GC_SPACE + get_name(l_runtime_trace))
                    l_result += make_line(T_u.escape_xml(l_runtime_trace.format_trace(i_source_event)).replace(System.lineSeparator(), System.lineSeparator() + get_indent()))
                    l_result += close_tag(PC_TAG_TRACE)
                }
                l_result += close_tag(i_source_event.get_event_type())
            } else {
                l_result += tag(i_source_event.get_event_type(), get_attributes_predefined(i_event_traces, i_source_event))
            }
        }
        return l_result
    }
}
