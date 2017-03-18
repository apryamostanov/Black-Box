package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination
import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_method_invocation
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.commons.implementation.static_string.T_static_string
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_event extends T_inherited_configurations implements I_event {

    private String p_event_type = GC_EMPTY_STRING
    private String p_class_name = GC_EMPTY_STRING
    private String p_method_name = GC_EMPTY_STRING
    private String p_statement_name = GC_EMPTY_STRING
    private Date p_datetimestamp = new Date()
    private ArrayList<I_trace> p_traces_runtime = new ArrayList<I_trace>()
    private ArrayList<I_trace> p_traces_config = new ArrayList<I_trace>()
    private T_static_string p_message = T_static_string.PC_EMPTY_STATIC_STRING
    private Throwable p_throwable = GC_NULL_OBJ_REF as Throwable
    private I_method_invocation p_invocation = GC_NULL_OBJ_REF as I_method_invocation
    private Integer p_line_number = GC_ZERO

    @Override
    String get_class_name() {
        return p_class_name
    }

    @Override
    String get_method_name() {
        return p_method_name
    }

    @Override
    void add_trace_runtime(I_trace i_trace) {
        p_traces_runtime.add(i_trace)
    }

    @Override
    void add_trace_config(I_trace i_trace_config) {
        p_traces_config.add(i_trace_config)
    }

    @Override
    ArrayList<I_trace> get_traces_runtime() {
        return p_traces_runtime
    }

    @Override
    ArrayList<I_trace> get_traces_config() {
        return p_traces_config
    }

    @Override
    String get_datetimestamp() {
        return p_datetimestamp.format(c().GC_DATETIMESTAMP_FORMAT)
    }

    @Override
    String get_message() {
        return tokenize(p_message, c().GC_MESSAGE_FORMAT_TOKEN_SEPARATOR_BEFORE, c().GC_MESSAGE_FORMAT_TOKEN_TRACE, get_traces_runtime())
    }

    @Override
    Throwable get_throwable() {
        return p_throwable
    }

    @Override
    String get_event_type() {
        return p_event_type
    }

    @Override
    void set_event_type(String i_event_type) {
        this.p_event_type = i_event_type
    }

    @Override
    void set_class_name(String i_class_name) {
        this.p_class_name = i_class_name
    }

    @Override
    void set_method_name(String i_method_name) {
        this.p_method_name = i_method_name
    }

    @Override
    void set_datetimestamp(Date i_datetimestamp) {
        this.p_datetimestamp = i_datetimestamp
    }

    @Override
    void add_traces_runtime(Collection<I_trace> i_traces_runtime) {
        this.p_traces_runtime.addAll(i_traces_runtime)
    }

    @Override
    void set_message(T_static_string i_message) {
        this.p_message = i_message
    }

    @Override
    void set_throwable(Throwable i_throwable) {
        this.p_throwable = i_throwable
    }

    @Override
    Boolean is_trace_masked(I_trace i_trace) {
        Boolean l_result = GC_FALSE
        for (l_trace_config in get_traces_config()) {
            if (l_trace_config.match_trace(i_trace.get_ref_class_name(), i_trace.get_name())) {
                l_result = l_trace_config.is_masked()
                break
            }
        }
        return l_result
    }

    @Override
    void set_invocation(I_method_invocation i_method_invocation) {
        p_invocation = i_method_invocation
    }

    @Override
    I_method_invocation get_invocation() {
        return p_invocation
    }

    @Override
    Integer get_line_number() {
        return p_line_number
    }

    @Override
    void set_line_number(Integer i_line_number) {
        p_line_number = i_line_number
    }

    @Override
    void set_statement_name(String i_statement_name) {
        p_statement_name = i_statement_name
    }

    @Override
    String get_statement_name() {
        return p_statement_name
    }
}
