package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_execution_node extends T_logging_base_6_util {

    String p_type = GC_EMPTY_STRING
    String p_name = GC_EMPTY_STRING
    String p_code = GC_EMPTY_STRING
    Integer p_line_number = GC_ZERO
    Long p_start_time = GC_NULL_OBJ_REF as Long
    Long p_end_time = GC_NULL_OBJ_REF as Long
    ArrayList<T_trace> p_traces = new ArrayList<T_trace>()
    ArrayList<T_execution_node> p_child_nodes = new ArrayList<T_execution_node>()
    T_execution_node p_parent_node = GC_NULL_OBJ_REF as T_execution_node
    Throwable p_throwable = GC_NULL_OBJ_REF as Throwable
    T_trace p_result = GC_NULL_OBJ_REF as T_trace

    String get_type() {
        return p_type
    }

    void set_type(String i_type) {
        p_type = i_type
    }

    String get_name_xml() {
        return p_name.replace(GC_POINT, GC_HYPHEN).replace(GC_COLON, GC_HYPHEN)
    }

    T_trace get_result() {
        return p_result
    }

    Throwable get_throwable() {
        return p_throwable
    }

    T_execution_node(String i_name) {
        p_name = i_name
    }

    T_execution_node() {

    }

    void set_code(String i_code) {
        p_code = i_code
    }

    String get_code() {
        return p_code
    }

    void set_throwable(Throwable i_throwable) {
        p_throwable = i_throwable
    }

    void set_result(T_trace i_result) {
        p_result = i_result
    }

    String get_name() {
        return p_name
    }

    void set_name(String i_method_name) {
        p_name = i_method_name
    }

    void stop_timing() {
        if (p_end_time == GC_NULL_OBJ_REF) {
            p_end_time = System.currentTimeMillis()
        }
    }

    void start_timing() {
        p_start_time = System.currentTimeMillis()
    }

    Long get_elapsed_time() {
        if (p_start_time == GC_NULL_OBJ_REF) {
            return GC_NULL_OBJ_REF as Long
        }
        stop_timing()
        return p_end_time - p_start_time
    }

    Integer get_line_number() {
        return p_line_number
    }

    void set_line_number(Integer i_line_number) {
        p_line_number = i_line_number
    }

    void set_parameter_traces(ArrayList<T_trace> i_traces) {
        p_traces = i_traces
    }

    ArrayList<T_trace> get_parameter_traces() {
        return p_traces
    }

    void set_parent_node(T_execution_node i_parent_node) {
        p_parent_node = i_parent_node
    }

    T_execution_node get_parent_node() {
        return p_parent_node
    }

    String get_profiling_key() {
        T_execution_node l_parent_node = get_parent_node()
        String l_profiling_key = GC_EMPTY_STRING
        while (is_not_null(l_parent_node)) {
            l_profiling_key += l_parent_node.get_name()
            l_parent_node = l_parent_node.get_parent_node()
        }
        l_profiling_key += get_name()
        return l_profiling_key
    }
}
