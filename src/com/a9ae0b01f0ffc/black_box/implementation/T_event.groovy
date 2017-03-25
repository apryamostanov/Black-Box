package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util
import com.a9ae0b01f0ffc.commons.implementation.static_string.T_static_string
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_event extends T_logging_base_6_util {

    private String p_event_type = GC_EMPTY_STRING
    private Date p_datetimestamp = new Date()
    private ArrayList<T_trace> p_standalone_traces = new ArrayList<T_trace>()
    private T_static_string p_message = T_static_string.PC_EMPTY_STATIC_STRING
    private T_execution_node p_execution_node = GC_NULL_OBJ_REF as T_execution_node
    private Integer p_line_number = GC_ZERO

    ArrayList<T_trace> get_traces_standalone() {
        return p_standalone_traces
    }

     String get_datetimestamp() {
        return p_datetimestamp.format(c().GC_DATETIMESTAMP_FORMAT)
    }

    String get_message() {
        return tokenize(p_message, c().GC_MESSAGE_FORMAT_TOKEN_SEPARATOR_BEFORE, c().GC_MESSAGE_FORMAT_TOKEN_TRACE, get_traces_standalone())
    }

    void serialize_traces() {
        for (T_trace l_trace in p_standalone_traces) {
            l_trace.serialize_trace()
        }
        for (T_trace l_trace in p_execution_node.get_parameter_traces()) {
            l_trace.serialize_trace()
        }
    }

    String get_event_type() {
        return p_event_type
    }

    void set_event_type(String i_event_type) {
        this.p_event_type = i_event_type
    }

    void set_datetimestamp(Date i_datetimestamp) {
        this.p_datetimestamp = i_datetimestamp
    }

    void set_message(T_static_string i_message) {
        this.p_message = i_message
    }

    void set_execution_node(T_execution_node i_method_invocation) {
        p_execution_node = i_method_invocation
    }

    T_execution_node get_execution_node() {
        return p_execution_node
    }

    Integer get_line_number() {
        return p_line_number
    }

    void set_line_number(Integer i_line_number) {
        p_line_number = i_line_number
    }

    void set_standalone_traces(ArrayList<T_trace> i_traces) {
        p_standalone_traces = i_traces
    }
}
