package com.a9ae0b01f0ffc.black_box.implementation

import com.a9ae0b01f0ffc.black_box.interfaces.*
import com.a9ae0b01f0ffc.black_box.main.T_logging_const
import com.a9ae0b01f0ffc.black_box.main.T_s
import com.a9ae0b01f0ffc.black_box.main.T_u
import com.a9ae0b01f0ffc.black_box_base.implementation.annotations.I_black_box

class T_trace extends T_inherited_configurations implements I_trace {

    Boolean p_muted = T_logging_const.GC_FALSE
    I_trace_formatter p_trace_formatter = T_logging_const.GC_NULL_OBJ_REF as I_trace_formatter
    String p_name = T_logging_const.GC_EMPTY_STRING
    Object p_ref = T_logging_const.GC_NULL_OBJ_REF
    String p_value = T_logging_const.GC_EMPTY_STRING
    String p_source = T_logging_const.GC_EMPTY_STRING
    String p_config_class = T_logging_const.GC_EMPTY_STRING

    @Override
    @I_black_box("error")
    Boolean is_muted() {
        return p_muted
    }

    @Override
    @I_black_box("error")
    Boolean is_masked() {
        if (p_mask == T_logging_const.GC_EMPTY_STRING || p_mask == T_logging_const.GC_FALSE_STRING || p_mask == T_logging_const.GC_TRACE_MASK_NONE) {
            return T_logging_const.GC_FALSE
        } else {
            return T_logging_const.GC_TRUE
        }
    }

    @Override
    @I_black_box("error")
    void set_muted(Boolean i_is_muted) {
        p_muted = i_is_muted
    }

    @Override
    @I_black_box("error")
    String format_trace(I_event i_source_event) {
        String l_result_string = T_logging_const.GC_EMPTY_STRING
        if (get_formatter() != T_logging_const.GC_NULL_OBJ_REF) {
            l_result_string += get_formatter().format_trace(this, i_source_event)
        } else {
            l_result_string += to_string()
        }
        return l_result_string
    }

    @Override
    @I_black_box("error")
    String toString() {
        String l_result_string = T_logging_const.GC_EMPTY_STRING
        l_result_string += to_string()
        return l_result_string
    }

    @I_black_box("error")
    private String unmasked() {
        return T_u.nvl(p_value, p_ref.toString())
    }

    @I_black_box("error")
    private String masked() {
        if (p_ref instanceof I_maskable) {
            if (p_value != T_logging_const.GC_EMPTY_STRING) {
                return T_s.c().GC_DEFAULT_TRACE_MASKED
//there is no control on how objects are serialized, thus it is unknown how to mask their serialized representation.
            } else {
                return ((I_maskable) p_ref).to_string_masked(p_mask)
            }
        } else {
            return T_s.c().GC_DEFAULT_TRACE_MASKED
        }
    }

    @I_black_box("error")
    private Boolean is_trace_missing() {
        return (p_ref == T_logging_const.GC_NULL_OBJ_REF && (p_value == T_logging_const.GC_NULL_OBJ_REF || p_value == T_logging_const.GC_EMPTY_STRING || p_value == T_s.c().GC_DEFAULT_TRACE))
    }

    @I_black_box("error")
    private String to_string() {
        if (is_trace_missing()) {
            return T_s.c().GC_DEFAULT_TRACE
        } else if (is_muted()) {
            return T_s.c().GC_DEFAULT_TRACE_MUTED
        } else if (is_masked()) {
            if (p_mask == T_logging_const.GC_TRACE_MASK_ALL || p_mask == T_logging_const.GC_TRUE_STRING) {
                masked()
            } else if (p_mask == T_logging_const.GC_TRACE_MASK_SENSITIVE) {
                if (p_ref instanceof I_sensitive) {
                    masked()
                } else {
                    return unmasked()
                }
            } else if (p_mask == T_logging_const.GC_TRACE_MASK_ALL_EXCEPT_NON_SENSITIVE) {
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
    @I_black_box("error")
    void set_formatter(I_trace_formatter i_trace_formatter) {
        p_trace_formatter = i_trace_formatter
    }

    @Override
    @I_black_box("error")
    I_trace_formatter get_formatter() {
        return p_trace_formatter
    }

    @Override
    @I_black_box("error")
    String get_name() {
        return p_name
    }

    @Override
    @I_black_box("error")
    String get_search_name_config() {
        String l_result = T_logging_const.GC_EMPTY_STRING
        if (get_ref() != T_logging_const.GC_NULL_OBJ_REF && p_config_class != T_logging_const.GC_EMPTY_STRING) {
            l_result = get_ref().getClass().getCanonicalName()
        } else if (get_ref() == T_logging_const.GC_NULL_OBJ_REF && p_config_class != T_logging_const.GC_EMPTY_STRING) {
            l_result = p_config_class
        } else {
            l_result = p_name
        }
        return l_result
    }

    @Override
    @I_black_box("error")
    String get_ref_class_name() {
        String l_result = T_logging_const.GC_EMPTY_STRING
        if (get_ref() != T_logging_const.GC_NULL_OBJ_REF) {
            l_result = get_ref().getClass().getCanonicalName()
        }
        return l_result
    }

    @Override
    @I_black_box("error")
    void set_name(String i_name) {
        p_name = i_name
    }

    @Override
    @I_black_box("error")
    Object get_ref() {
        return p_ref
    }

    @Override
    @I_black_box("error")
    void set_ref(Object i_ref) {
        p_ref = i_ref
    }

    @Override
    @I_black_box("error")
    void set_val(String i_value) {
        p_value = i_value
    }

    @Override
    @I_black_box("error")
    String get_val() {
        return p_value
    }

    @Override
    @I_black_box("error")
    void set_source(String i_source) {
        p_source = i_source
    }

    @Override
    @I_black_box("error")
    String get_source() {
        return p_source
    }

    @Override
    @I_black_box("error")
    void set_class(String i_class) {
        p_config_class = i_class
    }

    @Override
    @I_black_box("error")
    String get_config_class() {
        return p_config_class
    }

    @I_black_box("error")
    Boolean match_trace(I_trace i_trace_new) {
        return (get_search_name_config() == T_u.nvl(i_trace_new.get_ref_class_name(), i_trace_new.get_name()) || get_search_name_config() == i_trace_new.get_name())
    }

    @Override
    @I_black_box("error")
    String get_ref_guid() {
        if (p_ref instanceof I_object_with_guid) {
            return p_ref.get_guid()
        } else {
            return T_logging_const.GC_EMPTY_STRING
        }
    }

}
