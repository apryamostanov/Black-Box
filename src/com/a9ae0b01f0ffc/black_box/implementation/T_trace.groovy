package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.main.T_logging_base_6_util
import com.a9ae0b01f0ffc.commons.interfaces.I_object_with_uid
import groovy.transform.CompileStatic
import groovy.transform.Memoized

class T_trace extends T_logging_base_6_util {

    String p_name = GC_EMPTY_STRING
    Object p_ref = GC_NULL_OBJ_REF
    String p_value = GC_EMPTY_STRING

    T_trace() {
    }

    T_trace(String p_name, Object p_ref) {
        this.p_name = p_name
        this.p_ref = p_ref
    }

    void serialize_trace() {
        if (is_null(p_value)) {
            p_value = toString()
        }
    }

    String toString() {
        nvl(p_value, p_ref.toString())
    }

    String get_name() {
        return p_name
    }

    String get_ref_class_name() {
        String l_result = GC_EMPTY_STRING
        if (get_ref() != GC_NULL_OBJ_REF) {
            l_result = get_ref().getClass().getCanonicalName()
        }
        return l_result
    }

    void set_name(String i_name) {
        p_name = i_name
    }

    Object get_ref() {
        return p_ref
    }

    void set_ref(Object i_ref) {
        p_ref = i_ref
    }

    void set_val(String i_value) {
        p_value = i_value
    }

    String get_val() {
        return p_value
    }

}
