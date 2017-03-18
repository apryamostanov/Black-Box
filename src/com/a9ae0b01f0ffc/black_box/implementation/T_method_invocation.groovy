package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.interfaces.I_destination
import com.a9ae0b01f0ffc.black_box.interfaces.I_method_invocation
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class T_method_invocation extends T_logging_base_6_util implements I_method_invocation {

    String p_class_name = GC_EMPTY_STRING
    String p_method_name = GC_EMPTY_STRING
    String p_statement_name = GC_EMPTY_STRING
    Integer p_line_number = GC_ZERO
    Long p_start_time = GC_NULL_OBJ_REF as Long
    Long p_end_time = GC_NULL_OBJ_REF as Long
    HashMap<I_destination, ArrayList<String>> p_events_by_destination = new HashMap<I_destination, ArrayList<String>>()

    @Override
    String get_class_name() {
        return p_class_name
    }

    @Override
    String get_method_name() {
        return p_method_name
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
    void stop_timing() {
        if (p_end_time == GC_NULL_OBJ_REF) {
            p_end_time = System.currentTimeMillis()
        }
    }

    @Override
    void start_timing() {
        p_start_time = System.currentTimeMillis()
    }


    @Override
    Long get_elapsed_time() {
        if (p_start_time == GC_NULL_OBJ_REF) {
            return GC_NULL_OBJ_REF as Long
        }
        stop_timing()
        return p_end_time - p_start_time
    }

    @Override
    String toString() {
        return "T_method_invocation{" +
                "p_class_name='" + p_class_name + '\'' +
                ", p_method_name='" + p_method_name + '\'' +
                ", p_method_arguments=" + p_method_arguments +
                ", p_start_time=" + p_start_time +
                ", p_end_time=" + p_end_time +
                '}'
    }

    @Override
    Boolean is_event_logged_for_destination(String i_event_type, I_destination i_destination) {
        if (p_events_by_destination.containsKey(i_destination)) {
            return p_events_by_destination.get(i_destination).contains(i_event_type)
        } else {
            return GC_FALSE
        }
    }

    @Override
    void set_event_logged_for_destination(String i_event_type, I_destination i_destination) {
        if (p_events_by_destination.containsKey(i_destination)) {
            p_events_by_destination.get(i_destination).add(i_event_type)
        } else {
            p_events_by_destination.put(i_destination, new ArrayList<String>([i_event_type]))
        }
    }

    @Override
    String get_statement_name() {
        return p_statement_name
    }

    @Override
    Integer get_line_number() {
        return p_line_number
    }

    @Override
    void set_statement_name(String i_statement_name) {
        p_statement_name = i_statement_name
    }

    @Override
    void set_line_number(Integer i_line_number) {
        p_line_number = i_line_number
    }
}
