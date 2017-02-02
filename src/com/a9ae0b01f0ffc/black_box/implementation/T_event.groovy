package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.implementation.destinations.T_destination
import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.exceptions.E_application_exception
import com.a9ae0b01f0ffc.static_string.T_static_string

class T_event extends T_inherited_configurations implements I_event {

    private String p_event_type = T_s.c().GC_EMPTY_STRING
    private String p_class_name = T_s.c().GC_EMPTY_STRING
    private String p_method_name = T_s.c().GC_EMPTY_STRING
    private Integer p_depth = T_s.c().GC_ZERO
    private Date p_datetimestamp = new Date()
    private ArrayList<I_trace> p_traces_runtime = new ArrayList<I_trace>()
    private ArrayList<I_trace> p_traces_config = new ArrayList<I_trace>()
    private T_static_string p_message = T_s.c().GC_NULL_OBJ_REF as T_static_string
    private Exception p_exception = T_s.c().GC_NULL_OBJ_REF as Exception

    @Override
    String get_class_name() {
        return p_class_name
    }

    @Override
    String get_method_name() {
        return p_method_name
    }

    @Override
    Integer get_depth() {
        return p_depth
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
        return p_datetimestamp.format(T_s.c().GC_LOG_DATETIMESTAMP_FORMAT)
    }

    @Override
    T_static_string get_message() {
        return p_message
    }

    @Override
    Exception get_exception() {
        return p_exception
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
    void set_depth(Integer i_depth) {
        this.p_depth = i_depth
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
    void set_exception(Exception i_exception) {
        this.p_exception = i_exception
    }

    @Override
    I_trace get_corresponding_trace(I_trace i_trace_config) {
        I_trace l_trace_result = T_s.c().GC_NULL_OBJ_REF as I_trace
        for (I_trace l_trace_config : get_traces_config()) {
            if (l_trace_config.match_trace(i_trace_config)) {
                l_trace_result = l_trace_config
                break
            }
        }
        if (l_trace_result == T_s.c().GC_NULL_OBJ_REF) {
            String l_trace_config_source = i_trace_config.get_source()
            if (l_trace_config_source == T_s.c().GC_TRACE_SOURCE_RUNTIME) {
                l_trace_result = get_corresponding_trace(T_destination.PC_TRACE_SOURCE_RUNTIME)
            }
            if (l_trace_config_source == T_s.c().GC_TRACE_SOURCE_CONTEXT) {
                l_trace_result = get_corresponding_trace(T_destination.PC_TRACE_SOURCE_RUNTIME)
            }
            if (l_trace_config_source == T_s.c().GC_TRACE_SOURCE_EXCEPTION_TRACES) {
                l_trace_result = get_corresponding_trace(T_destination.PC_TRACE_SOURCE_RUNTIME)
            }
        }
        return l_trace_result
    }

    @Override
    Boolean is_trace_muted(I_trace i_trace) {
        Boolean l_result
        I_trace l_trace = get_corresponding_trace(i_trace)
        l_result = l_trace == T_s.c().GC_NULL_OBJ_REF ? T_s.c().GC_FALSE : l_trace.is_muted()
        return l_result
    }

    @Override
    Boolean is_trace_masked(I_trace i_trace) {
        Boolean l_result
        I_trace l_trace = get_corresponding_trace(i_trace)
        l_result = l_trace == T_s.c().GC_NULL_OBJ_REF ? T_s.c().GC_FALSE : l_trace.is_masked()
        return l_result
    }

}
