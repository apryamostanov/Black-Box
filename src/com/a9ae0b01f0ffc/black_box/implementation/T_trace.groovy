package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.interfaces.I_event
import com.a9ae0b01f0ffc.black_box.interfaces.I_maskable
import com.a9ae0b01f0ffc.black_box.interfaces.I_non_sensitive
import com.a9ae0b01f0ffc.black_box.interfaces.I_object_with_guid
import com.a9ae0b01f0ffc.black_box.interfaces.I_sensitive
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace
import com.a9ae0b01f0ffc.black_box.interfaces.I_trace_formatter
import com.a9ae0b01f0ffc.black_box.main.T_const
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.black_box.main.T_u

class T_trace extends T_inherited_configurations implements I_trace {

    Boolean p_muted = T_const.GC_FALSE
    I_trace_formatter p_trace_formatter = T_const.GC_NULL_OBJ_REF as I_trace_formatter
    String p_name = T_const.GC_EMPTY_STRING
    Object p_ref = T_const.GC_NULL_OBJ_REF
    String p_value = T_const.GC_EMPTY_STRING
    String p_source = T_const.GC_EMPTY_STRING
    String p_config_class = T_const.GC_EMPTY_STRING

    @Override
    Boolean is_muted() {
        return p_muted
    }

    @Override
    Boolean is_masked() {
        if (p_mask == T_const.GC_EMPTY_STRING || p_mask == T_const.GC_FALSE_STRING || p_mask == T_const.GC_TRACE_MASK_NONE) {
            return T_const.GC_FALSE
        } else {
            return T_const.GC_TRUE
        }
    }

    @Override
    void set_muted(Boolean i_is_muted) {
        p_muted = i_is_muted
    }

    @Override
    String format_trace(I_event i_source_event) {
        String l_result_string = T_const.GC_EMPTY_STRING
        if (get_formatter() != T_const.GC_NULL_OBJ_REF) {
            l_result_string += get_formatter().format_trace(this, i_source_event)
        } else {
            l_result_string += to_string()
        }
        return l_result_string
    }

    @Override
    String toString() {
        String l_result_string = T_const.GC_EMPTY_STRING
        l_result_string += to_string()
        return l_result_string
    }

    private String unmasked() {
        return T_u.nvl(p_value, p_ref.toString())
    }

    private String masked() {
        if (p_ref instanceof I_maskable) {
            if (p_value != T_const.GC_EMPTY_STRING) {
                return T_s.c().GC_DEFAULT_TRACE_MASKED
//there is no control on how objects are serialized, thus it is unknown how to mask their serialized representation.
            } else {
                return ((I_maskable) p_ref).to_string_masked(p_mask)
            }
        } else {
            return T_s.c().GC_DEFAULT_TRACE_MASKED
        }
    }

    private Boolean is_trace_missing() {
        return (p_ref == T_const.GC_NULL_OBJ_REF && (p_value == T_const.GC_NULL_OBJ_REF || p_value == T_const.GC_EMPTY_STRING || p_value == T_s.c().GC_DEFAULT_TRACE))
    }

    private String to_string() {
        if (is_trace_missing()) {
            return T_s.c().GC_DEFAULT_TRACE
        } else if (is_muted()) {
            return T_s.c().GC_DEFAULT_TRACE_MUTED
        } else if (is_masked()) {
            if (p_mask == T_const.GC_TRACE_MASK_ALL || p_mask == T_const.GC_TRUE_STRING) {
                masked()
            } else if (p_mask == T_const.GC_TRACE_MASK_SENSITIVE) {
                if (p_ref instanceof I_sensitive) {
                    masked()
                } else {
                    return unmasked()
                }
            } else if (p_mask == T_const.GC_TRACE_MASK_ALL_EXCEPT_NON_SENSITIVE) {
                if (p_ref instanceof I_non_sensitive) {
                    return unmasked()
                } else {
                    return masked()
                }
            } else {
                return masked() // custom mask parameter, e.g. "last4digits"
            }
        } else {
            return unmasked()
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
    String get_search_name_config() {
        String l_result = T_const.GC_EMPTY_STRING
        if (get_ref() != T_const.GC_NULL_OBJ_REF && p_config_class != T_const.GC_EMPTY_STRING) {
            l_result = get_ref().getClass().getCanonicalName()
        } else if (get_ref() == T_const.GC_NULL_OBJ_REF && p_config_class != T_const.GC_EMPTY_STRING) {
            l_result = p_config_class
        } else {
            l_result = p_name
        }
        return l_result
    }

    @Override
    String get_ref_class_name() {
        String l_result = T_const.GC_EMPTY_STRING
        if (get_ref() != T_const.GC_NULL_OBJ_REF) {
            l_result = get_ref().getClass().getCanonicalName()
        }
        return l_result
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

    @Override
    void set_class(String i_class) {
        p_config_class = i_class
    }

    @Override
    String get_config_class() {
        return p_config_class
    }

    Boolean match_trace(I_trace i_trace_new) {
        return (get_search_name_config() == T_u.nvl(i_trace_new.get_ref_class_name(), i_trace_new.get_name()) || get_search_name_config() == i_trace_new.get_name())
    }

    @Override
    String get_ref_guid() {
        if (p_ref instanceof I_object_with_guid) {
            return p_ref.get_guid()
        } else {
            return T_const.GC_EMPTY_STRING
        }
    }

}
