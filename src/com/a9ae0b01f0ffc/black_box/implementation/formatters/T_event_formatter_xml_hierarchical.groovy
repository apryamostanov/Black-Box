package com.a9ae0b01f0ffc.black_box.implementation.formatters

import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_event_formatter
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.commons.implementation.exceptions.E_application_exception
import groovy.transform.ToString
import groovy.xml.XmlUtil
import org.codehaus.groovy.runtime.StackTraceUtils

@ToString(includeNames = true, includeFields = true)
class T_event_formatter_xml_hierarchical extends T_event_formatter implements I_event_formatter {

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

    static String get_class_name_short(I_trace i_trace) {
        String l_short_name = get_short_name(i_trace.get_ref_class_name())
        return make_class_name_attribute(l_short_name)
    }

    static String make_exception_class_name_attribute(String i_short_name) {
        return PC_ATTR_EXCEPTION_CLASS + GC_EQUALS + GC_XML_DOUBLE_QUOTE + i_short_name + GC_XML_DOUBLE_QUOTE
    }

    static String make_class_name_attribute(String i_short_name) {
        return PC_ATTR_CLASS + GC_EQUALS + GC_XML_DOUBLE_QUOTE + i_short_name + GC_XML_DOUBLE_QUOTE
    }

    static String get_name(I_trace i_trace) {
        if (i_trace.get_name() != c().GC_DEFAULT_TRACE_NAME) {
            return PC_ATTR_NAME + GC_EQUALS + GC_XML_DOUBLE_QUOTE + XmlUtil.escapeXml(i_trace.get_name()) + GC_XML_DOUBLE_QUOTE
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

    static String get_elapsed_time(I_event i_source_event) {
        String l_message = GC_EMPTY_STRING
        if (i_source_event.get_invocation().get_elapsed_time() != GC_NULL_OBJ_REF) {
            l_message = GC_XML_LESS + PC_TAG_ELAPSED_TIME + GC_SPACE + PC_ATTR_MILLISECONDS + GC_EQUALS + GC_XML_DOUBLE_QUOTE + i_source_event.get_invocation().get_elapsed_time().toString() + GC_XML_DOUBLE_QUOTE + GC_XML_END + GC_XML_GREATER
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

    String make_traces(ArrayList<I_trace> i_event_traces, I_event i_source_event, String i_tag_name) {
        String l_result = GC_EMPTY_STRING
        if (method_arguments_present(i_event_traces)) {
            for (I_trace l_runtime_trace in i_event_traces) {
                l_result += open_tag(i_tag_name, get_class_name_short(l_runtime_trace) + GC_SPACE + get_name(l_runtime_trace))
                l_result += make_line(escape_xml(l_runtime_trace.format_trace(i_source_event)).replace(System.lineSeparator(), System.lineSeparator() + get_indent()))
                l_result += close_tag(i_tag_name)
            }
        }
        return l_result
    }

    @Override
    String format_event(I_event i_source_event) {
        String l_result = GC_EMPTY_STRING
        String l_event_type = i_source_event.get_event_type()
        if (l_event_type == GC_EVENT_TYPE_METHOD_ENTER) {
            l_result += open_tag(i_source_event.get_method_name(), attrs(attr(PC_ATTR_DATETIMESTAMP, i_source_event.get_datetimestamp()), attr(PC_ATTR_CLASS, get_short_name(i_source_event.get_class_name())), attr(PC_ATTR_LINE, i_source_event.get_line_number().toString())))
            l_result += make_traces(i_source_event.get_traces_runtime(), i_source_event, PC_TRACE_TAG_NAME_PARAMETER)
        } else if (l_event_type == GC_EVENT_TYPE_STATEMENT_ENTER) {
            l_result += open_tag(i_source_event.get_statement_name(), attrs(attr(PC_ATTR_DATETIMESTAMP, i_source_event.get_datetimestamp()), attr(PC_ATTR_LINE, i_source_event.get_line_number().toString())))
            l_result += make_traces(i_source_event.get_traces_runtime(), i_source_event, PC_TRACE_TAG_NAME_PARAMETER)
        } else if (l_event_type == GC_EVENT_TYPE_RESULT) {
            l_result += make_traces(i_source_event.get_traces_runtime(), i_source_event, PC_TRACE_TAG_NAME_RESULT)
        } else if (l_event_type == GC_EVENT_TYPE_METHOD_EXIT) {
            l_result += make_line(get_elapsed_time(i_source_event))
            l_result += close_tag(i_source_event.get_method_name())
        } else if (l_event_type == GC_EVENT_TYPE_STATEMENT_EXIT) {
            l_result += make_line(get_elapsed_time(i_source_event))
            l_result += close_tag(i_source_event.get_statement_name())
        } else if (l_event_type == GC_EVENT_TYPE_METHOD_ERROR) {
            l_result += open_tag(PC_TAG_EXCEPTION, make_exception_class_name_attribute(i_source_event.get_throwable().getClass().getSimpleName()) + GC_SPACE + attrs(attr(PC_ATTR_DATETIMESTAMP, i_source_event.get_datetimestamp()), attr(PC_ATTR_MESSAGE, i_source_event.get_message()), attr(PC_ATTR_LINE, i_source_event.get_line_number().toString()), attr(PC_ATTR_CLASS, get_short_name(i_source_event.get_class_name())), attr(PC_ATTR_METHOD, get_short_name(i_source_event.get_method_name()))))
            if (i_source_event.get_throwable() != GC_NULL_OBJ_REF) {
                l_result += open_tag(PC_TAG_EXCEPTION_STACKTRACE)
                l_result += make_line(escape_xml(Arrays.toString(new StackTraceUtils().sanitizeRootCause(i_source_event.get_throwable())?.getStackTrace()).replace(GC_COMMA, System.lineSeparator() + get_indent())).replace(System.lineSeparator(), System.lineSeparator() + get_indent()))
                l_result += close_tag(PC_TAG_EXCEPTION_STACKTRACE)
                if (i_source_event.get_throwable() instanceof E_application_exception) {
                    l_result += make_traces(objects2traces(((E_application_exception) i_source_event.get_throwable()).get_traces(), GC_TRACE_SOURCE_EXCEPTION_TRACES), i_source_event, PC_TRACE_TAG_NAME_EXCEPTION_TRACE_SOURCE)
                }
            }
            l_result += make_traces(i_source_event.get_traces_runtime(), i_source_event, PC_TRACE_TAG_NAME_EXCEPTION_TRACE_UNIT)
            l_result += close_tag(PC_TAG_EXCEPTION)
        }  else if (l_event_type == GC_EVENT_TYPE_STATEMENT_ERROR) {
            l_result += open_tag(PC_TAG_EXCEPTION, make_exception_class_name_attribute(i_source_event.get_throwable().getClass().getSimpleName()) + GC_SPACE + attrs(attr(PC_ATTR_DATETIMESTAMP, i_source_event.get_datetimestamp()), attr(PC_ATTR_MESSAGE, i_source_event.get_message()), attr(PC_ATTR_LINE, i_source_event.get_line_number().toString()), attr(PC_ATTR_CLASS, get_short_name(i_source_event.get_class_name())), attr(PC_ATTR_METHOD, get_short_name(i_source_event.get_method_name()))))
            if (i_source_event.get_throwable() != GC_NULL_OBJ_REF) {
                l_result += open_tag(PC_TAG_EXCEPTION_STACKTRACE)
                l_result += make_line(escape_xml(Arrays.toString(new StackTraceUtils().sanitizeRootCause(i_source_event.get_throwable())?.getStackTrace()).replace(GC_COMMA, System.lineSeparator() + get_indent())).replace(System.lineSeparator(), System.lineSeparator() + get_indent()))
                l_result += close_tag(PC_TAG_EXCEPTION_STACKTRACE)
                if (i_source_event.get_throwable() instanceof E_application_exception) {
                    l_result += make_traces(objects2traces(((E_application_exception) i_source_event.get_throwable()).get_traces(), GC_TRACE_SOURCE_EXCEPTION_TRACES), i_source_event, PC_TRACE_TAG_NAME_EXCEPTION_TRACE_SOURCE)
                }
            }
            l_result += make_traces(i_source_event.get_traces_runtime(), i_source_event, PC_TRACE_TAG_NAME_EXCEPTION_TRACE_UNIT)
            l_result += close_tag(PC_TAG_EXCEPTION)
        } else {
            if ((![GC_EVENT_TYPE_INFO, GC_EVENT_TYPE_WARNING].contains(i_source_event.get_event_type())) && method_arguments_present(i_source_event.get_traces_runtime())) {
                l_result += open_tag(i_source_event.get_event_type(), attrs(attr(PC_ATTR_DATETIMESTAMP, i_source_event.get_datetimestamp()), attr(PC_ATTR_MESSAGE, i_source_event.get_message()), attr(PC_ATTR_CLASS, get_short_name(i_source_event.get_class_name())), attr(PC_ATTR_METHOD, i_source_event.get_method_name()), attr(PC_ATTR_LINE, i_source_event.get_line_number().toString())))
                l_result += make_traces(i_source_event.get_traces_runtime(), i_source_event, PC_TRACE_TAG_NAME_TRACE)
                l_result += close_tag(i_source_event.get_event_type())
            } else if (i_source_event.get_event_type() == GC_EVENT_TYPE_TRACES) {
                l_result += open_tag(i_source_event.get_event_type(), attrs(attr(PC_ATTR_DATETIMESTAMP, i_source_event.get_datetimestamp()), attr(PC_ATTR_CLASS, get_short_name(i_source_event.get_class_name())), attr(PC_ATTR_METHOD, i_source_event.get_method_name()), attr(PC_ATTR_LINE, i_source_event.get_line_number().toString())))
                l_result += make_traces(i_source_event.get_traces_runtime(), i_source_event, PC_TRACE_TAG_NAME_TRACE)
                l_result += close_tag(i_source_event.get_event_type())
            } else {
                l_result += tag(i_source_event.get_event_type(), attrs(attr(PC_ATTR_DATETIMESTAMP, i_source_event.get_datetimestamp()), attr(PC_ATTR_MESSAGE, i_source_event.get_message()), attr(PC_ATTR_CLASS, get_short_name(i_source_event.get_class_name())), attr(PC_ATTR_METHOD, i_source_event.get_method_name()), attr(PC_ATTR_LINE, i_source_event.get_line_number().toString())))
            }
        }
        return l_result
    }
}
