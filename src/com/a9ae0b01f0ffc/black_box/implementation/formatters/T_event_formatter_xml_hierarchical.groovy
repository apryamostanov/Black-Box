package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.implementation.T_event
import com.a9ae0b01f0ffc.black_box.implementation.T_trace
import com.a9ae0b01f0ffc.commons.implementation.exceptions.E_application_exception
import com.a9ae0b01f0ffc.commons.interfaces.I_object_with_uid
import groovy.transform.CompileStatic
import groovy.transform.ToString
import groovy.xml.XmlUtil
import org.codehaus.groovy.runtime.StackTraceUtils

@ToString(includeNames = true, includeFields = true)
class T_event_formatter_xml_hierarchical extends T_event_formatter {

    static final String PC_TAG_ELAPSED_TIME = "elapsed_time"
    static final String PC_TAG_EXCEPTION = "exception"
    static final String PC_TAG_EXCEPTION_STACKTRACE = "stacktrace"
    static final String PC_ATTR_MILLISECONDS = "milliseconds"
    static final String PC_ATTR_EXCEPTION_CLASS = "exception_class"
    static final String PC_ATTR_CLASS = "class"
    static final String PC_ATTR_METHOD = "method"
    static final String PC_ATTR_NAME = "name"
    static final String PC_ATTR_DATETIMESTAMP = "datetimestamp"
    static final String PC_ATTR_LINE = "line"
    static final String PC_ATTR_MESSAGE = "message"
    static final String PC_TRACE_TAG_NAME_PARAMETER = "argument"
    static final String PC_TRACE_TAG_NAME_RESULT = "result"
    static final String PC_TRACE_TAG_NAME_EXCEPTION_TRACE_SOURCE = "root_cause_exception_trace"
    static final String PC_TRACE_TAG_NAME_EXCEPTION_TRACE_UNIT = "local_cause_exception_trace"
    static final String PC_TRACE_TAG_NAME_TRACE = "trace"
    ThreadLocal<Integer> p_relative_xml_depth = new ThreadLocal<Integer>()

    T_event_formatter_xml_hierarchical() {
        p_relative_xml_depth.set(GC_ZERO)
    }

    static String get_class_name_short(T_trace T_trace) {
        String l_short_name = get_short_name(T_trace.get_ref_class_name())
        return make_class_name_attribute(l_short_name)
    }

    static String make_exception_class_name_attribute(String i_short_name) {
        return PC_ATTR_EXCEPTION_CLASS + GC_EQUALS + GC_XML_DOUBLE_QUOTE + i_short_name + GC_XML_DOUBLE_QUOTE
    }

    static String make_class_name_attribute(String i_short_name) {
        return PC_ATTR_CLASS + GC_EQUALS + GC_XML_DOUBLE_QUOTE + i_short_name + GC_XML_DOUBLE_QUOTE
    }

    static String get_name(T_trace T_trace) {
        if (T_trace.get_name() != c().GC_DEFAULT_TRACE_NAME) {
            return PC_ATTR_NAME + GC_EQUALS + GC_XML_DOUBLE_QUOTE + XmlUtil.escapeXml(T_trace.get_name()) + GC_XML_DOUBLE_QUOTE
        } else {
            return GC_EMPTY_STRING
        }
    }

    String make_line(String i_source_line) {
        String l_tag_string = get_indent() + i_source_line
        return l_tag_string == GC_EMPTY_STRING ? GC_EMPTY_STRING : l_tag_string + System.lineSeparator()
    }

    String get_indent() {
        return GC_EMPTY_STRING.padLeft(p_relative_xml_depth.get(), GC_SPACE)
    }

    String open_tag(String i_tag_name, String i_attributes = GC_EMPTY_STRING) {
        String l_tag_string = get_indent() + GC_XML_LESS + i_tag_name + (i_attributes == GC_EMPTY_STRING ? GC_EMPTY_STRING : GC_SPACE + i_attributes) + GC_XML_GREATER
        p_relative_xml_depth.set(p_relative_xml_depth.get() + Integer.parseInt(c().GC_XML_PAD_DEPTH))
        return l_tag_string == GC_EMPTY_STRING ? GC_EMPTY_STRING : l_tag_string + System.lineSeparator()
    }

