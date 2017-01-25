package com.a9ae0b01f0ffc.mighty_logger.implementation

import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_maskable
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_trace
import com.a9ae0b01f0ffc.mighty_logger.interfaces.I_trace_formatter
import com.a9ae0b01f0ffc.mighty_logger.main.T_s

class T_trace implements I_trace {

    Boolean p_muted = T_s.c().GC_FALSE
    Boolean p_masked = T_s.c().GC_FALSE
    I_trace_formatter p_trace_formatter = T_s.c().GC_NULL_OBJ_REF as I_trace_formatter
    String p_name = T_s.c().GC_EMPTY_STRING
    Object p_ref = T_s.c().GC_NULL_OBJ_REF
    String p_value = T_s.c().GC_EMPTY_STRING
    String p_source = T_s.c().GC_EMPTY_STRING

    @Override
    Boolean is_muted() {
        return p_muted
    }

    @Override
    Boolean is_masked() {
        return p_masked
    }

    @Override
    void set_muted(Boolean i_is_muted) {
        p_muted = i_is_muted
    }

    @Override
    void set_masked(Boolean i_is_masked) {
        p_masked = i_is_masked
    }

    @Override
    String toString() {
        if (p_ref == T_s.c().GC_NULL_OBJ_REF && (p_value == T_s.c().GC_NULL_OBJ_REF || p_value == T_s.c().GC_EMPTY_STRING || p_value == T_s.c().GC_DEFAULT_TRACE)) {
            return T_s.c().GC_DEFAULT_TRACE
        } else if (p_muted) {
            return T_s.c().GC_DEFAULT_TRACE_MUTED
        } else if (p_masked) {
            if (p_ref instanceof I_maskable) {
                if (p_value != T_s.c().GC_EMPTY_STRING) {
                    return T_s.c().GC_DEFAULT_TRACE_MASKED//there is no control on how objects are serialized, thus it is unknown how to mask their serialized representation.
                } else {
                    return ((I_maskable) p_ref).to_string_masked()
                }
            } else {
                return T_s.c().GC_DEFAULT_TRACE_MASKED
            }
        } else {
            if (p_value != T_s.c().GC_EMPTY_STRING) {
                return p_value
            } else {
                return p_ref.toString()
            }
        }
    }

    @Override
    void set_formatter(I_trace_formatter i_trace_formatter) {
        p_trace_formatter = i_trace_formatter
    }

    @Override
    I_trace_formatter get_formatter() {
        return p_trace_formatter
    }

    @Override
    String get_name() {
        return p_name
    }

    @Override
    void set_name(String i_name) {
        p_name = i_name
    }

    @Override
    Object get_ref() {
        return p_ref
    }

    @Override
    void set_ref(Object i_ref) {
        p_ref = i_ref
    }

    @Override
    void set_val(String i_value) {
        p_value = i_value
    }

    @Override
    String get_val() {
        return p_value
    }

    @Override
    void set_source(String i_source) {
        p_source = i_source
    }

    @Override
    String get_source() {
        return p_source
    }

}