    String tag(String i_tag_name, String i_attributes = GC_EMPTY_STRING) {
        String l_tag_string = get_indent() + GC_XML_LESS + i_tag_name + (i_attributes == GC_EMPTY_STRING ? GC_EMPTY_STRING : GC_SPACE + i_attributes) + GC_XML_END + GC_XML_GREATER
        return l_tag_string == GC_EMPTY_STRING ? GC_EMPTY_STRING : l_tag_string + System.lineSeparator()
    }

    String close_tag(String i_tag_name) {
        p_relative_xml_depth.set(p_relative_xml_depth.get() - Integer.parseInt(c().GC_XML_PAD_DEPTH))
        String l_tag_string = get_indent() + GC_XML_LESS + GC_XML_END + i_tag_name + GC_XML_GREATER
        return l_tag_string == GC_EMPTY_STRING ? GC_EMPTY_STRING : l_tag_string + System.lineSeparator()
    }

    static String get_elapsed_time(T_event i_source_event) {
        String l_message = GC_EMPTY_STRING
        if (i_source_event.get_execution_node().get_elapsed_time() != GC_NULL_OBJ_REF) {
            l_message = GC_XML_LESS + PC_TAG_ELAPSED_TIME + GC_SPACE + PC_ATTR_MILLISECONDS + GC_EQUALS + GC_XML_DOUBLE_QUOTE + i_source_event.get_execution_node().get_elapsed_time().toString() + GC_XML_DOUBLE_QUOTE + GC_XML_END + GC_XML_GREATER
        }
        return l_message
    }

    static String attr(String i_attr_name, String i_attr_value) {
        if (is_not_null(i_attr_value)) {
            return i_attr_name + GC_EQUALS + GC_XML_DOUBLE_QUOTE + i_attr_value + GC_XML_DOUBLE_QUOTE
        } else {
            return GC_EMPTY_STRING
        }
    }

    static String attrs(String... i_attrs = GC_SKIPPED_ARGS) {
        if (method_arguments_present(i_attrs)) {
            String l_result = GC_EMPTY_STRING
            for (l_attr in i_attrs) {
                l_result += l_attr + GC_SPACE
            }
            return l_result.trim()
        } else {
            return GC_EMPTY_STRING
        }
    }

    String make_traces(ArrayList<T_trace> T_event_traces, String i_tag_name) {
        String l_result = GC_EMPTY_STRING
        if (method_arguments_present(T_event_traces)) {
            for (T_trace l_runtime_trace in T_event_traces) {
                if (is_primitive(get_class_name_short(l_runtime_trace))) {
                    if (l_runtime_trace.toString().length() <= new Integer(c().GC_MAX_TRACE_LENGTH)) {
                        l_result += open_tag(i_tag_name, get_class_name_short(l_runtime_trace) + GC_SPACE + get_name(l_runtime_trace))
                        l_result += make_line(escape_xml(l_runtime_trace.toString()).replace(System.lineSeparator(), System.lineSeparator() + get_indent()))
                        l_result += close_tag(i_tag_name)
                    }
                } else if (l_runtime_trace.get_ref() instanceof I_object_with_uid) {
                    l_result += open_tag(i_tag_name, get_class_name_short(l_runtime_trace) + GC_SPACE + get_name(l_runtime_trace))
                    l_result += make_line(((I_object_with_uid)l_runtime_trace.get_ref()).get_guid())
                    l_result += close_tag(i_tag_name)
                }
            }
        }
        return l_result
    }

    static String get_name_attr(T_event i_event) {
        String l_event_type = i_event.get_event_type()
        String l_name_attr
        if (l_event_type != GC_EVENT_TYPE_METHOD_ENTER) {
            l_name_attr = escape_xml(i_event.get_execution_node().get_code())
        } else {
            l_name_attr = GC_EMPTY_STRING
        }
        return l_name_attr
    }

    static String get_line_attr(T_event i_event) {
        if (i_event.get_line_number() != GC_ZERO) {
            return i_event.get_line_number()
        } else {
            return i_event.get_execution_node().get_line_number()
        }
    }

    @Override
    String format_event(T_event i_source_event) {
        String l_result = GC_EMPTY_STRING
        String l_event_type = i_source_event.get_event_type()
        if ([GC_EVENT_TYPE_METHOD_ENTER, GC_EVENT_TYPE_STATEMENT_ENTER, GC_EVENT_TYPE_EXPRESSION_ENTER].contains(l_event_type)) {
            l_result += open_tag(i_source_event.get_execution_node().get_name_xml(), attrs(attr(PC_ATTR_DATETIMESTAMP, i_source_event.get_datetimestamp()), attr(PC_ATTR_NAME, get_name_attr(i_source_event)), attr(PC_ATTR_LINE, get_line_attr(i_source_event))))
            l_result += make_traces(i_source_event.get_execution_node().get_parameter_traces(), PC_TRACE_TAG_NAME_PARAMETER)
        } else if (l_event_type == GC_EVENT_TYPE_RESULT) {
            l_result += make_traces([i_source_event.get_execution_node().get_result()].toList() as ArrayList<T_trace>, PC_TRACE_TAG_NAME_RESULT)
        } else if ([GC_EVENT_TYPE_METHOD_EXIT, GC_EVENT_TYPE_STATEMENT_EXIT, GC_EVENT_TYPE_EXPRESSION_EXIT].contains(l_event_type)) {
            l_result += make_line(get_elapsed_time(i_source_event))
            l_result += close_tag(i_source_event.get_execution_node().get_name_xml())
        } else if ([GC_EVENT_TYPE_METHOD_ERROR, GC_EVENT_TYPE_STATEMENT_ERROR, GC_EVENT_TYPE_EXPRESSION_ERROR].contains(l_event_type)) {
            l_result += open_tag(PC_TAG_EXCEPTION, make_exception_class_name_attribute(i_source_event.get_execution_node().get_throwable().getClass().getSimpleName()) + GC_SPACE + attrs(attr(PC_ATTR_DATETIMESTAMP, i_source_event.get_datetimestamp()), attr(PC_ATTR_MESSAGE, i_source_event.get_message()), attr(PC_ATTR_LINE, get_line_attr(i_source_event)), attr(PC_ATTR_NAME, get_name_attr(i_source_event))))
            if (i_source_event.get_execution_node().get_throwable() != GC_NULL_OBJ_REF) {
                l_result += open_tag(PC_TAG_EXCEPTION_STACKTRACE)
                l_result += make_line(escape_xml(Arrays.toString(new StackTraceUtils().sanitizeRootCause(i_source_event.get_execution_node().get_throwable())?.getStackTrace()).replace(GC_COMMA, System.lineSeparator() + get_indent())).replace(System.lineSeparator(), System.lineSeparator() + get_indent()))
                l_result += close_tag(PC_TAG_EXCEPTION_STACKTRACE)
                if (i_source_event.get_execution_node().get_throwable() instanceof E_application_exception) {
                    l_result += make_traces(objects2traces(((E_application_exception) i_source_event.get_execution_node().get_throwable()).get_traces()), GC_TRACE_SOURCE_EXCEPTION_TRACES)
                }
            }
            l_result += make_traces(i_source_event.get_execution_node().get_parameter_traces(), PC_TRACE_TAG_NAME_EXCEPTION_TRACE_UNIT)
            l_result += close_tag(PC_TAG_EXCEPTION)
        } else {
            if ((![GC_EVENT_TYPE_INFO, GC_EVENT_TYPE_WARNING].contains(i_source_event.get_event_type())) && method_arguments_present(i_source_event.get_traces_standalone())) {
                l_result += open_tag(i_source_event.get_event_type(), attrs(attr(PC_ATTR_DATETIMESTAMP, i_source_event.get_datetimestamp()), attr(PC_ATTR_MESSAGE, i_source_event.get_message()), attr(PC_ATTR_LINE, get_line_attr(i_source_event))))
                l_result += make_traces(i_source_event.get_traces_standalone(), PC_TRACE_TAG_NAME_TRACE)
                l_result += close_tag(i_source_event.get_event_type())
            } else if (i_source_event.get_event_type() == GC_EVENT_TYPE_TRACES) {
                l_result += open_tag(i_source_event.get_event_type(), attrs(attr(PC_ATTR_DATETIMESTAMP, i_source_event.get_datetimestamp()), attr(PC_ATTR_LINE, get_line_attr(i_source_event))))
                l_result += make_traces(i_source_event.get_traces_standalone(), PC_TRACE_TAG_NAME_TRACE)
                l_result += close_tag(i_source_event.get_event_type())
            } else {
                l_result += tag(i_source_event.get_event_type(), attrs(attr(PC_ATTR_DATETIMESTAMP, i_source_event.get_datetimestamp()), attr(PC_ATTR_MESSAGE, i_source_event.get_message()), attr(PC_ATTR_LINE, get_line_attr(i_source_event))))
            }
        }
        return l_result
    }
}
